package org.greenpipe.workspace.model.dao;

// Generated 2014-7-28 15:19:46 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenpipe.workspace.model.bean.Vm;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Vm.
 * @see org.greenpipe.workspace.model.bean.Vm
 * @author Hibernate Tools
 */
public class VmHome {

	private static final Log log = LogFactory.getLog(VmHome.class);

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

	public void persist(Vm transientInstance) {
		log.debug("persisting Vm instance");
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

	public void attachDirty(Vm instance) {
		log.debug("attaching dirty Vm instance");
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

	public void attachClean(Vm instance) {
		log.debug("attaching clean Vm instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Vm persistentInstance) {
		log.debug("deleting Vm instance");
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			session.delete(persistentInstance);
			trans.commit();
			
			//sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Vm merge(Vm detachedInstance) {
		log.debug("merging Vm instance");
		try {
			Vm result = (Vm) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Vm findById(java.lang.Integer id) {
		log.debug("getting Vm instance with id: " + id);
		try {
			Vm instance = (Vm) sessionFactory.getCurrentSession().get(
					"org.greenpipe.workspace.model.bean.Vm", id);
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

	public List findByExample(Vm instance) {
		log.debug("finding Vm instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("org.greenpipe.workspace.model.bean.Vm")
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
