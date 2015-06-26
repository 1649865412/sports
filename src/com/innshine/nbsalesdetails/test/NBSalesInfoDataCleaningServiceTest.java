//package com.innshine.nbsalesdetails.test;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//
//import com.innshine.nbsalesdetails.entity.NbSalesDetails;
//import com.innshine.nbsalesdetails.service.NbSalesDetailsService;
//import com.innshine.nbsalesdetails.timetask.NBSalesInfoDataCleaningService;
//import com.innshine.productinfo.entity.ProductInfo;
//import com.innshine.productinfo.service.ProductInfoService;
//import com.utils.DateUtils;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-shiro.xml" })
//@TestExecutionListeners( { TransactionalTestExecutionListener.class })
//public class NBSalesInfoDataCleaningServiceTest extends AbstractJUnit4SpringContextTests
//{
//	@Autowired
//	private NbSalesDetailsService nbSalesDetailsService;
//	
//	@Autowired
//	private NBSalesInfoDataCleaningService nbSalesInfoDataCleaningService;
//	
//	@Autowired
//	private ProductInfoService productInfoService;
//	
//	public void addSales() throws ParseException
//	{
//		Random random = new Random(1000);
//		String updateTime = DateUtils.getNow(DateUtils.DEFAULT_FORMAT);
//		List<NbSalesDetails> list = new ArrayList<NbSalesDetails>();
//		for (int j = 0; j <= 50; j++)
//		{
//			for (int i = 1; i <= 12; i++)
//			{
//				double avgPrice = random.nextInt(1200);
//				String startTime = "2014-" + (i > 9 ? i : "0" + i) + "-01";
//				String endTime = DateUtils.format(DateUtils.getEndDayOfMonth(new SimpleDateFormat(
//						DateUtils.SIMPLE_DEFAULT_FORMAT).parse(startTime)), DateUtils.SIMPLE_DEFAULT_FORMAT);
//				double number = random.nextInt(2500);
//				NbSalesDetails nbSalesDetails = new NbSalesDetails();
//				nbSalesDetails.setUpccode("6912346" + j);
//				nbSalesDetails.setAvgCurrentPrice(avgPrice);
//				nbSalesDetails.setSalesNumber(number);
//				nbSalesDetails.setSalesAmount(number * avgPrice);
//				nbSalesDetails.setMarketStartTime(startTime);
//				nbSalesDetails.setMarketEndTime(endTime);
//				
//				nbSalesDetails.setUpdateTime(updateTime);
//				nbSalesDetails.setOrganizationId(10L);
//				nbSalesDetails.setPlatformId("" + j);
//				nbSalesDetails.setMaterialNumber("" + j);
//				nbSalesDetails.setEnteringTime(endTime);
//				nbSalesDetails.setOrderNumber(((Integer) random.nextInt(2000)).doubleValue());
//				nbSalesDetails.setStockNumber(((Integer) random.nextInt(888)).doubleValue());
//				nbSalesDetails.setArriveNumber(((Integer) random.nextInt(999)).doubleValue());
//				
//				list.add(nbSalesDetails);
//			}
//		}
//		
//		nbSalesDetailsService.saveOrUpdate(list);
//	}
//	
//	@Test
//	public void addProductInfo() throws ParseException
//	{
//		Random random = new Random(1000);
//		String updateTime = DateUtils.getNow(DateUtils.DEFAULT_FORMAT);
//		List<ProductInfo> list = new ArrayList<ProductInfo>();
//		for (int j = 0; j <= 50; j++)
//		{
//			double avgPrice = random.nextInt(1200);
//			String startTime = "2014-01-01";
//			// String endTime = DateUtils.format(DateUtils.getEndDayOfMonth(new
//			// SimpleDateFormat(
//			// DateUtils.SIMPLE_DEFAULT_FORMAT).parse(startTime)),
//			// DateUtils.SIMPLE_DEFAULT_FORMAT);
//			// double number = random.nextInt(2500);
//			// NbSalesDetails nbSalesDetails = new NbSalesDetails();
//			// nbSalesDetails.setUpccode("6912346" + j);
//			// nbSalesDetails.setAvgCurrentPrice(avgPrice);
//			// nbSalesDetails.setSalesNumber(number);
//			// nbSalesDetails.setSalesAmount(number * avgPrice);
//			// nbSalesDetails.setMarketStartTime(startTime);
//			// nbSalesDetails.setMarketEndTime(endTime);
//			//				
//			// nbSalesDetails.setUpdateTime(updateTime);
//			// nbSalesDetails.setOrganizationId(10L);
//			// nbSalesDetails.setPlatformId("" + j);
//			// nbSalesDetails.setMaterialNumber("" + j);
//			// nbSalesDetails.setEnteringTime(endTime);
//			// nbSalesDetails.setOrderNumber(((Integer)
//			// random.nextInt(2000)).doubleValue());
//			// nbSalesDetails.setStockNumber(((Integer)
//			// random.nextInt(888)).doubleValue());
//			// nbSalesDetails.setArriveNumber(((Integer)
//			// random.nextInt(999)).doubleValue());
//			
//			ProductInfo productInfo = new ProductInfo();
//			productInfo.setProductUpccode("6912346" + j);
//			productInfo.setProductPlatformId("" + j);
//			productInfo.setMaterialNumber("" + j);
//			productInfo.setProductName("test-" + j);
//			productInfo.setUpdateTime(updateTime);
//			productInfo.setOrganizationId(10L);
//			productInfo.setInlineOr2ndline(j % 2 == 0 ? "inline" : "line");
//			productInfo.setProductLfPf(j % 2 == 0 ? "LF" : "PF");
//			productInfo.setSystemId("" + j);
//			productInfo.setQuater("2014Q1");
//			productInfo.setSeries("Lifestyle");
//			productInfo.setOnMarketMonth("2014-01");
//			productInfo.setOnSalesTime("2014-01-01");
//			productInfo.setProductSex(j % 2 == 0 ? "男" : "女");
//			productInfo.setTagPrice(((Integer) random.nextInt(3000)).doubleValue());
//			list.add(productInfo);
//			
//		}
//		
//		productInfoService.saveOrUpdate(list);
//	}
//	
//	public void testDoDataCleaningTask()
//	{
//		nbSalesInfoDataCleaningService.doDataCleaningTask();
//	}
//	
// }
