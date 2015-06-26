package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.innshine.ordermanager.timetask.OCSStockDataCleaningService;

public class OcsStockDataCleaningTask implements Job {
	@Autowired
	private OCSStockDataCleaningService oCSStockDataCleaningService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		oCSStockDataCleaningService.doTask();
	}

}