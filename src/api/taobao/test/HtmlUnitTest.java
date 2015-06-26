package api.taobao.test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.util.JSONUtils;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnitTest
{
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		// String freshUrl =
		// "http://121.196.131.70/121.196.131.70/order_sync.php";
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("orderList", "917378896473263");
		// // params.put("order_bn", "917378896473263");
		// params.put("shopId", "9347a34bd5af903514d40c375f71c3ed");
		// params.put("type", "zx");
		// String response1 = WebUtils.doPost(freshUrl, params, 30 * 1000 * 60,
		// 30 * 1000 * 60);
		
		WebClient webClient = new WebClient();
		webClient.getOptions().setCssEnabled(false);
		
		// webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		// 3 启动客户端重定向
		webClient.getOptions().setRedirectEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.waitForBackgroundJavaScript(10000);
		webClient.getOptions().setTimeout(10000);
		webClient.setJavaScriptTimeout(10000);
		//
		// http://121.196.131.70/index.php/openapi/pam_callback/login/module/pam_passport_basic/type/shopadmin/appid/desktop/redirect/aHR0cDovLzEyMS4xOTYuMTMxLjcwL2luZGV4LnBocC8%3D"
		WebRequest webRequest = new WebRequest(
				new URL(
						"http://121.196.131.70/index.php/openapi/pam_callback/login/module/pam_passport_basic/type/shopadmin/appid/desktop/redirect/aHR0cDovLzEyMS4xOTYuMTMxLjcwL2luZGV4LnBocC8%3D"));
		webRequest.setHttpMethod(HttpMethod.POST);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new NameValuePair("uname", "yixibonb"));
		formparams.add(new NameValuePair("password", "123456"));
		webRequest.setRequestParameters(formparams);
		// webClient.getOptions().setTimeout(10000);
		// WebRequest webRequest = new WebRequest(new
		// URL("http://121.196.131.70/formDo.php"));
		// webRequest.setHttpMethod(HttpMethod.POST);
		// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		// formparams.add(new NameValuePair("orderList", "917378896473263"));
		// formparams.add(new NameValuePair("shopId",
		// "9347a34bd5af903514d40c375f71c3ed"));
		// formparams.add(new NameValuePair("type", "zx"));
		// webRequest.setRequestParameters(formparams);
		
		// HtmlPage page = (HtmlPage)
		// webClient.getCurrentWindow().getEnclosedPage();
		// p;age.setUserData("shopId", "9347a34bd5af903514d40c375f71c3ed",new
		// UserDataHandler);
		HtmlPage page = webClient.getPage(webRequest);
		// Set<Cookie> cookies = webClient.getCookies(webRequest.getUrl());
		
		// System.out.println(page.asXml());
		
		WebRequest webRequest1 = new WebRequest(new URL(
				"http://121.196.131.70/index.php?app=ome&ctl=admin_shop&act=sync_order"));
		webRequest1.setHttpMethod(HttpMethod.POST);
		List<NameValuePair> formparams1 = new ArrayList<NameValuePair>();
		formparams1.add(new NameValuePair("order_id", "911229689045432"));
		formparams1.add(new NameValuePair("shop_id", "9347a34bd5af903514d40c375f71c3ed"));
		formparams1.add(new NameValuePair("type", "zx"));
		// formparams1.add(new NameValuePair("ctl", "admin_shop"));
		// formparams1.add(new NameValuePair("act", "sync_order"));
		// formparams1.add(new NameValuePair("app", "ome"));
		webRequest1.setRequestParameters(formparams1);
		webRequest1.setHttpMethod(HttpMethod.POST);
		// webClient.getCookies(webRequest.getUrl());
		// CookieManager cookieManager = new CookieManager();
		// cookieManager.addCookie( webClient.getCookies(webRequest.getUrl()));
		// webClient.setCookieManager(new CookieManager());
		//		
		// for (Cookie cookie : cookies)
		// {
		// webRequest1.setAdditionalHeader("cookie", cookie + "");
		// }
		
		// WebResponse webResponse = webClient.loadWebResponse(webRequest1);
		// List<NameValuePair> nameValuePairs =
		// webResponse.getResponseHeaders();
		// for (NameValuePair nameValuePair : nameValuePairs)
		// {
		// System.out.println(nameValuePair.getName() + "==" +
		// nameValuePair.getValue());
		// }
		//		
		// System.out.println(webResponse.getContentAsString());
		
		HtmlPage htmlPage = webClient.getPage(webRequest1);
		
		System.out.println(htmlPage.asText());
		System.out.println(JSONUtils.isObject(htmlPage.asText()));
		
		// WebResponse webResponse = webClient.loadWebResponse(webRequest1);
		// System.out.println("StatusMessage == " +
		// webResponse.getStatusMessage());
		//		
		// System.out.println(webResponse.getStatusCode());
		// System.out.println(webResponse.getContentType());
		// webClient.printContentIfNecessary(webResponse);
		
		// HtmlForm htmlForm = page.getFormByName("form");
		//		
		// HtmlInput htmlInput = htmlForm.getInputByName("orderList");
		// htmlInput.setValueAttribute("917378896473263");
		//		
		// HtmlInput shopIdHtmlInput = htmlForm.getInputByName("shopId");
		// shopIdHtmlInput.setValueAttribute("9347a34bd5af903514d40c375f71c3ed");
		//		
		// HtmlInput typeHtmlInput = htmlForm.getInputByName("type");
		// typeHtmlInput.setValueAttribute("zx");
		//		
		// HtmlButtonInput buHtmlInput = htmlForm.getInputByValue("button");
		//		
		// // System.out.println(buHtmlInput.getAttribute("onclick"));
		// // WebResponse webResponse = buHtmlInput.click().getWebResponse();
		//		
		// ScriptResult scriptResult = page.executeJavaScript("javascript:" +
		// buHtmlInput.getAttribute("onclick"));
		//		
		// System.out.println(scriptResult.getJavaScriptResult());
		// System.out.println(scriptResult.getNewPage().getWebResponse().getContentAsString());
		// HtmlPage htmlPage = buHtmlInput.click();
		
		// System.out.println(htmlPage.asXml());
		// System.out.println(buHtmlInput.click().getWebResponse().getContentAsString());
		
		// System.out.println(htmlPage.asXml());
		
		// System.out.println(htmlPage.asText() + "==" + htmlPage.asXml());
		
		// System.out.println(page.asText() + "===" + page.asXml());
		// ScriptableObject scriptableObject = page.getScriptObject();
		
		// HtmlAnchor htmlAnchor = page.getAnchorByName("type");
		// System.out.println(htmlAnchor);
		
		// List<HtmlAnchor> htmlAnchors = page.getAnchors();
		
		// System.out.println(htmlAnchors);
		// System.out.println(ArrayUtils.toString(objs));
		
	}
}
