package com.base.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.CronExpression;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.QuartzConstant;
import com.base.dao.QrtzJobDetailsDao;
import com.base.dao.QuartzDao;
import com.base.dao.QuartzJobDao;
import com.base.entity.QrtzJob;
import com.base.entity.QrtzJobDetails;
import com.base.entity.QrtzTriggers;
import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils; 

@Service
@Transactional
public class SchedulerServiceImpl implements SchedulerService {
	private static final String NULLSTRING = null;
	private static final Date NULLDATE = null;

	@Resource(name="quartzScheduler")
	private Scheduler scheduler;
	//@Autowired
	private JobDetailImpl jobDetail;

	@Autowired
	private QuartzDao quartzDao;
    private QuartzJobDao quartzJobDao;
    @Autowired
    public void setQrtzJobDetailsDao(QuartzJobDao quartzJobDao){
    	this.quartzJobDao = quartzJobDao;
    	initQrtz();
    }
    /*****
     * 启动时初始化构建运行 
     */
    private void initQrtz(){ 
    	List<QrtzJob> list = quartzJobDao.findAllStartJob();
    	for(QrtzJob qrtz : list){
    		schedule(qrtz.getJobName(), qrtz.getJobGroup(),qrtz.getJobClass(), qrtz.getCronExpression());
    	}
    }
    
	@Override
	public List<QrtzTriggers> getQrtzTriggers() {
		return quartzDao.findAll();
	}

	@Override
	public void schedule(String cronExpression) {
		schedule(NULLSTRING, cronExpression);
	}

	@Override
	public void schedule(String name, String cronExpression) {
		schedule(name, NULLSTRING, cronExpression);
	}

	@Override
	public void schedule(String name, String group, String cronExpression) {
		try {
			schedule(name, group, new CronExpression(cronExpression));
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void schedule(CronExpression cronExpression) {
		schedule(NULLSTRING, cronExpression);
	}

	@Override
	public void schedule(String name, CronExpression cronExpression) {
		schedule(name, NULLSTRING, cronExpression);
	}

	@Override
	public void schedule(String name, String group, CronExpression cronExpression) {
		 
		if (isValidExpression(cronExpression)) {

			if (name == null || name.trim().equals("")) {
				name = UUID.randomUUID().toString();
			}

			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setCronExpression(cronExpression);

			TriggerKey triggerKey = new TriggerKey(name, group);
			
			trigger.setJobName(jobDetail.getKey().getName());
			trigger.setKey(triggerKey);

			try {
				scheduler.addJob(jobDetail, true);
				if (scheduler.checkExists(triggerKey)) {
					scheduler.rescheduleJob(triggerKey, trigger);
				} else {
					scheduler.scheduleJob(trigger);
				}
			} catch (SchedulerException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public void schedule(String name, String group,String jobClass, String cronExpression) {
		try{
		String detailName = name+"jobDetail";
		jobDetail = new JobDetailImpl();
		jobDetail.setName(detailName); 
		Job job = (Job)Class.forName(jobClass).newInstance();
		jobDetail.setJobClass(job.getClass());
		jobDetail.setDurability(true);
		jobDetail.setDescription(detailName+"runing !");
		CronExpression express = new CronExpression(cronExpression);
		if (isValidExpression(express)) {

			if (name == null || name.trim().equals("")) {
				name = UUID.randomUUID().toString();
			}

			CronTriggerImpl trigger = new CronTriggerImpl();
			trigger.setCronExpression(cronExpression);

			TriggerKey triggerKey = new TriggerKey(name, group);
			
			trigger.setJobName(jobDetail.getKey().getName());
			trigger.setKey(triggerKey);

			try {
				scheduler.addJob(jobDetail, true);
				if (scheduler.checkExists(triggerKey)) {
					scheduler.rescheduleJob(triggerKey, trigger);
				} else {
					scheduler.scheduleJob(trigger);
				}
			} catch (SchedulerException e) {
				throw new IllegalArgumentException(e);
			}
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public void schedule(Date startTime) {
		schedule(startTime, NULLDATE);
	}

	@Override
	public void schedule(Date startTime, String group) {
		schedule(startTime, NULLDATE, group);
	}

	@Override
	public void schedule(String name, Date startTime) {
		schedule(name, startTime, NULLDATE);
	}

	@Override
	public void schedule(String name, Date startTime, String group) {
		schedule(name, startTime, NULLDATE, group);
	}

	@Override
	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, 0);
	}

	@Override
	public void schedule(Date startTime, Date endTime, String group) {
		schedule(startTime, endTime, 0, group);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime, 0);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, String group) {
		schedule(name, startTime, endTime, 0, group);
	}

	@Override
	public void schedule(Date startTime, int repeatCount) {
		schedule(null, startTime, NULLDATE, 0);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule(null, startTime, endTime, 0);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount, String group) {
		schedule(null, startTime, endTime, 0, group);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, int repeatCount) {
		schedule(name, startTime, endTime, 0, 0L);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, int repeatCount, String group) {
		schedule(name, startTime, endTime, 0, 0L, group);
	}

	@Override
	public void schedule(Date startTime, int repeatCount, long repeatInterval) {
		schedule(null, startTime, NULLDATE, repeatCount, repeatInterval);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval);
	}

	@Override
	public void schedule(Date startTime, Date endTime, int repeatCount, long repeatInterval, String group) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval, group);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval) {
		schedule(name, startTime, endTime, repeatCount, repeatInterval, NULLSTRING);
	}

	@Override
	public void schedule(String name, Date startTime, Date endTime, int repeatCount, long repeatInterval, String group) {

		if (this.isValidExpression(startTime)) {

			if (name == null || name.trim().equals("")) {
				name = UUID.randomUUID().toString();
			}

			TriggerKey triggerKey = new TriggerKey(name, group);

			SimpleTriggerImpl trigger = new SimpleTriggerImpl();
			trigger.setKey(triggerKey);
			trigger.setJobName(jobDetail.getKey().getName());

			trigger.setStartTime(startTime);
			trigger.setEndTime(endTime);
			trigger.setRepeatCount(repeatCount);
			trigger.setRepeatInterval(repeatInterval);

			try {
				scheduler.addJob(jobDetail, true);
				if (scheduler.checkExists(triggerKey)) {
					scheduler.rescheduleJob(triggerKey, trigger);
				} else {
					scheduler.scheduleJob(trigger);
				}
			} catch (SchedulerException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	@Override
	public void schedule(Map<String, Object> map) {

		// 设置名称
		String name = MapUtils.getString(map, QuartzConstant.TRIGGERNAME);
		if (StringUtils.isEmpty(StringUtils.trim(name))) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
		}

		// 设置Trigger分组
		String group = MapUtils.getString(map, QuartzConstant.TRIGGERGROUP);
		if (StringUtils.isEmpty(group)) {
			group = Scheduler.DEFAULT_GROUP;
		}

		TriggerKey triggerKey = new TriggerKey(name, group);

		// 实例化SimpleTrigger
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		
		trigger.setJobName(jobDetail.getKey().getName());
		trigger.setKey(triggerKey);
		trigger.setRepeatInterval(1000L);

		// 设置开始时间
		String temp = MapUtils.getString(map, QuartzConstant.STARTTIME);
		if (StringUtils.isNotEmpty(temp)) {
			try {
				trigger.setStartTime(DateUtils.parseDate(temp, new String[] { "yyyy-MM-dd HH:mm" }));
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}

		// 设置结束时间
		temp = MapUtils.getString(map, QuartzConstant.ENDTIME);
		if (StringUtils.isNotEmpty(temp)) {
			try {
				trigger.setEndTime(DateUtils.parseDate(temp, new String[] { "yyyy-MM-dd HH:mm" }));
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}

		// 设置执行次数
		temp = MapUtils.getString(map, QuartzConstant.REPEATCOUNT);
		if (StringUtils.isNotEmpty(temp) && NumberUtils.toInt(temp) > 0) {
			trigger.setRepeatCount(NumberUtils.toInt(temp));
		}

		// 设置执行时间间隔
		temp = MapUtils.getString(map, QuartzConstant.REPEATINTERVEL);
		if (StringUtils.isNotEmpty(temp) && NumberUtils.toLong(temp) > 0) {
			trigger.setRepeatInterval(NumberUtils.toLong(temp) * 1000);
		}

		try {
			scheduler.addJob(jobDetail, true);
			if (scheduler.checkExists(triggerKey)) {
				scheduler.rescheduleJob(triggerKey, trigger);
			} else {
				scheduler.scheduleJob(trigger);
			}
		} catch (SchedulerException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void pauseTrigger(String triggerName) {
		pauseTrigger(triggerName, NULLSTRING);
	}

	@Override
	public void pauseTrigger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(new TriggerKey(triggerName, group));// 停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void resumeTrigger(String triggerName) {
		resumeTrigger(triggerName, NULLSTRING);
	}

	@Override
	public void resumeTrigger(String triggerName, String group) {
		try {
			scheduler.resumeTrigger(new TriggerKey(triggerName, group));// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean removeTrigdger(String triggerName) {
		return removeTrigdger(triggerName, NULLSTRING);
	}

	@Override
	public boolean removeTrigdger(String triggerName, String group) {
		TriggerKey triggerKey = new TriggerKey(triggerName, group);
		try {
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			return scheduler.unscheduleJob(triggerKey);// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isValidExpression(final CronExpression cronExpression) {

		CronTriggerImpl trigger = new CronTriggerImpl();
		trigger.setCronExpression(cronExpression);

		Date date = trigger.computeFirstFireTime(null);

		return date != null && date.after(new Date());
	}

	private boolean isValidExpression(final Date startTime) {

		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		trigger.setStartTime(startTime);

		Date date = trigger.computeFirstFireTime(null);

		return date != null && date.after(new Date());
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		 quartzDao.delete(id);
	}

	@Override
	public List<QrtzTriggers> findAll(Page page) {
		// TODO Auto-generated method stub
		return quartzDao.findAll();
	}

	@Override
	public List<QrtzTriggers> findByExample(
			Specification<QrtzTriggers> specification, Page page) {
		// TODO Auto-generated method stub 
		org.springframework.data.domain.Page<QrtzTriggers> springDataPage = quartzDao.findAll(specification, PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}

	@Override
	public QrtzTriggers get(Long id) {
		// TODO Auto-generated method stub
		return quartzDao.findOne(id);
	}

	@Override
	public void saveOrUpdate(QrtzTriggers qrtzTriggers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteQrtzJobDetails(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<QrtzJobDetails> findAllQrtzJobDetails(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QrtzJobDetails> findByExampleQrtzJobDetails(
			Specification<QrtzJobDetails> specification, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QrtzJobDetails getQrtzJobDetails(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QrtzJobDetails> getQrtzJobDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(QrtzJobDetails qrtzJobDetails) {
		// TODO Auto-generated method stub
		
	}

}
