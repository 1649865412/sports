package api.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.utils.Exceptions;

/**
 * 
 * 页面分析工具，没有界面的浏览器，<code>HtmlUnitUtils.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class HtmlUnitUtils
{
	
	/**
	 * 日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(HtmlUnitUtils.class);
	
	/**
	 * 创建模拟浏览器对象，里面设置了一些必要的参数
	 * 
	 * @return WebClient
	 */
	public static WebClient createWebClient()
	{
		// 模拟浏览器对象
		WebClient webClient = new WebClient();
		
		// 禁用CSS，防止二次请求下载CSS
		webClient.getOptions().setCssEnabled(false);
		
		// 加载JS不抛出错误
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		// 启动客户端重定向
		webClient.getOptions().setRedirectEnabled(true);
		
		// 关闭js解析
		webClient.getOptions().setJavaScriptEnabled(true);
		
		// 设置Ajax
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		webClient.waitForBackgroundJavaScript(10000);
		
		// 设置超时时间 ，默认1分钟
		webClient.getOptions().setTimeout(10000);
		
		webClient.setJavaScriptTimeout(10000);
		
		return webClient;
	}
	
	/**
	 * 模拟浏览一个页面，并返回整个页面对象，默认POST提交
	 * 
	 * @param url
	 *        请求URL
	 * @param params
	 *        参数
	 * @param httpMethod
	 *        请求方式 HttpMethod.post|HttpMethod.get，默认POST提交
	 * @param webClient
	 *        模拟浏览器对象
	 * @return HtmlPage返回整个页面对象
	 */
	public static HtmlPage getHtmlPage(String url, Map<String, String> params, HttpMethod httpMethod,
			WebClient webClient)
	{
		if (StringUtils.isEmpty(url) || null == webClient)
		{
			throw new IllegalArgumentException("param error ! url =" + url + ",webClient = " + webClient);
		}
		
		try
		{
			return webClient.getPage(consRequest(url, params, httpMethod));
			
		}
		catch (FailingHttpStatusCodeException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (MalformedURLException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IOException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
		
	}
	
	/**
	 * 模拟浏览一个页面，并返回整个页面对象
	 * <p>
	 * 
	 * @param url
	 *        请求URL
	 * @param webClient
	 *        模拟浏览器对象
	 * @return HtmlPage
	 */
	public static HtmlPage getHtmlPage(String url, WebClient webClient)
	{
		if (StringUtils.isEmpty(url) || null == webClient)
		{
			throw new IllegalArgumentException("param error ! url =" + url + ",webClient = " + webClient);
		}
		
		try
		{
			return webClient.getPage(url);
		}
		catch (FailingHttpStatusCodeException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (MalformedURLException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		catch (IOException e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
		
		return null;
		
	}
	
	/**
	 * 模拟浏览一个页面，并返回整个页面对象，默认POST提交
	 * 
	 * @param url
	 *        请求URL
	 * @param params
	 *        参数，key=参数名，value:参数值
	 * @param httpMethod
	 *        请求方式 HttpMethod.post|HttpMethod.get，默认POST提交
	 * @param webClient
	 *        模拟浏览器对象
	 * @return HtmlPage返回整个页面对象
	 * @throws IOException
	 * @throws FailingHttpStatusCodeException
	 */
	public static HtmlPage getHtmlPage(String url, Map<String, String> params, WebClient webClient)
	{
		return getHtmlPage(url, params, HttpMethod.POST, webClient);
		
	}
	
	/**
	 * 获取页面所有元素
	 * 
	 * @param htmlPage
	 *        模拟器获取的页面对象
	 * @return 页面所有元素
	 */
	public static String getHtmlAsXml(HtmlPage htmlPage)
	{
		if (null != htmlPage)
		{
			return htmlPage.asXml();
		}
		
		return null;
		
	}
	
	/**
	 * 获取页面所有文本信息
	 * 
	 * @param htmlPage
	 *        模拟器获取的页面对象
	 * @return 页面文件信息
	 */
	public static String getHtmlAsText(HtmlPage htmlPage)
	{
		if (null != htmlPage)
		{
			return htmlPage.asText();
		}
		
		return null;
		
	}
	
	/**
	 * 构造请求对象
	 * 
	 * @param url
	 *        请求URL
	 * @param params
	 *        参数，key=参数名，value:参数值
	 * @param httpMethod
	 *        提交方式
	 * @return WebRequest
	 * @throws MalformedURLException
	 */
	public static WebRequest consRequest(String url, Map<String, String> params, HttpMethod httpMethod)
			throws MalformedURLException
	{
		if (StringUtils.isEmpty(url))
		{
			throw new IllegalArgumentException("param error ! url =" + url);
		}
		
		WebRequest webRequest = new WebRequest(new URL(url));
		
		webRequest.setRequestParameters(getParameters(params));
		
		webRequest.setHttpMethod(null == httpMethod ? HttpMethod.POST : httpMethod);
		
		return webRequest;
	}
	
	/**
	 * 设置请求参数
	 * 
	 * @param params
	 *        传入的参数
	 * @return List< NameValuePair> 构造的参数对象
	 */
	private static List<NameValuePair> getParameters(Map<String, String> params)
	{
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		if (MapUtils.isNotEmpty(params))
		{
			for (Entry<String, String> entry : params.entrySet())
			{
				if (null != entry)
				{
					nameValuePairs.add(new NameValuePair(entry.getKey(), entry.getValue()));
				}
			}
		}
		return nameValuePairs;
	}
	
	/**
	 * 关闭浏览器
	 * 
	 * @param webClient
	 */
	public static void closeWebClient(WebClient webClient)
	{
		try
		{
			if (null != webClient)
			{
				webClient.closeAllWindows();
			}
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	/**
	 * 清理整个页面
	 * 
	 * @param htmlPage
	 */
	public static void clearPage(HtmlPage htmlPage)
	{
		try
		{
			if (null != htmlPage)
			{
				htmlPage.cleanUp();
			}
		}
		catch (Exception e)
		{
			LOG.error(Exceptions.getStackTraceAsString(e));
		}
	}
	
	public static void main(String[] args)
	{
		WebClient webClient = HtmlUnitUtils.createWebClient();
		
		String url = "http://121.196.131.70/index.php/openapi/pam_callback/login/module/pam_passport_basic/type/shopadmin/appid/desktop/redirect/aHR0cDovLzEyMS4xOTYuMTMxLjcwL2luZGV4LnBocC8%3D";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("uname", "yixibonb");
		params.put("password", "123456");
		
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("order_id", "11111");
		params1.put("shop_id", "9347a34bd5af903514d40c375f71c3ed");
		params1.put("type", "zx");
		
		HtmlPage htmlPage = HtmlUnitUtils.getHtmlPage(url, params, webClient);
		
		if (null != htmlPage)
		{
			System.out.println("登陆成功 ！！！");
			// System.out.println("test = " + htmlPage.asXml());
			// System.out.println(getHtmlAsXml(htmlPage));
			
			System.out.println(htmlPage.getTitleText());
		}
		
		String url2 = "http://121.196.131.70/index.php?app=ome&ctl=admin_shop&act=sync_order";
		HtmlPage htmlPage1 = HtmlUnitUtils.getHtmlPage(url2, params1, webClient);
		
		System.out.println(HtmlUnitUtils.getHtmlAsText(htmlPage1));
		
	}
	
}
