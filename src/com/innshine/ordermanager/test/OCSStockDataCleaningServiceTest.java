//package com.innshine.ordermanager.test;
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
//import com.innshine.ordermanager.timetask.OCSStockDataCleaningService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-shiro.xml" })
//@TestExecutionListeners( { TransactionalTestExecutionListener.class })
//public class OCSStockDataCleaningServiceTest extends AbstractJUnit4SpringContextTests
//{
//	@Autowired
//	private OCSStockDataCleaningService ocsStockDataCleaningService;
//	
//	@Test
//	public void testDataCleaningToBaseData()
//	{
//		ocsStockDataCleaningService.doTask();
//	}
// }
