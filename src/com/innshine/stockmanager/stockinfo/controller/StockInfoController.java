/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stockmanager.stockinfo.controller;

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
import com.innshine.stockmanager.stockinfo.entity.StockInfo;
import com.innshine.stockmanager.stockinfo.service.StockInfoService;

@Controller
@RequestMapping("/management/stockInfo")
public class StockInfoController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StockInfoController.class);
	
	@Autowired
	private StockInfoService stockInfoService;
	
	private static final String CREATE = "management/stockmanager/stock_info/create";
	private static final String UPDATE = "management/stockmanager/stock_info/update";
	private static final String LIST = "management/stockmanager/stock_info/list";
	private static final String VIEW = "management/stockmanager/stock_info/view";
	
	@RequiresPermissions("StockInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}StockInfo。")
	@RequiresPermissions("StockInfo:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid StockInfo stockInfo)
	{
		stockInfoService.saveOrUpdate(stockInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { stockInfo.getId() }));
		return AjaxObject.newOk("StockInfo添加成功！").toString();
	}
	
	@ModelAttribute("preloadStockInfo")
	public StockInfo preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			StockInfo stockInfo = stockInfoService.get(id);
			return stockInfo;
		}
		return null;
	}
	
	@RequiresPermissions("StockInfo:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		StockInfo stockInfo = stockInfoService.get(id);
		map.put("stockInfo", stockInfo);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}StockInfo的信息。")
	@RequiresPermissions("StockInfo:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadStockInfo") StockInfo stockInfo)
	{
		stockInfoService.saveOrUpdate(stockInfo);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { stockInfo.getId() }));
		return AjaxObject.newOk("StockInfo修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}StockInfo。")
	@RequiresPermissions("StockInfo:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		stockInfoService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("StockInfo删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}StockInfo。")
	@RequiresPermissions("StockInfo:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			StockInfo stockInfo = stockInfoService.get(ids[i]);
			stockInfoService.delete(stockInfo.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("StockInfo删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("StockInfo:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<StockInfo> specification = DynamicSpecifications.bySearchFilter(request, StockInfo.class);
		List<StockInfo> stockInfos = stockInfoService.findByExample(specification, page);
		
		map.put("page", page);
		map.put("stockInfos", stockInfos);
		
		return LIST;
	}
	
	@RequiresPermissions("StockInfo:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		StockInfo stockInfo = stockInfoService.get(id);
		map.put("stockInfo", stockInfo);
		return VIEW;
	}
}
