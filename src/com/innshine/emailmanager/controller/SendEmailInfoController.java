/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.emailmanager.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.shiro.ShiroUser;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.entity.main.User;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.innshine.emailmanager.entity.SendEmailInfo;
import com.innshine.emailmanager.service.SendEmailInfoService;
import com.utils.DateUtils;
import com.utils.SecurityUtils;

@Controller
@RequestMapping("/management/emailManager")
public class SendEmailInfoController
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailInfoController.class);
	@Autowired
	private SendEmailInfoService sendEmailInfoService;
	
	private static final String CREATE = "management/send_email/create";
	private static final String UPDATE = "management/send_email/update";
	private static final String LIST = "management/send_email/list";
	private static final String VIEW = "management/send_email/view";
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}接收人信息成功。")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	String create(@Valid SendEmailInfo sendEmailInfo)
	{
		modiftyAttr(sendEmailInfo);
		sendEmailInfoService.saveOrUpdate(sendEmailInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { sendEmailInfo.getId() }));
		return AjaxObject.newOk("接收人信息添加成功！").toString();
	}
	
	/**
	 * 更新一些隐藏属性
	 * @param sendEmailInfo
	 */
	private void modiftyAttr(SendEmailInfo sendEmailInfo)
	{
		ShiroUser shiroUser = SecurityUtils.getShiroUser();
		if(null != shiroUser)
		{
			User user = shiroUser.getUser();
			if(null != user)
			{
				sendEmailInfo.setUserId(user.getId());
				sendEmailInfo.setUserName(user.getUsername());
			}
			
			sendEmailInfo.setUserIpAddress(shiroUser.getIpAddress());
			sendEmailInfo.setUpdateTime(DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		}
		
	}

	@ModelAttribute("preloadSendEmailInfo")
	public SendEmailInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			SendEmailInfo sendEmailInfo = sendEmailInfoService.get(id);
			return sendEmailInfo;
		}
		return null;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		SendEmailInfo sendEmailInfo = sendEmailInfoService.get(id);
		map.put("sendEmailInfo", sendEmailInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}接收人的信息。")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadSendEmailInfo") SendEmailInfo sendEmailInfo)
	{	
		modiftyAttr(sendEmailInfo);
		sendEmailInfoService.saveOrUpdate(sendEmailInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { sendEmailInfo.getId() }));
		return AjaxObject.newOk("接收人修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}接收人。")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		sendEmailInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("接收人删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}接收人。")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			SendEmailInfo sendEmailInfo = sendEmailInfoService.get(ids[i]);
			sendEmailInfoService.delete(sendEmailInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("接收人删除成功！").setCallbackType("").toString();
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<SendEmailInfo> specification = DynamicSpecifications.bySearchFilter(request, SendEmailInfo.class);
		List<SendEmailInfo> sendEmailInfos = sendEmailInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("sendEmailInfos", sendEmailInfos);
		
		return LIST;
	}
	
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		SendEmailInfo sendEmailInfo = sendEmailInfoService.get(id);
		map.put("sendEmailInfo", sendEmailInfo);
		return VIEW;
	}
}
