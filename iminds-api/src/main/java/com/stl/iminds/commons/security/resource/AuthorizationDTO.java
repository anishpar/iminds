package com.stl.iminds.commons.security.resource;

import java.io.Serializable;

public class AuthorizationDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String alias;
	private String url;
	private long id;
	private String name;
	private String status;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "AuthorizationDTO [alias=" + alias + ", url=" + url + ", id=" + id + ", name=" + name + ", status="
				+ status + "]";
	}
	
	
}
