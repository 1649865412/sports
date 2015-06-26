package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.innshine.salesstockdetails.timetask.OcsSalesOrderDataCleaningService;

public class OcsSalesOrderDataCleaningTask implements Job {
	private OcsSalesOrderDataCleaningService ocsSalesOrderDataCleaningService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		ocsSalesOrderDataCleaningService.doTask();
	}

}
