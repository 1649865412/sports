package api.taobao.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.base.dao.sql.ConvertPageQueryFieldsToSQL;
import com.google.gson.JsonObject;
import com.taobao.api.internal.util.WebUtils;

public class Test
{
	// public static void main(String[] args) throws IOException
	// {
	// // // readerFile();
	// // // createSql(Trade.class);
	// //
	// // Trade trade = new Trade();
	// //
	// // trade.setAlipayId(1111L);
	// // trade.setTid(2222L);
	// //
	// // api.taobao.entity.Trade taobaoTrade = new api.taobao.entity.Trade();
	// // // ReflectionUtils.shallowCopyFieldState(trade, taobaoTrade);
	// //
	// // JSONArray jsonArray = JSONArray.fromObject(trade);
	// //
	// // System.out.println(jsonArray.toString());
	// // List<api.taobao.entity.Trade> list = (List<api.taobao.entity.Trade>)
	// JSONArray.toCollection(jsonArray,
	// // api.taobao.entity.Trade.class);
	// //
	// // Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	// //
	// // classMap.put("orders", Order.class);
	// // classMap.put("serviceOrders", ServiceOrder.class);
	// // classMap.put("serviceTags", LogisticsTag.class);
	// // classMap.put("promotionDetails", PromotionDetail.class);
	// //
	// // // classMap.put("dynamicConFieldList", ConditionField.class);
	// //
	// // // classMap.put("resultField", ResultField.class);
	// // JsonConfig jsonConfig = new JsonConfig();
	// // jsonConfig.setClassMap(classMap);
	// // jsonConfig.setRootClass(api.taobao.entity.Trade.class);
	// // // jsonConfig.setRootClass(Trade.class);
	// //
	// // List<api.taobao.entity.Trade> list1 = (List<api.taobao.entity.Trade>)
	// JSONArray.toCollection(jsonArray,
	// // jsonConfig);
	// //
	// // Object object = JSONArray.toArray(jsonArray,
	// api.taobao.entity.Trade.class, classMap);
	// //
	// // System.out.println(object);
	//		
	// }
	
	public static void main(String[] args) throws IOException
	{
		//		
		// File file1 = new
		// File("C:\\Documents and Settings\\Administrator\\桌面\\test.txt");
		//		
		// BufferedReader bufferedReader1 = new BufferedReader(new
		// FileReader(file1));
		// String tempString1 = null;
		//		
		// StringBuffer buffer = new StringBuffer();
		// // 一次读一行，读入null时文件结束
		//		
		// while ((tempString1 = bufferedReader1.readLine()) != null)
		// {
		// if (StringUtils.isNotEmpty(tempString1.trim()))
		// {
		// buffer.append(tempString1.trim()).append("\t");
		// }
		// }
		//		
		// String[] strs = buffer.toString().split("\t");
		//		
		// bufferedReader1.close();
		// System.out.println(buffer.toString());
		// System.out.println(ArrayUtils.toString(strs));
		// File file = new File(
		// "F:\\workspace\\sports_2014-12-22\\WebRoot\\WEB-INF\\views\\management\\taobao_trade\\update.jsp");
		// File file = new
		// File("F:\\workspace\\sports_2014-12-22\\src\\api\\taobao\\entity\\Trade.java");
		// BufferedReader bufferedReader = new BufferedReader(new
		// FileReader(file));
		// String tempString = null;
		// int line = 1;
		// int index = 1;
		//		
		// // 一次读一行，读入null时文件结束
		//		
		// while ((tempString = bufferedReader.readLine()) != null)
		// {
		// if (tempString.indexOf("@Column") != -1)
		// {
		// // System.out.println(strs[index]);
		// index++;
		// }
		// else
		// {
		// // 把当前行号显示出来
		// System.out.println(tempString);
		//				
		// }
		// line++;
		// }
		//		
		// bufferedReader.close();
		//		
		// DefaultHttpClient httpclient = new DefaultHttpClient();
		//		
		// DefaultHttpClient httpclient1 = new DefaultHttpClient();
		//		
		// // 设置登录参数uname=admin&password=sop7hm
		// List<org.apache.http.NameValuePair> formparams1 = new
		// ArrayList<org.apache.http.NameValuePair>();
		// List<org.apache.http.NameValuePair> formparams = new
		// ArrayList<org.apache.http.NameValuePair>();
		// formparams1.add(new BasicNameValuePair("uname", "admin"));
		// formparams1.add(new BasicNameValuePair("password", "sop7hm"));
		// formparams.add(new BasicNameValuePair("app", "ome"));
		// formparams.add(new BasicNameValuePair("ctl", "admin_shop"));
		// formparams.add(new BasicNameValuePair("act", "sync_order"));
		// formparams.add(new BasicNameValuePair("shop_id",
		// "34173cb38f07f89ddbebc2ac9128303f"));
		// formparams.add(new BasicNameValuePair("order_id",
		// "909326115295810"));
		// UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(formparams,
		// "UTF-8");
		// UrlEncodedFormEntity entity2 = new UrlEncodedFormEntity(formparams1,
		// "UTF-8");
		//		
		// HttpPost httppost1 = new HttpPost(
		// "http://121.196.131.70/index.php/openapi/pam_callback/login/module/pam_passport_basic/type/shopadmin/appid/desktop/redirect/aHR0cDovLzEyMS4xOTYuMTMxLjcwL2luZGV4LnBocC8%3D");
		// httppost1.setEntity(entity2);
		// httppost1.setHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// httppost1
		// .setHeader("User-Agent",
		// "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.101 Safari/537.36");
		//		
		// //
		// // // 处理请求，得到响应
		// HttpResponse response = httpclient.execute(httppost1);
		//		
		// //
		// String set_cookie = response.getFirstHeader("Set-Cookie").getValue();
		//		
		// System.out.println(response.getStatusLine());
		// System.out.println(response.getFirstHeader("Location").getValue());
		//
		//
		// // // 新建Http post请求
		// HttpPost httppost = new
		// HttpPost(response.getFirstHeader("Location").getValue());
		// httppost.setHeader("cookie",
		// response.getFirstHeader("Set-Cookie").getValue());
		// httppost.setHeader("Accept",
		// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		// httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.101 Safari/537.36");
		//		
		// // httppost.setHeader("Cookie", set_cookie);
		// httppost.setEntity(entity1);
		// HttpResponse response1 = httpclient1.execute(httppost);
		// // 打印Cookie值
		// System.out.println(set_cookie);
		// System.out.println(response1.getFirstHeader("Location"));
		//		
		// System.out.println(response1.getStatusLine());
		//		
		// // // 打印返回的结果
		// HttpEntity entity = response1.getEntity();
		//		
		// StringBuilder result = new StringBuilder();
		// if (entity != null)
		// {
		// InputStream instream = entity.getContent();
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(instream));
		// String temp = "";
		// while ((temp = br.readLine()) != null)
		// {
		// String str = new String(temp.getBytes(), "utf-8");
		// result.append(str);
		// }
		// }
		//		
		// //System.out.println("result == " + result);
		
		// HttpConnection httpConnection = new
		// HttpConnection(hostConfiguration);
		// String freshUrl =
		// "http://121.196.131.70/index.php/openapi/pam_callback/login/module/pam_passport_basic/type/shopadmin/appid/desktop/redirect/aHR0cDovLzEyMS4xOTYuMTMxLjcwL2luZGV4LnBocC8%3D?uname=admin&password=sop7hm";
		// String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60, 30
		// * 1000 * 60);
		String json = "{\"rsp\":\"fail\",\"msg\":\"\u540c\u6b65\u8ba2\u5355\u5931\u8d25(\u8ba2\u5355\u4e0d\u5b58\u5728\uff1a11111)\"}";
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		String rsp = jsonObject.getString("rsp");
		
		System.out
				.println(new String("\u540c\u6b65\u8ba2\u5355\u5931\u8d25(\u8ba2\u5355\u4e0d\u5b58\u5728\uff1a11111"));
		
	}
	
	private static <T> void createSql(Class<T> t)
	{
		Class<T> class1 = t;
		Field[] fields = class1.getDeclaredFields();
		
		for (Field field : fields)
		{
			String fieldName = field.getName();
			
			String typeName = field.getType().getSimpleName();
			System.out.println(typeName + "   " + fieldName);
		}
		
	}
	
	private static void readerFile() throws FileNotFoundException, UnsupportedEncodingException, IOException
	{
		File file = new File("C:\\Documents and Settings\\Administrator\\桌面\\Order.txt");
		
		FileInputStream fileInputStream = new FileInputStream(file);
		
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		while ((lineTxt = bufferedReader.readLine()) != null)
		{
			String[] s = lineTxt.split("\t");
			System.out.println("/**" + s[4] + "*/");
			System.out.println("private " + s[1] + " " + ConvertPageQueryFieldsToSQL.columnNameToFieldName(s[0]) + ";");
			// System.out.println(ArrayUtils.toString(s));
		}
		read.close();
	}
	
	/****
	 * 将abc_bbc 转成AbcBbc;
	 * 
	 * @param columnName
	 * @return
	 */
	public static String columnNameToClassName(String str)
	{
		if (str == null || "".equals(str))
			return null;
		String[] arrs = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (String s : arrs)
		{
			sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1).toLowerCase());
		}
		return sb.toString();
	}
}
