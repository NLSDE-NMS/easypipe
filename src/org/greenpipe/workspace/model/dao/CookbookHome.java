package org.greenpipe.workspace.model.dao;

// Generated 2014-7-28 15:19:46 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenpipe.workspace.model.bean.Cookbook;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Cookbook.
 * @see org.greenpipe.workspace.model.bean.Cookbook
 * @author Hibernate Tools
 */
public class CookbookHome {

	private static final Log log = LogFactory.getLog(CookbookHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return new Configuration().configure("configuration/hibernate.cfg.xml").buildSessionFactory();
			//return (SessionFactory) new InitialContext()
			//		.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Cookbook transientInstance) {
		log.debug("persisting Cookbook instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Cookbook instance) {
		log.debug("attaching dirty Cookbook instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Cookbook instance) {
		log.debug("attaching clean Cookbook instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Cookbook persistentInstance) {
		log.debug("deleting Cookbook instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Cookbook merge(Cookbook detachedInstance) {
		log.debug("merging Cookbook instance");
		try {
			Cookbook result = (Cookbook) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Cookbook findById(java.lang.Integer id) {
		log.debug("getting Cookbook instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			Cookbook instance = (Cookbook) session.get("org.greenpipe.workspace.model.bean.Cookbook", id);
			trans.commit();

			//Cookbook instance = (Cookbook) sessionFactory.getCurrentSession()
			//		.get("org.greenpipe.workspace.model.bean.Cookbook", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Cookbook instance) {
		log.debug("finding Cookbook instance by example");
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			List results = session.createCriteria("org.greenpipe.workspace.model.bean.Cookbook")
					.add(Example.create(instance)).list();
			trans.commit();
			
			//List results = sessionFactory
			//		.getCurrentSession()
			//		.createCriteria(
			//				"org.greenpipe.workspace.model.bean.Cookbook")
			//		.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
