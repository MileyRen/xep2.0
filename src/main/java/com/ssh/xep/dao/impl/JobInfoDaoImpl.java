package com.ssh.xep.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssh.xep.dao.JobInfoDao;
import com.ssh.xep.entity.JobFlowUser;
import com.ssh.xep.entity.JobInfo;

@Repository("jobInfoDao")
public class JobInfoDaoImpl implements JobInfoDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public JobInfo load(Integer id) {
		return (JobInfo) getSession().load(JobInfo.class, id);
	}

	public JobInfo get(Integer id) {
		return (JobInfo) getSession().get(JobInfo.class, id);
	}

	public List<JobInfo> findAll() {
		List<JobInfo> infos = getSession().createQuery("from JobInfo").list();
		return infos;
	}

	public void persist(JobInfo entity) {
		getSession().persist(entity);
	}

	public Integer save(JobInfo entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(JobInfo entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		JobInfo entity = load(id);
		getSession().delete(entity);
	}

	public void delete(List<Integer> ida) {
		StringBuilder sql = new StringBuilder("DELETE FROM JobInfo as j WHERE j.id in(-1");
		for(Integer id : ida) {
			sql.append(',');
			sql.append(id);
		}
		sql.append(")");
		getSession().createQuery(sql.toString()).executeUpdate();
	}

	public void flush() {
		getSession().flush();
	}

	public List<JobInfo> findAll(Integer userId) {
		List<JobInfo> infos = getSession().createQuery("from JobInfo as j where j.userId=?0").setInteger("0", userId).list();
		return infos;
	}

	public List<JobInfo> findAll(Integer userId, Integer flowBasicInfoId) {
		List<JobInfo> infos = getSession().createQuery("from JobInfo as j where j.userId=?0 and j.flowBasicInfoId=?1")
				.setInteger("0", userId).setInteger("1", flowBasicInfoId).list();
		return infos;
	}

	public List<JobFlowUser> findAllJoin(Integer userId, int lStart, int lEnd) {
		String sql = "SELECT\n" +
				"  j.id                 AS id,\n" +
				"  j.userId             AS userId,\n" +
				"  u.userName           AS userName,\n" +
				"  j.flowBasicInfoId    AS flowBasicInfoId,\n" +
				"  f.name               AS flowBasicInfoName,\n" +
//				"  j.bpmn               AS bpmn,\n" +
//				"  j.processInfo        AS processInfo,\n" +
				"  j.bgTime             AS bgTime,\n" +
				"  j.edTime             AS edTime\n" +
				"FROM JobInfo as j, FlowBasicInfo as f, User as u " +
				"WHERE j.flowBasicInfoId = f.id AND j.userId = u.id AND j.userId=?0 AND j.pId = ?1";
		List<Object[]> ol = getSession().createQuery(sql).setInteger("0", userId).setInteger("1", 0).setFirstResult(lStart).setMaxResults(lEnd).list();
		return convertToFList(ol);
	}

	public List<JobFlowUser> findAllJoin(Integer userId, Integer flowBasicInfoId, int lStart, int lEnd) {
		String sql = "SELECT\n" +
				"  j.id                 AS id,\n" +
				"  j.userId             AS userId,\n" +
				"  u.userName           AS userName,\n" +
				"  j.flowBasicInfoId    AS flowBasicInfoId,\n" +
				"  f.name               AS flowBasicInfoName,\n" +
//				"  j.bpmn               AS bpmn,\n" +
//				"  j.processInfo        AS processInfo,\n" +
				"  j.bgTime             AS bgTime,\n" +
				"  j.edTime             AS edTime\n" +
				"FROM JobInfo as j, FlowBasicInfo as f, User as u " +
				"WHERE j.flowBasicInfoId = f.id AND j.userId = u.id AND j.userId=?0 "+
				"AND j.flowBasicInfoId=?1 AND j.pId = ?2";
		List<Object[]> ol = getSession().createQuery(sql).setInteger("0", userId).setInteger("1", flowBasicInfoId).setInteger("2", 0).setFirstResult(lStart).setMaxResults(lEnd).list();
		return convertToFList(ol);
	}

	private List<JobFlowUser> convertToFList(List<Object[]> ol) {
		List<JobFlowUser> ret = new ArrayList<>(ol.size());
		for(Object[] os : ol) {
			JobFlowUser f = new JobFlowUser();
			f.setId((Integer) os[0]);
			f.setUserId((Integer) os[1]);
			f.setUserName((String) os[2]);
			f.setFlowBasicInfoId((Integer) os[3]);
			f.setFlowBasicInfoName((String) os[4]);
//			f.setBpmn((String) os[5]);
//			f.setProcessInfo((String) os[6]);
			f.setBgTime((Long) os[5]);
			f.setEdTime((Long) os[6]);
			ret.add(f);
		}
		return ret;
	}

	public int count() {
		String sql = "select count(*) from JobInfo as j where j.processInfo=null";
		long count = (long) getSession().createQuery(sql).uniqueResult();
		return (int) count;
	}
}
