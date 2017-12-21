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
	
	/**
	 *预约开始时间 
	 */
	public static class AppointTimeStart{
		public final static Date FIRST = DateUtils.getTimeOfDay(new Date(),12,30,0,0);
		
		public final static Date SECOND = DateUtils.getTimeOfDay(new Date(),12,40,0,0);
		
		public final static Date THREE = DateUtils.getTimeOfDay(new Date(),12,50,0,0);
		
		public final static Date FOUR = DateUtils.getTimeOfDay(new Date(),13,0,0,0);
	}
	
	/**
	 * 预约结束时间 
	 */
	public static class AppointTimeEnd{
		public final static Date FIRST = DateUtils.getTimeOfDay(new Date(),12,40,0,0);
		
		public final static Date SECOND = DateUtils.getTimeOfDay(new Date(),12,50,0,0);
		
		public final static Date THREE = DateUtils.getTimeOfDay(new Date(),12,60,0,0);
		
		public final static Date FOUR = DateUtils.getTimeOfDay(new Date(),13,10,0,0);
	}
	
	/**
	 *评论打分 
	 */
	public static class CommentScore{
		public final static int ONE = 1;
		
		public final static int TWO = 1;
		
		public final static int THREE = 1;
		
		public final static int FOUR = 1;
		
		public final static int FIVE = 1;
	}
}
