package com.base;

import java.util.HashMap;
import java.util.Map;

public class QuartzConstant {
	public static final String 	TRIGGERNAME = "triggerName";
	public static final String 	TRIGGERGROUP = "triggerGroup";
	public static final String STARTTIME = "startTime";
	public static final String ENDTIME = "endTime";
	public static final String REPEATCOUNT = "repeatCount";
	public static final String REPEATINTERVEL = "repeatInterval";
	public static final String PAUSE = "pause";
	public static final String RESUME = "resume";
	public static final String REMOVE = "remove";
	public static final String RESUME_NUM = "1";
	public static final String REMOVE_NUM = "2";
	public static final String PAUSE_NUM = "0";
	
	public static final Map<String,String> status = new HashMap<String,String>();
	static{
		status.put("ACQUIRED", "运行中");
		status.put("PAUSED", "暂停中");
		status.put("WAITING", "等待中");		
	}
}
