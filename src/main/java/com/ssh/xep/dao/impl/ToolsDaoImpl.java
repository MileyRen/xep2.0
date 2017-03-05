package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.ToolsDao;
import com.ssh.xep.entity.Tools;

@Repository("toolsDao")
public class ToolsDaoImpl implements ToolsDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public Tools load(Integer id) {
		return (Tools) getSession().load(Tools.class, id);
	}

	public Tools get(Integer id) {
		return (Tools) getSession().get(Tools.class, id);
	}

	public List<Tools> findAll() {
		List<Tools> infos = getSession().createQuery("from Tools").list();
		return infos;
	}

	public void persist(Tools entity) {
		getSession().persist(entity);
	}

	public Integer save(Tools entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(Tools entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		Tools entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<Tools> findAll(Integer userId) {
		List<Tools> infos = getSession().createQuery("from Tools where userId=?0").setInteger("0", userId)
				.list();
		return infos;
	}

}
