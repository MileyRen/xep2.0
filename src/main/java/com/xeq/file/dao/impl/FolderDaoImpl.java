package com.xeq.file.dao.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xeq.file.dao.FolderDao;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;

@Repository("FolderDao")
public class FolderDaoImpl extends BaseDao implements FolderDao {
	private Logger log = Logger.getLogger(FolderDaoImpl.class);

	@Override
	public String parentPath(Integer parentFolderId, Stack<FileAndFolder> folderStack, String rootPath) {
		
		String path = PathFormat.strEnd(rootPath);

		for (FileAndFolder fgr : folderStack) {
			try {
				path = PathFormat.strEnd(path + fgr.getName());
			} catch (Exception e) {
				log.info(e.getStackTrace());
				break;
			}
		}
		return path;
	}

	@Override
	public FileAndFolder getById(Integer Id) {

		String hql = "from FileAndFolder where id=:id";
		FileAndFolder fileAndFolder = (FileAndFolder) getSession().createQuery(hql).setInteger("id", Id).uniqueResult();
		return fileAndFolder;
	}

	@Override
	public int delete(Integer id) {
		String hql = "delete FileAndFolder as f where f.id=?";
		int ret = getSession().createQuery(hql).setInteger(0, id).executeUpdate();
		return ret;
	}

	@Override
	public void deleteFolder(FileAndFolder folder) {
		getSession().delete(folder);
	}

	@Override
	public int saveFileAndFolder(FileAndFolder fileAndFolder) {
		return (int) getSession().save(fileAndFolder);
	}

	@Override
	public List<FileAndFolder> pageReviwe(PageSource page, String hql) {
		Query query = getSession().createQuery(hql);
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List<FileAndFolder> list = query.list();
		for (FileAndFolder fileAndFolder : list) {
			try {
				fileAndFolder.setName(URLDecoder.decode(fileAndFolder.getName(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return list;

	}


	@Override
	public List<FileAndFolder> getAll(String hql) {
		Query query = getSession().createQuery(hql);
		List<FileAndFolder> list = query.list();
		return list;
	}

	@Override
	public void update(FileAndFolder obg) {
		Session session = getSession();
		session.saveOrUpdate(obg);
	}

	@Override
	public int createFolder(FileAndFolder fgr) {
		return (int) getSession().save(fgr);
	}
}
