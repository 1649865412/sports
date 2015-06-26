package com.innshine.shopAnalyse.checkInterface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;

@Service
@Transactional
public class ProcessInterFaceImpl implements ProcessInterFace {

	/**
	 * 导出报表实现接口
	 * 
	 */
	@Autowired
	@Qualifier("excelShopProductionAnalyseService")
	private AchieveInterface excelShopProductionAnalyseService;
    
	
	/*
	@Autowired
	private ConditionText conditionText;
*/
	public void getProcess(ShopAnalyseCheckEntity shopExcel,
			Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//shopExcel.setConditionText(conditionText.ChangeText(shopExcel));
		
        int functionId=shopExcel.getExcelType();
        Long brandId=shopExcel.getBrandId();
   
		//功能id,1top10,2销售日报，3月报，4月报摘要 5销售分析 6月度统计
			if (functionId == 1)
				excelShopProductionAnalyseService.getTop10List( param,  request,
						response, shopExcel);

			else if (functionId == 2)
				excelShopProductionAnalyseService.getShopDay( param,  request,
						response, shopExcel);

			else if (functionId == 3)
				excelShopProductionAnalyseService.getShopMonth( param, request,
						response, shopExcel);

			else if (functionId == 4)
				excelShopProductionAnalyseService.getShopMonthPoint(param, request, 
						response, shopExcel);

			else if (functionId == 5)
				excelShopProductionAnalyseService.getShopAnalse(param, request,
						response, shopExcel);
			
			else if (functionId == 6)
				excelShopProductionAnalyseService.getMonthAnalse( param,request, 
						response, shopExcel);

	}
}
