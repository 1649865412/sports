package com.base.test;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.base.dao.QuartzJobDao;

public class OneTask implements Job {

	private static final Logger log = LoggerFactory.getLogger(OneTask.class);

	@Autowired
	private QuartzJobDao quartzJobDao;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		log.debug(arg0.getFireInstanceId());
		log.debug("now time is:" + (new Date()));
		System.out.println("i'm running :" + (new Date()));
		//System.out.println("size :" + (quartzJobDao.count()));

	}

}
