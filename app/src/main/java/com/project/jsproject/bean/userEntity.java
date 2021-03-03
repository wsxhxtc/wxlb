package com.project.jsproject.bean;

import java.io.Serializable;


public class userEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String username_;

	private String pwd_;

	private String status_;

	private String describe_;

	private String type_;

	private String tel;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername_() {
		return username_;
	}

	public void setUsername_(String username_) {
		this.username_ = username_;
	}

	public String getPwd_() {
		return pwd_;
	}

	public void setPwd_(String pwd_) {
		this.pwd_ = pwd_;
	}

	public String getStatus_() {
		return status_;
	}

	public void setStatus_(String status_) {
		this.status_ = status_;
	}

	public String getDescribe_() {
		return describe_;
	}

	public void setDescribe_(String describe_) {
		this.describe_ = describe_;
	}

	public String getType_() {
		return type_;
	}

	public void setType_(String type_) {
		this.type_ = type_;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
