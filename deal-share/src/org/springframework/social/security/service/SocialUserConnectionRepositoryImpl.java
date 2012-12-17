package org.springframework.social.security.service;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.social.security.dao.SocialUserDAO;
import org.springframework.social.security.model.Socialuser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public class SocialUserConnectionRepositoryImpl implements ConnectionRepository {

    private String userId;
    private SocialUserDAO socialUserDAO;
    private ConnectionFactoryLocator connectionFactoryLocator;
    private TextEncryptor textEncryptor;

    public SocialUserConnectionRepositoryImpl(String userId,
                                              SocialUserDAO socialUserDAO,
                                              ConnectionFactoryLocator connectionFactoryLocator,
                                              TextEncryptor textEncryptor) {
        this.userId = userId;
        this.socialUserDAO = socialUserDAO;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    public MultiValueMap<String, Connection<?>> findAllConnections() {
    	Logger.getLogger(getClass()).debug("Inside findAllConnections()");
        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();

        List<Socialuser> allSocialUsers = socialUserDAO.findByUserId(userId);
        for (Socialuser socialUser : allSocialUsers) {
            ConnectionData connectionData = toConnectionData(socialUser);
            Connection<?> connection = createConnection(connectionData);
            connections.add(connectionData.getProviderId(), connection);
        }

        return connections;
    }

    public List<Connection<?>> findConnections(String providerId) {
    	Logger.getLogger(getClass()).debug("Inside findConnections() by providerId");
        List<Connection<?>> connections = new ArrayList<Connection<?>>();

        List<Socialuser> socialUsers = socialUserDAO.findByUserIdAndProviderId(userId, providerId);
        for (Socialuser socialUser : socialUsers) {
            ConnectionData connectionData = toConnectionData(socialUser);
            Connection<?> connection = createConnection(connectionData);
            connections.add(connection);
        }

        return connections;
    }

    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
    	Logger.getLogger(getClass()).debug("Inside findConnections() by apiType");
        String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();

        // do some lame stuff to make the casting possible
        List<?> connections = findConnections(providerId);
        return (List<Connection<A>>) connections;
    }

    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
    	Logger.getLogger(getClass()).debug("Inside findConnectionsToUsers() by providerUserIds");
        MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();

        List<Socialuser> allSocialUsers = socialUserDAO.findByUserIdAndProviderUserIds(userId, providerUserIds);
        for (Socialuser socialUser : allSocialUsers) {
            ConnectionData connectionData = toConnectionData(socialUser);
            Connection<?> connection = createConnection(connectionData);
            connections.add(connectionData.getProviderId(), connection);
        }

        return connections;
    }

    public Connection<?> getConnection(ConnectionKey connectionKey) {
    	Logger.getLogger(getClass()).debug("Inside getConnection() by connectionKey");
        Socialuser socialUser = socialUserDAO.get(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        if (socialUser == null) {
            throw new NoSuchConnectionException(connectionKey);
        }
        return createConnection(toConnectionData(socialUser));
    }

    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
    	Logger.getLogger(getClass()).debug("Inside getConnection() by providerUserId");
        String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
        Socialuser socialUser = socialUserDAO.get(userId, providerId, providerUserId);
        if (socialUser == null) {
            throw new NoSuchConnectionException(new ConnectionKey(providerId, providerUserId));
        }
        return (Connection<A>) createConnection(toConnectionData(socialUser));
    }

    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
    	Logger.getLogger(getClass()).debug("Inside getPrimaryConnection()");
        Connection<A> connection = findPrimaryConnection(apiType);
        if (connection == null) {
            String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
    	Logger.getLogger(getClass()).debug("Inside findPrimaryConnection()");
        String providerId = connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();

        List<Socialuser> socialUsers = socialUserDAO.findPrimaryByUserIdAndProviderId(userId, providerId);
        Connection<A> connection = null;
        if (socialUsers != null && !socialUsers.isEmpty()) {
            connection = (Connection<A>) createConnection(toConnectionData(socialUsers.get(0)));
        }

        return connection;
    }

    @Transactional(readOnly = false)
    public void addConnection(Connection<?> connection) {
    	Logger.getLogger(getClass()).debug("Inside addConnection()");
        ConnectionData connectionData = connection.createData();
        
        // check if this social account is already connected to a local account
        List<String> userIds = socialUserDAO.findUserIdsByProviderIdAndProviderUserId(
                connectionData.getProviderId(), connectionData.getProviderUserId()
        );
        if (!userIds.isEmpty()) {
            throw new DuplicateConnectionException(new ConnectionKey(connectionData.getProviderId(), connectionData.getProviderUserId()));
        }
        //check if this user already has a connected account for this provider
        List<Socialuser> socialUsers = socialUserDAO.findByUserIdAndProviderId(userId, connectionData.getProviderId());
        if (!socialUsers.isEmpty()) {
            throw new DuplicateConnectionException(new ConnectionKey(connectionData.getProviderId(), connectionData.getProviderUserId()));
        }

        Integer maxRank = socialUserDAO.selectMaxRankByUserIdAndProviderId(userId, connectionData.getProviderId());
        int nextRank = (maxRank == null ? 1 : maxRank + 1);

        Socialuser socialUser = new Socialuser();
        socialUser.setUserId(userId);
        socialUser.setProviderId(connectionData.getProviderId());
        socialUser.setProviderUserId(connectionData.getProviderUserId());
        socialUser.setRank(nextRank);
        socialUser.setDisplayName(connectionData.getDisplayName());
        socialUser.setProfileUrl(connectionData.getProfileUrl());
        socialUser.setImageUrl(connectionData.getImageUrl());

        // encrypt these values
        socialUser.setAccessToken(encrypt(connectionData.getAccessToken()));
        socialUser.setSecret(encrypt(connectionData.getSecret()));
        socialUser.setRefreshToken(encrypt(connectionData.getRefreshToken()));

        //socialUser.setExpireTime(connectionData.getExpireTime().toString());

        try {
        	Logger.getLogger(SocialUserConnectionRepositoryImpl.class).debug("Inside try block method()");
            socialUserDAO.save(socialUser);
            Logger.getLogger(SocialUserConnectionRepositoryImpl.class).debug("Number of Users after save : " + socialUserDAO.findByUserId(userId).size());
        } catch (DataIntegrityViolationException e) {
        	Logger.getLogger(SocialUserConnectionRepositoryImpl.class).debug("Inside catch block addConnection method()");
            throw new DuplicateConnectionException(new ConnectionKey(connectionData.getProviderId(), connectionData.getProviderUserId()));
        }
    }

    @Transactional(readOnly = false)
    public void updateConnection(Connection<?> connection) {
    	Logger.getLogger(getClass()).debug("Inside update()");
        ConnectionData connectionData = connection.createData();
        Socialuser socialUser = socialUserDAO.get(userId, connectionData.getProviderId(), connectionData.getProviderUserId());
        if (socialUser != null) {
            socialUser.setDisplayName(connectionData.getDisplayName());
            socialUser.setProfileUrl(connectionData.getProfileUrl());
            socialUser.setImageUrl(connectionData.getImageUrl());

            socialUser.setAccessToken(encrypt(connectionData.getAccessToken()));
            socialUser.setSecret(encrypt(connectionData.getSecret()));
            socialUser.setRefreshToken(encrypt(connectionData.getRefreshToken()));

            //socialUser.setExpireTime(connectionData.getExpireTime().toString());
            socialUserDAO.save(socialUser);
        }
    }

    @Transactional(readOnly = false)
    public void removeConnections(String providerId) {
        // TODO replace with bulk delete HQL
    	Logger.getLogger(getClass()).debug("Inside removeConnections() by provideId");
        List<Socialuser> socialUsers = socialUserDAO.findByUserIdAndProviderId(userId, providerId);
        for (Socialuser socialUser : socialUsers) {
            socialUserDAO.delete(socialUser);
        }
    }

    @Transactional(readOnly = false)
    public void removeConnection(ConnectionKey connectionKey) {
    	Logger.getLogger(getClass()).debug("Inside removeConnections() by connectionKey");
        Socialuser socialUser = socialUserDAO.get(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
        if (socialUser != null) {
            socialUserDAO.delete(socialUser);
        }
    }

    private ConnectionData toConnectionData(Socialuser socialUser) {
    	Logger.getLogger(SocialUserConnectionRepositoryImpl.class).debug("Inside toConnectionData() !!");
    	String accessToken = decrypt(socialUser.getAccessToken());
    	String secretToken = decrypt(socialUser.getSecret());
    	String refreshToken = decrypt(socialUser.getRefreshToken());
        return new ConnectionData(socialUser.getProviderId(),
                socialUser.getProviderUserId(),
                socialUser.getDisplayName(),
                socialUser.getProfileUrl(),
                socialUser.getImageUrl(),

                accessToken,
                secretToken,
                refreshToken,
                null);
                //convertZeroToNull(Long.parseLong(socialUser.getExpireTime())));
    }

    private Connection<?> createConnection(ConnectionData connectionData) {
    	Logger.getLogger(getClass()).debug("Inside createConnection()");
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
        return connectionFactory.createConnection(connectionData);
    }

    private Long convertZeroToNull(Long expireTime) {
    	
        return (expireTime != null && expireTime == 0 ? null : expireTime);
    }

    private String decrypt(String encryptedText) {
    	Logger.getLogger(getClass()).debug("Inside decrypt()");
        return (textEncryptor != null && encryptedText != null) ? textEncryptor.decrypt(encryptedText) : encryptedText;
    }

    private String encrypt(String text) {
    	Logger.getLogger(getClass()).debug("Inside encrypt()");
        return (textEncryptor != null && text != null) ? textEncryptor.encrypt(text) : text;
    }
}
