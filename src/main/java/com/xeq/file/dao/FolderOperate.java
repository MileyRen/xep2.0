package com.xeq.file.dao;

public interface FolderOperate {
	/**
	 * 创建文件夹
	 * 
	 * @name 文件夹名称
	 * @param path文件路径
	 */
	boolean createRealFolder(String name, String path);

	/** 删除单个文件 */
	boolean delete(String path);

	/** 删除目录 及目录下文件 */
	boolean deleteDirectory(String dir);

	/** 移动文件夹或文件 **/
	boolean removeFileOrFolder(String fromPath, String toPath);

	String FormetFileSize(long fileS);
}
