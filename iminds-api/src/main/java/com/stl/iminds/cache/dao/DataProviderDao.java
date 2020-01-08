package com.stl.iminds.cache.dao;

import java.util.List;

import com.stl.iminds.cache.dto.DataProviderDto;

public interface DataProviderDao {
	
	/**
	 * Get Data providers detail based on the given module name. It returns all data providers in case of moduleName as Null.
	 * @param moduleName
	 * @return List of DataProviderDto
	 */
	public List<DataProviderDto> getDataProvider();

}
