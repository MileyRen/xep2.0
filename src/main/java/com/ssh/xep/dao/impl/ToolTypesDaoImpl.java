package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.ToolTypesDao;
import com.ssh.xep.entity.ToolTypes;

@Repository("toolTypesDao")
public class ToolTypesDaoImpl implements ToolTypesDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public ToolTypes load(Integer id) {
		return (ToolTypes) getSession().load(ToolTypes.class, id);
	}

	public ToolTypes get(Integer id) {
		return (ToolTypes) getSession().get(ToolTypes.class, id);
	}

	public List<ToolTypes> findAll() {
		List<ToolTypes> infos = getSession().createQuery("from ToolTypes").list();
		return infos;
	}

	public void persist(ToolTypes entity) {
		getSession().persist(entity);
	}

	public Integer save(ToolTypes entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(ToolTypes entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		ToolTypes entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<ToolTypes> findAll(Integer userId) {
		List<ToolTypes> infos = getSession().createQuery("from ToolTypes where addedUserId=?0").setInteger("0", userId)
				.list();
		return infos;
	}

}
