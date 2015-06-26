package com.innshine.daily.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.SqlDao;

/**
 * 
 * <code>GatherDailyDao.java</code>
 * <p>
 * <p>
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Repository
public class GatherDailyDao
{
	@Autowired
	private SqlDao sqlDao;
	
	/**
	 * 根据SQL key 、参数集合、对象class ，查询对应数据
	 * 
	 * @param <T>
	 *        实体bean
	 * @param sqlKey
	 *        SQL key
	 * @param params
	 *        参数列表
	 * @param classzz
	 *        实体bean class
	 * @return List< T>
	 */
	public <T> List<T> queryGatherDailyData(String sqlKey, Map<String, Object> params, Class<T> classzz)
	{
		try
		{
			return sqlDao.queryListBySql(params, sqlKey, classzz);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return new ArrayList<T>();
	}
	
}
