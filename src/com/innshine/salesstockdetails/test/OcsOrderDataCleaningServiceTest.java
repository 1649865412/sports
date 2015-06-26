package com.innshine.salesstockdetails.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.innshine.salesstockdetails.timetask.OcsReturnOrderDataCleaningService;
import com.innshine.salesstockdetails.timetask.OcsSalesOrderDataCleaningService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:applicationContext-shiro.xml" })
@TestExecutionListeners( { TransactionalTestExecutionListener.class })
public class OcsOrderDataCleaningServiceTest extends AbstractJUnit4SpringContextTests
{
	@Autowired(required = true)
	private OcsSalesOrderDataCleaningService salesOrderDataCleaningService;
	
	@Autowired(required = true)
	private OcsReturnOrderDataCleaningService ocsReturnOrderDataCleaningService;
	
	@Test
	public void testDoTask()
	{
		salesOrderDataCleaningService.doTask();
	}
	
	@Test
	public void testReturnDoTask()
	{
		ocsReturnOrderDataCleaningService.doTask();
	}
	
	public void testOutDate()
	{
		System.out.println((new Date().getTime()) / 1000);
	}
}
