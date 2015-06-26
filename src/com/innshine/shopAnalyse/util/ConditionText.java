package com.innshine.shopAnalyse.util;

import org.apache.commons.lang3.StringUtils;

import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;



public class ConditionText {
	
	private static String BEGIN="(查询过滤条件为: ";
	private static String SYMBOL=":";
	private static String SYMBOL_MIDDLE=";   ";
	private static String RESULT_ALL="所有";
	private static String END="其他查询条件默认)";
	
	
	
	/**
	 * 功能:求查询条件str
	 * <p>作者 杨荣忠 2014-9-23 上午09:47:04
	 * @param shopExcel
	 * @return
	 * @throws Exception
	 */
    public static String ChangeText(ShopAnalyseCheckEntity shopExcel) throws Exception{
    	String condition=BEGIN;
    	condition+=getValueText(shopExcel);
    	condition+=END;
    	return condition;
    }
    
    
    /**
     * 功能:
     * <p>作者 杨荣忠 2014-9-23 上午09:47:38
     * @param shopExcel
     * @return
     */
    public static String getValueText(ShopAnalyseCheckEntity shopExcel){
      String str="";
      
      String beginTime=shopExcel.getBeginTime();
      String endTime=shopExcel.getEndTime(); 
      String productPlatformId=shopExcel.getProductPlatformId();
      String quater=shopExcel.getQuater();
      String productLfPf=shopExcel.getProductLfPf();
      String beginPrice=shopExcel.getBeginPrice();
      String endPrice=shopExcel.getEndPrice();
      String materialNumber=shopExcel.getMaterialNumber();
      
      str+=changeValue("开始时间",beginTime);
      str+=changeValue("结束时间",endTime);
      str+=changeValue("产品ID",productPlatformId);
      str+=changeValue("季节",quater);
      str+=changeValue("LF/PF",productLfPf);
      str+=changeValue("开始价格",beginPrice);
      str+=changeValue("结束价格",endPrice);
      str+=changeValue("款号",materialNumber);
    
      return str; 
    }
 
    /**
     * 功能:
     * <p>作者 杨荣忠 2014-9-23 上午09:47:59
     * @param title
     * @param value
     * @return
     */
    public static String changeValue(String title,String value){
  	  String str=""; 
  	  
  	  // 2014-10-15 修改查询数据为空是，报系统发生内部错误:即空指针错误
  	  /*
  	  if (!value.trim().equals("")){
  		  str=title+SYMBOL+value+SYMBOL_MIDDLE;
  	  }
  	  */
  	  if(StringUtils.isNotEmpty(value))
  	  {
  		 str = title + SYMBOL + value + SYMBOL_MIDDLE;
  	  }
  	  
  	  return str;
    }
    
    
 
    
}
