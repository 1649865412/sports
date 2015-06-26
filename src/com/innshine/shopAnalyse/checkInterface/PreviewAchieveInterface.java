package com.innshine.shopAnalyse.checkInterface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
/**
 * 
 *  <code>预览报表接口PreviewAchieveInterface.java</code>
 *  <p>
 *  <p>Copyright  2014 All right reserved.
 *  @author 杨荣忠 时间 2014-9-23 下午04:53:29	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public interface PreviewAchieveInterface {

	/**
	 * top10
	 */
	public String getTop10List(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 * 销售日报
	 */
	public String getShopDay(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 * 月报
	 */
	public String getShopMonth(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 *月报摘要
	 */
	public String getShopMonthPoint(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 *销售分析
	 */
	public String getShopAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

	/**
	 * 月度统计
	 * 
	 * @throws Exception
	 * 
	 */
	public String getMonthAnalse(Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response,
			ShopAnalyseCheckEntity ShopAnalyseCheckEntity) throws Exception;

}
