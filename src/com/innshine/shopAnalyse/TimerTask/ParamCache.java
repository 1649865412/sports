package com.innshine.shopAnalyse.TimerTask;

import java.util.Calendar;
import java.util.Date;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.dao.SqlDao;
import com.base.service.component.BusinessCache;

import com.innshine.shopAnalyse.service.ShopProductionAnalyseService;


@Service
public class ParamCache {
	
	@Autowired
	private BusinessCache businessCache;
	
	
	@Autowired
	private ShopProductionAnalyseService shopProductionAnalyseService;

	private SqlDao sqlDao;

	@Autowired
	public void setSqlDao(SqlDao sqlDao){
		this.sqlDao = sqlDao;
		refresh();
	}
	
	public void refresh() {
		try {
			ParamListener.setParam( shopProductionAnalyseService,  businessCache); 
		} catch (Exception e) {
			Log.info(new Date()+"===============参数缓存有误");
			e.printStackTrace();
		}
	}		
}
