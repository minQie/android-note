package com.wmc.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeUtil {
	public static String getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int apm = calendar.get(Calendar.AM_PM);
		String format = apm==0?"M��d�� ����h:mm":"M��d�� ����h:mm";
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format,Locale.getDefault());
		String time = simpleDateFormat.format(new Date());
		return time;
    }
}
