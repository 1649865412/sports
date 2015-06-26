/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.setting.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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

import com.base.entity.main.Brand;
import com.base.entity.main.User;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.shiro.ShiroUser;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.innshine.setting.entity.GeneralSetting;
import com.innshine.setting.service.GeneralSettingService;
import com.utils.SecurityUtils;

@Controller
@RequestMapping("//management/setting")
public class GeneralSettingController
{
	private static final Logger log = LoggerFactory
			.getLogger(GeneralSettingController.class);
	@Autowired
	private GeneralSettingService generalSettingService;

	private static final String CREATE = "/management/setting/create";
	private static final String UPDATE = "/management/setting/update";
	private static final String LIST = "/management/setting/list";
	private static final String VIEW = "/management/setting/view";

	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df,
				true));
	}

	@RequiresPermissions("GeneralSetting:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map) throws Exception
	{
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User u = shiroUser.getUser();
		List<Brand> allBrandList = generalSettingService.getBrandListAll();
		List<Integer> brandListId = getBrandList(u.getUseBrandId());
		map.put("allBrandList", allBrandList);
		map.put("brandListId", brandListId);
		map = defaultValue(map, allBrandList, brandListId);
		return CREATE;
	}

	@RequiresPermissions("GeneralSetting:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
			throws Exception
	{
		GeneralSetting generalSetting = generalSettingService.get(id);
		map.put("generalSetting", generalSetting);
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User u = shiroUser.getUser();
		List<Brand> allBrandList = generalSettingService.getBrandListAll();
		List<Integer> brandListId = getBrandList(u.getUseBrandId());
		map.put("allBrandList", allBrandList);
		map.put("brandListId", brandListId);
		map = defaultValue(map, allBrandList, brandListId);
		return UPDATE;
	}

	public Map<String, Object> defaultValue(Map<String, Object> map,
			List<Brand> allBrandList, List<Integer> brandListId)
	{
		if (brandListId != null)
		{
			if (brandListId.size() != 0)
			{
				map.put("defaultValue", brandListId.get(0));
			} else
			{
				map.put("defaultValue", allBrandList.get(0).getId());
			}
		} else
		{
			map.put("defaultValue", allBrandList.get(0).getId());
		}

		return map;
	}

	@Log(message = "添加了id={0}GeneralSetting。")
	@RequiresPermissions("GeneralSetting:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid GeneralSetting generalSetting)
	{
		
		GeneralSetting GeneralSettingNow = generalSettingService.getByBranId(generalSetting.getBrandId());
		if (GeneralSettingNow != null)
		{
			generalSettingService.delete(GeneralSettingNow.getId());
		}
		generalSetting
				.setBrandName(generalSetting.getBrandName().split("_")[0]);
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User u = shiroUser.getUser();
		generalSetting.setUpdatedTime(new Date());
		generalSetting.setUserName(u.getUsername());
		generalSetting.setUpdatedUser(u.getId().intValue());
		generalSetting.setRoleId(u.getUserRoles().get(0).getRole().getId()
				.intValue());
		generalSetting.setRoleName(u.getUserRoles().get(0).getRole().getName());
		generalSettingService.saveOrUpdate(generalSetting);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { generalSetting.getId() }));
		return AjaxObject.newOk("GeneralSetting添加成功！").toString();
	}

	@Log(message = "修改了id={0}GeneralSetting的信息。")
	@RequiresPermissions("GeneralSetting:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	String update(
			@Valid @ModelAttribute("preloadGeneralSetting") GeneralSetting generalSetting)
	{
		
		GeneralSetting GeneralSettingNow = generalSettingService.get(generalSetting.getId());
		if (GeneralSettingNow != null)
		{
			generalSettingService.delete(GeneralSettingNow.getId());
		}
		generalSetting
				.setBrandName(generalSetting.getBrandName().split("_")[0]);
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		User u = shiroUser.getUser();
		generalSetting.setUpdatedTime(new Date());
		generalSetting.setUpdatedUser(u.getId().intValue());
		generalSetting.setUserName(u.getUsername());
		generalSetting.setRoleId(u.getUserRoles().get(0).getRole().getId()
				.intValue());
		generalSetting.setRoleName(u.getUserRoles().get(0).getRole().getName());
		generalSettingService.saveOrUpdate(generalSetting);
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { generalSetting.getId() }));
		return AjaxObject.newOk("GeneralSetting修改成功！").toString();
	}

	@ModelAttribute("preloadGeneralSetting")
	public GeneralSetting preload(
			@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			GeneralSetting generalSetting = generalSettingService.get(id);
			return generalSetting;
		}
		return null;
	}

	@Log(message = "删除了id={0}GeneralSetting。")
	@RequiresPermissions("GeneralSetting:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody
	String delete(@PathVariable Long id)
	{
		generalSettingService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { id }));
		return AjaxObject.newOk("GeneralSetting删除成功！").setCallbackType("")
				.toString();
	}

	@Log(message = "批量删除了id={0}GeneralSetting。")
	@RequiresPermissions("GeneralSetting:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			GeneralSetting generalSetting = generalSettingService.get(ids[i]);
			generalSettingService.delete(generalSetting.getId());
		}

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(
				new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("GeneralSetting删除成功！").setCallbackType("")
				.toString();
	}

	@RequiresPermissions("GeneralSetting:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String list(ServletRequest request, Page page,
			Map<String, Object> map)
	{
		Specification<GeneralSetting> specification = DynamicSpecifications
				.bySearchFilter(request, GeneralSetting.class);
		List<GeneralSetting> generalSettings = generalSettingService
				.findByExample(specification, page);

		map.put("page", page);
		map.put("generalSettings", generalSettings);

		return LIST;
	}

	@RequiresPermissions("GeneralSetting:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		GeneralSetting generalSetting = generalSettingService.get(id);
		map.put("generalSetting", generalSetting);
		return VIEW;
	}

	public List<Integer> getBrandList(String str)
	{
		List list = new ArrayList();
		if (str != null)
		{
			list = java.util.Arrays.asList(str.split(","));

		}
		return list;
	}
}
