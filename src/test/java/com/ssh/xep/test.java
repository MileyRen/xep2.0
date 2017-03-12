package com.ssh.xep;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gene.utils.User;
import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.impl.PathFormat;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-hibernate.xml" })
public class test {
	@Autowired
	private FolderService folderService;
	@Autowired
	private FolderDao FolderDao;

	@Test
	public void tes() throws FileNotFoundException {
		/*User user = new User();
		user.setId(1);
		String rootPath = PathFormat.strEnd("D:\\xeptest");
		String mappingpath = PathFormat.strEnd("D:\\xeptest")
				+ folderService.getAll("From FileAndFolder where userId=" + 1 + " and type='mapping'").get(0).getName();
		List<File> files = folderService.scanMappingPath(mappingpath);
		for (File file : files) {
			System.out.println(file.getPath());
			folderService.syncFiles(mappingpath, rootPath, file, user);
		}*/
		File file = new File("D:\\xeptest\\xeptest111\\folder_map\\mv1.mp4");
		file.length();
		System.out.println(file.length());

	}
	/*@Test
	public void teses(){
		FileAndFolder f2File = new FileAndFolder();
		f2File.setName("folder22");
		f2File.setParentFolderId(-1);
		f2File.setSize("");
		f2File.setTime(new Date());
		f2File.setType("folder");
		f2File.setUserId(1);
		FileAndFolder deleteFlag = folderService.getById(-1);
		f2File.setDeleteFlag(deleteFlag);
		folderService.createFolder(f2File);
	}*/
}
