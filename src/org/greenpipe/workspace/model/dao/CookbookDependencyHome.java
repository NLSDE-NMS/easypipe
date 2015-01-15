package org.greenpipe.workspace.model.dao;

// Generated 2014-7-28 15:19:46 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenpipe.workspace.model.bean.CookbookDependency;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class CookbookDependency.
 * @see org.greenpipe.workspace.model.bean.CookbookDependency
 * @author Hibernate Tools
 */
public class CookbookDependencyHome {

	private static final Log log = LogFactory
			.getLog(CookbookDependencyHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(CookbookDependency transientInstance) {
		log.debug("persisting CookbookDependency instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(CookbookDependency instance) {
		log.debug("attaching dirty CookbookDependency instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CookbookDependency instance) {
		log.debug("attaching clean CookbookDependency instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(CookbookDependency persistentInstance) {
		log.debug("deleting CookbookDependency instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CookbookDependency merge(CookbookDependency detachedInstance) {
		log.debug("merging CookbookDependency instance");
		try {
			CookbookDependency result = (CookbookDependency) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public CookbookDependency findById(java.lang.Integer id) {
		log.debug("getting CookbookDependency instance with id: " + id);
		try {
			CookbookDependency instance = (CookbookDependency) sessionFactory
					.getCurrentSession()
					.get("org.greenpipe.workspace.model.bean.CookbookDependency",
							id);
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

	public List findByExample(CookbookDependency instance) {
		log.debug("finding CookbookDependency instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"org.greenpipe.workspace.model.bean.CookbookDependency")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
