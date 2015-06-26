package com.innshine.shopAnalyse.checkInterface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;

/**
 * 
 *  <code>导出报表接口AchieveInterface.java</code>
 *  <p>
 *  <p>Copyright  2014 All right reserved.
 *  @author 杨荣忠 时间 2014-9-23 下午04:53:59	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public interface AchieveInterface
{

	/**
	 * top10
	 */
	public void getTop10List(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 * 销售日报
	 */
	public void getShopDay(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 * 月报
	 */
	public void getShopMonth(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 *月报摘要
	 */
	public void getShopMonthPoint(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 *销售分析
	 */
	public void getShopAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 * 月度统计
	 * 
	 * @throws Exception
	 * 
	 */
	public void getMonthAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

}
