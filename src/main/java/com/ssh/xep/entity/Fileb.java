package com.ssh.xep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fileupload_library_dataset", catalog = "xep")
public class Fileb implements Serializable {

	private static final long serialVersionUID = 7620108806007241960L;

	private int id;
	private int folderIdId;
	private String datasetId;
	private String createIime;
	private String updateIime;
	private String name;
	private int userId;
	private int size;
	private String type;
	private int deleted;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "folder_id_id", nullable = false)
	public int getFolderIdId() {
		return folderIdId;
	}

	public void setFolderIdId(int folderIdId) {
		this.folderIdId = folderIdId;
	}

	@Column(name = "dataset_id", nullable = false)
	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	@Column(name = "create_time", nullable = false)
	public String getCreateIime() {
		return createIime;
	}

	public void setCreateIime(String createIime) {
		this.createIime = createIime;
	}

	@Column(name = "update_time", nullable = false)
	public String getUpdateIime() {
		return updateIime;
	}

	public void setUpdateIime(String updateIime) {
		this.updateIime = updateIime;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "size", nullable = false)
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "deleted", nullable = false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

}
