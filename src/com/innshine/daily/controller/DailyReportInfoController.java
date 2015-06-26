/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.daily.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.innshine.daily.entity.DailyReportInfo;
import com.innshine.daily.service.DailyReportInfoService;
import com.innshine.daily.utils.Excel2HtmlUtil;
import com.innshine.productinfo.utils.ExcelFileUtils;

@Controller
@RequestMapping("/management/dailyReport")
public class DailyReportInfoController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyReportInfoController.class);
	
	@Autowired
	private DailyReportInfoService dailyReportInfoService;
	
	private static final String CREATE = "management/daily_report/create";
	private static final String UPDATE = "management/daily_report/update";
	private static final String LIST = "management/daily_report/list";
	private static final String VIEW = "management/daily_report/view";

	private static final String PREVIEW = "management/daily_report/preview";
	
	@RequiresPermissions("DailyReportInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}DailyReportInfo。")
	@RequiresPermissions("DailyReportInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid DailyReportInfo dailyReportInfo)
	{
		dailyReportInfoService.saveOrUpdate(dailyReportInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { dailyReportInfo.getId() }));
		return AjaxObject.newOk("DailyReportInfo添加成功！").toString();
	}
	
	@ModelAttribute("preloadDailyReportInfo")
	public DailyReportInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			DailyReportInfo dailyReportInfo = dailyReportInfoService.get(id);
			return dailyReportInfo;
		}
		return null;
	}
	
	@RequiresPermissions("DailyReportInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		DailyReportInfo dailyReportInfo = dailyReportInfoService.get(id);
		map.put("dailyReportInfo", dailyReportInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}DailyReportInfo的信息。")
	@RequiresPermissions("DailyReportInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadDailyReportInfo") DailyReportInfo dailyReportInfo)
	{
		dailyReportInfoService.saveOrUpdate(dailyReportInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { dailyReportInfo.getId() }));
		return AjaxObject.newOk("DailyReportInfo修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}DailyReportInfo。")
	@RequiresPermissions("DailyReportInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		dailyReportInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("DailyReportInfo删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}DailyReportInfo。")
	@RequiresPermissions("DailyReportInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			DailyReportInfo dailyReportInfo = dailyReportInfoService.get(ids[i]);
			dailyReportInfoService.delete(dailyReportInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("DailyReportInfo删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("DailyReportInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<DailyReportInfo> specification = DynamicSpecifications.bySearchFilter(request,
				DailyReportInfo.class);
		List<DailyReportInfo> dailyReportInfos = dailyReportInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("dailyReportInfos", dailyReportInfos);
		// map.put("emailInfos", sendEmailInfoService.findAll());
		
		return LIST;
	}
	
	@RequiresPermissions("DailyReportInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		DailyReportInfo dailyReportInfo = dailyReportInfoService.get(id);
		map.put("dailyReportInfo", dailyReportInfo);
		return VIEW;
	}
	
	@RequiresPermissions("DailyReportInfo:view")
	@RequestMapping(value = "/downLoad/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public void downloadFile(@PathVariable Long id, Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Throwable
	{
		DailyReportInfo dailyReportInfo = dailyReportInfoService.get(id);
		
		if (null != dailyReportInfo)
		{
			String fileName = dailyReportInfo.getFileCreateName();
			String fileSavePath = dailyReportInfo.getDefaultSavePath();
			String filePath = fileSavePath + File.separator + fileName;
			if (new File(filePath).exists())
			{
				ExcelFileUtils.excelExport(request, response, filePath, null, false);
			}
			else
			{
				
				throw new FileNotFoundException("对不起，导出文件失败，文件不存在！");
			}
		}
	}
	
	@RequiresPermissions("DailyReportInfo:view")
	@RequestMapping(value = "/preview/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String previewFile(@PathVariable Long id, Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) throws Throwable
	{
		DailyReportInfo dailyReportInfo = dailyReportInfoService.get(id);
		
		if (null != dailyReportInfo)
		{
			String fileName = dailyReportInfo.getFileCreateName();
			String fileSavePath = dailyReportInfo.getDefaultSavePath();
			String filePath = fileSavePath + File.separator + fileName;
			if (new File(filePath).exists())
			{
				String content = Excel2HtmlUtil.excel07ToHtml(filePath);
				map.put("ExcelToHtml", content);
			}
			else
			{
				//map.put("error_message", "对不起，预览失败，文件不存在！"");
				throw new FileNotFoundException("对不起，预览失败，文件不存在！");
			}
		}
		
		map.put("id", id);
		
		return PREVIEW;
	}
}
