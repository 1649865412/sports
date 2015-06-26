package com.innshine.shopAnalyse.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.log.Log;
import com.base.log.LogMessageObject;
import com.base.log.impl.LogUitls;
import com.base.shiro.ShiroUser;
import com.base.util.dwz.Page;
import com.base.util.persistence.DynamicSpecifications;
import com.base.util.persistence.SearchFilter;
import com.innshine.common.utils.ExportFieldsConfigUtils;
import com.innshine.shopAnalyse.checkInterface.PreviewProcessInterFace;
import com.innshine.shopAnalyse.checkInterface.ProcessInterFace;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckResult;
import com.innshine.shopAnalyse.service.ShopProductionAnalyseService;
import com.innshine.shopAnalyse.util.Constant;
import com.innshine.shopAnalyse.util.OtherTool;
import com.utils.SecurityUtils;

/**
 * 
 *  <code>销售分析报表</code>
 *  <p>
 *  <p>Copyright  2014 All right reserved.
 *  @author 杨荣忠 时间 2014-9-23 下午04:47:48	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@Controller
@RequestMapping("/management/shopAnalyse")
public class ShopProductionAnalyseController
{

	private static final Logger log = LoggerFactory
			.getLogger(ShopProductionAnalyseController.class);
	
	/**
	 * 销售分析公共业务类
	 * 
	 */
	@Autowired
	private ShopProductionAnalyseService shopProductionAnalyseService;

	
	/**
	 * 导出处理接口
	 * 
	 */
	@Autowired
	private ProcessInterFace processInterFace;

	/**
	 * 预览处理接口
	 * 
	 */
	@Autowired
	private PreviewProcessInterFace previewProcessInterFace;

	private static final String LIST = "management/shopAnalyse/list";
	private static final String MONTH_TYPESELECT = "management/shopAnalyse/monthTypeSelect";
	private static final String GROUP_SELECT = "management/shopAnalyse/groupSelect";
	private static final String PREVIEW = "management/shopAnalyse/preview";
	private static final String MONTHLY_SELECT_COLOUMN = "management/shopAnalyse/monthly_select_column";

	private static final String TOP10_COLUMNE_SELECT = "management/shopAnalyse/top10Column";
	private static final String DAYSALES_COLUMNE_SELECT = "management/shopAnalyse/daySalesColumn";

	
	/**
	 * 月报类型选择页面
	 * 
	 */
	@RequestMapping(value = "/toMonthTypeSelectExcel")
	public String toMonthTypeSelectExcel(int type, Map<String, Object> map)
			throws Exception
	{
		map.put("type", type);
		return MONTH_TYPESELECT;
	}

	/**
	 * 销售分析分类页面
	 * 
	 */
	@RequestMapping(value = "/toGroupSelectExcel")
	public String GroupSelect(int type, Map<String, Object> map)
			throws Exception
	{
		map.put("type", type);
		return GROUP_SELECT;
	}

	/**
	 * top10与日销售可选列页面
	 * 
	 */
	@RequestMapping(value = "/toColumnSelect")
	public String toColumnSelect(int type, Map<String, Object> map,
			int top0rdayType) throws Exception
	{
		map.put("type", type);
		map.put("top0rdayType", top0rdayType);
		String str = "";
		if (top0rdayType == 1)
		{
			// top10选择页面
			str = TOP10_COLUMNE_SELECT;
		}
		if (top0rdayType == 2)
		{   //日报选择页面
			str = DAYSALES_COLUMNE_SELECT;
		}
		return str;
	}
	
	

	/**
	 * 查询销售分析页面(界面所有查询条件)
	 * 
	 */
	@Log(message = "在销售分析模块查询")
	@RequestMapping(value = "/list")
	public String list(Page page,
			@Valid ShopAnalyseCheckEntity shopAnalyseEntity,
			Map<String, Object> map) throws Exception
	{
	
		Map<String, Object> param = new HashMap();
		List<ShopAnalyseCheckResult> result = shopProductionAnalyseService
				.getShopAnalyseCheckEntityList(param, shopAnalyseEntity, page);
		map.put("result", result);
		map.put("shopAnalyseEntity", shopAnalyseEntity);
		map.put("page", page);
		map.put("ParamAll", Constant.ParamAll);
		return LIST;
	}

	
	/**
	 * Excel模板表导出
	 */
	@SuppressWarnings("unchecked")
	//@Log(message = "在销售分析模块导出。")
	@RequestMapping(value = "/toExcel")
	public void toExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			@Valid ShopAnalyseCheckEntity shopExcel) throws Exception
	{
		Map<String, Object> param = new HashMap();
		processInterFace.getProcess(shopExcel, param, request, response);
	}

	
	
	
	/**
	 * Excel数据预览
	 * 
	 */
	@RequestMapping(value = "/previewList")
	public String previewList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			@Valid ShopAnalyseCheckEntity shopExcel) throws Exception
	{
		Map<String, Object> param = new HashMap();
		String str = previewProcessInterFace.getProcess(shopExcel, param,
				request, response);
		map.put("str", str);
		return PREVIEW;
	}
	
	/**
	 * Excel数据预览
	 * 
	 */
	@RequestMapping(value = "/preMonthlyViewList")
	public String preMonthlyViewList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,String type) throws Exception
	{
		Map<String, String>  fieldsMap = ExportFieldsConfigUtils.getInstance().getFieldsConfig("shop.analyse.monthly");
		map.put("fieldsMap", fieldsMap);
		map.put("type", type);
		return MONTHLY_SELECT_COLOUMN;
	}

}
