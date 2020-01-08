package com.stl.iminds.cache.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stl.core.base.cache.CacheAdditionalData;
import com.stl.core.base.cache.CacheProviderFactory;
import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.db.DBManager;
import com.stl.iminds.cache.constant.CacheConstant;
import com.stl.iminds.cache.dao.DataProviderDao;
import com.stl.iminds.cache.dto.DataProviderDto;

@Component
public class DataProviderDaoImpl implements DataProviderDao{
	
	private static final String CLASSNAME="DataProviderDao";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Autowired 
	DBManager dbManager; 
	
	@Override
	public List<DataProviderDto> getDataProvider() {
		String strMethodName = "getDataProviderByModule";
		List<DataProviderDto> dataProviderList = null;
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Started");

		String statement = "SELECT CACHEID,DESCRIPTION,DATAPROVIDERCLASS,RELOADTIME,LOADONSERVERSTARTUP,TRANSACTIONALCACHE,EXTERNALCACHE,EXTERNALMODULE,CACHEPREFIX FROM TBLSDATAPROVIDER";

		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, statement);
				ResultSet resultSet = dbManager.executeQuery(pStmt, null, null)) {
			if(Objects.nonNull(resultSet)) {
				dataProviderList = new ArrayList<>();
				while(resultSet.next()) {
					DataProviderDto dataProvider = new DataProviderDto();
					dataProvider.setCacheId(resultSet.getString("CACHEID"));
					dataProvider.setDescription(resultSet.getString("DESCRIPTION"));
					dataProvider.setFqn(resultSet.getString("DATAPROVIDERCLASS"));
					dataProvider.setReloadTime(resultSet.getInt("RELOADTIME"));
					dataProvider.setLoadonServerStartup("Y".equals(resultSet.getString("LOADONSERVERSTARTUP")));
					dataProvider.setTransactionalCache("Y".equals(resultSet.getString("TRANSACTIONALCACHE")));
					dataProvider.setExternalCache("Y".equals(resultSet.getString("EXTERNALCACHE")));
					dataProvider.setExternalModule(resultSet.getString("EXTERNALMODULE"));
					dataProvider.setCachePrefix(resultSet.getString("CACHEPREFIX"));
					
					CacheAdditionalData cacheAdditionalData=CacheProviderFactory.getProviderInstance().getCacheAdditionalInformation(dataProvider.getCacheId(), CacheConstant.DEFAULT_TENANT_ID);
					if(cacheAdditionalData!=null){
						dataProvider.setDataSize(cacheAdditionalData.getDatasize());
						dataProvider.setTotalItems(cacheAdditionalData.getTotalItems());
						dataProvider.setLastReloadDate(cacheAdditionalData.getLastReloadDate());
						dataProvider.setNextReloadDate(cacheAdditionalData.getNextReloadDate());
					}
					dataProviderList.add(dataProvider);
				}
			}
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName,"Data Provider details are fetched successfully");
		} 
		catch (SQLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, "SQLException occured. Message: " + e.getMessage(), e);
		}

		return dataProviderList;
	}

}
