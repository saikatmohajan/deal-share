package org.springframework.social.security.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.model.User;
import org.springframework.stereotype.Repository;

/**
 * Implements UserDAO using Hibernate and its Criteria API.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user) {
    	Logger.getLogger(getClass()).debug("Inside save()");
        sessionFactory.getCurrentSession().save(user);
    }

    public User findByUsername(String username) {
    	Logger.getLogger(getClass()).debug("Inside findByUsername()");
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

}
