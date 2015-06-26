/**
 * There are <a href="https://github.com/ketayao/keta-custom">keta-custom</a> code generation
 */
package api.taobao.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
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

import api.taobao.entity.Trade;
import api.taobao.service.TradesSoldGetService;
import api.taobao.utils.ExcelUtils;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.util.dwz.AjaxObject;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.util.persistence.SearchFilter;
import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.productinfo.service.ProductInfoService;
import com.innshine.shopAnalyse.util.ExcelModelTool;

@Controller
@RequestMapping("/management/taobaoTradeSoldGet")
public class TaobaoTradeController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TaobaoTradeController.class);
	
	@Autowired
	private TradesSoldGetService tTradeService;
	
	@Autowired
	private ProductInfoService productInfoService;
	
	private static final String CREATE = "/management/taobao_trade/create";
	private static final String UPDATE = "/management/taobao_trade/update";
	private static final String LIST = "/management/taobao_trade/list";
	private static final String VIEW = "/management/taobao_trade/view";
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
	}
	
	@RequiresPermissions("TradeManagement:save")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String preCreate(Map<String, Object> map)
	{
		return CREATE;
	}
	
	@Log(message = "添加了id={0}。")
	@RequiresPermissions("TradeManagement:save")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody String create(@Valid Trade tTrade)
	{
		tTradeService.saveOrUpdate(tTrade);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { tTrade.getId() }));
		return AjaxObject.newOk("添加成功！").toString();
	}
	
	@ModelAttribute("preloadTrade")
	public Trade preload(@RequestParam(value = "id", required = false) Long id)
	{
		if (id != null)
		{
			Trade Trade = tTradeService.get(id);
			return Trade;
		}
		return null;
	}
	
	@RequiresPermissions("TradeManagement:edit")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
	public String preUpdate(@PathVariable Long id, Map<String, Object> map)
	{
		Trade Trade = tTradeService.get(id);
		map.put("Trade", Trade);
		return UPDATE;
	}
	
	@Log(message = "修改了id={0}的信息。")
	@RequiresPermissions("TradeManagement:edit")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody String update(@Valid @ModelAttribute("preloadTrade") Trade Trade)
	{
		tTradeService.saveOrUpdate(Trade);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Trade.getId() }));
		return AjaxObject.newOk("订单修改成功！").toString();
	}
	
	@Log(message = "删除了id={0}订单。")
	@RequiresPermissions("TradeManagement:delete")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody String delete(@PathVariable Long id)
	{
		tTradeService.delete(id);
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { id }));
		return AjaxObject.newOk("订单删除成功！").setCallbackType("").toString();
	}
	
	@Log(message = "批量删除了id={0}订单。")
	@RequiresPermissions("TradeManagement:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody String deleteMany(Long[] ids)
	{
		for (int i = 0; i < ids.length; i++)
		{
			Trade Trade = tTradeService.get(ids[i]);
			tTradeService.delete(Trade.getId());
		}
		
		LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] { Arrays.toString(ids) }));
		return AjaxObject.newOk("订单删除成功！").setCallbackType("").toString();
	}
	
	@RequiresPermissions("TradeManagement:view")
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(ServletRequest request, Page page, Map<String, Object> map)
	{
		Specification<Trade> specification = DynamicSpecifications.bySearchFilter(request, Trade.class);
		List<Trade> Trades = tTradeService.findByExample(specification, page);
		

		map.put("page", page);
		map.put("Trades", Trades);
		map.put("taobaoTradeStatusMap",ExportFieldsConfigUtils.getInstance().getFieldsConfig("taobao.trade.status"));
		
		return LIST;
	}
	
	@RequiresPermissions("TradeManagement:view")
	@RequestMapping(value = "/view/{id}", method = { RequestMethod.GET })
	public String view(@PathVariable Long id, Map<String, Object> map)
	{
		Trade Trade = tTradeService.get(id);
		map.put("Trade", Trade);
		map.put("taobaoTradeStatusMap",ExportFieldsConfigUtils.getInstance().getFieldsConfig("taobao.trade.status"));
		return VIEW;
	}
	
	
	
	@RequestMapping(value = "/excelExport", method = { RequestMethod.GET, RequestMethod.POST })
	public void exportDataFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Collection<SearchFilter> filters = DynamicSpecifications.genSearchFilter(request);
		
		// 处理选框操作导出
		productInfoService.modifyValuesByFieldName(filters);
		
		Specification<Trade> specification = DynamicSpecifications.bySearchFilter(Trade.class, filters);
		List<Trade> trades = tTradeService.findByExample(specification);
		
		Workbook workbook = tTradeService.exportExcel(trades);
		
		String fileName = String.valueOf(System.currentTimeMillis());
		
		ExcelModelTool.setResponses(request, response, fileName+ ExcelUtils.getSuffix(fileName, workbook));
		
		OutputStream out = response.getOutputStream();
		workbook.write(out);
	}
}
