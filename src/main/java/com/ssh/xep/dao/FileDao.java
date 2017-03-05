package com.ssh.xep.dao;

import java.util.List;

import com.xeq.file.domain.FileAndFolder;

public interface FileDao extends GenericDao<FileAndFolder, Integer> {
	List<FileAndFolder> findAll(Integer userId);
	List<FileAndFolder> findAll(Integer userId, String type);
}
