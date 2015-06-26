package com.innshine.shopAnalyse.controler;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.log.Log;
import com.base.util.dwz.Page;
import com.innshine.chart.ChartConstants;
import com.innshine.shopAnalyse.Excelprocess.TimeAnalyseProcess;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckResult;
import com.innshine.shopAnalyse.excelEntity.ImgPathLine;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseTimeGroup;
import com.innshine.shopAnalyse.service.ShopProductionAnalyseService;
import com.innshine.shopAnalyse.util.Constant;
import com.innshine.shopAnalyse.util.DateUtil;
import com.innshine.shopAnalyse.util.OtherTool;
import com.innshine.shopAnalyse.util.TimeTool;
import com.utils.excel.template.ExportConfig;


/**
 * 
 *  <code>销售 分析时间纵向</code>
 *  <p>
 *  <p>Copyright  2014 All right reserved.
 *  @author 杨荣忠 时间 2014-9-23 下午04:47:12	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@Controller
@RequestMapping("/management/shopTimeAnalyse")
public class ShopTimeAnaylseControler
{

	private static final Logger log = LoggerFactory
			.getLogger(ShopProductionAnalyseController.class);

	/**
	 * 销售分析公共业务类
	 * 
	 */
	@Autowired
	private ShopProductionAnalyseService shopProductionAnalyseService;

	private static final String TIMELIST = "management/shopAnalyse/timelist";
	private static final String GROUP_SELECT = "management/shopAnalyse/timeGroupSelect";
	private static final String PREVIEW = "management/shopAnalyse/preview";

	/**
	 * 销售分析分类页面
	 * 
	 */
	@RequestMapping(value = "/toGroupSelectExcel")
	public String GroupSelect(Map<String, Object> map,String type) throws Exception
	{
		map.put("type", type);
		return GROUP_SELECT;
	}

	/**
	 * 销售分析模块(时间维度分析)(R日M月Z季Y年)
	 * 
	 */
	@Log(message = "在销售分析模块时间维度查询")
	@RequestMapping(value = "/timeAnalyse")
	public String timeAnlyse(Page page, Map<String, Object> map,
			@Valid ShopAnalyseTimeCheckEntity shopAnalyseTimeCheckEntity)
			throws Exception
	{
		Map<String, Object> param = new HashMap();
		List<ShopAnalyseTimeCheckResult> shopTimeAnaylseResultList = shopProductionAnalyseService
				.getShopAnalyseTimeCheckEntityList(param,
						shopAnalyseTimeCheckEntity, page);
		map.put("page", page);
		map.put("shopAnalyseTimeCheckEntity", shopAnalyseTimeCheckEntity);
		map.put("shopTimeAnaylseResultList", shopTimeAnaylseResultList);
		map.put("numList", OtherTool.getNum(1, 12));
		map.put("ParamAll", Constant.ParamAll);
		return TIMELIST;
	}

	
	/**
	 * 销售分析模块(时间维度分析EXCEL导出)(R日M月Z季Y年)
	 */
//	@Log(message = "在销售分析模块导出{1},时间类型是：{2}查询范围在：{3},同比周期为：{4}")
	@RequestMapping(value = "/timeAnalyseExcelPOI")
	public void timeAnalyseExcelPOI(HttpServletRequest request,
			Map<String, Object> map, HttpServletResponse response,
			@Valid ShopAnalyseTimeCheckEntity shopTime) throws Exception
	{

		// 如果只有一个时间范围则当前时间到往前推30天
		shopTime = TimeTool.check(shopTime);

		Map<String, List<Object[]>> dataMap = Collections
				.synchronizedMap(new LinkedHashMap());// 表格值(R日M月Z季Y年)

		Map<String, Object> param = new HashMap();
		String selectTimeType = shopTime.getSelectTimeType();
		ImgPathLine imgPathLine = createQueryParams(shopTime, param, selectTimeType);
		ExportConfig<ShopAnalyseTimeGroup> config = shopProductionAnalyseService
				.getConfig(param, imgPathLine, shopTime);

		TimeAnalyseProcess.exportExcelPoi(config, response, request);
	}

	private ImgPathLine createQueryParams(ShopAnalyseTimeCheckEntity shopTime, Map<String, Object> param,
			String selectTimeType) throws Exception, ParseException
	{
		Map<String, String> timemapNow = TimeTool.getMapTime(shopTime, 0);
		String firstTime = timemapNow.get("beginTime");
		String endTimeFirst = timemapNow.get("endTime");
		String imgEndTime = "";

		// 处理报表导出图的合理结束时间
		// 当前时间到往前推30天;统计类型若为天与月，则时间范围在开始时间的基础上加上最多不超过45天记录;如果只有一个时间范围则以默认时间范围为准;
		if (selectTimeType.equals("M") || selectTimeType.equals("R"))
		{
			if (DateUtil.RMDayCheck(firstTime, endTimeFirst))
			{
				imgEndTime = DateUtil.getNewDay(45, firstTime);
			} else
			{
				imgEndTime = endTimeFirst;
			}
		}
		String[] xValue = OtherTool.getxValue(shopTime, timemapNow);
		String[] xValueLast = selectTimeType.equals("R") != true ? xValue
				: DateUtil.xValueR(firstTime, imgEndTime);// 横轴

		// 设置所查范围的时间段,求导出图数据
		param.put("beginTime", firstTime);
		param.put("endTime", imgEndTime);
		ImgPathLine imgPathLine = getImgPathLine(shopTime, param, xValueLast,
				xValue);

		// 报表表格查询范围重置为查询的结束时间
		param.put("endTime", endTimeFirst);
		return imgPathLine;
	}

	/**
	 * 功能:生成时间纵向分析图
	 * <p>
	 * 作者 admin 2014-9-23 上午09:28:50
	 * 
	 * @param shopTime
	 * @param param
	 * @param xValueLast
	 * @param xValue
	 * @return
	 * @throws Exception
	 */

	public ImgPathLine getImgPathLine(ShopAnalyseTimeCheckEntity shopTime,
			Map<String, Object> param, String[] xValueLast, String[] xValue)
			throws Exception
	{

		double[][] data = null;// 点值(R日M月Z季Y年)
		String[] typeValueNew = shopProductionAnalyseService.getTimeTypeValue(
				param, shopTime);

		data = shopProductionAnalyseService.timeAnalyseYZMR(param, xValue,
				typeValueNew, shopTime);

		ImgPathLine imgPathLine = new ImgPathLine(data, typeValueNew,
				xValueLast, "时间", "销量", "时间纵向销售分析报表", ChartConstants.LINE_CHART);

		return imgPathLine;
	}
	
	/**
	 * 销售分析模块(时间维度分析EXCEL导出)(R日M月Z季Y年)
	 */
	//@Log(message = "在销售分析模块导出{1},时间类型是：{2}查询范围在：{3},同比周期为：{4}")
	@RequestMapping(value = "/timeAnalyseExcelPreview")
	public String timeAnalyseExcelPreviewPOI(HttpServletRequest request,
			Map<String, Object> map, HttpServletResponse response,
			@Valid ShopAnalyseTimeCheckEntity shopTime) throws Exception
	{

		// 如果只有一个时间范围则当前时间到往前推30天
		shopTime = TimeTool.check(shopTime);

		Map<String, List<Object[]>> dataMap = Collections
				.synchronizedMap(new LinkedHashMap());// 表格值(R日M月Z季Y年)

		Map<String, Object> param = new HashMap();
		String selectTimeType = shopTime.getSelectTimeType();
		ImgPathLine imgPathLine = createQueryParams(shopTime, param, selectTimeType);
		
		ExportConfig<ShopAnalyseTimeGroup> config = shopProductionAnalyseService
				.getConfig(param, imgPathLine, shopTime);

		String str =TimeAnalyseProcess.previewExcelPoi(config,imgPathLine,request);
		
		map.put("str", str);
		map.put("exportFormId", "exportEXCEL");
		
		return PREVIEW;
	}

}
