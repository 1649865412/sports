package com.innshine.stock.utils;

import java.util.HashMap;
import java.util.Map;

public class StockUtils {
	
	public static String order = "1";
	public static  String orderCN = "订货";
	
	public static  String arrival = "2";
	public static  String arrivalCN = "到货";
	
	public static Map<String,String> stockType = new HashMap<String,String>(); 
	static{
		stockType.put(order, orderCN);
		stockType.put(arrival, arrivalCN);
	}
}
