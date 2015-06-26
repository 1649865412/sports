package com.innshine.shopAnalyse.TimerTask;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.base.dao.SqlDao;
import com.base.service.SummaryCacheService;
import com.base.service.component.BusinessCache;

import com.innshine.shopAnalyse.entity.ParamList;
import com.innshine.shopAnalyse.service.ShopProductionAnalyseService;

public class ParamListener implements Serializable {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ParamListener.class);


	@SuppressWarnings("unchecked")
	public static List getResult(BusinessCache businessCache, String cacheKey,
			String indexKey) {
		List list = (List) businessCache.get(cacheKey, indexKey);
		return list;
	}
	
	
	
   /**
    * 功能:查询页面下拉参数值获取
    * <p>作者 杨荣忠 2014-9-23 上午09:39:25
    * @param shopProductionAnalyseService
    * @param businessCache
    * @throws Exception
    */
	public static void setParam(
			ShopProductionAnalyseService shopProductionAnalyseService,
			BusinessCache businessCache) throws Exception {
		Map<String, Object> param = new HashMap();
		List<List<ParamList>> list = shopProductionAnalyseService
				.getParamTypeList(param);
		com.innshine.shopAnalyse.util.Constant.ParamAll = list;
		com.innshine.stockAnalyse.util.Constant.ParamAll=list;

	}
}
