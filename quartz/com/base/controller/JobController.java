package com.base.controller;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.QuartzConstant;
import com.base.entity.QrtzJob;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.service.QuartzJobService;
import com.base.service.SchedulerService;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;

@Controller
@RequestMapping("/scheduler/job")
public class JobController {
	private static final Logger log = LoggerFactory
			.getLogger(JobController.class);
	@Autowired
	private QuartzJobService quartzJobService;
	
	@Autowired
	private SchedulerService schedulerService;

	private static final String CREATE = "/scheduler/job/create";
	private static final String UPDATE = "/scheduler/job/update";
	private static final String LIST = "/scheduler/job/list";
	private static final String VIEW = "/scheduler/job/view";

	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df,
				true));
	}

	@RequiresPermissions("Scheduler:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}

	@Log(message = "添加了id={0}scheduler。")
	@RequiresPermissions("Scheduler:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid QrtzJob qrtzJob) {
		schedulerService.schedule(qrtzJob.getJobName(), qrtzJob.getJobGroup(), qrtzJob.getJobClass(), qrtzJob.getCronExpression()); 
		qrtzJob.setJobId(qrtzJob.getJobName());
		quartzJobService.saveOrUpdate(qrtzJob);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { qrtzJob.getId() }));
		return AjaxObject.newOk("Job添加成功！").toString();
	}

	@ModelAttribute("preloadScheduler")
	public QrtzJob preload(
			@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			QrtzJob qrtzJob = quartzJobService.get(id);
			return qrtzJob;
		}
		return null;
	}

	@RequiresPermissions("Scheduler:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		QrtzJob qrtzJob = quartzJobService.get(id);
		map.put("qrtzJob", qrtzJob);
		return UPDATE;
	}
 
	@Log(message = "修改了id={0}Scheduler的信息。")
	@RequiresPermissions("Scheduler:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(
			@Valid @ModelAttribute("preloadScheduler") QrtzJob qrtzJob) {
		/**
		 * 先删除，再增加
		 */
		schedulerService.removeTrigdger(qrtzJob.getJobName(), qrtzJob.getJobGroup());
		schedulerService.schedule(qrtzJob.getJobName(), qrtzJob.getJobGroup(), qrtzJob.getJobClass(), qrtzJob.getCronExpression());
		qrtzJob.setJobId(qrtzJob.getJobName());
		quartzJobService.saveOrUpdate(qrtzJob);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { qrtzJob.getId() }));
		return AjaxObject.newOk("Job修改成功！").toString();
	}
	
	@RequiresPermissions("Scheduler:edit")
	@RequestMapping(value = "/jobstatus", method = RequestMethod.POST)
	public @ResponseBody
	String jobstatus(String action, String triggerName, String group,Long id,
			Map<String, Object> map) {

		boolean rs = true;
		if (action != null && StringUtils.isNotBlank(triggerName)
				 ) {
			try {
				triggerName = URLDecoder.decode(String.valueOf(triggerName),
						"utf-8");
				group = URLDecoder.decode(String.valueOf(group), "utf-8");
				if (QuartzConstant.PAUSE.equals(action)) {
					schedulerService.pauseTrigger(triggerName, group);
					QrtzJob qrtz = quartzJobService.get(id);
					qrtz.setJobStatus(QuartzConstant.PAUSE_NUM);
					quartzJobService.saveOrUpdate(qrtz);
				} else if (QuartzConstant.RESUME.equals(action)) {
					schedulerService.removeTrigdger(triggerName);
					schedulerService.resumeTrigger(triggerName, group);
					QrtzJob qrtz = quartzJobService.get(id);
					qrtz.setJobStatus(QuartzConstant.RESUME_NUM);
					quartzJobService.saveOrUpdate(qrtz);
				} else if (QuartzConstant.REMOVE.equals(action)) { 
					schedulerService.removeTrigdger(triggerName, group);
					quartzJobService.delete(id);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				rs = false;
			}
		}
		return rs ? "0" : "1";
	}

	@Log(message = "删除了id={0}Job。")
	@RequiresPermissions("Scheduler:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		QrtzJob qrtzJob = quartzJobService.get(id);
		schedulerService.removeTrigdger(qrtzJob.getJobName(), qrtzJob.getJobGroup());
		quartzJobService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { id }));
		return AjaxObject.newOk("Job删除成功！").setCallbackType("")
				.toString();
	}

	@Log(message = "批量删除了id={0}Scheduler。")
	@RequiresPermissions("Scheduler:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			QrtzJob qrtzJob = quartzJobService.get(ids[i]); 
			schedulerService.removeTrigdger(qrtzJob.getJobName(), qrtzJob.getJobGroup());
			quartzJobService.delete(qrtzJob.getId());
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("Job删除成功！").setCallbackType("")
				.toString();
	}

	
	
	@RequiresPermissions("Scheduler:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(ServletRequest request, Page page,
			Map<String, Object> map)
	{
		Specification<QrtzJob> specification = DynamicSpecifications
				.bySearchFilter(request, QrtzJob.class);
		List<QrtzJob> qrtzJobs = quartzJobService
				.findByExample(specification, page);

		map.put("page", page);
		map.put("qrtzJobs", qrtzJobs);

		return LIST;
	}
	 
	@RequiresPermissions("Scheduler:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map) {
		QrtzJob qrtzJob = quartzJobService.get(id);
		map.put("qrtzJob", qrtzJob);
		return VIEW;
	}

}
