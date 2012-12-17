package org.springframework.social.security.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.model.Socialuser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implements SocialuserDAO using Hibernate and its Criteria API.
 * 
 * Based on this class:
 * 
 * https://github.com/mschipperheyn/spring-social-jpa/blob/master/spring-social-jpa/src/main/java/org/springframework/social/connect/jpa/JpaConnectionRepository.java
 */
@Repository
@SuppressWarnings("unchecked")
public class SocialUserDAOImpl implements SocialUserDAO {

    private static final String USER_ID = "userId";
    private static final String PROVIDER_ID = "providerId";
    private static final String PROVIDER_USER_ID = "providerUserId";
    private static final String RANK = "rank";

    @Autowired
    SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    private Criteria createCriteria() {
        return getCurrentSession().createCriteria(Socialuser.class);
    }

    @Transactional(readOnly = false)
    public void save(Socialuser socialUser) {
    	//Transaction t = getCurrentSession().beginTransaction();
    	Logger.getLogger(getClass()).debug("Inside save() transactional");
        getCurrentSession().save(socialUser);
        //getCurrentSession().flush();
        //t.commit();
        Logger.getLogger(getClass()).debug("User ID immidiate After save() called : " + get(socialUser.getUserId(), socialUser.getProviderId(), socialUser.getProviderUserId()).getUserId());
    }

    public List<Socialuser> findByProviderId(String providerId) {
    	Logger.getLogger(getClass()).debug("Inside  findByProviderId()");
        return (List<Socialuser>) createCriteria().add(Restrictions.eq(PROVIDER_ID, providerId)).list();
    }

    public List<Socialuser> findByUserId(String userId) {
    	Logger.getLogger(getClass()).debug("Inside findByUserId()");
        return (List<Socialuser>) createCriteria().add(Restrictions.eq(USER_ID, userId)).list();
    }

    public List<Socialuser> findByUserIdAndProviderId(String userId, String providerId) {
    	Logger.getLogger(getClass()).debug("Inside findbyUserIdAndProviderId()");
        return (List<Socialuser>) createCriteria()
                .add(Restrictions.eq(USER_ID, userId))
                .add(Restrictions.eq(PROVIDER_ID, providerId))
                .list();
    }

    public List<Socialuser> findByUserIdAndProviderUserIds(String userId, MultiValueMap<String, String> providerUserIds) {
    	Logger.getLogger(getClass()).debug("Inside findByUserIdAndProviderUserIds()");
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq(USER_ID, userId));
        Disjunction or = Restrictions.disjunction();
        for (String providerId : providerUserIds.keySet()) {
            or.add(
                    Restrictions.and(
                            Restrictions.eq(PROVIDER_ID, providerId),
                            Restrictions.in(PROVIDER_USER_ID, providerUserIds.get(providerId))
                    )
            );
        }
        return (List<Socialuser>) criteria.list();
    }

    public Socialuser get(String userId, String providerId, String providerUserId) {
    	Logger.getLogger(getClass()).debug("Inside get()");
        return (Socialuser) createCriteria()
                .add(Restrictions.eq(USER_ID, userId))
                .add(Restrictions.eq(PROVIDER_ID, providerId))
                .add(Restrictions.eq(PROVIDER_USER_ID, providerUserId))
                .uniqueResult();
    }

    public List<Socialuser> findPrimaryByUserIdAndProviderId(String userId, String providerId) {
    	Logger.getLogger(getClass()).debug("Inside findPrimaryByUserIdAndProviderId()");
        return (List<Socialuser>) createCriteria()
                .add(Restrictions.eq(USER_ID, userId))
                .add(Restrictions.eq(PROVIDER_ID, providerId))
//        .add(Restrictions.eq("rank", 1))
                .addOrder(Order.asc(RANK))
                .list();
    }

    public Integer selectMaxRankByUserIdAndProviderId(String userId, String providerId) {
    	Logger.getLogger(getClass()).debug("Inside selectMaxRankByUserIdAndProviderId()");
        return (Integer) createCriteria()
                .add(Restrictions.eq(USER_ID, userId))
                .add(Restrictions.eq(PROVIDER_ID, providerId))
                .setProjection(Projections.max(RANK))
                .uniqueResult();
    }

    public List<String> findUserIdsByProviderIdAndProviderUserId(String providerId, String providerUserId) {
    	Logger.getLogger(getClass()).debug("Inside findUserIdsByProviderIdAndProviderUserId()");
        List<Socialuser> socialUsers = (List<Socialuser>) createCriteria()
                .add(Restrictions.eq(PROVIDER_ID, providerId))
                .add(Restrictions.eq(PROVIDER_USER_ID, providerUserId))
                .list();
        Logger.getLogger(getClass()).debug("Number of users : " + socialUsers.size());
        List<String> userIds = new ArrayList<String>(socialUsers.size());
        for (Socialuser socialUser : socialUsers) {
        	Logger.getLogger(getClass()).debug("Inside for loop iteration in method findUserIdsByProviderIdAndProviderUserId !!");
            userIds.add(socialUser.getUserId());
        }
        return userIds;
    }

    public List<String> findUserIdsByProviderIdAndProviderUserIds(String providerId, Set<String> providerUserIds) {
    	Logger.getLogger(getClass()).debug("Inside findUserIdsByProviderIdAndProviderUserIds()");
        List<Socialuser> socialUsers = (List<Socialuser>) createCriteria()
                .add(Restrictions.eq(PROVIDER_ID, providerId))
                .add(Restrictions.in(PROVIDER_USER_ID, providerUserIds))
                .list();
        List<String> userIds = new ArrayList<String>(socialUsers.size());
        for (Socialuser socialUser : socialUsers) {
            userIds.add(socialUser.getUserId());
        }
        return userIds;
    }

    public void delete(Socialuser socialUser) {
    	Logger.getLogger(getClass()).debug("Inside delete()");
        getCurrentSession().delete(socialUser);
    }

}
