package cn.com.kuaidian.util.dwz;

import java.util.Date;

import cn.com.kuaidian.util.DateUtils;

/**
 * 
 * @author 
 * 2016年5月30日下午12:24:56
 */
public class Constants {
	
	/**
	 * 系统响应状态码
	 */
	public static class StatusCode{
		/**成功*/
		public final static int SUCCESS = 200;
		/**错误*/
		public final static int ERROR = 300;
		/**超时*/
		public final static int TIMEOUT = 301;
	}
	
	
	public static class AppointTimeStart{
		public final static Date FIRST = DateUtils.getTimeOfDay(new Date(),12,30,0,0);
		
		public final static Date SECOND = DateUtils.getTimeOfDay(new Date(),12,40,0,0);
		
		public final static Date THREE = DateUtils.getTimeOfDay(new Date(),12,50,0,0);
		
		public final static Date FOUR = DateUtils.getTimeOfDay(new Date(),13,0,0,0);
	}
	
	
	public static class AppointTimeEnd{
		public final static Date FIRST = DateUtils.getTimeOfDay(new Date(),12,40,0,0);
		
		public final static Date SECOND = DateUtils.getTimeOfDay(new Date(),12,50,0,0);
		
		public final static Date THREE = DateUtils.getTimeOfDay(new Date(),12,60,0,0);
		
		public final static Date FOUR = DateUtils.getTimeOfDay(new Date(),13,10,0,0);
	}
}
