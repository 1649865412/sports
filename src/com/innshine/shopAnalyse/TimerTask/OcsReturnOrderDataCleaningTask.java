package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.innshine.salesstockdetails.timetask.OcsReturnOrderDataCleaningService;

public class OcsReturnOrderDataCleaningTask implements Job {
	@Autowired
	private OcsReturnOrderDataCleaningService ocsReturnOrderDataCleaningService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		ocsReturnOrderDataCleaningService.doTask();
	}

}
