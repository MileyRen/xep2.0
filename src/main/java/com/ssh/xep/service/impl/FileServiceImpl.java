package com.ssh.xep.service.impl;

import com.ssh.xep.dao.FileDao;
import com.xeq.file.domain.FileAndFolder;
import com.ssh.xep.entity.TreeList;
import com.ssh.xep.service.FileService;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.util.LinkedList;
import java.util.List;

@Service("fileService")
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDao dao;

	@Deprecated
	public FileAndFolder load(Integer id) {
		return dao.load(id);
	}

	public FileAndFolder get(Integer id) throws DocumentException {
		return dao.get(id);
	}

	public List<FileAndFolder> findAll() throws DocumentException {
		return dao.findAll();
	}

	public List<TreeList> findAll(Integer userId) throws DocumentException {
		List<FileAndFolder> files = dao.findAll(userId);
		List<TreeList> treeList = new LinkedList<>();
		combineTreeList(treeList, files, -1);
		return treeList;
	}

	public List<TreeList> findAll(Integer userId, String type) throws DocumentException {
		List<FileAndFolder> files = dao.findAll(userId, type);
		List<TreeList> treeList = new LinkedList<>();
		combineTreeList(treeList, files, -1);
		return treeList;
	}

	private void combineTreeList(List<TreeList> treeList, List<FileAndFolder> files, int pId) {
		for(FileAndFolder file : files) {
			if(file.getParentFolderId() == pId) {
				TreeList t = new TreeList();
				t.setId(file.getId());
				t.setName(file.getName());
				t.setFolderId(file.getParentFolderId());
				t.setType(file.getType());
				if(t.getType().equals("folder")) {
					t.setChildren(new LinkedList<>());
					combineTreeList(t.getChildren(), files, file.getId());
				}
				treeList.add(t);
			}
		}
	}

	public void pessist(FileAndFolder entity) {
		dao.persist(entity);
	}

	public Integer save(FileAndFolder entity) throws ParserConfigurationException {
		return dao.save(entity);
	}

	public void saveOrUpdate(FileAndFolder entity) throws ParserConfigurationException {
		dao.saveOrUpdate(entity);
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	public void flush() {
		dao.flush();
	}

}
