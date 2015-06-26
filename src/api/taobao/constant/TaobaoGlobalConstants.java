package api.taobao.constant;

public class TaobaoGlobalConstants
{
	public static boolean isRunJobs = true;
	public static boolean isFenxiaoRunJobs = true;
	public static boolean isEnableSystemOutInfo = false;
	
	public final static String callBackUrl = "http://localhost:9000";
	
	public final static String APP_KEY_LOCAL = "12062308";
	public final static String APP_SERCET_LOCAL = "e8afc886437b850743a79bf3a6fb6969";
	
	public final static String APP_KEY_SERVER = "12120851";
	public final static String APP_SERCET_SERVER = "8a273b64591889959712bca96a38b656";
	
	public final static String APP_KEY_SERVER_LO = "12147374";
	public final static String APP_SERCET_SERVER_LO = "b9759464d9e25e2eb6377514ccda522d";
	
	public final static String APP_KEY = APP_KEY_SERVER_LO;
	public final static String APP_SERCET = APP_SERCET_SERVER_LO;
	
	public final static String SANDBOX_URL = "http://gw.api.tbsandbox.com/router/rest";
	public final static String GET_SESSION_URL_SANDBOX = "http://container.api.tbsandbox.com/container";
	
	public final static String TAOBAO_URL = "http://gw.api.taobao.com/router/rest";
	public final static String GET_SESSION_URL_TAOBAO = "http://container.api.taobao.com/container";
	
	public final static String SERVER_URL = TAOBAO_URL;// SANDBOX_URL;//TAOBAO_URL;
	public final static String GET_SESSION_URL = GET_SESSION_URL_TAOBAO;
	
	
}
