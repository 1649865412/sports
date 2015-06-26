/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockloginfo.controller;

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

import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.innshine.stockmanager.stockloginfo.entity.StockLogInfo;
import com.innshine.stockmanager.stockloginfo.service.StockLogInfoService;

@Controller
@RequestMapping("/management/stockLogInfo")
public class StockLogInfoController {
    private static final Logger log = LoggerFactory.getLogger(StockLogInfoController.class);
	@Autowired
	private StockLogInfoService stockLogInfoService;
	
	private static final String CREATE = "management/stockmanager/stock_log_info/create";
	private static final String UPDATE = "management/stockmanager/stock_log_info/update";
	private static final String LIST = "management/stockmanager/stock_log_info/list";
	private static final String VIEW = "management/stockmanager/stock_log_info/view";
	
	@RequiresPermissions("StockLogInfo:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了id={0}StockLogInfo。")
	@RequiresPermissions("StockLogInfo:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid StockLogInfo stockLogInfo) {
		stockLogInfoService.saveOrUpdate(stockLogInfo);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stockLogInfo.getId()}));
		return AjaxObject.newOk("StockLogInfo添加成功！").toString();
	}
	
	@ModelAttribute("preloadStockLogInfo")
	public StockLogInfo preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			StockLogInfo stockLogInfo = stockLogInfoService.get(id);
			return stockLogInfo;
		}
		return null;
	}
	
	@RequiresPermissions("StockLogInfo:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		StockLogInfo stockLogInfo = stockLogInfoService.get(id);
		map.put("stockLogInfo", stockLogInfo);
		return UPDATE;
	}
	
	@Log(message="修改了id={0}StockLogInfo的信息。")
	@RequiresPermissions("StockLogInfo:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadStockLogInfo")StockLogInfo stockLogInfo) {
		stockLogInfoService.saveOrUpdate(stockLogInfo);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stockLogInfo.getId()}));
		return AjaxObject.newOk("StockLogInfo修改成功！").toString();
	}

	@Log(message="删除了id={0}StockLogInfo。")
	@RequiresPermissions("StockLogInfo:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		stockLogInfoService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{id}));
		return AjaxObject.newOk("StockLogInfo删除成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量删除了id={0}StockLogInfo。")
	@RequiresPermissions("StockLogInfo:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			StockLogInfo stockLogInfo = stockLogInfoService.get(ids[i]);
			stockLogInfoService.delete(stockLogInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
		return AjaxObject.newOk("StockLogInfo删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("StockLogInfo:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) {
		Specification<StockLogInfo> specification = DynamicSpecifications.bySearchFilter(request, StockLogInfo.class);
		List<StockLogInfo> stockLogInfos = stockLogInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("stockLogInfos", stockLogInfos);

		return LIST;
	}
	
	@RequiresPermissions({"StockLogInfo:view","StockInfoManager:show","StockLogInfo:show"})
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		StockLogInfo stockLogInfo = stockLogInfoService.get(id);
		map.put("stockLogInfo", stockLogInfo);
		return VIEW;
	}
}
