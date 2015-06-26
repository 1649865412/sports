package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import api.utils.LogInfoUtilService;

import com.innshine.nbsalesdetails.timetask.NBSalesInfoDataCleaningService;

public class NBSalesInfoDataCleaningTask implements Job {
	@Autowired
	private NBSalesInfoDataCleaningService nBSalesInfoDataCleaningService;
	
	/**
	 * 日记记录业务类
	 * 
	 */
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		nBSalesInfoDataCleaningService.doDataCleaningTask();
	}

}
