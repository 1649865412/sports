package api.taobao.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.taobao.entity.OCSOrdersInfo;

import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.innshine.salesstockdetails.service.OcsOrderDataCleaningService;
import com.utils.Exceptions;

/**
 * 查询OCS数据库服务类 <code>OCSService.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

@Service
@Transactional
public class OCSService
{
	private static final String QUERY_FIELD_NAME_ORDER_BN = "orderBn";
	
	private static final String QUERY_FIELD_NAME_SHOP_ID = "shopId";
	
	/**
	 * 查询 一段时间范围内的OCS订单数量
	 */
	private static final String OCS_ORDERS_RANGE_TOTAL_COUNT_SQL = "ocs.orders.range.total.count.sql";
	
	/**
	 * 查询 一段时间范围内的OCS订单信息详情
	 */
	private static final String OCS_ORDERS_RANGE_INFO_SQL = "ocs.orders.range.info.sql";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OCSService.class);
	
	@Autowired(required = true)
	private OcsOrderDataCleaningService ocsOrderDataCleaningService;
	
	/**
	 * 数据同步结束时间
	 */
	private static final String QUERY_FIELD_NAME_END_TIME = "endTime";
	
	/**
	 * 数据开始结束时间
	 */
	private static final String QUERY_FIELD_NAME_START_TIME = "startTime";
	
	/**
	 * 根据开始、结束时间，查询一段时间范围内的OCS订单数据
	 * 
	 * @param <T>
	 *        返回的实体bean
	 * @param startDate
	 *        开始时间范围
	 * @param endTime
	 *        结果时间范围
	 * @param clazz
	 *        返回实体bean Class
	 * @return List<T>
	 */
	public <T> List<T> queryOCSOrdersInfoByTime(Date startDate, Date endTime, Class<T> clazz)
	{
		Map<String, Object> params = getParams(startDate, endTime);
		try
		{
			return ocsOrderDataCleaningService.findBySalesOrderByTime(params, OCS_ORDERS_RANGE_INFO_SQL, clazz);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
		
	}
	
	/**
	 * 根据开始、结束时间，查询一段时间范围内的OCS订单数据
	 * 
	 * @param <T>
	 *        返回的实体bean
	 * @param startDate
	 *        开始时间范围
	 * @param endTime
	 *        结果时间范围
	 * @param clazz
	 *        返回实体bean Class
	 * @return List<T>
	 */
	public <T> BigInteger queryOCSOrdersNumberByTime(Date startDate, Date endTime)
	{
		Map<String, Object> params = getParams(startDate, endTime);
		
		try
		{
			String sql = SQLProperties.getInstance().getSql(OCS_ORDERS_RANGE_TOTAL_COUNT_SQL);
			SQLHelper sqlHelper = new SQLHelper(sql);
			String executeSql = sqlHelper.parepareSQLtext(params);
			return ocsOrderDataCleaningService.findBySalesOrderByNumber(executeSql);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
		
	}
	
	/**
	 * 构造查询参数
	 * 
	 * @param startDate
	 *        开始时间
	 * @param endTime
	 *        结束时间
	 * @return
	 */
	private Map<String, Object> getParams(Date startDate, Date endTime)
	{
		return getParams(startDate, endTime, null, null);
	}
	
	public List<OCSOrdersInfo> queryOCSOrdersInfoByTime(Date startDate, Date endTime, String shopId,
			Class<OCSOrdersInfo> class1)
	{
		return queryOCSOrdersInfoByTime(startDate, endTime, shopId, null, class1);
	}
	
	public List<OCSOrdersInfo> queryOCSOrdersInfoByTime(Date startDate, Date endTime, String shopId, String orderBn,
			Class<OCSOrdersInfo> class1)
	{
		
		Map<String, Object> params = getParams(startDate, endTime, shopId, orderBn);
		try
		{
			return ocsOrderDataCleaningService.findBySalesOrderByTime(params, OCS_ORDERS_RANGE_INFO_SQL, class1);
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
	}
	
	private Map<String, Object> getParams(Date startDate, Date endTime, String shopId, String orderBn)
	{
		// 构造参数
		Map<String, Object> params = new HashMap<String, Object>();
		if (null != startDate && null != endTime)
		{
			params.put(QUERY_FIELD_NAME_START_TIME, startDate.getTime() / 1000);
			params.put(QUERY_FIELD_NAME_END_TIME, endTime.getTime() / 1000);
			
		}
		
		if (StringUtils.isNotEmpty(shopId))
		{
			params.put(QUERY_FIELD_NAME_SHOP_ID, shopId);
		}
		
		if (StringUtils.isNotEmpty(orderBn))
		{
			params.put(QUERY_FIELD_NAME_ORDER_BN, orderBn);
		}
		
		return params;
	}
	
}
