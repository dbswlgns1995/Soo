package com.jihoonyoon.soo.notepad.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

/**
 * Created by MohMah on 8/21/2016.
 */
public class TimeUtils{
	public static final PrettyTime prettyTime;

	static{
		prettyTime = new PrettyTime();
	}

	public static String getHumanReadableTimeDiff(Date lastTime){
		if (lastTime == null) return "";
		prettyTime.setReference(new Date());
		return prettyTime.format(lastTime);
	}
}
