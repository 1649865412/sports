package com.innshine.salesstockdetails.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.dao.SqlDao;
import com.base.util.dwz.Page;
import com.utils.Exceptions;

/**
 * OCS订单信息数据清洗数据操作服务类，如增、删、改、查等
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Service
@Transactional
public class OcsOrderDataCleaningService
{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(OcsOrderDataCleaningService.class);
	
	@Autowired
	private SqlDao sqlDao;
	
	@Resource(name = "ocsEntityManagerFactory")
	private EntityManagerFactory ocsEm;
	
	/**
	 * 根据订单最后修改时间，查询对应订单的销售量信息
	 * 
	 * @param <T>
	 *        返回的List中存放的泛型对象
	 * @param params
	 *        查询SQL
	 * @param ocsOrdersRangeInfoSql
	 *        分页对象
	 * @param clazz
	 *        List中存放的泛型对象Class
	 * @return List< T >
	 * @throws Exception
	 */
	public <T> List<T> findBySalesOrderByTime(Map<String, Object> params, String ocsOrdersRangeInfoSql, Class<T> clazz)
			throws Exception
	{
		if (MapUtils.isNotEmpty(params))
		{
			try
			{
				sqlDao.setOcsEm();
				List<T> list = sqlDao.queryListBySql(params, ocsOrdersRangeInfoSql, clazz);
				return list;
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			finally
			{
				sqlDao.resetEm();
			}
		}
		
		return null;
		
	}
	
	/**
	 * 根据订单最后修改时间，查询对应订单的销售量信息
	 * 
	 * @param <T>
	 *        返回的List中存放的泛型对象
	 * @param sql
	 *        查询SQL
	 * @param page
	 *        分页对象
	 * @param clazz
	 *        List中存放的泛型对象Class
	 * @return List< T >
	 * @throws Exception
	 */
	public <T> List<T> findBySalesOrderByTime(String sql, Page page, Class<T> clazz) throws Exception
	{
		if (StringUtils.isNotEmpty(sql))
		{
			try
			{
				sqlDao.setOcsEm();
				List<T> list = sqlDao.queryByPage(sql, page, clazz);
				return list;
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			finally
			{
				sqlDao.resetEm();
			}
		}
		
		return null;
		
	}
	
	public BigInteger findBySalesOrderByNumber(String executeSql)
	{
		if (StringUtils.isNotEmpty(executeSql))
		{
			try
			{
				return (BigInteger) ocsEm.createEntityManager().createNativeQuery(executeSql).getSingleResult();
			}
			catch (Exception e)
			{
				LOGGER.error(Exceptions.getStackTraceAsString(e));
			}
			finally
			{
				sqlDao.resetEm();
			}
		}
		
		return null;
	}
}
