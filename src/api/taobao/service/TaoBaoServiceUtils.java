package api.taobao.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api.taobao.constant.GlobalConstants;
import api.taobao.constant.TaobaoGlobalConstants;
import api.taobao.entity.RefreshTokenBean;
import api.taobao.entity.Shop;
import api.utils.Util;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.WebUtils;

/**
 * 提供获取淘宝API连接服务类 <code>TaoBaoService.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class TaoBaoServiceUtils
{
	
	private static final Logger LOG = LoggerFactory.getLogger(TaoBaoServiceUtils.class);
	public static final String FOMATE_JSON = "json";
	public static final String FOMATE_XML = "xml";
	
	/**
	 * 根据shop中的appkey - appSecret - sessionky 获取对应api连接，返回TaobaoClient
	 * 
	 * @param shop
	 *        拉订单的店铺对象
	 * @param dataFomate
	 *        返回的数据类型json/xml TaoBaoService.FOMATE_JSON/TaoBaoService.FOMATE_XML
	 * @return TaobaoClient
	 */
	public static TaobaoClient getTaobaoClient(Shop shop, String dataFomate)
	{
		// // 0表淘宝旗舰店，1表示分销，2表示京东，5表示其他
		// if (shop.getIsFenxiao() == 1)
		// {
		// return getTaobaoFenxiaoClient(shop);
		// }
		// else
		// {
		return getTaobaoNormalClient(shop, dataFomate);
		// }
	}
	
	public static TaobaoClient getTaobaoNormalClient(Shop shop, String dataFormate)
	{
		String appKey = shop.getAppkey();
		String appSecret = shop.getAppSecret();
		String serverUrl = TaobaoGlobalConstants.TAOBAO_URL;
		int isTest = shop.getIsTest();
		if (isTest == GlobalConstants.YES)
		{
			serverUrl = TaobaoGlobalConstants.SANDBOX_URL;
		}
		TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret,
				StringUtils.isEmpty(dataFormate) ? FOMATE_JSON : dataFormate);
		return client;
	}
	
	/**
	 * 更新Session和Refresh_token 到shop表中
	 */
	public static boolean updateShopSession(Shop shop, RefreshTokenBean rb)
	{
		boolean falg = false;
		try
		{
			if (rb != null)
			{
				if (rb.getRefresh_token() != null && rb.getTop_session() != null)
				{
					shop.setRefreshToken(rb.getRefresh_token());
					shop.setSessionKey(rb.getTop_session());
					// shop.save();
					falg = true;
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return falg;
	}
	
	/**
	 * 获取新的sessionKey和Refresh_token
	 * 
	 * @return
	 * @throws IOException
	 */
	public static RefreshTokenBean getRefreshTokenBean(String freshUrl) throws Exception
	{
		LOG.info("----freshUrl----" + freshUrl);
		// import com.taobao.api.internal.util.WebUtils
		String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60, 30 * 1000 * 60);
		
		LOG.info("---response---" + response);
		
		RefreshTokenBean rb = new RefreshTokenBean();
		rb = Util.json2bean(response, RefreshTokenBean.class);
		
		return rb;
		
	}
	
	/**
	 * 获取RefreshToken签名
	 * 
	 * @param shop
	 * @return
	 */
	public static String getRefreshTokenSign(Shop shop) throws Exception
	{
		String appkey = shop.getAppkey();
		String secret = shop.getAppSecret();
		// 注意：如果解析未获得refresh token，可以传入session key
		String refreshToken = shop.getRefreshToken();
		String sessionkey = shop.getSessionKey();
		
		Map<String, String> signParams = new TreeMap<String, String>();
		signParams.put("appkey", appkey);
		signParams.put("refresh_token", sessionkey);
		signParams.put("sessionkey", sessionkey);
		
		StringBuilder paramsString = new StringBuilder();
		Set<Entry<String, String>> paramsEntry = signParams.entrySet();
		for (Entry<String, String> paramEntry : paramsEntry)
		{
			paramsString.append(paramEntry.getKey()).append(paramEntry.getValue());
		}
		String charset = "utf-8";
		String sign = DigestUtils.md5Hex((paramsString.toString() + secret).getBytes(charset)).toUpperCase();
		String signEncoder = URLEncoder.encode(sign, charset);
		String appkeyEncoder = URLEncoder.encode(appkey, charset);
		String refreshTokenEncoder = URLEncoder.encode(refreshToken, charset);
		String sessionkeyEncoder = URLEncoder.encode(sessionkey, charset);
		
		StringBuffer freshUrl = new StringBuffer("http://container.open.taobao.com/container/refresh?");
		freshUrl.append("appkey=" + appkeyEncoder);
		
		if (refreshToken != null && (!"".equals(refreshToken)))
		{
			freshUrl.append("&refresh_token=" + refreshTokenEncoder);
		}
		else
		{
			// 传入session key作为refresh_token参数
			freshUrl.append("&refresh_token=" + sessionkeyEncoder);
		}
		freshUrl.append("&sessionkey=" + sessionkeyEncoder);
		freshUrl.append("&sign=" + signEncoder);
		
		return freshUrl.toString();
		
	}
}
