package api.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.taobao.api.internal.util.WebUtils;

/**
 *  <code>apiText.java</code>
 *  <p>
 *  <p>Copyright  2015 All right reserved.
 *  @author 杨荣忠 时间 2015-1-7 上午09:53:50	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
public class apiText
{
	// 测试main
	public static void main(String[] args) throws Exception {  

		FileReader reader=new FileReader("E:\\js.js");
		BufferedReader in=new BufferedReader(reader,1024);
		
		  String inputLine;   
	      ScriptEngineManager scriptManager = new ScriptEngineManager();
		  ScriptEngine js = scriptManager.getEngineByExtension("js");
		  js.eval(in);
		  Invocable inv = (Invocable) js;
	
		  // p1的获取 执行js中的方法
		  String p1 = (String) inv.invokeFunction("sendRequest");
		  System.out.println(p1);
		  
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


	}
	  
	
}
