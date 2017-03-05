package com.gene.utils;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Tool
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.gene.utils.Tool
 * @author MyEclipse Persistence Tools
 */
public class ToolDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ToolDAO.class);
	
	// property constants
	public static final String TOOL_NAME = "toolName";
	public static final String IS_SHARED = "isShared";
	public static final String SAVED_RESULTS = "savedResults";
	public static final String DESCRIBTION = "describtion";
	public static final String PARAMETERS = "parameters";
	
	
	public ToolDAO(){}

	public void save(Tool transientInstance) {
		log.debug("saving Tool instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tool persistentInstance) {
		log.debug("deleting Tool instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tool findById(java.lang.Integer id) {
		log.debug("getting Tool instance with id: " + id);
		try {
			Tool instance = (Tool) getSession().get("com.gene.utils.Tool", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tool instance) {
		log.debug("finding Tool instance by example");
		try {
			List results = getSession().createCriteria("com.gene.utils.Tool")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Tool instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tool as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByToolName(Object toolName) {
		return findByProperty(TOOL_NAME, toolName);
	}

	public List findByIsShared(Object isShared) {
		return findByProperty(IS_SHARED, isShared);
	}

	public List findBySavedResults(Object savedResults) {
		return findByProperty(SAVED_RESULTS, savedResults);
	}

	public List findByDescribtion(Object describtion) {
		return findByProperty(DESCRIBTION, describtion);
	}

	public List findByParameters(Object parameters) {
		return findByProperty(PARAMETERS, parameters);
	}

	public List findAll() {
		log.debug("finding all Tool instances");
		try {
			String queryString = "from Tool";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tool merge(Tool detachedInstance) {
		log.debug("merging Tool instance");
		try {
			Tool result = (Tool) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tool instance) {
		log.debug("attaching dirty Tool instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tool instance) {
		log.debug("attaching clean Tool instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}