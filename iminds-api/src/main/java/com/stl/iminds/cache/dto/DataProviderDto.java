package com.stl.iminds.cache.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataProviderDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String cacheId;
	private String description;
	private String fqn;
	private boolean loadonServerStartup;
	private int reloadTime;
	private boolean transactionalCache;
	private int totalItems;
	private int dataSize;
	private Date nextReloadDate;
	private Date lastReloadDate;
	private boolean externalCache;
	private String externalModule;
	private String cachePrefix;
	
	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFqn() {
		return fqn;
	}

	public void setFqn(String fqn) {
		this.fqn = fqn;
	}

	public boolean isLoadonServerStartup() {
		return loadonServerStartup;
	}

	public void setLoadonServerStartup(boolean loadonServerStartup) {
		this.loadonServerStartup = loadonServerStartup;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public boolean isTransactionalCache() {
		return transactionalCache;
	}

	public void setTransactionalCache(boolean transactionalCache) {
		this.transactionalCache = transactionalCache;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public Date getNextReloadDate() {
		return nextReloadDate;
	}

	public void setNextReloadDate(Date nextReloadDate) {
		this.nextReloadDate = nextReloadDate;
	}

	public Date getLastReloadDate() {
		return lastReloadDate;
	}

	public void setLastReloadDate(Date lastReloadDate) {
		this.lastReloadDate = lastReloadDate;
	}

	public boolean isExternalCache() {
		return externalCache;
	}

	public void setExternalCache(boolean externalCache) {
		this.externalCache = externalCache;
	}

	public String getExternalModule() {
		return externalModule;
	}

	public void setExternalModule(String externalModule) {
		this.externalModule = externalModule;
	}
	
	public String getCachePrefix() {
		return cachePrefix;
	}

	public void setCachePrefix(String cachePrefix) {
		this.cachePrefix = cachePrefix;
	}

	@Override
	public String toString() {
		return "DataProviderDto [cacheId=" + cacheId + ", description=" + description + ", fqn=" + fqn
				+ ", loadonServerStartup=" + loadonServerStartup + ", reloadTime=" + reloadTime
				+ ", transactionalCache=" + transactionalCache + ", totalItems=" + totalItems + ", dataSize=" + dataSize
				+ ", nextReloadDate=" + nextReloadDate + ", lastReloadDate=" + lastReloadDate + ", externalCache="
				+ externalCache + ", externalModule=" + externalModule + ", cachePrefix=" + cachePrefix + "]";
	}
	
}
