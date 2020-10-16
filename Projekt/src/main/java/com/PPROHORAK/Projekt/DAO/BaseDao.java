package com.PPROHORAK.Projekt.DAO;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

public class BaseDao< T extends Serializable> {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public <T> T save (final T o) { return (T) sessionFactory.getCurrentSession().save(o);}

    public void delete (final Object object) {
        sessionFactory.getCurrentSession().delete(object);}

        public <T> T get (final Class <T> type, final Long id) {return (T) sessionFactory.getCurrentSession().get(type,id);}

        public <T> T merge (final T o) {return (T) sessionFactory.getCurrentSession().merge(o);}

        public <T> void saveOrUpdate (final T o) {sessionFactory.getCurrentSession().saveOrUpdate(o);}

        public <T> List<T> getAll(final Class<T> type)
        {
            final Session session= sessionFactory.getCurrentSession();
            final Criteria criteria = session.createCriteria(type);
            return criteria.list();

        }


}
