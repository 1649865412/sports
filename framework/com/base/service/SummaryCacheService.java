package com.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.SqlDao;
import com.base.entity.component.ArriveTip;
import com.base.entity.component.DayOrderArriver;
import com.base.entity.component.DaySellAndStock;
import com.base.entity.component.SecurtySaleNum;
import com.base.entity.component.Summary;
import com.base.entity.component.TurnoverNum;
import com.base.service.component.BusinessCache;

@Service
public class SummaryCacheService {
	@Autowired
	private BusinessCache businessCache;
	
	private SqlDao sqlDao;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SummaryCacheService.class);

	private String summaryCacheKey = "summaryData";
	
	private String indexKey = "indexKey";
	private String dayOrderArriver = "dayOrderArriver";
	private String daySellAndStock = "daySellAndStock";
	private String arriveTip = "arriveTip";
	private String securtySaleNums = "securtySaleNums";
	private String stockTurnoverNums = "stockTurnoverNums";
	
	
	@Autowired
	public void setSqlDao(SqlDao sqlDao){
		this.sqlDao = sqlDao;
		refresh();
	}
	
	public void refresh() {
		try {
			
			Map<String, Object> param = new HashMap<String, Object>();
			//订货数量+到货数量+各自金额
			List<DayOrderArriver> dayOrderArriverList =  sqlDao.queryListBySql(param,"index.queryDayOrderArriver", DayOrderArriver.class);
		
			//销售数量+库存数量+各自金额
			List<DaySellAndStock> daySellAndStockList =  sqlDao.queryListBySql(param,"index.queryDaySellAndStock", DaySellAndStock.class);
		
			//产品将到货数量
			List<ArriveTip> arriveTipList =  sqlDao.queryListBySql(param,"index.arriveTip", ArriveTip.class);
			
			
		   //产品安全库存低于预设值数量
			List<SecurtySaleNum> securtySaleNumsList =  sqlDao.queryListBySql(param,"index.securtySaleNums", SecurtySaleNum.class);
			
			//产品库存周转低于预设值数量
			List<TurnoverNum> stockTurnoverNumsList =  sqlDao.queryListBySql(param,"index.stockTurnoverNums",  TurnoverNum.class);
			
			/*List<DayOrderArriver> dayOrderArriverList =new ArrayList();
			List<DaySellAndStock> daySellAndStockList = new ArrayList();
			List<TurnoverNum> stockTurnoverNumsList =new ArrayList();
			List<SecurtySaleNum> securtySaleNumsList =new ArrayList();*/
			
			businessCache.replaceCache(summaryCacheKey, arriveTip, arriveTipList);
			businessCache.replaceCache(summaryCacheKey, securtySaleNums, securtySaleNumsList);
			businessCache.replaceCache(summaryCacheKey, dayOrderArriver, dayOrderArriverList);
			businessCache.replaceCache(summaryCacheKey, stockTurnoverNums, stockTurnoverNumsList);
			businessCache.replaceCache(summaryCacheKey, daySellAndStock, daySellAndStockList);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}
	 
	


	@SuppressWarnings("unchecked")
	public List<Summary> getSummary() {
		List<Summary> list = (List<Summary>)businessCache.get(summaryCacheKey, indexKey);
		if(list == null){
			refresh();
			list = (List<Summary>)businessCache.get(summaryCacheKey, indexKey);
		}
		return list;
	}
	
	public Map<String,Object> getCacheData(){
		Map<String,Object> data = new HashMap<String, Object>();
		data.put(dayOrderArriver, businessCache.get(summaryCacheKey, dayOrderArriver));
		data.put(daySellAndStock, businessCache.get(summaryCacheKey, daySellAndStock));
		data.put(arriveTip, businessCache.get(summaryCacheKey, arriveTip));
		data.put(securtySaleNums, businessCache.get(summaryCacheKey, securtySaleNums));
		data.put(stockTurnoverNums, businessCache.get(summaryCacheKey, stockTurnoverNums));
		
		return data;
	}

}
