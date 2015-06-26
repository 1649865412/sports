package api.taobao.constant;

import java.text.SimpleDateFormat;



public class GlobalConstants
{
	public static final int ROOT = 0;
	public static final int ALL = -1;
	public static final int YES = 1;
	public static final int NO = 0;
	public static final int LIMIT_NO = 0;
	
	public static final String KEY_ADMIN_ROOT = "root";
	public static final String KEY_ADMIN_ID = "Admin_Id";
	public static final String KEY_ADMIN_LOGINID = "Admin_LoginId";
	public static final String KEY_SHOP_ID = "Shop_Id";
	public static final String KEY_SHOP_APPKEY = "Shop_appKey";
	public static final String KEY_SHOP_APPSECRET = "Shop_app_secret";
	public static final String KEY_SHOP_SESSIONKEY = "shop_session_key";
	
	public static final String SOURCE_NAME_TAOBAO = "taobao";
	public static final int SOURCE_TAOBAO = 1;
	
	
	// 正式环境
	public final static String TAOBAO_URL = "http://gw.api.taobao.com/router/rest";
	public final static String GET_SESSION_URL_TAOBAO = "http://container.api.taobao.com/container";
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat formatter_time = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat datematter = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat datematter_date = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat formatter_date = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat datematter_MMDD = new SimpleDateFormat("MM-dd");
	
	
}
