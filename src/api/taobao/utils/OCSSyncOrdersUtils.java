package api.taobao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.taobao.entity.Shop;
import api.taobao.entity.Trade;
import api.utils.HtmlUnitUtils;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.utils.Exceptions;

/**
 * OCS订单同步接口调用工具类 <code>OCSInterfaceUtils.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class OCSSyncOrdersUtils
{
	private static final String DEFAULT_RESPONSE_PARAMS_MSG = "msg";
	
	/**
	 * 响应结果中的标成功与失败的参数名
	 */
	private static final String DEFAULT_RESPONSE_PARAMS_RSP = "rsp";
	
	/**
	 * 同步成功
	 */
	private static final String DEFAULT_RESPONSE_SUCC = "succ";
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OCSSyncOrdersUtils.class);
	
	/**
	 * 订单类型
	 */
	private static final String DEFAULT_SYNC_TYPE = "zx";
	
	/**
	 * OCS 登陆URL
	 */
	private static final String LOGIN_URL = "http://121.196.131.70/index.php/openapi/pam_callback/login/module/pam_passport_basic/type/shopadmin/appid/desktop/redirect/aHR0cDovLzEyMS4xOTYuMTMxLjcwL2luZGV4LnBocC8%3D";
	
	/**
	 * OCS 调用订单同步接口URL
	 */
	private static final String SYNC_ORDER_URL = "http://121.196.131.70/index.php?app=ome&ctl=admin_shop&act=sync_order";
	
	/**
	 * 参数名：登陆用户名,
	 */
	private static final String LOGIN_PARAM_USER_NAME = "uname";
	/**
	 * 参数名：登陆用户密码
	 */
	private static final String LOGIN_PARAM_PASSWORD = "password";
	
	/**
	 * 用户名
	 */
	private static final String LOGIN_PARAM_USER_NAME_VALUE = "downorder";
	
	/**
	 * 密码
	 */
	private static final String LOGIN_PARAM_PASSWORD_VALUE = "downorder";
	
	/**
	 * 订单同步参数名：订单编号
	 */
	private static final String SYNC_ORDER_PARAM_ORDER_ID = "order_id";
	
	/**
	 *订单同步参数名：类型
	 */
	private static final String SYNC_ORDER_PARAM_TYPE = "type";
	
	/**
	 * 订单同步参数名：店铺ID
	 */
	private static final String SYNC_ORDER_PARAM_SHOP_ID = "shop_id";
	
	/**
	 * 登陆成功标致
	 */
	// private static final String LOGIN_SUCCESS_FLAG =
	// "管理后台 - Powered By ShopEx";
	
	/**
	 * 登陆成功标致
	 */
	private static final String LOGIN_SUCCESS_FLAG_SUFFIX1 = "管理后台";
	
	/**
	 * 登陆成功标致
	 */
	private static final String LOGIN_SUCCESS_FLAG_SUFFIX2 = "ShopEx";
	
	/**
	 * 同步差异订单至OCS，并返回同步失败的订单
	 * 
	 * @param trades
	 *        需要同步的交易订单列表
	 * @param shop
	 *        店铺属性对象
	 * @return List< Trade>
	 */
	public static List<Trade> syncOrderToOcs(List<Trade> trades, Shop shop)
	{
		List<Trade> syncFailList = new ArrayList<Trade>();
		if (CollectionUtils.isNotEmpty(trades) && null != shop)
		{
			WebClient webClient = null;
			try
			{
				// 创建模拟浏览器
				webClient = HtmlUnitUtils.createWebClient();
				
				// 效验是否登陆成功
				if (isLogin(webClient))
				{
					for (Trade trade : trades)
					{
						if (trade != null)
						{
							Long tid = trade.getTid();
							
							if (null != tid)
							{
								// OCS订单同步，返回同步失败与成功信息,true:同步成功 false:同步失败
								if (isSyncOrder(String.valueOf(tid), shop, webClient))
								{
									currentThreadSlepp();
								}
								else
								{
									syncFailList.add(trade);
								}
							}
						}
						
					}
				}
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
			finally
			{
				// 关闭模拟浏览器
				HtmlUnitUtils.closeWebClient(webClient);
			}
		}
		
		return syncFailList;
		
	}
	
	/**
	 * 休眠500MS，防止请求过快，导致同步失败
	 */
	private static void currentThreadSlepp()
	{
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * OCS订单同步，返回同步失败与成功信息,true:同步成功 false:同步失败
	 * 
	 * @param tid
	 *        订单号
	 * @param shop
	 *        本地店铺属性对象
	 * @param webClient
	 *        模拟的浏览器对象
	 * @return true:同步成功 false:同步失败
	 */
	private static boolean isSyncOrder(String tid, Shop shop, WebClient webClient)
	{
		boolean flag = false;
		// 构造同步参数
		Map<String, String> syncParamsMap = getSyncOrdersParams(tid, shop);
		
		// 同步订单
		HtmlPage htmlPage = HtmlUnitUtils.getHtmlPage(SYNC_ORDER_URL, syncParamsMap, webClient);
		
		if (null != htmlPage)
		{
			// 获取响应结果 ，文本信息
			String response = HtmlUnitUtils.getHtmlAsText(htmlPage);
			
			// {\"rsp\":\"fail\",\"msg\":\"\u540c\u6b65\u8ba2\u5355\u5931\u8d25(\u8ba2\u5355\u4e0d\u5b58\u5728\uff1a11111)
			// {"rsp":"succ"}
			if (LOG.isInfoEnabled())
			{
				String msg = "order_id=" + tid + ",shop_id=" + (null != shop ? shop.getShopId() : "");
				LOG.info("========== Ocs Sync Order Info. " + msg + ", response = " + response + "==========");
			}
			
			flag = isResopnseMsg(response);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * 处理响应结果，如果返回消息为succ，则为true,故则为false
	 * 
	 * @param response
	 *        响应结果字符串
	 * @return true:同步成功 false：同步失败
	 */
	private static boolean isResopnseMsg(String response)
	{
		if (StringUtils.isNotEmpty(response))
		{
			try
			{
				JSONObject jsonObject = JSONObject.fromObject(response);
				String rsp = jsonObject.getString(DEFAULT_RESPONSE_PARAMS_RSP);
				// succ 表示同步成功
				if (DEFAULT_RESPONSE_SUCC.equalsIgnoreCase(rsp))
				{
					return true;
				}
				
				else
				{
					String msg = jsonObject.getString(DEFAULT_RESPONSE_PARAMS_MSG);
					if (null != msg)
					{
						LOG.warn("Ocs Sync Order Failed ! Response msg = " + new String(msg));
					}
				}
				
			}
			catch (Exception e)
			{
				LOG.error(Exceptions.getStackTraceAsString(e));
			}
		}
		
		return false;
	}
	
	/**
	 * 构造同步参数
	 * 
	 * @param orderId
	 *        订单号
	 * @param shop
	 *        本地店铺属性对象
	 * @return Map<String, String>
	 */
	private static Map<String, String> getSyncOrdersParams(String orderId, Shop shop)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(SYNC_ORDER_PARAM_ORDER_ID, orderId);
		map.put(SYNC_ORDER_PARAM_SHOP_ID, shop.getShopId());
		map.put(SYNC_ORDER_PARAM_TYPE, DEFAULT_SYNC_TYPE);
		return map;
	}
	
	/**
	 * 效验是否登陆成功，登陆成功才能继续向下执行
	 * 
	 * @param webClient
	 * @return
	 */
	private static boolean isLogin(WebClient webClient)
	{
		if (null != webClient)
		{
			HtmlPage htmlPage = HtmlUnitUtils.getHtmlPage(LOGIN_URL, getLoginParams(), webClient);
			
			if (null != htmlPage)
			{
				String title = htmlPage.getTitleText();
				
				if (StringUtils.isNotEmpty(title))
				{
					if (title.indexOf(LOGIN_SUCCESS_FLAG_SUFFIX1) != -1)
					{
						return true;
					}
					if (title.indexOf(LOGIN_SUCCESS_FLAG_SUFFIX2) != -1)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 构造登陆参数
	 * 
	 * @return Map< String, String > key:参数名 value:参数值
	 */
	private static Map<String, String> getLoginParams()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(LOGIN_PARAM_USER_NAME, LOGIN_PARAM_USER_NAME_VALUE);
		map.put(LOGIN_PARAM_PASSWORD, LOGIN_PARAM_PASSWORD_VALUE);
		return map;
	}
}
