package com.innshine.shopAnalyse.util;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.innshine.shopAnalyse.entity.ShopAnalyseCheckEntity;
import com.innshine.shopAnalyse.entity.ShopAnalyseTimeCheckEntity;
import com.innshine.shopAnalyse.entity.ShopTimeYMQD;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseEntity;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseGroup;
import com.innshine.shopAnalyse.excelEntity.ShopAnalyseTimeGroup;
import com.innshine.shopAnalyse.excelEntity.ShopDayEntity;
import com.innshine.shopAnalyse.excelEntity.TOP10Entity;
import com.utils.reflection.ReflectionUtils;



public class ParamTool {

	/**
	 * 功能:销售分析模块查询过滤条件
	 * <p>作者 杨荣忠 2014-9-23 上午10:51:53
	 * @param param
	 * @param shopAnalyseEntity
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> getShopAnaylseCheckParam(
			Map<String, Object> param, ShopAnalyseCheckEntity shopAnalyseEntity)
			throws ParseException {

		param = TimeTool.getParamTime(param, shopAnalyseEntity);
		String productPlatformId = shopAnalyseEntity.getProductPlatformId();
		String quater = shopAnalyseEntity.getQuater();
		String productLfPf = shopAnalyseEntity.getProductLfPf();
		String beginPrice = shopAnalyseEntity.getBeginPrice();
		String endPrice = shopAnalyseEntity.getEndPrice();
		Long brandId = shopAnalyseEntity.getBrandId();
		String materialNumber = shopAnalyseEntity.getMaterialNumber();
		String monthlyFieldName = shopAnalyseEntity.getMonthlyFieldName();

		if (productPlatformId != null && (productPlatformId != ""))
			param.put("productPlatformId", productPlatformId);
		if (StringUtils.isNotEmpty(quater))
		{	param.put("quater", quater);
		
		 	// modifty 2014-10-15 处理“销售分析” ，group by 中Q季参数名与where条件参数名冲突，导致查询数据空null，
		    // 故where中的Q季参数名前面加个 “_”，加以区分，不影响其它SQL beign
			param.put("_quater", quater);
			// modifty 2014-10-15 处理“销售分析” ，group by 中Q季参数名与where条件参数名冲突，导致查询数据空null，
		    // 故where中的Q季参数名前面加个 “_”，加以区分，不影响其它SQL end
			
		}
		if (productLfPf != null && (productLfPf != ""))
			param.put("productLfPf", productLfPf);
		if (beginPrice != null && (beginPrice != ""))
			param.put("beginPrice", beginPrice);
		if (endPrice != null && (endPrice != ""))
			param.put("endPrice", endPrice);
		if (brandId != null)
			param.put("brandId", brandId);
	
		// modifty 2014-10-15 问题单 添加款号查询  begin
		if(StringUtils.isNotEmpty(materialNumber))
			param.put("materialNumber", materialNumber);
		// modifty 2014-10-15 问题单 添加款号查询 end
		
		// modifty 2014-10-16  问题单 删除月报分析类型。在月度统计中，支持选择首列，  begin
		if(StringUtils.isNotEmpty(monthlyFieldName))
		{
			param.put("monthlyFieldName", monthlyFieldName);
		}
		// modifty 2014-10-16 问题单 删除月报分析类型。在月度统计中，支持选择首列， end
		return param;
	}
	
	
	public static Map<String, Object> getShopAnaylseCheckParamGroup(
			Map<String, Object> param, ShopAnalyseCheckEntity shopAnalyseEntity)
			throws ParseException {
		param = TimeTool.getParamTime(param, shopAnalyseEntity);
		Long brandId = shopAnalyseEntity.getBrandId();
		if (brandId != null)
			param.put("brandId", brandId);
		return param;
	}
	
	/**
	 * 功能:销售时间分析 属性检测
	 * <p>作者 杨荣忠 2014-9-23 下午12:06:38
	 * @param param
	 * @param shopAnalyseTimeCheckEntity
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getShopAnaylseTimeCheckParam(
			Map<String, Object> param,
			ShopAnalyseTimeCheckEntity shopAnalyseTimeCheckEntity)
			throws Exception {

		shopAnalyseTimeCheckEntity = TimeTool.check(shopAnalyseTimeCheckEntity);
		Map<String, String> timemap = TimeTool.getMapTime(
				shopAnalyseTimeCheckEntity, 0);

		param.put("beginTime", timemap.get("beginTime"));
		param.put("endTime", timemap.get("endTime"));

		param = timeAnalyseDataMap(param, shopAnalyseTimeCheckEntity);

		return param;
	}

	
	/**
	 * 功能:
	 * <p>作者 杨荣忠 2014-9-23 下午12:07:50
	 * @param param
	 * @param shopAnalyseTimeCheckEntity
	 * @return
	 */
	public static Map<String, Object> timeAnalyseDataMap(
			Map<String, Object> param,
			ShopAnalyseTimeCheckEntity shopAnalyseTimeCheckEntity) {
		String quater = shopAnalyseTimeCheckEntity.getQuater();
		String series = shopAnalyseTimeCheckEntity.getSeries();
		Long brandId = shopAnalyseTimeCheckEntity.getBrandId();
		if (quater != null && (quater != ""))
			param.put("quater", quater);
		if (series != null && (series != ""))
			param.put("series", series);
		if (brandId != null)
			param.put("brandId", brandId);
		return param;
	}


	public static Map<String, Object> timeAnalyseDataMapGroup(
			Map<String, Object> param,
			ShopAnalyseTimeCheckEntity shopAnalyseTimeCheckEntity) {
		
		Long brandId = shopAnalyseTimeCheckEntity.getBrandId();
		if (brandId != null)
			param.put("brandId", brandId);
		return param;
	}

	
	public static Map<String, Object> ShopAnslyseParam(
			Map<String, Object> param, String groupSelect[])
			throws ParseException {
		if (groupSelect.length == 1) {
			param.put("ShopAnaylseType", groupSelect[0]);
			if (OtherTool.checkShopAnalyseQ(groupSelect[0])) {
				
				/**
				 * 当月时间(补上两个参数，用于求销售分析Q季本月销售)
				 */
				param.put("nowMonthBegin", DateUtil.nowmonthfirst());
				param.put("nowTimeEnd", DateUtil.nowtimeString());
			}
		} else {
			// 此处是多分类的
			for (int i = 0; i < groupSelect.length; i++) {
				param.put(groupSelect[i] + "", groupSelect[i]);
			}
			// 多级分类，取第一个排序，方便报表组件合并
			param.put("FirstOrder", groupSelect[0]);
			// param.put("SecondOrder",groupSelect[1] );

		}
		return param;
	}

	public static Map<String, Object> ShopAnslyseTimeParam(
			Map<String, Object> param, String groupSelect[])
			throws ParseException {
		for (int i = 0; i < groupSelect.length; i++) {
			param.put(groupSelect[i] + "", groupSelect[i]);
		}
		param.put("FirstOrder", groupSelect[0]);
		return param;
	}
	
	
	/**
	 * 功能:top10索引与占比
	 * <p>作者 杨荣忠 2014-9-23 下午04:26:00
	 * @param TOP10EntityList
	 * @return
	 */
	public static List<TOP10Entity> TOP10EntityListGetIndexAndDiscount(
			List<TOP10Entity> TOP10EntityList) {
		if (TOP10EntityList != null) {
			double sum = 0;
			double sumNum = 0;
			String all = "-";
			for (int i = 0; i < TOP10EntityList.size(); i++) {
				TOP10Entity tOP10Entity = TOP10EntityList.get(i);
				try {
					sum += Double.parseDouble(tOP10Entity.getSalesSum());
				} catch (Exception e) {
					sum += 0;
				}
				try {
					sumNum += Double.parseDouble(tOP10Entity.getSalesNumber());
				} catch (Exception e) {
					sumNum += 0;
				}
			}
			for (int i = 0; i < TOP10EntityList.size(); i++) {
				TOP10Entity tOP10Entity = TOP10EntityList.get(i);
				tOP10Entity = OtherTool.getTOP10Entity(tOP10Entity, i, sum);
			}
			TOP10Entity TOP10EntityAll = new TOP10Entity("汇总：", all, all, all,
					all, all, all, DateUtil.changeMoneyType(sumNum), DateUtil
							.changeMoneyType(sum), "100");

			TOP10EntityList.add(TOP10EntityAll);
		}
		return TOP10EntityList;
	}

	
	/**
	 * 功能:日报占比
	 * <p>作者 杨荣忠 2014-9-23 下午04:26:26
	 * @param ShopDayEntityList
	 * @return
	 */
	public static List<ShopDayEntity> ShopDayEntityGetDiscount(
			List<ShopDayEntity> ShopDayEntityList) {
		if (ShopDayEntityList != null) {
			double sum = 0;
			double sumNum = 0;
			String all = "-";
			for (int i = 0; i < ShopDayEntityList.size(); i++) {
				ShopDayEntity ShopDayEntity = ShopDayEntityList.get(i);
				try {
					sum += Double.parseDouble(ShopDayEntity.getSalesSum());
				} catch (Exception e) {
					sum += 0;
				}
				try {
					sumNum += Double
							.parseDouble(ShopDayEntity.getSalesNumber());
					// 转成标准金钱格式，方便看
					ShopDayEntity.setSalesNumber(DateUtil
							.changeMoneyType(Double.parseDouble(ShopDayEntity
									.getSalesNumber())));
				} catch (Exception e) {
					sumNum += 0;
				}
			}

			for (int i = 0; i < ShopDayEntityList.size(); i++) {
				ShopDayEntity ShopDayEntity = ShopDayEntityList.get(i);
				ShopDayEntity = OtherTool.getShopDayEntity(ShopDayEntity, sum);
			}

			ShopDayEntity ShopDayEntity = new ShopDayEntity("汇总：", all, all,
					all, all, all, all, DateUtil.changeMoneyType(sumNum),
					DateUtil.changeMoneyType(sum), "100");
			ShopDayEntityList.add(ShopDayEntity);
		}
		return ShopDayEntityList;
	}

	/**
	 * 功能:销售分析分类增加折扣
	 * <p>作者 杨荣忠 2014-9-23 下午04:26:43
	 * @param ShopAnalyseEntityList
	 * @param groupSelect  页面选择字段列表
	 * @return
	 */
	public static List<ShopAnalyseGroup> ShopAnalyseEntityGetGroupSumAndDiscountCheck(
			List<ShopAnalyseGroup> ShopAnalyseEntityList, String[] groupSelect) {
		if (ShopAnalyseEntityList != null) {
			double all_salesNumber = 0;
			for (int i = 0; i < ShopAnalyseEntityList.size(); i++) {
				ShopAnalyseGroup ShopAnalyseGroup = ShopAnalyseEntityList
						.get(i);
				try {
					all_salesNumber += Double.parseDouble(ShopAnalyseGroup
							.getSalesNumber());
				} catch (Exception e) {
					all_salesNumber += 0;
				}
			}
			for (int i = 0; i < ShopAnalyseEntityList.size(); i++) {
				ShopAnalyseGroup ShopAnalyseGroup = ShopAnalyseEntityList
						.get(i);
				ShopAnalyseGroup = OtherTool.getShopAnalyseGroup(
						ShopAnalyseGroup, all_salesNumber);
				try{
					double value=OtherTool.doNum(ShopAnalyseGroup.getSalesNumber());
					ShopAnalyseGroup.setSalesNumber(DateUtil.changeMoneyType(value));
				}catch(Exception e){
					ShopAnalyseGroup.setSalesNumber("-");
				}
			}
			
			// modifty 2014-10-15 "销售分析" 选择多个字段时，添加合计 begin
			// 添加合计
			ShopAnalyseGroup totalShopAnalyseGroup = new ShopAnalyseGroup();
			totalShopAnalyseGroup.setDiscount("100%");
			totalShopAnalyseGroup.setSalesNumber(String.valueOf(all_salesNumber));
			addTotalTitleByGroupType(totalShopAnalyseGroup,groupSelect);
			ShopAnalyseEntityList.add(totalShopAnalyseGroup);
			// modifty 2014-10-15 "销售分析" 选择多个字段时，添加合计 end
		}
		return ShopAnalyseEntityList;
	}

	/**
	 * 添加合计行标题
	 * @param totalShopAnalyseGroup 记录合计对象实体bean
	 * @param groupSelect 页面分组字段，默认已排序后数组
	 */
	private static void addTotalTitleByGroupType(Object totalShopAnalyseGroup, String[] groupSelect)
	{
		if(null != totalShopAnalyseGroup && ArrayUtils.isNotEmpty(groupSelect))
		{	
			String fieldName =	groupSelect[0];
			if(StringUtils.isNotEmpty(fieldName))
			{
				try
				{	
					fieldName = fieldName.indexOf("_") != -1 ? ConvertPageQueryFieldsToSQL.columnNameToFieldName(fieldName):fieldName;
					ReflectionUtils.setFieldValue(totalShopAnalyseGroup, fieldName, "合计");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		}
		
	}


	/**
	 * 功能:销售分析Q季单个导出求总各与售青率
	 * <p>作者 杨荣忠 2014-9-23 下午04:27:21
	 * @param ShopAnalyseEntityList
	 * @return
	 */
	public static List<ShopAnalyseEntity> ShopAnalyseEntityQuaterGetSumAndDiscount(
			List<ShopAnalyseEntity> ShopAnalyseEntityList) {
		if (ShopAnalyseEntityList != null) {
			double all_nowMonthSalesNumber = 0;
			double all_allSalesSum = 0;
			double all_arriveNumber = 0;
			String rate_out = "";// 售罄率
			for (int i = 0; i < ShopAnalyseEntityList.size(); i++) {
				ShopAnalyseEntity ShopAnalyseEntity = ShopAnalyseEntityList
						.get(i);
				all_nowMonthSalesNumber += OtherTool.doNum(ShopAnalyseEntity
						.getNowMonthSalesNumber());
				all_allSalesSum += OtherTool.doNum(ShopAnalyseEntity
						.getAllSalesSum());
				all_arriveNumber += OtherTool.doNum(ShopAnalyseEntity
						.getArriveNumber());
			}
			for (int i = 0; i < ShopAnalyseEntityList.size(); i++) {
				ShopAnalyseEntity ShopAnalyseEntity = ShopAnalyseEntityList
						.get(i);
				ShopAnalyseEntity = OtherTool.getShopAnalyseEntity(
						ShopAnalyseEntity, all_nowMonthSalesNumber);
			}
			try {
				// modifty 2014-10-15 两个double 0/0  相除得到NaN值，做一个特殊处理，防止页面展示该字段乱码(不认识的字符) begin
				double tmp = all_allSalesSum/ all_arriveNumber;
				rate_out = DateUtil.changeMoneyType(Double.isNaN(tmp)? 0  : tmp*100);
				// modifty 2014-10-15 两个double 0/0  相除得到NaN值，做一个特殊处理，防止页面展示该字段乱码(不认识的字符) end
			} catch (Exception e) {
				rate_out = "-";
			}
			// moidfty 2014-10-15 修改“TIL”为“合计” 
			// 分析类别 本月销售数量 本月销售占比 累积销售 售罄率（销售数量/到货数量）
			//ShopAnalyseEntity ShopAnalyseEntityAll = new ShopAnalyseEntity(
			//		"TIL", DateUtil.changeMoneyType(all_nowMonthSalesNumber),
			//		"100%", DateUtil.changeMoneyType(all_allSalesSum), rate_out);
			
			ShopAnalyseEntity ShopAnalyseEntityAll = new ShopAnalyseEntity(
					"合计", DateUtil.changeMoneyType(all_nowMonthSalesNumber),
					"100%", DateUtil.changeMoneyType(all_allSalesSum),rate_out);
			ShopAnalyseEntityList.add(ShopAnalyseEntityAll);
		}
		return ShopAnalyseEntityList;
	}

	
	
	public static List<ShopAnalyseEntity> ShopAnalyseEntityGetSumAndDiscount(
			List<ShopAnalyseEntity> ShopAnalyseEntityList) {
		if (ShopAnalyseEntityList != null) {
			double all_salesNumber = 0;
			double all_salesSum = 0;
			double all_salesTagMoney = 0;

			for (int i = 0; i < ShopAnalyseEntityList.size(); i++) {
				ShopAnalyseEntity ShopAnalyseEntity = ShopAnalyseEntityList
						.get(i);
				all_salesNumber += OtherTool.doNum(ShopAnalyseEntity
						.getSalesNumber());
				all_salesSum += OtherTool
						.doNum(ShopAnalyseEntity.getSalesSum());
				all_salesTagMoney += OtherTool.doNum(ShopAnalyseEntity
						.getSalesTagMoney());
			}

			for (int i = 0; i < ShopAnalyseEntityList.size(); i++) {
				ShopAnalyseEntity ShopAnalyseEntity = ShopAnalyseEntityList
						.get(i);
				ShopAnalyseEntity = OtherTool.getShopAnalyseEntity(
						ShopAnalyseEntity, all_salesNumber, all_salesSum,
						all_salesTagMoney);

			}
			// 分析类别 销售数量 销售占比 付款金额 付款金额占比 吊牌金额 吊牌金额占比
			ShopAnalyseEntity ShopAnalyseEntityAll = new ShopAnalyseEntity(
					"合计", DateUtil.changeMoneyType(all_salesNumber), "100%",
					DateUtil.changeMoneyType(all_salesSum) + "", "100%",
					DateUtil.changeMoneyType(all_salesTagMoney), "100%");
			ShopAnalyseEntityList.add(ShopAnalyseEntityAll);
		}

		return ShopAnalyseEntityList;
	}

	public static Double getShopAnalyseTimeAllSales(String[] tableDay,
			String year, List<ShopTimeYMQD> list) {
		double all_Num = 0;
		for (int j = 0; j < tableDay.length; j++) {
			Object object[] = new Object[3];
			String time = year + tableDay[j].replace("-", "");
			if (list.size() > 0) {
				for (int m = 0; m < list.size(); m++) {
					// 取时间
					ShopTimeYMQD shopTimeYMQD = list.get(m);
					String sqlTime = shopTimeYMQD.getTimeGroup() + "";
					if (time.equals(sqlTime)) {
						try {
							all_Num += Double.parseDouble(shopTimeYMQD
									.getSalesNumSum());
						} catch (Exception e) {
							all_Num += 0;
						}
					}
				}
			}
		}
		return all_Num;
	}

	
	/**
	 * 功能: 时间纵向分析占比与增长率
	 * <p>作者 杨荣忠 2014-9-23 下午04:28:04
	 * @param list
	 * @param shopTime
	 * @return
	 */
	public static List<ShopAnalyseTimeGroup> getTimeAnalyse(
			List<ShopAnalyseTimeGroup> list, ShopAnalyseTimeCheckEntity shopTime) {
		if (list != null) {
			int circle = shopTime.getCircle();
			double yearNowSum = 0;
			double yearOneSum = 0;
			double yearTwoSum = 0;
			for (int i = 0; i < list.size(); i++) {
				ShopAnalyseTimeGroup shopAnalyseTimeGroup = list.get(i);
				try {
					yearNowSum += Double.parseDouble(shopAnalyseTimeGroup
							.getSalesNumber());
				} catch (Exception e) {
					yearNowSum += 0;
				}
				try {
					yearOneSum += Double.parseDouble(shopAnalyseTimeGroup
							.getYearOneSalesNumber());
				} catch (Exception e) {
					yearOneSum += 0;
				}
				if (circle == 2) {
					try {
						yearTwoSum += Double.parseDouble(shopAnalyseTimeGroup
								.getYearTwoSalesNumber());
					} catch (Exception e) {
						yearTwoSum += 0;
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				ShopAnalyseTimeGroup shopAnalyseTimeGroup = list.get(i);
				// 占比
				shopAnalyseTimeGroup = OtherTool.getShopAnalyseTimeGroup(
						shopAnalyseTimeGroup, circle, yearNowSum, yearOneSum,
						yearTwoSum);
				// 增长率
				shopAnalyseTimeGroup = OtherTool.getShopAnalyseTimeGroupRaise(
						shopAnalyseTimeGroup, circle);
				// 数字格式化
				shopAnalyseTimeGroup = OtherTool
						.changeShopAnalyseTimeGroupFormat(shopAnalyseTimeGroup);
			}
		}
		return list;
	}

}
