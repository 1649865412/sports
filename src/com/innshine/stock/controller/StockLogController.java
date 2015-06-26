/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package com.innshine.stock.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletRequest;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.base.service.BrandService;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.entity.main.Brand;
import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.innshine.setting.service.GeneralSettingService;
import com.innshine.stock.entity.StockLog;
import com.innshine.stock.service.StockLogService;
import com.innshine.stock.utils.StockUtils;

@Controller
@RequestMapping("//management/stockmanager/stock_log")
public class StockLogController {
    private static final Logger log = LoggerFactory.getLogger(StockLogController.class);
	@Autowired
	private StockLogService stockLogService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private GeneralSettingService generalSettingService;
	
	
	private static final String CREATE = "/management/stockmanager/stock_log/create";
	private static final String UPDATE = "/management/stockmanager/stock_log/update";
	private static final String LIST = "/management/stockmanager/stock_log/list";
	private static final String VIEW = "/management/stockmanager/stock_log/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, 
				new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("StockLog:save")
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String preCreate(Map<String, Object> map) {
		return CREATE;
	}
	
	@Log(message="添加了id={0}StockLog。")
	@RequiresPermissions("StockLog:save")
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody String create(@Valid StockLog stockLog) {
		stockLogService.saveOrUpdate(stockLog);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stockLog.getId()}));
		return AjaxObject.newOk("StockLog添加成功！").toString();
	}
	
	
	@ModelAttribute("preloadStockLog")
	public StockLog preload(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			StockLog stockLog = stockLogService.get(id);
			return stockLog;
		}
		return null;
	}
	
	@RequiresPermissions("StockLog:edit")
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map) {
		StockLog stockLog = stockLogService.get(id);
		map.put("stockLog", stockLog);
		return UPDATE;
	}
	
	@Log(message="修改了id={0}StockLog的信息。")
	@RequiresPermissions("StockLog:edit")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadStockLog")StockLog stockLog) {
		stockLogService.saveOrUpdate(stockLog);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{stockLog.getId()}));
		return AjaxObject.newOk("StockLog修改成功！").toString();
	}

	@Log(message="删除了id={0}StockLog。")
	@RequiresPermissions("StockLog:delete")
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id) {
		stockLogService.delete(id);

		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{id}));
		return AjaxObject.newOk("StockLog删除成功！").setCallbackType("").toString();
	}
	
	@Log(message="批量删除了id={0}StockLog。")
	@RequiresPermissions("StockLog:delete")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			StockLog stockLog = stockLogService.get(ids[i]);
			stockLogService.delete(stockLog.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[]{Arrays.toString(ids)}));
		return AjaxObject.newOk("StockLog删除成功！").setCallbackType("").toString();
	}

	@RequiresPermissions("StockLog:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(ServletRequest request, Page page, Map<String, Object> map) throws Exception {
		
		Specification<StockLog> specification = DynamicSpecifications.bySearchFilter(request, StockLog.class);
		List<StockLog> stockLogs = stockLogService.findByExample(specification, page);
		map.put("allBrandList",  generalSettingService.getBrandListAll());
		
		map.put("page", page);
		map.put("stockLogs", stockLogs);
		map.put("stockType", StockUtils.stockType);
		
		List<Brand> brands = brandService.findAll();
		map.put("brands", brands);
		
		return LIST;
	}
	
	@RequiresPermissions("StockLog:view")
	@RequestMapping(value="/view/{id}", method={RequestMethod.GET})
	public String view(@PathVariable Long id, Map<String, Object> map) {
		StockLog stockLog = stockLogService.get(id);
		map.put("stockLog", stockLog);
		return VIEW;
	}
}
