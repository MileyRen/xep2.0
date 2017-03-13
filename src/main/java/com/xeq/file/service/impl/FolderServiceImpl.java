package com.xeq.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gene.utils.User;
import com.xeq.file.dao.FolderDao;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.dao.impl.BaseDao;
import com.xeq.file.dao.impl.PathFormat;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.domain.PageSource;
import com.xeq.file.service.FolderService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("FolderService")
public class FolderServiceImpl extends BaseDao implements FolderService {

	@Autowired
	private FolderDao folderDao;
	@Autowired
	private FolderOperate folderOperate;

	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}

	// @Override
	// public List<FileAndFolder> getByFolderOrFiles(Integer userId, Integer
	// parentFolderId) {
	// return folderDao.getByFolderOrFiles(userId, parentFolderId);
	// }

	@Override
	public FileAndFolder getById(Integer Id) {
		return folderDao.getById(Id);
	}

	@Override
	public String parentPath(Integer parentFolderId, Stack<FileAndFolder> folderStack, String rootPath) {
		return folderDao.parentPath(parentFolderId, folderStack, rootPath);
	}

	@Override
	public int delete(Integer id) {
		return folderDao.delete(id);
	}

	@Override
	public void deleteFolder(FileAndFolder folder) {
		folderDao.deleteFolder(folder);
	}

	@Override
	public int saveFileAndFolder(FileAndFolder fileAndFolder) {
		return folderDao.saveFileAndFolder(fileAndFolder);
	}

	@Override
	public List<FileAndFolder> pageReviwe(PageSource page, String hql) {
		return folderDao.pageReviwe(page, hql);
	}

	@Override
	public List<FileAndFolder> getAll(String hql) {
		return folderDao.getAll(hql);
	}

	@Override
	public void update(FileAndFolder obj) {
		folderDao.update(obj);
	}

	@Override
	public int createFolder(FileAndFolder fgr) {
		return folderDao.createFolder(fgr);
	}

	@Override
	public List<String> getToPath(Integer fId, Integer tId, List<String> list, Integer userId) {
		FileAndFolder fgr = folderDao.getById(tId);
		// 若有重名文件夹，则不移动
		// 判断要移动的文件夹名称与目的文件夹的子文件夹不重名
		if (fgr.getParentFolderId() == fId) {
			return null;
		} else {
			list.add(fgr.getName());
			if (fgr.getParentFolderId() > -1) {
				return getToPath(fId, fgr.getParentFolderId(), list, userId);
			} else {
				return list;
			}
		}
	}

	@Override
	public String getToPath(Integer fId, Integer tId, String root_Path, Integer userId) {
		root_Path = PathFormat.strEnd(root_Path);
		String result = root_Path;
		try {
			List<String> list = new ArrayList<String>();
			list = getToPath(fId, tId, list, userId);
			StringBuffer sf = new StringBuffer();
			sf.append(root_Path);
			for (int i = list.size() - 1; i >= 0; i--) {
				sf.append(PathFormat.strEnd(list.get(i)));
			}
			result = sf.toString();
		} catch (NullPointerException e) {
			return result;
		}
		return result;
	}

	@Override
	public List<FileAndFolder> intoPath(Integer tId, List<FileAndFolder> list, Integer userId) {
		FileAndFolder fgr = folderDao.getById(tId);
		list.add(fgr);
		try {
			if (fgr.getParentFolderId() > -1) {
				return intoPath(fgr.getParentFolderId(), list, userId);
			} else {
				return list;
			}
		} catch (NullPointerException e) {
			return list;
		}
	}

	@Override
	public String intoPath(Integer tId, Integer userId, String rootPath) {
		String result = PathFormat.strEnd(rootPath);
		try {
			List<FileAndFolder> list = new ArrayList<FileAndFolder>();
			list = intoPath(tId, list, userId);
			StringBuffer sf = new StringBuffer();
			sf.append(PathFormat.strEnd(rootPath));
			for (int i = list.size() - 1; i >= 0; i--) {
				sf.append(PathFormat.strEnd(list.get(i).getName()));
			}
			result = sf.toString();
		} catch (NullPointerException e) {
			return result;
		}
		return result;
	}

	/** 返回所有文件夹的JsonArray */
	@Override
	public JSONArray getJsonArray(Integer userId) {
		String hqlFolder = "From FileAndFolder where userId=" + userId + "  and type='folder'";
		JSONArray ja = new JSONArray();
		JSONObject jObject = new JSONObject();
		jObject.put("id", -1);
		jObject.put("text", "root");
		jObject.put("parentId", -2);
		jObject.put("iconCls", "icon-folder");

		List<FileAndFolder> lists = folderDao.getAll(hqlFolder);
		JSONArray jsonArray = new JSONArray();
		for (FileAndFolder fileAndFolder : lists) {
			if (fileAndFolder.getParentFolderId() == -1) {
				jsonArray.add(getJson(fileAndFolder, 1));
			}
		}

		jObject.put("children", jsonArray);
		ja.add(jObject);
		System.out.println(ja.toString());
		return ja;
	}

	/** 获得当前级别的jsonObject */
	@Override
	public JSONObject getJson(FileAndFolder fileAndFolder, Integer userId) {
		Integer newPId = fileAndFolder.getId();
		String hqlFolder = "From FileAndFolder where userId=" + userId + " and type='folder' and parentFolderId="
				+ newPId;
		System.out.println("hal=" + hqlFolder);
		List<FileAndFolder> lists = folderDao.getAll(hqlFolder);
		JSONObject jt = new JSONObject();
		jt.put("id", fileAndFolder.getId());
		jt.put("text", fileAndFolder.getName());
		jt.put("parentId", fileAndFolder.getParentFolderId());
		jt.put("iconCls", "icon-folder");
		JSONArray array = new JSONArray();
		if (lists.size() != 0) {// 若不为最后一层文件夹
			jt.put("state", "closed");
			for (FileAndFolder ff : lists) {
				array.add(getJson(ff, userId));
			}
			jt.put("children", array);
		}
		return jt;
	}

	@Override
	public List<File> scanMappingPath(String mappingRootPath) {
		List<File> fileLists = new ArrayList<File>();
		List<File> files = (List<File>) FileUtils.listFiles(new File(mappingRootPath), null, true);
		for (File file : files) {
			fileLists.add(file);
		}
		return fileLists;
	}

	@Override
	public boolean syncFiles(String mappingRootPath, String rootPath, File mappingFile, User user) {
		String fileSize = folderOperate.FormetFileSize(mappingFile.length());
		boolean syncRet = false;
		String mappingPathStr = mappingFile.getPath();
		String path = mappingPathStr.substring(PathFormat.strEnd(mappingRootPath).length());
		System.out.println(path);
		String split = "";
		if (File.separator.equals("\\")) {
			split = "\\\\";
		} else
			split = "\\";
		String[] pathList = path.split(split);

		/** j为parentId;temp为实际路径 ,temp_map为映射路径 */
		String temp = PathFormat.strEnd(rootPath);
		int j = this.getAll(
				"FROM FileAndFolder WHERE userId=" + user.getId() + " and parentFolderId=-1 and type='mapping_copy'")
				.get(0).getId();
		for (int i = 0; i < pathList.length; i++) {
			FileAndFolder f2File = new FileAndFolder();
			FileAndFolder deleteFlag = this.getById(j);
			String pathindex = temp + PathFormat.strEnd(pathList[i]);
			if (i == pathList.length - 1) {
				String fileN = mappingFile.getName();
				String tp = fileN.substring(fileN.indexOf("."));
				String fileName = fileN.substring(0, fileN.indexOf("."));
				Integer size = this.getAll("from  FileAndFolder where userId=" + user.getId() + " and parentFolderId="
						+ j + " and name='" + fileName + "' and type='" + tp + "'").size();
				if (size > 0) {
					fileName = fileName + new Date().getTime();
					mappingFile.renameTo(
							new File(PathFormat.strEnd(mappingFile.getParentFile().getPath()) + fileName + tp));
					mappingPathStr = PathFormat.strEnd(mappingFile.getParentFile().getPath()) + fileName + tp;
				}
				// 若该级别为文件，则进行移动，并将结果插入到数据库
				folderOperate.removeFileOrFolder(mappingPathStr, temp);
				f2File.setName(fileName);
				f2File.setParentFolderId(j);
				f2File.setSize(fileSize);
				f2File.setTime(new Date());
				f2File.setType(tp);
				f2File.setUserId(user.getId());
				f2File.setDeleteFlag(deleteFlag);
				this.saveFileAndFolder(f2File);
				syncRet = true;
				continue;
			} else if ((!new File(pathindex).exists()) && (i != pathList.length - 1)) {
				// pathindex为一个文件夹路径，且真实路径下不存在，则创建一个文件夹，并将结果插入到数据库中,并将temp加一级
				try {
					FileUtils.forceMkdir(new File(pathindex));
					f2File.setName(pathList[i]);
					f2File.setParentFolderId(j);
					f2File.setSize("");
					f2File.setTime(new Date());
					f2File.setType("folder");
					f2File.setUserId(user.getId());
					f2File.setDeleteFlag(deleteFlag);
					this.saveFileAndFolder(f2File);
				} catch (IOException e) {
					System.out.println("创建文件夹失败");
				}
			}
			temp = pathindex;
			// 将j进入一级
			j = folderDao.getAll("from  FileAndFolder where userId=" + user.getId() + " and parentFolderId=" + j
					+ " and name='" + pathList[i] + "'").get(0).getId();
		}
		return syncRet;
	}

	public FolderOperate getFolderOperate() {
		return folderOperate;
	}

	public void setFolderOperate(FolderOperate folderOperate) {
		this.folderOperate = folderOperate;
	}

	public FolderDao getFolderDao() {
		return folderDao;
	}
}
