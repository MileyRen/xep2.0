package com.ssh.xep.dao.impl;

import com.ssh.xep.dao.FlowBasicInfoDao;
import com.ssh.xep.entity.FlowBasicInfo;
import com.ssh.xep.entity.FlowGroupUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("flowBasicInfoDao")
public class FlowBasicInfoDaoImpl implements FlowBasicInfoDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public FlowBasicInfo load(Integer id) {
		return (FlowBasicInfo) getSession().load(FlowBasicInfo.class, id);
	}

	public FlowBasicInfo get(Integer id) {
		return (FlowBasicInfo) getSession().get(FlowBasicInfo.class, id);
	}

	public List<FlowBasicInfo> findAll() {
//		return (List<FlowBasicInfo>) getSession().createQuery("from FlowBasicInfo").list();
		return null;
	}

	public long count() {
		String sql = "select count(*) from FlowBasicInfo as f";
		return (long) getSession().createQuery(sql).uniqueResult();
	}

	public List<FlowBasicInfo> findAll(Integer userId, String startDate, String endDate) {
		String sql = "from FlowBasicInfo as f where 1=1 and (f.userId=?0) ";
		sql += " and (f.createDate between ?1 and ?2)";
		return (List<FlowBasicInfo>) getSession().createQuery(sql).setInteger("0", userId).setString("1", startDate).setString("2", endDate).list();
	}

	public List<FlowBasicInfo> findAll(Integer userId, int[] auths, String startDate, String endDate) {
		String sql = "from FlowBasicInfo where 1=1 and (userId=?0 ";
		if (auths != null && auths.length != 0) {
			sql += "or auth in (";
			for (int auth : auths) {
				sql += auth;
				sql += ",";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += ")";
		}
		sql += ")";
		sql += " and (createDate between ?1 and ?2)";
		return (List<FlowBasicInfo>) getSession().createQuery(sql).setInteger("0", userId).setString("1", startDate).setString("2", endDate).list();
	}

	@Override
	public List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, int lStart, int lEnd, int auth) {
		String sql = "SELECT\n" +
				"  f.id         AS id,\n" +
				"  f.name       AS name,\n" +
				"  u.id         AS userId,\n" +
				"  u.userName   AS userName,\n" +
				"  f.flowNum    AS flowNum,\n" +
				"  f.flow       AS flow,\n" +
				"  f.auth       AS auth,\n" +
				"  g.id	        AS groupId,\n" +
				"  g.name       AS groupName,\n" +
				"  f.createDate AS createDate\n" +
				"FROM FlowBasicInfo as f, FlowGroupInfo as g, User as u " +
				"WHERE f.groupId = g.id AND f.userId = u.id AND f.auth=?0 AND\n" +
				"      (f.userId = ?1 ";
		if (auths != null && auths.length != 0) {
			sql += "or f.auth in (";
			for (int a : auths) {
				sql += a;
				sql += ",";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += ")";
		}
		sql += ")";
		sql += " and (f.createDate between ?2 and ?3)";
		List<Object[]> ol = getSession().createQuery(sql).setInteger("1", userId).setInteger("0", auth).setString("2", startDate).setString("3", endDate).setMaxResults(lEnd).setFirstResult(lStart).list();
		return convertToFList(ol);
	}

	private List<FlowGroupUser> convertToFList(List<Object[]> ol) {
		List<FlowGroupUser> ret = new ArrayList<>(ol.size());
		for(Object[] os : ol) {
			FlowGroupUser f = new FlowGroupUser();
			f.setId((Integer) os[0]);
			f.setName((String) os[1]);
			f.setUserId((Integer) os[2]);
			f.setUserName((String) os[3]);
			f.setFlowNum((Integer) os[4]);
			f.setFlow((String) os[5]);
			f.setAuth((Short) os[6]);
			f.setGroupId((Integer) os[7]);
			f.setGroupName((String) os[8]);
			f.setCreateDate((String) os[9]);
			ret.add(f);
		}
		return ret;
	}

	@Override
	public List<FlowGroupUser> findAllJoin(Integer userId, int[] auths, String startDate, String endDate, int lStart, int lEnd) {
		String sql = "SELECT\n" +
				"  f.id         AS id,\n" +
				"  f.name       AS name,\n" +
				"  u.id       	AS userId,\n" +
				"  u.userName   AS userName,\n" +
				"  f.flowNum    AS flowNum,\n" +
				"  f.flow       AS flow,\n" +
				"  f.auth       AS auth,\n" +
				"  g.id	        AS groupId,\n" +
				"  g.name       AS groupName,\n" +
				"  f.createDate AS createDate\n" +
				"FROM FlowBasicInfo as f, FlowGroupInfo as g, User as u\n" +
				"WHERE f.groupId = g.id AND f.userId = u.id AND 1 = 1 AND\n" +
				"      (f.userId = ?0 ";
		if (auths != null && auths.length != 0) {
			sql += "or f.auth in (";
			for (int a : auths) {
				sql += a;
				sql += ",";
			}
			if (sql.endsWith(",")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += "))";
		}
		sql += " and (f.createDate between ?1 and ?2)";
		System.out.println(sql);
		List<Object[]> ol = getSession().createQuery(sql).setInteger("0", userId).setString("1", startDate).setString("2", endDate).setMaxResults(lEnd).setFirstResult(lStart).list();
		return convertToFList(ol);
	}

	public void persist(FlowBasicInfo entity) {
		getSession().persist(entity);
	}

	public Integer save(FlowBasicInfo entity) {
		return (Integer) getSession().save(entity);
	}

	public void saveOrUpdate(FlowBasicInfo entity) {
		getSession().saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		FlowBasicInfo entity = load(id);
		getSession().delete(entity);
	}

	public void delete(List<Integer> ids) {
		StringBuilder sql = new StringBuilder("DELETE FROM FlowBasicInfo as f WHERE f.id in(-1");
		for(Integer id : ids) {
			sql.append(',');
			sql.append(id);
		}
		sql.append(")");
		getSession().createQuery(sql.toString()).executeUpdate();
	}

	public void flush() {
		getSession().flush();
	}

}
