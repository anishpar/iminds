package com.stl.iminds.cache.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.stl.core.base.cache.CacheProviderFactory;
import com.stl.core.base.cache.ICacheProvider;
import com.stl.core.base.cache.IDataProvider;
import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.iminds.cache.constant.CacheConstant;
import com.stl.iminds.cache.dao.DataProviderDao;
import com.stl.iminds.cache.dto.DataProviderDto;
import com.stl.iminds.cache.service.CacheService;


@Service("CacheServiceImpl")
public class CacheServiceImpl implements CacheService {
	private static final String CLASSNAME="CacheServiceImpl";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Autowired
	private AutowireCapableBeanFactory beanFactory;
	
	@Autowired
	Environment env;
	
	@Autowired
	DataProviderDao dataProviderDao;
	
	/**
	 * Initialize.
	 */
	public void initializeCache() {
		String strMethodName="initialize";
		if (LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, "Started");

		Map<Long, Set<String>> cacheMap=null;
		Set<String> cacheSet=null;
		try{
			//Register DataProvider
			List<DataProviderDto> dataProviderDatas = dataProviderDao.getDataProvider();
			if(dataProviderDatas!=null){
				cacheSet=new HashSet<>();
				cacheMap=new HashMap<>();
				ICacheProvider iCacheProvider=CacheProviderFactory.getProviderInstance();
				for(DataProviderDto data:dataProviderDatas){
					registerDataProvider(strMethodName, iCacheProvider, data);
					if(data.isLoadonServerStartup()){
						cacheSet.add(data.getCacheId());
					}
				}
				//Load On Server Startup Cache
				Integer iReloadAllTime=null;
				String strReloadAllTime=env.getProperty(CacheConstant.CACHE_RELOAD_ALL_TIME_PROPERTY_ALIAS);
				if(strReloadAllTime!=null){
					iReloadAllTime=Integer.parseInt(strReloadAllTime);
				}
				cacheMap.put(CacheConstant.DEFAULT_TENANT_ID, cacheSet);
				iCacheProvider.reloadAll(cacheMap,true,iReloadAllTime);
			}else{
				if (LOGGER.isWarnEnabled()) {
					LOGGER.warnLog(CLASSNAME, strMethodName, "Dataprovider is not found.");
				}
			}
		}
		catch (Exception e) {
			LOGGER.errorLog( CLASSNAME, strMethodName,"Exception occurred while fetching " + strMethodName, e);
		}
		if (LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, "Ended");
	}

	private void registerDataProvider(String strMethodName, ICacheProvider iCacheProvider, DataProviderDto data){
		IDataProvider iDataProvider;
		try{
			iDataProvider = (IDataProvider) Class.forName(data.getFqn()).getDeclaredConstructor().newInstance();
			beanFactory.autowireBean(iDataProvider);
			iDataProvider.setTransactionalCache(data.isTransactionalCache());
			iDataProvider.setTimeToExpire(data.getReloadTime());
			iDataProvider.setExternalCache(data.isExternalCache());
			iDataProvider.setExternalModule(data.getExternalModule());
			iDataProvider.setCachePrefix(data.getCachePrefix());
			iCacheProvider.registerDataProvider(data.getCacheId(), iDataProvider);
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException exception){
			if(LOGGER.isErrorEnabled()) LOGGER.errorLog(CLASSNAME, strMethodName,"Cannot register " + data.getFqn() + " data provider.",exception);
		}
	}

	@Override
	public Map<Object, Object> reloadCache(String cacheId) {
		return CacheProviderFactory.getProviderInstance().reloadCache(cacheId, CacheConstant.DEFAULT_TENANT_ID,false);
	}

	@Override
	public Map<Object, Object> get(String cacheId) {
		return CacheProviderFactory.getProviderInstance().get(cacheId, CacheConstant.DEFAULT_TENANT_ID);
	}

	@Override
	public boolean reloadAll(Map<Long, Set<String>> cacheMap) {
		Integer iReloadAllTime=null;
		String strReloadAllTime=env.getProperty(CacheConstant.CACHE_RELOAD_ALL_TIME_PROPERTY_ALIAS);
		if(strReloadAllTime!=null){
			iReloadAllTime=Integer.parseInt(strReloadAllTime);
		}
		return CacheProviderFactory.getProviderInstance().reloadAll(cacheMap,false,iReloadAllTime);
	}
}
