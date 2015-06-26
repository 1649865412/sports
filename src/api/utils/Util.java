package api.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import api.taobao.constant.TaobaoGlobalConstants;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util
{
	public static void systemOutPrintForJob(String s)
	{
		if (TaobaoGlobalConstants.isEnableSystemOutInfo)
		{
			System.out.println(s);
		}
	}
	
	public static String getNotNullString(String s)
	{
		if (s == null)
		{
			return "";
		}
		else
		{
			return s;
		}
	}
	
	public static String getSplitString(String[] strArray, String separator)
	{
		StringBuffer s = new StringBuffer();
		if (strArray == null || strArray.length == 0)
		{
			return s.toString();
		}
		if (separator == null)
		{
			separator = ",";
		}
		for (int i = 0; i < strArray.length; i++)
		{
			s.append(strArray[i]);
			if (i != strArray.length - 1)
			{
				s.append(separator);
			}
		}
		return s.toString();
	}
	
	public static long parseLong(String s)
	{
		long v = 0;
		try
		{
			v = Long.parseLong(s);
		}
		catch (Exception e)
		{
		}
		return v;
	}
	
	public static BigDecimal parseBigDecimal(String value)
	{
		BigDecimal result = null;
		try
		{
			result = new BigDecimal(value);
		}
		catch (Exception ex)
		{
			result = new BigDecimal(0);
		}
		return result;
	}
	
	public static String getJsonOutputString(HttpServletRequest request, HttpServletResponse response,
			com.google.gson.JsonObject jobj)
	{
		boolean scriptTag = false;
		String cb = request.getParameter("callback");
		if (cb != null)
		{
			scriptTag = true;
			response.setContentType("text/javascript");
		}
		else
		{
			response.setContentType("application/x-json");
		}
		StringBuffer jsonbuffer = new StringBuffer();
		if (scriptTag)
		{
			jsonbuffer.append(cb + "(");
		}
		// response.getWriter().print(jsonData);
		jsonbuffer.append(jobj.toString());
		if (scriptTag)
		{
			jsonbuffer.append(");");
		}
		return jsonbuffer.toString();
	}
	
	/**
	 * MD5����
	 * 
	 * @param plainText
	 * @return
	 */
	public static String getMD5Hash(String plainText)
	{
		MessageDigest messageDigest = null;
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		byte[] hashByte = messageDigest.digest(plainText.getBytes());
		StringBuffer buffer = new StringBuffer(32);
		for (int i = 0; i < hashByte.length; i++)
		{
			String aHash = Integer.toHexString(0xFF & hashByte[i]);
			if (aHash.length() < 2)
				aHash = aHash + "e";
			buffer.append(aHash);
		}
		return buffer.toString();
	}
	
	public static String getMD5Normal(String plainText)
	{
		MessageDigest messageDigest = null;
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		byte[] hashByte = messageDigest.digest(plainText.getBytes());
		StringBuffer buffer = new StringBuffer(32);
		for (int i = 0; i < hashByte.length; i++)
		{
			String aHash = Integer.toHexString(0xFF & hashByte[i]);
			// if(aHash.length() <2 )aHash = aHash + "e";
			buffer.append(aHash);
		}
		return buffer.toString();
	}
	
	/**
	 * 返回页数
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public static int getPage(int start, int limit)
	{
		int page = (int) Math.floor((double) start / limit) + 1;
		return page;
	}
	
	public static String getSqlLimitString(String sql, int begin, int number)
	{
		StringBuffer pagingSelect = new StringBuffer(100);
		pagingSelect.append(sql);
		pagingSelect.append(" limit " + begin + ", " + number);
		return pagingSelect.toString();
	}
	
	public static String Base64Decode(String str)
	{
		if (str == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		String keyvalues = null;
		try
		{
			keyvalues = new String(decoder.decodeBuffer(str));
		}
		catch (IOException e)
		{
			return null;
		}
		return keyvalues;
	}
	
	public static String Base64Encode(String str)
	{
		if (str == null)
			return null;
		BASE64Encoder encoder = new BASE64Encoder();
		String keyvalues = null;
		try
		{
			keyvalues = new String(encoder.encodeBuffer(str.getBytes()));
		}
		catch (Exception e)
		{
			return null;
		}
		return keyvalues;
	}
	
	/*
	 * copy对象属性��
	 * 
	 * @param source
	 * 
	 * @param dest
	 */
	public static void copyProperties(Object dest, Object source)
	{
		try
		{
			PropertyUtils.copyProperties(dest, source);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 序列化方法
	 * 
	 * @param bean
	 * @param type
	 * @return
	 */
	public static String bean2json(Object bean, Type type)
	{
		Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
				.setDateFormat(DateFormat.LONG).create();
		return gson.toJson(bean);
	}
	
	/**
	 * 反序列化方法
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T json2bean(String json, Type type)
	{
		// Gson gson = new
		// GsonBuilder().registerTypeAdapter(java.util.Date.class, new
		// UtilDateDeserializer()).setDateFormat(DateFormat.LONG).create();
		Gson gson = new GsonBuilder().registerTypeAdapter(java.util.Date.class, new UtilDateDeserializer())
				.setDateFormat(DateFormat.LONG).create();
		return gson.fromJson(json, type);
	}
	
	public String postXml()
	{
		// Get file to be posted
		/*
		 * String strXMLFilename = args[1]; File input = new
		 * File(strXMLFilename);
		 * 
		 * // Prepare HTTP post PostMethod post = new PostMethod(strURL);
		 * 
		 * // Request content will be retrieved directly // from the input
		 * stream // Per default, the request content needs to be buffered // in
		 * order to determine its length. // Request body buffering can be
		 * avoided when // content length is explicitly specified
		 * post.setRequestEntity(new InputStreamRequestEntity( new
		 * FileInputStream(input), input.length()));
		 * 
		 * // Specify content type and encoding // If content encoding is not
		 * explicitly specified // ISO-8859-1 is assumed post.setRequestHeader(
		 * "Content-type", "text/xml; charset=ISO-8859-1");
		 * 
		 * // Get HTTP client HttpClient httpclient = new HttpClient();
		 * 
		 * // Execute request try {
		 * 
		 * int result = httpclient.executeMethod(post);
		 * 
		 * // Display status code System.out.println("Response status code: " +
		 * result);
		 * 
		 * // Display response System.out.println("Response body: ");
		 * System.out.println(post.getResponseBodyAsString());
		 * 
		 * } finally { // Release current connection to the connection pool //
		 * once you are done post.releaseConnection(); }
		 */
		return "";
	}
	
	/**
	 * 判断字符串是否全是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 去除字符串中的回车、换行符、制表符以及前后空格,不去中间空格
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlankNotEty(String str)
	{
		String dest = "";
		if (str != null)
		{
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("").trim();
		}
		return dest;
	}
}
