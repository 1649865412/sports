package com.innshine.shopAnalyse.checkInterface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;


/**
 * 品牌预览处理类配置 <code>ProcessInterFaceImpl.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

@Service
@Transactional
public class PreviewProcessInterFaceImpl implements PreviewProcessInterFace {

	
	
	/**
	 * 
	 * 
	 */
	@Autowired
	@Qualifier("previewExcelShopProductionAnalyseService")
	private PreviewAchieveInterface previewExcelShopProductionAnalyseService;

	/*@Autowired
	private ConditionText conditionText;*/
	

	public String  getProcess(ShopAnalyseCheckEntity shopExcel,
			Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		  //  shopExcel.setConditionText(conditionText.ChangeText(shopExcel));
            String str="";
            int functionId=shopExcel.getExcelType();
            Long brandId=shopExcel.getBrandId();
            
		// 功能id,1top10,2销售日报，3月报，4月报摘要 5销售分析 6月度统计     
			if (functionId == 1)
		    	str=previewExcelShopProductionAnalyseService.getTop10List(param,  request,
						response,shopExcel);

			else if (functionId == 2)
				str=previewExcelShopProductionAnalyseService.getShopDay(param,  request,
						response,shopExcel);

			else if (functionId == 3)
				str=previewExcelShopProductionAnalyseService.getShopMonth( param,  request,
						response,shopExcel);

			else if (functionId == 4)
				str=previewExcelShopProductionAnalyseService.getShopMonthPoint(param,  request, 
						response,shopExcel);

			else if (functionId == 5)
				str=previewExcelShopProductionAnalyseService.getShopAnalse(
						param,  request, response,shopExcel);

			else if (functionId == 6)
				str=previewExcelShopProductionAnalyseService.getMonthAnalse( param,  request, 
						response,shopExcel);

     return str;
	}
}
