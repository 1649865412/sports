package com.innshine.shopAnalyse.TimerTask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.innshine.daily.service.GatherDailyService;

public class GatherDailyTask implements Job {
	@Autowired
	private GatherDailyService gatherDailyService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		gatherDailyService.gatherDailyDoTask();
	}

}
