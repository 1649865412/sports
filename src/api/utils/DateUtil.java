package api.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil
{
	public static final String FORMAT_DATETIME_NORMAL = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_LONGDATETIME = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_LONGSHORTDATETIME = "yyyy-MM-dd HH:mm:ss.S";
	
	public static final String FORMAT_SHORTDATETIME = "yyyy-MM-dd";
	public static final String FORMAT_LONGDATETIME_COMPACT = "yyyyMMddHHmmssSSS";
	public static final String FORMAT_SHORTDATETIME_COMPACT = "yyyyMMdd";
	
	private DateUtil()
	{
	}
	
	/**
	 * 获取Timestamp类型的当前时间
	 * 
	 * @return Timestamp（年-月-日 时:分:秒.毫秒）
	 */
	public static java.sql.Timestamp getNowDateTime()
	{
		java.sql.Timestamp curDateTime = getMasterDateTime();
		return curDateTime;
	}
	
	/**
	 * 获取Date类型的当前时间,通过Timestamp转化成Date
	 * 
	 * @return Date（年-月-日 时:分:秒.毫秒）
	 */
	public static java.util.Date getNowDate()
	{
		java.util.Date curDateTime = getMasterDateTime();
		return curDateTime;
	}
	
	/**
	 * 获取sql.Date类型的当前时间
	 * 
	 * @return sql.Date（年-月-日）
	 */
	public static java.sql.Date getNowSqlDate()
	{
		java.sql.Date curDateTime = getSqlDate(getNowDate());
		return curDateTime;
	}
	
	/**
	 * 根据传入的util.Date类型，获取sql.Date类型
	 * 
	 * @param utilDate
	 * @return sql.Date（年-月-日）
	 */
	public static java.sql.Date getSqlDate(java.util.Date utilDate)
	{
		if (utilDate == null)
		{
			return null;
		}
		// 用sql.Date接收util.Date会丢失Time部分,剩下day部分
		java.sql.Date curDateTime = new java.sql.Date(utilDate.getTime());
		return curDateTime;
	}
	
	/**
	 * 根据传入的修改具体时间对象和时间增量返回被修改的时间
	 * 
	 * @param oFlag
	 *        修改具体时间对象
	 * @param amount
	 *        时间增量
	 * @return Timestamp
	 */
	public static java.sql.Timestamp nowDateTimeChange(int oFlag, int amount)
	{
		java.sql.Timestamp curDateTime = null;
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(getMasterDateTime());
			cal.add(oFlag, amount);
			curDateTime = new java.sql.Timestamp(cal.getTimeInMillis());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return curDateTime;
	}
	
	/**
	 * 根据传入的Timestamp类型对象、修改具体时间对象和时间增量,获得被修改的时间
	 * 
	 * @param nowDateTime
	 * @param oFlag
	 *        修改具体时间对象
	 * @param amount
	 *        时间增量
	 * @return Timestamp
	 */
	public static java.sql.Timestamp nowDateTimeChange(java.sql.Timestamp nowDateTime, int oFlag, int amount)
	{
		java.sql.Timestamp curDateTime = null;
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(nowDateTime);
			cal.add(oFlag, amount);
			curDateTime = new java.sql.Timestamp(cal.getTimeInMillis());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return curDateTime;
	}
	
	/**
	 * 根据当前时间,传入需要改变的日期和增量，获得改变后的时间
	 * 
	 * @param oFlag
	 *        int类型改变值
	 * @param amount
	 *        int类型增量
	 * @return Date
	 */
	public static java.util.Date nowDateChange(int oFlag, int amount)
	{
		java.util.Date curDateTime = null;
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(getMasterDateTime());
			cal.add(oFlag, amount);
			curDateTime = new java.util.Date(cal.getTimeInMillis());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return curDateTime;
	}
	
	/**
	 * 根据传入的时间,需要改变的日期和增量，获得改变后的时间
	 * 
	 * @param nowDate
	 * @param oFlag
	 *        int类型改变值
	 * @param amount
	 *        int类型增量
	 * @return util.Date
	 */
	public static java.util.Date nowDateChange(java.util.Date nowDate, int oFlag, int amount)
	{
		java.util.Date curDateTime = null;
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(nowDate);
			cal.add(oFlag, amount);
			curDateTime = new java.util.Date(cal.getTimeInMillis());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return curDateTime;
	}
	
	/**
	 * 根据传入的时间进行比较，相等返回0，first在second之后返回1，first在second之前返回-1
	 * 
	 * @param firstDateTime
	 * @param secondDateTime
	 * @return int
	 */
	public static int compareDateTime(java.util.Date firstDateTime, java.util.Date secondDateTime)
	{
		int rlt = 0;
		try
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(firstDateTime);
			long l1 = cal.getTimeInMillis();
			cal.setTime(secondDateTime);
			long l2 = cal.getTimeInMillis();
			long rr = l1 - l2;
			if (rr == 0)
			{
				return 0;
			}
			if (rr < 0)
			{
				return -1;
			}
			if (rr > 0)
			{
				return 1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return rlt;
	}
	
	/**
	 * 取得enddate 之间 startdate的秒
	 * 
	 * @param startdate
	 *        Date
	 * @param enddate
	 *        Date
	 * @return int
	 */
	public static int getSeconds(java.util.Date startdate, java.util.Date enddate)
	{
		long time = enddate.getTime() - startdate.getTime();
		int totalS = new Long(time / 1000).intValue();
		return totalS;
	}
	
	@SuppressWarnings("deprecation")
	private static java.sql.Timestamp formatDateTime(String argDate, String fFlag, Locale locale)
	{
		java.sql.Timestamp tt = null;
		try
		{
			if (locale == null)
			{
				locale = Locale.getDefault();
			}
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag, locale);
			java.util.Date date = new java.util.Date(argDate);
			String s = objSDF.format(date);
			int lng = s.trim().length();
			objSDF.applyPattern(fFlag.trim().substring(0, lng));
			tt = new java.sql.Timestamp(objSDF.parse(s).getTime());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tt;
		
	}
	
	/**
	 * 根据传入的时间格式，格式化传入的String对象成为Timestamp类型对象
	 * 
	 * @param argDate
	 *        String对象
	 * @param fFlag
	 * @return Timestamp
	 */
	public static java.sql.Timestamp getDateTimeFormatByString(String argDate, String fFlag)
	{
		java.sql.Timestamp tt = null;
		try
		{
			int lng = argDate.trim().length();
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag);
			objSDF.applyPattern(fFlag.trim().substring(0, lng));
			tt = new java.sql.Timestamp(objSDF.parse(argDate).getTime());
		}
		catch (Exception e)
		{
			try
			{
				tt = formatDateTime(argDate, fFlag, null);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return tt;
	}
	
	public static void main(String[] args)
	{
		DateUtil.getDateTimeFormatByString("111111111", "yyyy-MM-dd");
	}
	
	/**
	 * 根据传入的String、对象模型和时区格式化成Timestamp
	 * 
	 * @param argDate
	 * @param fFlag
	 * @param locale
	 * @return Timestamp
	 */
	public static java.sql.Timestamp getDateTimeFormatByString(String argDate, String fFlag, Locale locale)
	{
		java.sql.Timestamp tt = null;
		try
		{
			int lng = argDate.trim().length();
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag, locale);
			objSDF.applyPattern(fFlag.trim().substring(0, lng));
			tt = new java.sql.Timestamp(objSDF.parse(argDate).getTime());
		}
		catch (Exception e)
		{
			try
			{
				tt = formatDateTime(argDate, fFlag, locale);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return tt;
	}
	
	/**
	 * 根据传入的String类型时间，格式化成Date类型的时间
	 * 
	 * @param argDate
	 *        String类型时间(年-月-日 时:分:秒)
	 * @return Date
	 */
	public static java.util.Date getDateFormatByNormalString(String argDate)
	{
		String fFlag = FORMAT_DATETIME_NORMAL;
		java.util.Date tt = null;
		try
		{
			int lng = argDate.trim().length();
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag);
			objSDF.applyPattern(fFlag.trim().substring(0, lng));
			tt = new java.util.Date(objSDF.parse(argDate).getTime());
		}
		catch (Exception e)
		{
			try
			{
				tt = formatDateTime(argDate, fFlag, null);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return tt;
	}
	
	/**
	 * 根据传入的时间格式，格式化传入的String对象成为Date类型对象
	 * 
	 * @param argDate
	 *        String类型时间对象
	 * @param fFlag
	 *        时间格式
	 * @return Date
	 */
	public static java.util.Date getDateFormatByString(String argDate, String fFlag)
	{
		java.util.Date tt = null;
		try
		{
			int lng = argDate.trim().length();
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag);
			objSDF.applyPattern(fFlag.trim().substring(0, lng));
			tt = new java.util.Date(objSDF.parse(argDate).getTime());
		}
		catch (Exception e)
		{
			try
			{
				tt = formatDateTime(argDate, fFlag, null);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return tt;
	}
	
	/**
	 * 根据传入的String、对象模型和时区格式化成util.Date
	 * 
	 * @param argDate
	 * @param fFlag
	 * @param locale
	 * @return util.Date
	 */
	public static java.util.Date getDateFormatByString(String argDate, String fFlag, Locale locale)
	{
		java.util.Date tt = null;
		try
		{
			int lng = argDate.trim().length();
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag, locale);
			objSDF.applyPattern(fFlag.trim().substring(0, lng));
			tt = new java.util.Date(objSDF.parse(argDate).getTime());
		}
		catch (Exception e)
		{
			try
			{
				tt = formatDateTime(argDate, fFlag, locale);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		return tt;
	}
	
	/**
	 * 根据传入的Timestamp类型时间和自定义的时间格式，获得格式化成String类型的时间表示法
	 * 
	 * @param argDate
	 *        Timestamp类型时间
	 * @param fFlag
	 *        时间格式
	 * @return String
	 */
	public static String getStringFormatByTimestamp(java.sql.Timestamp argDate, String fFlag)
	{
		String strDateTime = "";
		try
		{
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag.trim());
			strDateTime = objSDF.format(argDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strDateTime;
	}
	
	/**
	 * 根据传入的Date类型时间和自定义的时间格式，获得String类型的时间表示法
	 * 
	 * @param argDate
	 *        Date类型时间
	 * @param fFlag
	 *        时间格式
	 * @return String
	 */
	public static String getStringFormatByDate(java.util.Date argDate, String fFlag)
	{
		String strDateTime = "";
		try
		{
			java.text.SimpleDateFormat objSDF = new java.text.SimpleDateFormat(fFlag.trim());
			strDateTime = objSDF.format(argDate);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return strDateTime;
	}
	
	/**
	 * 获取Timestamp类型的当前时间
	 * 
	 * @return Timestamp（年-月-日 时:分:秒.毫秒）
	 */
	public static java.sql.Timestamp getMasterDateTime()
	{
		java.sql.Timestamp value = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
		return value;
	}
	
	/**
	 * 获取sql.Date类型的当前时间,Timestamp类型转换成sql.Date类型，缺少时分秒
	 * 
	 * @return sql.Date（年-月-日）
	 */
	public static java.sql.Date getMasterDateTime2()
	{
		java.sql.Date value = new java.sql.Date(getMasterDateTime().getTime());
		return value;
	}
	
	@SuppressWarnings("unused")
	public static boolean judgeDate(String argDate)
	{
		boolean tag = false;
		try
		{
			java.sql.Timestamp tt = getDateTimeFormatByString(argDate, FORMAT_LONGDATETIME);
			tag = true;
		}
		catch (Exception e)
		{
			tag = false;
		}
		return tag;
	}
	
	public static String[] getStringArrayFormatByString(String argDate, String regex)
	{
		// 0:��,1:��,2:��
		String result[] = argDate.split(regex);
		// ȥ�����,�����:2007-06-02
		for (int i = 0; i < result.length; i++)
		{
			if (result[i].startsWith("0"))
			{
				int idx = result[i].indexOf("0");
				result[i] = result[i].substring(idx);
			}
		}
		return result;
	}
	
	public static int[] getIntArrayFormatByString(String argDate, String regex)
	{
		String strResult[] = getStringArrayFormatByString(argDate, regex);
		int intResult[] = new int[3];
		for (int i = 0; i < intResult.length; i++)
		{
			intResult[i] = Integer.parseInt(strResult[i]);
		}
		return intResult;
	}
	
	/**
	 * 根据传入时间获得第二天的当前时间
	 * 
	 * @param now
	 * @return Date
	 */
	public static java.util.Date getTomorrow(java.util.Date now)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now.getTime());
		cal.add(Calendar.DATE, +1);
		java.util.Date tomorrow = new java.util.Date(cal.getTimeInMillis());
		return tomorrow;
	}
	
	/**
	 * 根据当前时间获得前一天的util.Date类型时间
	 * 
	 * @param now
	 * @return util.Date(年-月-日 时:分:秒.毫秒)
	 */
	public static java.util.Date getYesterday(java.util.Date now)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(now.getTime());
		cal.add(Calendar.DATE, -1);
		// 获得util.Date(年-月-日 时:分:秒.毫秒)这种类型必须用Timestamp转换
		java.sql.Timestamp yesterday = new java.sql.Timestamp(cal.getTimeInMillis());
		
		return yesterday;
	}
	
	/**
	 * 获得当前时间的开始时刻(精确到毫秒)
	 * 
	 * @return date
	 * @throws Exception
	 */
	public static java.util.Date getTodayStartTime() throws Exception
	{
		return getStartTime(getNowDate());
	}
	
	/**
	 * 获得当前时间的结束时刻(精确到毫秒)
	 * 
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getTodayEndTime() throws Exception
	{
		return getEndTime(getNowDate());
	}
	
	/**
	 * 根据当前时间获得前一天的开始时刻(精确到毫秒)
	 * 
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getYesterdayStartTime() throws Exception
	{
		return getStartTime(getYesterday());
	}
	
	/**
	 * 根据当前时间获得前一天的结束时刻(精确到毫秒)
	 * 
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getYesterdayEndTime() throws Exception
	{
		return getEndTime(getYesterday());
	}
	
	/**
	 * 根据传入的时间得到传入时间当天的开始时刻(精确到毫秒)
	 * 
	 * @param util
	 *        .Date
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getStartTime(java.util.Date date) throws Exception
	{
		Calendar begin = Calendar.getInstance();
		begin.setTimeInMillis(date.getTime());
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		java.util.Date beginTime = new java.util.Date(begin.getTimeInMillis());
		return beginTime;
	}
	
	/**
	 * 根据传入的时间得到传入时间当天的最后时刻(精确到毫秒)
	 * 
	 * @param date
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getEndTime(java.util.Date date) throws Exception
	{
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(date.getTime());
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);
		java.util.Date endTime = new java.util.Date(end.getTimeInMillis());
		return endTime;
	}
	
	/**
	 * 获得前一天的当前时间
	 * 
	 * @return util.Date
	 */
	public static java.util.Date getYesterday()
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		java.util.Date date = cal.getTime();
		return date;
	}
	
	/**
	 * 根据当前时间获得前一天的格式化成String
	 * 
	 * @return String类型为（ 年-月-日 ）
	 */
	public static String getYesterdayShortFormatString()
	{
		String yesterday = DateUtil.getStringFormatByDate(getYesterday(), DateUtil.FORMAT_SHORTDATETIME);
		return yesterday;
	}
	
	/**
	 * 根据传入的时间格式化成String
	 * 
	 * @param date
	 * @return String类型为（ 年-月-日 ）
	 */
	public static String getStringFormatByDate(java.util.Date date)
	{
		String strDate = DateUtil.getStringFormatByDate(date, DateUtil.FORMAT_SHORTDATETIME);
		return strDate;
		
	}
	
	/**
	 * 根据当前时间获得上月第一天的日期
	 * 
	 * @return String类型为（ 年-月-日 ）
	 * @throws Exception
	 */
	public static String getLastMonthShortDateFormatString()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, -1);
		java.util.Date date = cal.getTime();
		String strDate = DateUtil.getStringFormatByDate(date, DateUtil.FORMAT_SHORTDATETIME);
		return strDate;
	}
	
	/**
	 * 根据当前时间获得上月第一天的开始时间即上月第一天的0分0秒
	 * 
	 * @return String类型为（ 年-月-日）
	 * @throws Exception
	 */
	public static String getLastMonthDateTimeFormatString() throws Exception
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置当月第一天
		cal.add(Calendar.MONTH, -1);// 取得上月
		java.util.Date date = cal.getTime();
		java.util.Date stratDate = DateUtil.getStartTime(date);
		String strDate = DateUtil.getStringFormatByDate(stratDate);
		return strDate;
	}
	
	/**
	 * 根据当前时间获得上月第一天的开始时间即上月第一天的0分0秒
	 * 
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getLastMonthStartDateTime() throws Exception
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置当月第一天
		cal.add(Calendar.MONTH, -1);// 取得上月
		java.util.Date date = cal.getTime();
		java.util.Date stratDate = DateUtil.getStartTime(date);
		return stratDate;
	}
	
	/**
	 * 根据当前时间获得上月最后一天结束时间即上月第最后一天的59分59秒
	 * 
	 * @return util.Date
	 * @throws Exception
	 * @throws Exception
	 */
	public static java.util.Date getLastMonthEndDateTime() throws Exception
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		java.util.Date date = cal.getTime();
		java.util.Date endDate = DateUtil.getEndTime(date);
		return endDate;
	}
	
	/**
	 * 根据传入的时间获得传入时间当月第一天的开始时间即上月第一天的0分0秒
	 * 
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getCurrentMonthStartDateTime(Calendar cal) throws Exception
	{
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置当月第一天
		java.util.Date date = cal.getTime();
		java.util.Date stratDate = DateUtil.getStartTime(date);
		return stratDate;
	}
	
	/**
	 * 根据传入的时间和天数增量，获取传入天增量的当天开始时刻(0时0分0秒)
	 * 
	 * @param date
	 * @param diffDay
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getStartTimeByDiffDay(java.util.Date date, int diffDay) throws Exception
	{
		Calendar begin = Calendar.getInstance();
		begin.setTimeInMillis(date.getTime());
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		begin.add(Calendar.DATE, diffDay);
		java.util.Date beginTime = new java.util.Date(begin.getTimeInMillis());
		return beginTime;
	}
	
	/**
	 * * 根据传入的时间和天数增量，获取传入天增量的当天开始时刻(0时0分0秒)
	 * 
	 * @param date
	 * @param diffDay
	 * @return util.Date
	 * @throws Exception
	 */
	public static java.util.Date getEndTimeByDiffDay(java.util.Date date, int diffDay) throws Exception
	{
		Calendar begin = Calendar.getInstance();
		begin.setTimeInMillis(date.getTime());
		begin.set(Calendar.HOUR_OF_DAY, 23);
		begin.set(Calendar.MINUTE, 59);
		begin.set(Calendar.SECOND, 59);
		begin.set(Calendar.MILLISECOND, 999);
		begin.add(Calendar.DATE, diffDay);
		java.util.Date beginTime = new java.util.Date(begin.getTimeInMillis());
		return beginTime;
	}
	
	/**
	 * 根据传入年月获得月份的最后一天
	 * 
	 * @param result
	 * @param flag
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayByMonth(String result, int flag) throws ParseException
	{
		result = result + "01";
		// Date date = Global.formatter_date.parse(result);
		// String str_date = Global.datematter_date.format(date);
		// Calendar c1 = Calendar.getInstance();
		// c1.setTime(Global.datematter_date.parse(str_date));
		// c1.add(Calendar.MONTH, flag);
		// c1.add(Calendar.DATE, -flag);
		// Date time = c1.getTime();
		return null;
	}
	
	/**
	 * 根据传入的两个时间算出时间间隔天数 lxk
	 * 
	 * @param starDate
	 * @param endDate
	 * @return
	 */
	public static long getDifferenceTime(Date starDate, Date endDate)
	{
		long star = starDate.getTime();
		long end = endDate.getTime();
		long difference = Math.abs(star - end);
		difference /= 3600 * 1000 * 24;
		return difference;
	}
	
	public static String gbEncoding(String gbString)
	{
		char[] utfBytes = gbString.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++)
		{
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2)
			{
				hexB = "00" + hexB;
			}
			buffer.append("" + hexB);
		}
		return buffer.substring(0);
	}
	
	public static String toUnicode(String theString, boolean escapeSpace)
	{
		
		int len = theString.length();
		
		int bufLen = len * 2;
		
		if (bufLen < 0)
		{
			
			bufLen = Integer.MAX_VALUE;
			
		}
		
		StringBuffer outBuffer = new StringBuffer(bufLen);
		
		for (int x = 0; x < len; x++)
		{
			
			char aChar = theString.charAt(x);
			
			// Handle common case first, selecting largest block that
			
			// avoids the specials below
			
			if ((aChar > 61) && (aChar < 127))
			{
				
				if (aChar == '\\')
				{
					
					outBuffer.append('\\');
					outBuffer.append('\\');
					
					continue;
					
				}
				
				outBuffer.append(aChar);
				
				continue;
				
			}
			
			switch (aChar)
			{
				
				case ' ':

					if (x == 0 || escapeSpace)
						
						outBuffer.append('\\');
					
					outBuffer.append(' ');
					
					break;
				
				case '\t':
					outBuffer.append('\\');
					outBuffer.append('t');
					
					break;
				
				case '\n':
					outBuffer.append('\\');
					outBuffer.append('n');
					
					break;
				
				case '\r':
					outBuffer.append('\\');
					outBuffer.append('r');
					
					break;
				
				case '\f':
					outBuffer.append('\\');
					outBuffer.append('f');
					
					break;
				
				case '=': // Fall through
				
				case ':': // Fall through
				
				case '#': // Fall through
				
				case '!':

					outBuffer.append('\\');
					outBuffer.append(aChar);
					
					break;
				
				default:

					if ((aChar < 0x0020) || (aChar > 0x007e))
					{
						
						outBuffer.append('\\');
						
						outBuffer.append('u');
						
						outBuffer.append(toHex((aChar >> 12) & 0xF));
						
						outBuffer.append(toHex((aChar >> 8) & 0xF));
						
						outBuffer.append(toHex((aChar >> 4) & 0xF));
						
						outBuffer.append(toHex(aChar & 0xF));
						
					}
					else
					{
						
						outBuffer.append(aChar);
						
					}
					
			}
			
		}
		
		return outBuffer.toString();
		
	}
	
	private static char toHex(int nibble)
	{
		
		return hexDigit[(nibble & 0xF)];
		
	}
	
	private static final char[] hexDigit = {

	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'

	};
}
