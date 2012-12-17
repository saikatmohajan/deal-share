package org.springframework.social.security.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.security.dao.SocialUserDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public class SocialUserServiceImpl implements SocialUserService {

    @Autowired
    private SocialUserDAO socialUserDAO;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;
    private TextEncryptor textEncryptor;
    @Value("${social.crypto.password}")
    private String encryptionPassword;
    @Value("${social.crypto.enabled:false}")
    private boolean encryptCredentials;

    @PostConstruct
    public void initializeTextEncryptor() {
    	Logger.getLogger(getClass()).debug("Inside initializeTextEncryptor()");
        textEncryptor = Encryptors.text(encryptionPassword, KeyGenerators.string().generateKey());
    }

    public List<String> findUserIdsWithConnection(Connection<?> connection) {
    	Logger.getLogger(getClass()).debug("Inside findUserIdsWithConnection()");
        ConnectionKey key = connection.getKey();
        List<String> localUserIds = socialUserDAO.findUserIdsByProviderIdAndProviderUserId(key.getProviderId(), key.getProviderUserId());
        return localUserIds;
    }

    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
    	Logger.getLogger(getClass()).debug("Inside findUserIdsConnectedTo()");
    	HashSet<String> userIdsConnectedTo = new HashSet<String>(socialUserDAO.findUserIdsByProviderIdAndProviderUserIds(providerId, providerUserIds));
    	Logger.getLogger(getClass()).debug("Number of User IDs connected to : " + userIdsConnectedTo.size());
        return userIdsConnectedTo;
    }

    public ConnectionRepository createConnectionRepository(String userId) {
    	Logger.getLogger(getClass()).debug("Inside createConnectionRepository()");
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new SocialUserConnectionRepositoryImpl(
                userId,
                socialUserDAO,
                connectionFactoryLocator,
                (encryptCredentials ? textEncryptor : null)
        );
    }
}
