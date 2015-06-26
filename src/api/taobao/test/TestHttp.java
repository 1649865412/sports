package api.taobao.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.taobao.api.internal.util.WebUtils;

public class TestHttp
{
	
	public static void main(String[] args) throws IOException
	{
		String freshUrl = "http://121.196.131.70/mocklogin.php";
		// String freshUrl = "http://121.196.131.70/order_sync.php";
		// // String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60,
		// 30
		// // * 1000 * 60);
		//		
		// // System.out.println(response);
		//		
		Map<String, String> params = new HashMap<String, String>();
		params.put("order_id", "926160501656271");
		// params.put("order_bn", "917378896473263");9347a34bd5af903514d40c375f71c3ed
		params.put("shop_id", "9347a34bd5af903514d40c375f71c3ed");
		params.put("type", "zx");
		// params.put("app", "ome");
		// params.put("ctl", "admin_shop");
		// params.put("act", "sync_order");
		// //app=ome&ctl=admin_shop&act=sync_order
		
		String response1 = WebUtils.doPost(freshUrl, params, 30 * 1000 * 60, 30 * 1000 * 60);
		
		System.out.println(response1 + "===" + new String("\u8ba2\u5355\u53f7\u4e0d\u80fd\u4e3a\u7a7aempty!"));
		//System.out.println(response1 + "===" + new String("\u540c\u6b65\u8ba2\u5355\u5931\u8d25"));
		//		
		
		
		/*
		DefaultHttpClient httpclient = new DefaultHttpClient();
		
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		formparams.add(new BasicNameValuePair("orderList", "917378896473263"));
		formparams.add(new BasicNameValuePair("shopId", "9347a34bd5af903514d40c375f71c3ed"));
		formparams.add(new BasicNameValuePair("type", "zx"));
		UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(formparams, "UTF-8");
		HttpPost httppost = new HttpPost(freshUrl);
		httppost.setEntity(entity1);
		
		

		// 处理请求，得到响应
		HttpResponse response = httpclient.execute(httppost);
		
		// // 打印返回的结果
		HttpEntity entity = response.getEntity();
		
		System.out.println(response.getStatusLine());
		
		StringBuilder result = new StringBuilder();
		if (entity != null)
		{
			InputStream instream = entity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String temp = "";
			while ((temp = br.readLine()) != null)
			{
				String str = new String(temp.getBytes(), "utf-8");
				result.append(str).append("\r\n");
			}
		}
		
		System.out.println("result == " + result);*/
	}
	
}
