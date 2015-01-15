package org.greenpipe.workspace.model.dao;

// Generated 2014-7-28 15:19:46 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenpipe.workspace.model.bean.Workspace;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

/**
 * Home object for domain model class Workspace.
 * @see org.greenpipe.workspace.model.bean.Workspace
 * @author Hibernate Tools
 */
public class WorkspaceHome {

	private static final Log log = LogFactory.getLog(WorkspaceHome.class);

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

	public void persist(Workspace transientInstance) {
		log.debug("persisting Workspace instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			session.persist(transientInstance);
			trans.commit();

			//sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Workspace instance) {
		log.debug("attaching dirty Workspace instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			session.saveOrUpdate(instance);
			trans.commit();

			//sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Workspace instance) {
		log.debug("attaching clean Workspace instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Workspace persistentInstance) {
		log.debug("deleting Workspace instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Workspace merge(Workspace detachedInstance) {
		log.debug("merging Workspace instance");
		try {
			Workspace result = (Workspace) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Workspace findById(java.lang.Integer id) {
		log.debug("getting Workspace instance with id: " + id);
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			Workspace instance = (Workspace) session.get("org.greenpipe.workspace.model.bean.Workspace", id);
			trans.commit();

			//Workspace instance = (Workspace) sessionFactory.getCurrentSession()
			//		.get("org.greenpipe.workspace.model.bean.Workspace", id);
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

	public List findByExample(Workspace instance) {
		log.debug("finding Workspace instance by example");
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			List results = session.createCriteria("org.greenpipe.workspace.model.bean.Workspace")
					.add(Example.create(instance)).list();
			trans.commit();

			//List results = sessionFactory
			//		.getCurrentSession()
			//		.createCriteria(
			//				"org.greenpipe.workspace.model.bean.Workspace")
			//		.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByExampleUser(Workspace instance) {
		log.debug("finding Workspace instance by example");
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			Example example = Example.create(instance).excludeZeroes();
			Criteria criteria = session.createCriteria("org.greenpipe.workspace.model.bean.Workspace");
			criteria.add(example);
			criteria.add(Restrictions.eq("user", instance.getUser()));
			List results = criteria.list();
			trans.commit();

			//List results = sessionFactory
			//		.getCurrentSession()
			//		.createCriteria(
			//				"org.greenpipe.workspace.model.bean.Workspace")
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
