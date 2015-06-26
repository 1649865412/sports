package com.innshine.stockAnalyse.controler;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.log.Log;
import com.base.util.dwz.Page;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.stockAnalyse.util.OtherTool;
import com.innshine.stockAnalyse.Excelmodel.StockTimeAnalyseExcelModel;
import com.innshine.stockAnalyse.entity.StockTime;
import com.innshine.stockAnalyse.entity.StockTimeList;
import com.innshine.stockAnalyse.service.StockProductionAnalyseService;
import com.innshine.stockAnalyse.util.Constant;
import com.innshine.stockAnalyse.util.DateUtil;
import com.innshine.stockAnalyse.util.TimeTool;


/**
 * 
 *  <code>库存时间纵向分析</code>
 *  <p>
 *  <p>Copyright  2014 All right reserved.
 *  @author 杨荣忠 时间 2014-9-23 下午04:48:25	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@Controller
@RequestMapping("/management/suitTimeAnalyse")
public class StockTimeAnaylseControler
{

	/**
	 * 库存分析公共业务类
	 * 
	 */
	@Autowired
	private StockProductionAnalyseService suitProductionAnalyseService;
	
	private static final String PREVIEW = "management/stockAnalyseTime/preview";

	private static final String TIMELIST = "management/stockAnalyseTime/timelist";

	/**
	 * 库存分析模块(时间维度分析)(R日M月Z季Y年)
	 */
	@Log(message = "{0}在库存分析模块时间维度查询")
	@RequestMapping(value = "/timeAnalyse")
	public String timeAnlyse(HttpServletRequest request, Page page,
			Map<String, Object> map, @Valid StockTime suitTime)
			throws Exception
	{
		Map<String, Object> param = new HashMap();

		List<StockTimeList> result = suitProductionAnalyseService
				.getTimeGetList(param, suitTime, page);

		map.put("ParamAll", Constant.ParamAll);
		map.put("page", page);
		map.put("numList", OtherTool.getNum(1, 12));
		map.put("result", result);
		map.put("suitTime", suitTime);
		return TIMELIST;
	}

	/**
	 * 库存分析模块(时间维度分析导出)
	 */
	@Log(message = "{0}在库存分析模块导出{1},时间类型是：{2}查询范围在：{3},同比周期为：{4}")
	@RequestMapping(value = "/timeAnalyseExcelPOI")
	public void timeAnalyseExcelPOI(HttpServletRequest request,
			Map<String, Object> map, HttpServletResponse response,
			@Valid StockTime suitTime) throws Exception
	{
		String SuitselectTimeType = suitTime.getSuitselectTimeType();
		String[] tableDay = null;// 库存分析导出表 纵列
		double[][] data = null;// 图点值(R日M月Z季Y年)

		Map<String, List<Object[]>> dataMap = Collections
				.synchronizedMap(new LinkedHashMap());// 表格值(R日M月Z季Y年)

		Map<String, Object> param = new HashMap();

		Map<String, String> timemapNow = TimeTool.getMapTime(suitTime, 0);
		String firstTime = timemapNow.get("beginTime");
		String endTime = timemapNow.get("endTime");

		Map<String, String[]> Xline_TypeValue = OtherTool.getXline_TypeValue(
				suitTime, timemapNow);
		String[] typeValue = Xline_TypeValue.get("typeValue");// 图例
		String[] xValue = Xline_TypeValue.get("xValue");// 横轴

		if (SuitselectTimeType.equals("M") || SuitselectTimeType.equals("R") || "W".equalsIgnoreCase(SuitselectTimeType))
		{
			if (DateUtil.RMDayCheck(firstTime, endTime))
			{
				endTime = DateUtil.getNewDay(45, firstTime);
			}
		}
		tableDay = DateUtil.xValueRM(firstTime, endTime);
		data = suitProductionAnalyseService.timeAnalyseYZMR(param, xValue,
				typeValue, SuitselectTimeType, suitTime);

		dataMap = suitProductionAnalyseService.timeAnalyseDataMap(param,
				typeValue, tableDay, suitTime);

		xValue = SuitselectTimeType.equals("R") != true ? xValue : DateUtil
				.xValueR(firstTime, endTime);

		StockTimeAnalyseExcelModel.exportExcelPoi(dataMap, data, typeValue,
				xValue, tableDay, response, request); // 导出：表格值,点值，图例，横轴,表格日时间

	}
	
	/**
	 * Excel数据预览
	 * 
	 */
	@RequestMapping(value = "/previewList")
	public String previewList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,@Valid StockTime suitTime) throws Exception
	{
		String SuitselectTimeType = suitTime.getSuitselectTimeType();
		String[] tableDay = null;// 库存分析导出表 纵列
		double[][] data = null;// 图点值(R日M月Z季Y年)

		Map<String, List<Object[]>> dataMap = Collections
				.synchronizedMap(new LinkedHashMap());// 表格值(R日M月Z季Y年)

		Map<String, Object> param = new HashMap();

		Map<String, String> timemapNow = TimeTool.getMapTime(suitTime, 0);
		String firstTime = timemapNow.get("beginTime");
		String endTime = timemapNow.get("endTime");

		Map<String, String[]> Xline_TypeValue = OtherTool.getXline_TypeValue(
				suitTime, timemapNow);
		String[] typeValue = Xline_TypeValue.get("typeValue");// 图例
		String[] xValue = Xline_TypeValue.get("xValue");// 横轴

		if (SuitselectTimeType.equals("M") || SuitselectTimeType.equals("R") || "W".equalsIgnoreCase(SuitselectTimeType))
		{
			if (DateUtil.RMDayCheck(firstTime, endTime))
			{
				endTime = DateUtil.getNewDay(45, firstTime);
			}
		}
		tableDay = DateUtil.xValueRM(firstTime, endTime);
		data = suitProductionAnalyseService.timeAnalyseYZMR(param, xValue,
				typeValue, SuitselectTimeType, suitTime);

		dataMap = suitProductionAnalyseService.timeAnalyseDataMap(param,
				typeValue, tableDay, suitTime);

		xValue = SuitselectTimeType.equals("R") != true ? xValue : DateUtil
				.xValueR(firstTime, endTime);

		String htmlStr = StockTimeAnalyseExcelModel.previewExcelPoi(dataMap, data, typeValue,
				xValue, tableDay, response, request); // 导出：表格值,点值，图例，横轴,表格日时间
		map.put("str", htmlStr);
		return PREVIEW;
	}
	

}
