package com.ssh.xep.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.FileDao;
import com.xeq.file.domain.FileAndFolder;

@Repository("fileDao")
public class FileDaoImpl implements FileDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public FileAndFolder load(Integer id) {
		return (FileAndFolder) getSession().load(FileAndFolder.class, id);
	}

	public FileAndFolder get(Integer id) {
		return (FileAndFolder) getSession().get(FileAndFolder.class, id);
	}

	public List<FileAndFolder> findAll() {
		List<FileAndFolder> infos = getSession().createQuery("from FileAndFolder").list();
		return infos;
	}

	public void persist(FileAndFolder entity) {
		getSession().persist(entity);
	}

	public Integer save(FileAndFolder entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(FileAndFolder entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		FileAndFolder entity = load(id);
		getSession().delete(entity);
	}

	public void flush() {
		getSession().flush();
	}

	public List<FileAndFolder> findAll(Integer userId) {
		List<FileAndFolder> infos = getSession().createQuery("from FileAndFolder where userId=?0").setInteger("0", userId)
				.list();
		return infos;
	}

	public List<FileAndFolder> findAll(Integer userId, String type) {
		List<FileAndFolder> infos = getSession().createQuery("from FileAndFolder where userId=?0 and type=?1").setInteger("0", userId)
				.setString("1", type).list();
		return infos;
	}

}
