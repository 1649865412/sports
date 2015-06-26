package com.innshine.shopAnalyse.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import com.base.dao.sql.ResultToBean;
import com.base.dao.sql.SQLHelper;
import com.base.dao.sql.SQLProperties;
import com.innshine.shopAnalyse.dao.ExcelShopAnalyseDao;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;
import com.innshine.shopAnalyse.excelEntity.MonthEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseTimeGroup;
import com.innshine.shopAnalyse.excelEntity.ShopDayEntity;
import com.innshine.shopAnalyse.excelEntity.TOP10Entity;
import com.utils.excel.template.ExportConfig;

public class OtherTool {

	public static Map<String, Object> getShopAnaylseTimeLastGroup(
			Map<String, Object> param, ShopAnalyseTimeCheckEntity shopTime)
			throws Exception {
		String ShopOptions = shopTime.getShopOptionsNameTime();
		String OrderList = shopTime.getOrderListTime();
		String[] groupSelect = getShopAnalyseParam(ShopOptions, OrderList);
		int length = groupSelect.length;
		param.put(groupSelect[0], groupSelect[0]);
		return param;
	}

	
	public static<T> ExportConfig<T>top10_daySales_Column(ExportConfig<T> config,String value){
		Vector<String> displayProperties = config.getDisplayProperties();
		Vector<String> header =config.getHeader();
		
		Vector<String> newdisplayProperties=new Vector<String>();
		Vector<String> newheader=new Vector<String>();
		
		//String value=shopAnalyseCheckEntity.getColumnValue();
	    //注意此处head与dis原先在常量里的顺序是对应的
		int count =0;
		int columnSize=displayProperties.size();
		for(int i=0;i<displayProperties.size();i++){
			if(checkArrayHave(value,displayProperties.get(i))){
				 newdisplayProperties.add(displayProperties.get(i));
				 newheader.add(header.get(i));
				 count+=1;
			}
		}
		//要据所选字段的多少，智能变更表格列宽度
		config.setDisplayProperties(newdisplayProperties);
		config.setHeader(newheader);		
		int columnWidth=(columnSize/count)*15;
		config.setColumnWidth(columnWidth);
		
		return config;
		
	}
	
	public static boolean checkArrayHave(String value,String str){
		boolean flag=false;
		String[] array=value.split(",");
		for(int i=0;i<array.length;i++){
			if(array[i].equals(str)){
				flag=true;
				break;
			}
		}
		return flag;
		
	}
	
	public static String ShopTimeAnalyseText(Map<String, Object> param) {
		String begin = (String) param.get("beginTime");
		String end = (String) param.get("endTime");
		String str = "(同比时间范围" + begin.substring(5) + "至" + end.substring(5)
				+ ")";
		return str;
	}

	public static String addSQL(String[] SQLArray, Map<String, Object> param)
			throws Exception {
		String str = "";
		if (SQLArray != null) {
			for (int i = 0; i < SQLArray.length; i++) {
				String sql = SQLProperties.getInstance().getSql(SQLArray[i]);
				SQLHelper sqlHelper = new SQLHelper(sql);
				String executeSql = sqlHelper.parepareSQLtext(param);
				str += executeSql;
			}
		}

		return str;
	}

	public static String[] getxValue (ShopAnalyseTimeCheckEntity shopTime,Map<String, String> timemapNow) throws ParseException{
		String[] xValue = null;// 横轴
		String selectTimeType = shopTime.getSelectTimeType();
		String firstTime = timemapNow.get("beginTime");
		String endTime = timemapNow.get("endTime");
		if (selectTimeType.equals("M") || selectTimeType.equals("R")) {
			if (DateUtil.RMDayCheck(firstTime, endTime)) {
				endTime = DateUtil.getNewDay(45, firstTime);
			}
			xValue = DateUtil.xValueRM(firstTime, endTime);
			
		}

		else if (selectTimeType.equals("Z")) {
			xValue = DateUtil.xValueZ(shopTime.getQuarterfirstTime(), shopTime
					.getQuarterendTime());		
		}

		else if (selectTimeType.equals("Y")) {
			int Quarterfirst = 1;
			int Quarterendend = 4;
			xValue = DateUtil.xValueY(Quarterfirst, Quarterendend);

		}
		return xValue;
	}
	
	
	public static String[] ShopTimeAnalyseHead(
			ShopAnalyseTimeCheckEntity shopTime, String groupSelect[],
			Map<String, Object> param) {
		String[] head = null;
		int circle = shopTime.getCircle();
		String begin = (String) param.get("beginTime");
		int year = Integer.parseInt(begin.substring(0, 4));
		String[] baseGroup = ExcelConstant.SHOP_TIME_ANALYSE_GROUP;
		head = OtherTool.concat(OtherTool.getShopAnalyseGroupStr(groupSelect),
				getShopTimeAnalyseYear(baseGroup, year + "年"));
		if (circle == 1) {
			head = OtherTool.concat(head, getShopTimeAnalyseYear(baseGroup,
					year - circle + "年"));

		}
		if (circle == 2) {
			head = OtherTool.concat(head, getShopTimeAnalyseYear(baseGroup,
					year - circle + 1 + "年"));
			head = OtherTool.concat(head, getShopTimeAnalyseYear(baseGroup,
					year - circle + "年"));
		}

		return head;
	}

	public static String[] ShopTimeAnalyseDisplay(
			ShopAnalyseTimeCheckEntity shopTime, String groupSelect[]) {
		String[] display = null;
		int circle = shopTime.getCircle();
		display = OtherTool.concat(OtherTool.getUpcase(groupSelect),
				ExcelConstant.SHOP_TIME_ANALYS);
		display = OtherTool.concat(display,
				ExcelConstant.SHOP_TIME_ONE_YEAR_ANALYSE_GROUP);
		if (circle == 1) {

		}
		if (circle == 2) {
			display = OtherTool.concat(display,
					ExcelConstant.SHOP_TIME_TWO_YEAR_ANALYSE_GROUP);
		}
		return display;
	}

	public static String[] getShopTimeAnalyseYear(String[] value, String year) {
		String[] array = new String[value.length];
		if (value != null) {
			for (int i = 0; i < value.length; i++) {
				array[i] = year + value[i];
			}
		}
		return array;
	}

	public static String[] getShopTimeAnalyseSQLarray(
			ShopAnalyseTimeCheckEntity shopTime) {
		int circle = shopTime.getCircle();
		String[] array = null;
		if (circle == 1) {
			array = Constant.SQLArray_DEFAULT;
		}
		if (circle == 2) {
			array = Constant.SQLArray_ALL;
		}
		return array;
	}

	public static String[] getShopAnalyseParam(String ShopOptions,
			String OrderList) throws Exception {
		// String ShopOptions = shopAnalyseCheckEntity.getShopOptions();
		// String OrderList = shopAnalyseCheckEntity.getOrderList();
		String[] groupSelect = OtherTool.getShopAnlyseSelectGroupList(
				ShopOptions, OrderList, ShopOptions, 1);// 所选分列（从大到小）
		return groupSelect;
	}

	public static boolean checkShopAnalyseQ(String content) {
		boolean tag = false;
		if (content.equals("quater"))
			tag = true;
		return tag;
	}

	public static String[] getShopAnalyseGroupStr(String[] groupSelect) {
		String[] content = new String[groupSelect.length];
		for (int i = 0; i < groupSelect.length; i++) {
			content[i] = changeShopAnalyseContent(groupSelect[i]);
		}
		return content;
	}

	// <!-- 销售分析 +分类： 种类，Q季，系列，一线还是二线，性别-->
	// "product_type","quater", "series", "inline_or2ndline", "product_sex"
	public static String changeShopAnalyseContent(String content) {
		String str = "";
		if (content.equals("product_type"))
			str = "种类";
		else if (content.equals("quater"))
			str = "Q季";
		else if (content.equals("series"))
			str = "系列";
		else if (content.equals("inline_or2ndline"))
			str = "Inline/2ndline";
		else if (content.equals("product_sex"))
			str = "性别";
		return str;
	}

	public static String[] getUpcase(String[] groupSelect) {
		String arrayUpcase[] = new String[groupSelect.length];
		for (int i = 0; i < groupSelect.length; i++) {
			arrayUpcase[i] = ResultToBean.coloumnToObject(groupSelect[i]);
		}
		return arrayUpcase;
	}

	// 转成大数据库字段
	public static String changeWord(String name) {
		List<Character> indexlist = isAcronym(name);
		String text = name;
		for (int i = 0; i < indexlist.size(); i++) {
			String a = indexlist.get(i) + "";
			String b = String.valueOf(Character.toLowerCase(indexlist.get(i)));
			text = text.replace(a, "_" + b);
		}
		return text;
	}

	// 求大写字母
	public static List<Character> isAcronym(String word) {
		List indexlist = new ArrayList();
		int index = 0;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (!Character.isLowerCase(c)) {
				indexlist.add(c);
			}
		}
		return indexlist;
	}

	public static boolean checkHaveSelect(String ShopOptions) {
		boolean flag = false;
		if (!ShopOptions.split(",")[0].equals(0 + "")) {
			flag = true;
		}
		return flag;
	}

	// 判断是不是数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();

	}

	// 处理过来的顺序集，非数字顺序转为0
	public static List ChangeOrder(String[] orderList) {
		List list = new ArrayList();
		for (int i = 0; i < orderList.length; i++) {
			if (!isNumeric(orderList[i])) {
				orderList[i] = 0 + "";
			}
			list.add(orderList[i]);
		}
		return list;
	}

	// 获取所选排序的对应索引号
	public static int[] OrderList(List ChangeOrder, List ChangeGroup) {
		int[] num = new int[ChangeGroup.size()];
		for (int i = 0; i < ChangeGroup.size(); i++) {
			int a = Integer.parseInt(ChangeGroup.get(i).toString());
			int b = Integer.parseInt(ChangeOrder.get(a).toString());
			num[i] = b;
		}
		return num;
	}

	// 获取销售分析(包括套装)动态列(全)（从大到小）
	public static String[] getShopAnlyseSelectGroupList(String ShopOptions,
			String OrderList, String ShopOptionsName, int shopType)
			throws Exception {
		String[] groupList = null;

		if (checkHaveSelect(ShopOptions)) {
			// 获取所选排序的对应大小号
			int[] numList = OrderList(ChangeOrder(OrderList.split(",")),
					ChangeGroup(ShopOptions.split(","), shopType));
			int[] numListName = Arrays.copyOf(numList, numList.length);

			groupList = groupOrderList(ShopOptionsName.split(","), numListName);
		}
		return groupList;
	}

	// 对数组从大到小排序
	private static int[] sortdesc(int[] a) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] > a[i]) {
					int t = a[j];
					a[j] = a[i];
					a[i] = t;
				}
			}
		}
		return a;
	}

	public static int getIndex(int num, int value[], List haveList) {
		int index = 0;
		for (int j = 0; j < value.length; j++) {
			if (num == value[j] && checkList(haveList, j)) {
				index = j;
				break;
			}
		}
		return index;
	}

	// 给动态列按从大到小排的优先顺序排序
	public static String[] groupOrderList(String[] order, int[] numList) {
		int[] numListArray = Arrays.copyOf(numList, numList.length);
		String[] orderArray = Arrays.copyOf(order, order.length);
		List<Integer> haveIndexList = new ArrayList();// 建一个list专门存储已标志的索引，防止相同索引取同一个地方对应值
		int indexArray[] = sortdesc(numList);// 从大到小排
		if (indexArray.length != 0) {
			for (int i = 0; i < indexArray.length; i++) {
				int index = getIndex(indexArray[i], numListArray, haveIndexList);
				order[i] = orderArray[index];
				haveIndexList.add(index);
			}
		}
		return order;
	}

	// 获取所选排序的索引号type=1:销售分析
	public static List ChangeGroup(String[] group, int type) {
		List list = new ArrayList();
		String[] ArrayList = null;
		if (type == 1) {
			ArrayList = Arrays.copyOf(Constant.GROUP_NAME_CHECK,
					Constant.GROUP_NAME_CHECK.length);
		}
		for (int i = 0; i < group.length; i++) {
			for (int j = 0; j < ArrayList.length; j++) {
				if (ArrayList[j].equals(group[i])) {
					list.add(j);
				}
			}
		}
		return list;
	}

	public static List<String> foreachTool(List first, List second,
			List<String> con) {
		for (int i = 0; i < first.size(); i++) {
			for (int j = 0; j < second.size(); j++) {
				con.add(first.get(i) + " and " + second.get(j));
			}
		}
		return con;
	}

	public static Object[] preSum(Object[] object, String[] preSumResult) {
		// 同比汇总：销售数量，金额，折扣
		String prenumSum = disDiscount(object[0], preSumResult[0]);
		// String presalesMoney = disDiscount(object[1], preSumResult[1]);
		// String predisCount = disDiscount(object[3], preSumResult[2]);
		object = concat(object, new Object[] { prenumSum });
		return object;
	}

	// 判断有无包含对应值，返回false
	public static boolean checkGroupOne(String groupSelect[], String str) {
		boolean flag = true;
		for (int j = 0; j < groupSelect.length; j++) {
			if (groupSelect[j].trim().equals(str.trim())) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static String[] preSumResult(List<Object[]> result) {
		// 同比汇总：销售数量，金额，折扣
		int size = result.size() - 1;
		String prenumSumAll = result.get(size)[0] != null ? result.get(size)[0]
				+ "" : null;
		/*
		 * String presalesMoneyAll = result.get(size)[1] != null ? result
		 * .get(size)[1] + "" : null; String predisCountAll =
		 * result.get(size)[3] != null ? result.get(size)[3] + "" : null;
		 */
		String[] objectResult = new String[] { prenumSumAll };
		return objectResult;
	}

	public static List<Integer> getNum(int begin, int end) {
		List<Integer> list = new ArrayList();
		for (int i = begin; i <= end; i++) {
			list.add(i);
		}
		return list;
	}

	public static Object[] shopTimeAnalyseObject(Object[] object,
			Object[] preobject) {
		// 同比：销售数量，金额，折扣
		String prenumSum = disDiscount(object[0], preobject[0]);
		// String presalesMoney = disDiscount(object[1], preobject[1]);
		// String predisCount = disDiscount(object[3], preobject[3]);
		object = concat(object, new Object[] { prenumSum });
		return object;
	}

	public static String disDiscount(Object a, Object b) {
		String result = "";
		try {
			Double preResult = Double.parseDouble(b.toString());
			Double nowResult = Double.parseDouble(a.toString());
			if (preResult != 0) {
				double num = ((nowResult / preResult) - 1) * 100;
				result = DateUtil.changeMoneyType(num);
			} else {
				result = "100";
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	
	/**
	 * 功能:
	 * <p>作者 admin 2014-9-23 上午09:18:40
	 * @param object 
	 * @param index
	 * @return
	 */
	public static double doNum(Object object[], int index) {
		double num = 0;
		try {
			num = object[index] != null ? Double
					.parseDouble(object[index] + "") : 0;
		} catch (Exception e) {
			num = 0;
		}
		return num;

	}
	

	/**
	 * 功能:
	 * <p>作者 admin 2014-9-22 下午06:07:45
	 * @param condition
	 * @param str
	 * @return
	 */
	public static int checkGroupOne(List<String> condition, String str) {
		int index = -1;
		for (int j = 0; j < condition.size(); j++) {
			if (condition.get(j).equals(str)) {
				index = j;
				break;
			}
		}
		return index;
	}

	// type:0是月报，type:1是月度统计
	public static List<MonthEntity> getCollectMonthData(
			List<MonthEntity> plateTimeList, List<String> monthList, int type) {
		List<MonthEntity> list = new ArrayList();
		double lastAllMoney = 0;
		for (int i = 0; i < monthList.size(); i++) {
			double sales = 0;// 数量
			double amount = 0;// 金额
			double accounted = 100;
			String rateRise = "-";// 汇总金额增长率（本月总除以上个月总-1）*100%
			String month = monthList.get(i) + "";
			for (int j = 0; j < plateTimeList.size(); j++) {
				MonthEntity monthEntity = plateTimeList.get(j);
				String TimeMonth = monthEntity.getTimeMonth();
				if (month.equals(TimeMonth)) {
					sales += doNum(monthEntity.getAllSales());// 汇总销售数量
					amount += doNum(monthEntity.getAllAmount()); // 汇总销售金额
				}
			}
			MonthEntity monthData = new MonthEntity();
			if (type == 0) {
				monthData = new MonthEntity(sales, amount, accounted);
			}
			if (type == 1) {
				if (i > 0) {
					if (lastAllMoney != 0) {
						try {
							rateRise = DateUtil.changeMoneyType((amount
									/ lastAllMoney - 1) * 100);
						} catch (Exception e) {
							rateRise = "-";
						}
					} else {
						rateRise = "100";
					}
				}
				monthData = new MonthEntity(sales, amount, accounted, rateRise);
			}
			lastAllMoney = amount;
			list.add(monthData);
		}
		return list;
	}
	

	// 月报统计报表金额增长率（本月除以上个月-1）*100%
	public static String getMonthRateRise(List<MonthEntity> quter_TimeList,
			MonthEntity monthEntity) {
		String rateRise = "100";
		String Quater = monthEntity.getQuater();
		String money = monthEntity.getAllAmount();
		String month = monthEntity.getTimeMonth();
		String LastTimeMonth = "";
		String year = "";
		if (!month.equals("01")) {
			LastTimeMonth = getMonthLastMonth(month);
			year = monthEntity.getTimeYear();
		} else {
			LastTimeMonth = "12";
			year = Integer.parseInt(monthEntity.getTimeYear()) - 1 + "";
		}
		for (int i = 0; i < quter_TimeList.size(); i++) {
			MonthEntity lastMonthEntity = quter_TimeList.get(i);
			if (lastMonthEntity.getQuater().equals(Quater)
					&& lastMonthEntity.getTimeMonth().equals(LastTimeMonth)
					&& lastMonthEntity.getTimeYear().equals(year)) {
				try {
					// （本月除以上个月-1）*100%
					double value= Double.parseDouble(lastMonthEntity
							.getAllAmount());
                 if(value!=0){
                	 rateRise = DateUtil
						.changeMoneyType((Double.parseDouble(money)/ value - 1) * 100);
                 }else{
                	 rateRise = "100";
                 }
					
				} catch (Exception e) {
					rateRise = "-";
				}
				break;
			}
		}
		if(rateRise=="-"){
			System.out.println("hello");
		}
		return rateRise;

	}
	// 月报统计报表金额增长率（本月除以上个月-1）*100%
	public static String getMonthRateRise(List<MonthEntity> quter_TimeList,
			MonthEntity monthEntity,ShopAnalyseCheckEntity shopAnalyseCheckEntity) {
		String rateRise = "100";
		String Quater = ExcelShopAnalyseDao.getValue(monthEntity, shopAnalyseCheckEntity);
		String money = monthEntity.getAllAmount();
		String month = monthEntity.getTimeMonth();
		String LastTimeMonth = "";
		String year = "";
		if (!month.equals("01")) {
			LastTimeMonth = getMonthLastMonth(month);
			year = monthEntity.getTimeYear();
		} else {
			LastTimeMonth = "12";
			year = Integer.parseInt(monthEntity.getTimeYear()) - 1 + "";
		}
		for (int i = 0; i < quter_TimeList.size(); i++) {
			MonthEntity lastMonthEntity = quter_TimeList.get(i);
			String lastValue = ExcelShopAnalyseDao.getValue(lastMonthEntity, shopAnalyseCheckEntity);
			if (lastValue.equals(Quater)
					&& lastMonthEntity.getTimeMonth().equals(LastTimeMonth)
					&& lastMonthEntity.getTimeYear().equals(year)) {
				try {
					// （本月除以上个月-1）*100%
					double value= Double.parseDouble(lastMonthEntity
							.getAllAmount());
					if(value!=0){
						rateRise = DateUtil
						.changeMoneyType((Double.parseDouble(money)/ value - 1) * 100);
					}else{
						rateRise = "100";
					}
					
				} catch (Exception e) {
					rateRise = "-";
				}
				break;
			}
		}
		if(rateRise=="-"){
			System.out.println("hello");
		}
		return rateRise;
		
	}

	public static String getMonthLastMonth(String month) {
		String lastMonth = "";
		if (month.indexOf("0") == 0) {
			lastMonth = "0" + (Integer.parseInt(month.substring(1)) - 1);

		} else {
			if (month.indexOf("1") == 0) {
				lastMonth = "" + (Integer.parseInt(month) - 1);
				if (lastMonth.length() == 1)
					lastMonth = "0" + lastMonth;
			} else {
				lastMonth = "" + (Integer.parseInt(month.substring(1)) - 1);
			}

		}
		return lastMonth;
	}

	// public

	public static List<Double> getMonthShanlyseSum(
			List<MonthEntity> plateTimeList, List<String> monthList) {
		List<Double> list = new ArrayList();
		for (int i = 0; i < monthList.size(); i++) {
			double sum = 0;
			double result = 0;
			for (int j = 0; j < plateTimeList.size(); j++) {
				MonthEntity monthEntity = plateTimeList.get(j);
				if (monthEntity.getTimeMonth().equals(monthList.get(i))) {
					try {
						String value = monthEntity.getAllAmount();
						double amonut = Double.parseDouble(value);
						result = amonut;
					} catch (Exception e) {
						result = 0;
					}
					sum += result;
				}
			}
			list.add(sum);
		}
		return list;
	}

	public static List<Integer> getGroupConditionIndex(List<String> condition,
			List<String> list, List<Integer> IndexList) {
		int numIndex = -1;
		for (int m = 0; m < list.size(); m++) {
			numIndex = checkGroupOne(condition, list.get(m));
			if (numIndex != -1 && checkList(IndexList, numIndex)) {
				IndexList.add(numIndex);
			}
		}

		return IndexList;
	}

	public static List<String> getMonthShanlyse(List timeList) {
		List<String> list = new ArrayList();
		for (int i = 0; i < timeList.size(); i++) {
			String month = timeList.get(i).toString().substring(5, 7);
			list.add(month);
		}
		return list;
	}

	public static List chagneList(List list, String str, boolean flag) {
		List array = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				array.add(str + "='" + list.get(i) + "'");
			} else {
				if (flag == true)
					array.add(str + " IS NULL");
				else {
					continue;
				}
			}
		}
		return array;
	}

	// 判断有无包含对应值，返回索引
	public static int checkGroupOneIndex(String groupSelect[], String str) {
		int index = -1;
		for (int j = 0; j < groupSelect.length; j++) {
			if (groupSelect[j].trim().equals(str.trim())) {
				index = j;
				break;
			}
		}
		return index;
	}

	public static double doNum(String value) {
		double num = 0;
		try {
			num = value != null ? Double.parseDouble(value + "") : 0;
		} catch (Exception e) {
			num = 0;
		}
		return num;

	}

	public static String doNumFormat(String value) {
		String num = "";
		double result = 0;
		try {
			result = value != null ? Double.parseDouble(value + "") : 0;
		} catch (Exception e) {
			result = 0;
		}
		num = DateUtil.changeMoneyType(result);
		return num;

	}

	public static int doNumInt(String value) {
		int num = 0;
		try {
			num = value != null ? Integer.parseInt(value + "") : 0;
		} catch (Exception e) {
			num = 0;
		}
		return num;

	}

	public static Vector<String> getHeaderAndDisplayProperties(String HEAD[]) {
		Vector<String> header = new Vector<String>();
		for (int i = 0; i < HEAD.length; i++) {
			header.add(HEAD[i]);
		}
		return header;
	}

	public static ShopAnalyseGroup getShopAnalyseGroup(
			ShopAnalyseGroup ShopAnalyseGroup, double all_salesNumber) {
		try {
			double discount = Double.parseDouble(ShopAnalyseGroup
					.getSalesNumber())
					/ all_salesNumber * 100;
			ShopAnalyseGroup.setDiscount(DateUtil.changeMoneyType(discount));
		} catch (Exception e) {
			ShopAnalyseGroup.setDiscount("-");
		}
		return ShopAnalyseGroup;
	}

	// 销售分析单个增加汇总与折扣
	public static List<ShopAnalyseEntity> ShopAnalyseEntityGetSumAndDiscountCheck(
			List<ShopAnalyseEntity> ShopAnalyseEntityList, String groupSelect[]) {
		// 判断是不是选择Q季分类
		if (OtherTool.checkShopAnalyseQ(groupSelect[0]))
			ShopAnalyseEntityList = ParamTool
					.ShopAnalyseEntityQuaterGetSumAndDiscount(ShopAnalyseEntityList);
		else
			// 种类，系列，一线还是二线，性别
			ShopAnalyseEntityList = ParamTool
					.ShopAnalyseEntityGetSumAndDiscount(ShopAnalyseEntityList);

		return ShopAnalyseEntityList;
	}

	public static ShopAnalyseEntity getShopAnalyseEntity(
			ShopAnalyseEntity ShopAnalyseEntity, double all_nowMonthSalesNumber) {
		try {
			double nowMonthSalesNumberDiscount = (Double
					.parseDouble(ShopAnalyseEntity.getNowMonthSalesNumber()) / all_nowMonthSalesNumber) * 100;
			ShopAnalyseEntity.setNowMonthSalesNumberDiscount(DateUtil
					.changeMoneyType(nowMonthSalesNumberDiscount));
		} catch (Exception e) {
			ShopAnalyseEntity.setNowMonthSalesNumberDiscount("-");
		}

		try {
			ShopAnalyseEntity.setAllSalesSum(DateUtil.changeMoneyType(Double
					.parseDouble(ShopAnalyseEntity.getAllSalesSum())));
		} catch (Exception e) {
			ShopAnalyseEntity.setAllSalesSum("-");
		}
		return ShopAnalyseEntity;
	}

	public static List<String[]> getSum(int begin, int end,
			List<String[]> groupArray) {
		List<String[]> Array = new ArrayList();
		for (int i = 0; i < groupArray.size(); i++) {
			int num = Integer.parseInt(groupArray.get(i)[2]);
			if (begin <= num && num <= end)
				Array.add(groupArray.get(i));
		}
		return Array;
	}

	// 求一个数组的索引范围内的行总数(以最后一列为主)
	public static int GetListRowSum(int begin, int end,
			List<String[]> groupArray) {
		int max = Integer.parseInt(groupArray.get(groupArray.size() - 1)[2]);// 求记录最后一条的行索引
		if (end > max) {
			end = max;
		}
		int num = 0;
		List<String[]> Array = getSum(begin, end, groupArray);
		if (Array.size() > 0) {
			for (int i = 0; i < Array.size(); i++) {
				String arrayOne[] = Array.get(i);
				num += Integer.parseInt(arrayOne[1]);
			}
		}
		return num;
	}

	public static String[] concat(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static ShopAnalyseEntity getShopAnalyseEntity(
			ShopAnalyseEntity ShopAnalyseEntity, double all_salesNumber,
			double all_salesSum, double all_salesTagMoney) {
		String rate = "%";
		try {
			double salesNumberDiscount = (Double.parseDouble(ShopAnalyseEntity
					.getSalesNumber()) / all_salesNumber) * 100;
			ShopAnalyseEntity.setSalesNumberDiscount(DateUtil
					.changeMoneyType(salesNumberDiscount)
					+ rate);
			ShopAnalyseEntity.setSalesNumber(DateUtil.changeMoneyType(Double
					.parseDouble(ShopAnalyseEntity.getSalesNumber())));

		} catch (Exception e) {
			ShopAnalyseEntity.setSalesNumberDiscount("-");
		}
		try {
			double salesSumDiscount = (Double.parseDouble(ShopAnalyseEntity
					.getSalesSum()) / all_salesSum) * 100;
			ShopAnalyseEntity.setSalesSumDiscount(DateUtil
					.changeMoneyType(salesSumDiscount)
					+ rate);
			ShopAnalyseEntity.setSalesSum(DateUtil.changeMoneyType(Double
					.parseDouble(ShopAnalyseEntity.getSalesSum())));
		} catch (Exception e) {
			ShopAnalyseEntity.setSalesSumDiscount("-");
		}
		try {
			double salesTagDiscount = (Double.parseDouble(ShopAnalyseEntity
					.getSalesTagMoney()) / all_salesTagMoney) * 100;
			ShopAnalyseEntity.setSalesTagDiscount(DateUtil
					.changeMoneyType(salesTagDiscount)
					+ rate);
			ShopAnalyseEntity.setSalesTagMoney(DateUtil.changeMoneyType(Double
					.parseDouble(ShopAnalyseEntity.getSalesTagMoney())));
		} catch (Exception e) {
			ShopAnalyseEntity.setSalesTagDiscount("-");
		}
		return ShopAnalyseEntity;
	}

	public static TOP10Entity getTOP10Entity(TOP10Entity tOP10Entity, int i,
			double sum) {
		tOP10Entity.setNumIndex(i + 1 + "");
		double discount = 0;
		try {
			discount = (Double.parseDouble(tOP10Entity.getSalesSum()) / sum) * 100;
			tOP10Entity.setDiscount(DateUtil.changeMoneyType(discount));

		} catch (Exception e) {
			tOP10Entity.setDiscount("-");
		}
		// 转成标准金钱格式，方便看
		tOP10Entity.setSalesSum(DateUtil.changeMoneyType(Double
				.parseDouble(tOP10Entity.getSalesSum())));
		return tOP10Entity;

	}

	public static ShopDayEntity getShopDayEntity(ShopDayEntity ShopDayEntity,
			double sum) {
		try {
			double discount = (Double.parseDouble(ShopDayEntity.getSalesSum()) / sum) * 100;
			ShopDayEntity.setDiscount(DateUtil.changeMoneyType(discount));
			// 转成标准金钱格式，方便看
			ShopDayEntity.setSalesSum(DateUtil.changeMoneyType(Double
					.parseDouble(ShopDayEntity.getSalesSum())));
		} catch (Exception e) {
			ShopDayEntity.setDiscount("-");
		}
		return ShopDayEntity;
	}

	// 时间纵向分析增长率(（今年除去年-1）*100)
	public static ShopAnalyseTimeGroup getShopAnalyseTimeGroupRaise(
			ShopAnalyseTimeGroup shopAnalyseTimeGroup, int circle) {

		double nowSales = doNum(shopAnalyseTimeGroup.getSalesNumber());
		double lastOneSales = doNum(shopAnalyseTimeGroup.getYearOneSalesNumber());
		double lastTwoSales = doNum(shopAnalyseTimeGroup.getYearTwoSalesNumber());

		try {
			if (lastOneSales != 0) {
				Double value = ((nowSales / lastOneSales) - 1) * 100;
				shopAnalyseTimeGroup.setRaiseCount(DateUtil
						.changeMoneyType(value));
			} else {
				shopAnalyseTimeGroup.setRaiseCount("100");
			}
		} catch (Exception e) {
			shopAnalyseTimeGroup.setRaiseCount("-");
		}
		
		try {
			if (lastTwoSales != 0) {
				Double value = ((lastOneSales / lastTwoSales) - 1) * 100;
				shopAnalyseTimeGroup.setYearOneRaiseCount(DateUtil
						.changeMoneyType(value));
			} else {
				shopAnalyseTimeGroup.setYearOneRaiseCount("100");
			}
		} catch (Exception e) {
			shopAnalyseTimeGroup.setYearOneRaiseCount("-");
		}
		if(circle==1){
			shopAnalyseTimeGroup.setYearOneRaiseCount("-");
		}
		// 没有第四年的数据，所以且设为"-"，如需要，后期可扩展得出
		if (circle == 2) {
			/*
			 * try{ if(lastTwoSales!=0){ Double
			 * value=(lastOneSales/lastTwoSales)-1*100;
			 * shopAnalyseTimeGroup.setYearTwoRaiseCount
			 * (DateUtil.changeMoneyType(value)); }else{
			 * shopAnalyseTimeGroup.setYearTwoRaiseCount("-"); }
			 * }catch(Exception e){
			 * shopAnalyseTimeGroup.setYearTwoRaiseCount("-"); }
			 */
			shopAnalyseTimeGroup.setYearTwoRaiseCount("-");
		}
		return shopAnalyseTimeGroup;
	}

	/**
	 * 功能:格式化相关数据格式（货币格式）
	 * <p>作者 杨荣忠 2014-9-30 上午09:52:13
	 * @param shopAnalyseTimeGroup
	 * @return
	 */
	public static ShopAnalyseTimeGroup changeShopAnalyseTimeGroupFormat(
			ShopAnalyseTimeGroup shopAnalyseTimeGroup) {

		shopAnalyseTimeGroup.setSalesNumber(doNumFormat(shopAnalyseTimeGroup
				.getSalesNumber()));
		shopAnalyseTimeGroup.setStockNumber(doNumFormat(shopAnalyseTimeGroup
				.getStockNumber()));
		shopAnalyseTimeGroup
				.setYearOneSalesNumber(doNumFormat(shopAnalyseTimeGroup
						.getYearOneSalesNumber()));
		shopAnalyseTimeGroup
				.setYearOneStockNumber(doNumFormat(shopAnalyseTimeGroup
						.getYearOneStockNumber()));
		shopAnalyseTimeGroup
				.setYearTwoSalesNumber(doNumFormat(shopAnalyseTimeGroup
						.getYearTwoSalesNumber()));
		shopAnalyseTimeGroup
				.setYearTwoStockNumber(doNumFormat(shopAnalyseTimeGroup
						.getYearTwoStockNumber()));

		return shopAnalyseTimeGroup;
	}

	public static ShopAnalyseTimeGroup getShopAnalyseTimeGroup(
			ShopAnalyseTimeGroup shopAnalyseTimeGroup, int circle,
			double yearNowSum, double yearOneSum, double yearTwoSum) {
		String yearNowDiscount = "";
		String yearOneDiscount = "";
		String yearTwoDiscount = "";
		try {
			if (yearNowSum != 0) {
				double value = Double.parseDouble(shopAnalyseTimeGroup
						.getSalesNumber())
						/ yearNowSum;
				yearNowDiscount = DateUtil.changeMoneyType(value * 100);
				shopAnalyseTimeGroup.setDiscount(yearNowDiscount);
			} else {
				shopAnalyseTimeGroup.setDiscount("100");
			}

		} catch (Exception e) {
			shopAnalyseTimeGroup.setDiscount("-");
		}
		try {
			if (yearOneSum != 0) {
				double value = Double.parseDouble(shopAnalyseTimeGroup
						.getYearOneSalesNumber())
						/ yearOneSum;
				yearOneDiscount = DateUtil.changeMoneyType(value * 100);
				shopAnalyseTimeGroup.setYearOneDiscount(yearOneDiscount);
			} else {
				shopAnalyseTimeGroup.setYearOneDiscount("100");
			}
		} catch (Exception e) {
			shopAnalyseTimeGroup.setYearOneDiscount("-");
		}
		if (circle == 2) {
			try {
				if (yearTwoSum != 0) {
					double value = Double.parseDouble(shopAnalyseTimeGroup
							.getYearTwoSalesNumber())
							/ yearTwoSum;
					yearTwoDiscount = DateUtil.changeMoneyType(value * 100);
					shopAnalyseTimeGroup.setYearTwoDiscount(yearTwoDiscount);
				} else {
					shopAnalyseTimeGroup.setYearTwoDiscount("100");
				}
			} catch (Exception e) {
				shopAnalyseTimeGroup.setYearTwoDiscount("-");
			}
		}
		return shopAnalyseTimeGroup;
	}

	public static Object[] concat(Object[] a, Object[] b) {
		Object[] c = new Object[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static boolean checkListSpace(List<String> list, int num) {
		boolean flag = true;
		if (list.size() != 0) {
			Collections.sort(list, new Comparator() {
				public int compare(Object _o1, Object _o2) {
					return chineseCompare(_o1, _o2);
				}
			});
			int max = Integer.parseInt(list.get(list.size() - 1));
			if (num <= max)
				flag = false;
		}
		return flag;
	}

	public static boolean checkList(List<Integer> haveList, int num) {
		boolean flag = true;
		if (haveList.size() > 0) {
			for (int i = 0; i < haveList.size(); i++) {
				if (haveList.get(i) == num) {
					flag = false;
				}
			}
		}
		return flag;
	}

	public static int chineseCompare(Object arg0, Object arg1) {
		return Integer.parseInt(arg0 + "") - Integer.parseInt(arg1 + "");
		// return 0;
	}

	// 测试main
	public static void main(String[] args) throws Exception {
		// System.out.println(getMonthLastMonth("04"));
	}

}
