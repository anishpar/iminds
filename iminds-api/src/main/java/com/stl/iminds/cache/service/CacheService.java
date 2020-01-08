package com.stl.iminds.cache.service;

import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public interface CacheService {
	/**
	 * This function is called at initialization. It will register all data provider of cache and load server start up cache.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void initializeCache();
	
	/**
	  * This method is used to get the cache data map associated with the given 
	  * identifier from the cache. it will get new data for identifier and put result into cache.
	  * @param strCacheId : Cache identifier
	  * @return Map from the cache associated with the key
	  */
   public Map<Object,Object> reloadCache(String cacheId);
   
   /**
    * This method returns the data object for the given cache identifier.
    * @param strCacheId : Cache identifier
    * @return returns cached data object for the given cache identifier and return null in case of error
    */
   public Map<Object,Object> get(String cacheId);
   
   /**
	 * This function is used to reload All cache for given TenantId and cache list.
	 * @param cacheMap : Cache Map list
	 * @return boolean true if successfully reloaded else false.
	 */
	public boolean reloadAll(Map<Long, Set<String>> cacheMap);
	
}
