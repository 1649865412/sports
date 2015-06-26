package com.innshine.shopAnalyse.checkInterface;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;

public interface PreviewProcessInterFace {

	/**
	 * 预览处理接口
	 * 
	 * @throws Exception
	 * 
	 */
	public String getProcess( ShopAnalyseCheckEntity shopExcel,
			Map<String, Object> param, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
