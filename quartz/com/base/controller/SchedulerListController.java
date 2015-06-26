package com.base.controller;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
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
import com.base.entity.QrtzTriggers;
import com.base.entity.main.Brand;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.service.BrandService;
import com.base.service.SchedulerService;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.innshine.stock.controller.StockLogController;
import com.innshine.stock.entity.StockLog;
import com.innshine.stock.service.StockLogService;
import com.innshine.stock.utils.StockUtils;
import com.utils.DateUtils;

@Controller
@RequestMapping("/schedulerlist/monitor")
public class SchedulerListController {
	private static final Logger log = LoggerFactory
			.getLogger(SchedulerController.class);
	@Autowired
	private SchedulerService schedulerService;
	// private SchedulerFactoryBean schedulerFactoryBean;
	 
	private StdScheduler scheduler;

	private static final String CREATE = "/scheduler/monitor/create";
	private static final String UPDATE = "/scheduler/monitor/update";
	private static final String LIST = "/scheduler/monitor/list";
	private static final String VIEW = "/scheduler/monitor/view";

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
	String create(@Valid QrtzTriggers qrtzTriggers) {
		schedulerService.saveOrUpdate(qrtzTriggers);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { qrtzTriggers.getId() }));
		return AjaxObject.newOk("Scheduler添加成功！").toString();
	}

	@ModelAttribute("preloadScheduler")
	public QrtzTriggers preload(
			@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			QrtzTriggers qrtzTriggers = schedulerService.get(id);
			return qrtzTriggers;
		}
		return null;
	}

	@RequiresPermissions("Scheduler:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		QrtzTriggers qrtzTriggers = schedulerService.get(id);
		map.put("qrtzTriggers", qrtzTriggers);
		return UPDATE;
	}

	@RequiresPermissions("Scheduler:edit")
	@RequestMapping(value = "/jobstatus", method = RequestMethod.POST)
	public @ResponseBody
	String jobstatus(String action, String triggerName, String group,
			Map<String, Object> map) {

		boolean rs = true;
		if (action != null && StringUtils.isNotBlank(triggerName)
				&& StringUtils.isNotBlank(group)) {
			try {
				triggerName = URLDecoder.decode(String.valueOf(triggerName),
						"utf-8");
				group = URLDecoder.decode(String.valueOf(group), "utf-8");

				JobKey jobKey = JobKey.jobKey(triggerName, group);
				if (QuartzConstant.PAUSE.equals(action)) {
					scheduler.pauseJob(jobKey);
				} else if (QuartzConstant.RESUME.equals(action)) {
					scheduler.resumeJob(jobKey);
					// schedulerService.resumeTrigger(triggerName, group);
				} else if (QuartzConstant.REMOVE.equals(action)) {
					scheduler.deleteJob(jobKey);
					// schedulerService.removeTrigdger(triggerName, group);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				rs = false;
			}
		}
		return rs ? "0" : "1";
	}

	@Log(message = "修改了id={0}Scheduler的信息。")
	@RequiresPermissions("Scheduler:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(
			@Valid @ModelAttribute("preloadScheduler") QrtzTriggers qrtzTriggers) {
		schedulerService.saveOrUpdate(qrtzTriggers);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { qrtzTriggers.getId() }));
		return AjaxObject.newOk("Scheduler修改成功！").toString();
	}

	@Log(message = "删除了id={0}Scheduler。")
	@RequiresPermissions("Scheduler:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id) {
		schedulerService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { id }));
		return AjaxObject.newOk("Scheduler删除成功！").setCallbackType("")
				.toString();
	}

	@Log(message = "批量删除了id={0}Scheduler。")
	@RequiresPermissions("Scheduler:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			QrtzTriggers qrtzTriggers = schedulerService.get(ids[i]);
			schedulerService.delete(qrtzTriggers.getId());
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("Scheduler删除成功！").setCallbackType("")
				.toString();
	}

	@RequiresPermissions("Scheduler:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(ServletRequest request, Page page,
			Map<String, Object> map) {

		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		List<QrtzTriggers> queryList = new ArrayList<QrtzTriggers>();
		try {
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);

			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler
						.getTriggersOfJob(jobKey);

				for (Trigger tr : triggers) {
					QrtzTriggers qt = new QrtzTriggers();
					qt.setTriggerName(jobKey.getName());
					qt.setTriggerGroup(jobKey.getGroup());
					qt.setStartTime(DateUtils.format(tr.getStartTime()));
					qt.setEndTime(DateUtils.format(tr.getEndTime()));
					qt.setDescription(tr.getDescription());
					qt.setCalendarName(tr.getCalendarName());
					qt.setNextFireTime(DateUtils.format(tr.getNextFireTime()));
					qt.setPrevFireTime(DateUtils.format(tr
							.getPreviousFireTime()));
					qt.setPriority(tr.getPriority());
					TriggerState triggerState = scheduler.getTriggerState(tr
							.getKey());

					qt.setStatu(triggerState.name());

					queryList.add(qt);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		map.put("qrtzTriggerss", queryList);

		return LIST;
	}

	@RequiresPermissions("Scheduler:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map) {
		QrtzTriggers qrtzTriggers = schedulerService.get(id);
		map.put("qrtzTriggers", qrtzTriggers);
		return VIEW;
	}

}
