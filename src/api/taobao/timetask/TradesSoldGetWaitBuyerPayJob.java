package api.taobao.timetask;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import api.taobao.service.TradesSoldGetService;
import api.utils.LogInfoUtilService;

import com.utils.DateUtils;
import com.utils.Exceptions;

/**
 * 检测3天内付款交易信息获取定时任务类
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */

public class TradesSoldGetWaitBuyerPayJob implements Job
{
	private static final Logger LOG = LoggerFactory.getLogger(TradesSoldGetWaitBuyerPayJob.class);
	
	@Autowired
	private TradesSoldGetService tradesSoldGetService;
	
	@Autowired
	private LogInfoUtilService logInfoUtilService;
	
	public void doWaitBuyerPay()
	{
		LOG.info("//=========================== Taobao Trade Wait Buyer Pay Data Start =====================//");
		LOG.info(" Begin Time  : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		logInfoUtilService.error("检测3天内未支付订单任务执行开始......");
		try
		{
			tradesSoldGetService.checkNonPaymentTradeInfo();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			logInfoUtilService.error("检测3天内未支付订单任务执行异常：" + Exceptions.getStackTraceAsString((Exception) e));
		}
		logInfoUtilService.info("检测3天内未支付订单任务执行结束......");
		LOG.info(" End Time : " + DateUtils.getNow(DateUtils.DEFAULT_FORMAT));
		LOG.info("//=============================Taobao Trade Wait Buyer Pay Data End ======================//");
		
	}
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		doWaitBuyerPay();
		
	}
}
