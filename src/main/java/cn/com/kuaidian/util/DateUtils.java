package cn.com.kuaidian.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;


/**
 * 日期工具类
 * 
 * @author 
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {
	
	public static final int YEAR=Calendar.YEAR;
	public static final int MONTH=Calendar.MONTH;
	public static final int DATE=Calendar.DATE;
	public static final int HOUR=Calendar.HOUR;
	public static final int MINUTE=Calendar.MINUTE;
	public static final int SECOND=Calendar.SECOND;
	
	//getTimeBeforeDay
	public static String getTimestampString(int days) {// Timestamp
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return tsFormat.format(cal.getTime());
	}
	
	//getDayString
	public static String getDateString(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd");
		return tsFormat.format(cal.getTime());
	}
	
	//getDay
	public static Date getTimestamp(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
		return cal.getTime();
	}

	/**
	 * yyyy-mm-dd 00:00:00
	 * @param days
	 * @return yyyy-mm-dd 00:00:00
	 */
	public static Date getDate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),0,0,0);

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = df.format(cal.getTime());
		Date date = null;
		try{
			date = df.parse(dateStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	public static String fromTimestamp(long timestamp){
		if(timestamp<0){
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = df.format(timestamp);
		return date;
	}
	
	public static String toDate(Date date) {
		if (date == null) {
			return "";
		}
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);		
	}
	
	public static String toDate0(Date date) {
		String s=toDate(date);
		s+=(s.trim().length()>0?" 00:00:00":"");
		return s;
	}

	public static String toTime(Date date) {
		if (date == null) {
			return "";
		}
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	public static String toTimestamp(Date date) {
		if (date == null) {
			return "";
		}
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public static Date getFirstdayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1,0,0,0);
		return cal.getTime();
	}
	
	public static Date getLastdayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 1,23,59,59);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	//得到下个月的开始
	public static Date getFirstdayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 1,0,0,0);
		return cal.getTime();
	}
	
	//得到上一个星期的开始
	public static Date getLastWeekStart(){
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DATE, -(cld.get(Calendar.DAY_OF_WEEK) - 1));
		cld.add(Calendar.DATE, -7);
		cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE), 0, 0, 0);
		return cld.getTime();
	}
	
	//得到上一个星期的结束
	public static Date getLastWeekEnd(){
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DATE, -(cld.get(Calendar.DAY_OF_WEEK) - 1));
		cld.add(Calendar.DATE, -1);
		cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE), 23, 59, 59);
		return cld.getTime();
	} 
	
	//取得当前日期是多少周(201035)
	public static int getWeekOfYear2(Date date){
		Calendar c = Calendar.getInstance();
		//设置星期天为一周的开始
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		//设置只有一天，也当成是一周(如：2011年1月1日是2011年的第一周)
		c.setMinimalDaysInFirstWeek(1);
		c.setTime(date);
		SimpleDateFormat tsFormat = new SimpleDateFormat("MM");
		//特殊处理最后一周(不在同一年)的情况
		if("12".equals(tsFormat.format(date)) && c.get(Calendar.WEEK_OF_YEAR) == 1){
			if(c.get(Calendar.WEEK_OF_YEAR) < 10){
				return Integer.parseInt((c.get(Calendar.YEAR)+1)+"0"+c.get(Calendar.WEEK_OF_YEAR));
			}else{
				return Integer.parseInt((c.get(Calendar.YEAR)+1)+""+c.get(Calendar.WEEK_OF_YEAR));
			}
		}else{
			if(c.get(Calendar.WEEK_OF_YEAR) < 10){
				return Integer.parseInt(c.get(Calendar.YEAR)+"0"+c.get(Calendar.WEEK_OF_YEAR));
			}else{
				return Integer.parseInt(c.get(Calendar.YEAR)+""+c.get(Calendar.WEEK_OF_YEAR));
			}		    
	    }
	} 
	
    /**
     * 获取指定星期的开始时刻00:00:00.000
     * @param interval 年份拼周数如201008
     * @return Date java.util.Date
     */
    public static Date getForstdayOfWeek(int interval) {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.YEAR, interval/100);
        cld.set(Calendar.WEEK_OF_YEAR, interval%100);
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE), 0, 0, 0);
        return cld.getTime();
    }

    /**
     * 获取指定星期的结束时刻00:00:00.000
     * @param interval 年份拼周数如201008
     * @return Date java.util.Date
     */
    public static Date getLastdayOfWeek(int interval) {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.YEAR, interval/100);
        cld.set(Calendar.WEEK_OF_YEAR, interval%100+1);
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE)-1, 23, 59, 59);
        return cld.getTime();
    }

    /**
     * 获取指定月的开始时刻00:00:00.000
     * @param interval 年份拼月份如201008
     * @return Date java.util.Date
     */
    public static Date getForstdayOfMonth(int interval) {
        Calendar cld = Calendar.getInstance();
        cld.set(interval/100, interval%100-1, 1, 0, 0, 0);
        return cld.getTime();
    }

    /**
     * 获取指定月的结束时刻00:00:00.000
     * @param interval 年份拼月份如201008
     * @return Date java.util.Date
     */
    public static Date getLastdayOfMonth(int interval) {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.YEAR, interval/100);
        cld.set(Calendar.MONTH, interval%100);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE)-1, 23, 59, 59);
        return cld.getTime();
    }

    /**
     * 获取指定时间的年份周数201035
     * @return Date java.util.Date
     */
    public static int getWeekOfYear(Date date){
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.YEAR)*100 + cld.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定时间的年份月份201005
     * @return Date java.util.Date
     */
    public static int getMonthOfYear(Date date){
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.YEAR) * 100 + cld.get(Calendar.MONTH) + 1;
    }

    public static Date getBeginLastDay(int days){
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DATE, cld.get(Calendar.DATE)-days);
        cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE), 0, 0, 0);
        return cld.getTime();
    }

    public static Date getEndLastDay(int days){
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DATE, cld.get(Calendar.DATE)-days);
        cld.set(cld.get(Calendar.YEAR), cld.get(Calendar.MONTH), cld.get(Calendar.DATE), 23, 59, 59);
        return cld.getTime();
    }

    /**
     * 获取某天的开始时刻00:00:00.000
     * @param date 需要获取天内的时间
     * @return Date java.util.Date
     */
    public static Date getStartOfDay(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }
    /**
     * 获取某天的结束时刻23:59:59.000
     * @param date 需要获取天内的时间
     * @return Date java.util.Date
     */
    public static Date getEndOfDay(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.HOUR_OF_DAY, 23);
        cld.set(Calendar.MINUTE, 59);
        cld.set(Calendar.SECOND, 59);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }
    
    //获得当前系统时间
	public static String getNow(){
		Date date = new Date();
		DateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dates=sdf.format(date);
		return dates;
	}
	
	//根据格式化标准获取时间
	public static String getTime(String format){
		Date date = new Date();
		DateFormat sdf =new SimpleDateFormat(format);
		String dates=sdf.format(date);
		return dates;
	}
	

    //比较两个时间相差的天数，正数为strDate2大于strDate1
	public static int diffDay(String strDate1, String strDate2) {
		Date d1 = null;
		Date d2 = null;
		int day = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			d1 = format.parse(strDate1);
			d2 = format.parse(strDate2);
			if (d1 != null && d2 != null) {
				day = (int) ((d2.getTime() - d1.getTime()) / 1000 / 3600 / 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}
	
	//判断时间和当前时间相差多少天,正数为传入时间是多少天前
	public static int nowDiffDay(String time){		
		return diffDay(time,getNow());
	}
	
	//判断时间是否是当前时间的一个范围内
	public static boolean judgeDiffDay(String time,int day){
		int diff=diffDay(time,getNow());
		if(diff<=day){
			return true;
		}else{
			return false;
		}
	}
	
	//得到指定时间,n是数字,正数是将来的，负数是以前的，type是定义对那部分的:年月日，format格式化样式
	public static String getTargetTime(int n,int type,String format){
        Calendar c=Calendar.getInstance();
        DateFormat df=new SimpleDateFormat(format);
        c.setTime(new Date());
        c.add(type,n);
        Date d2=c.getTime();
        String s=df.format(d2);
        return s;
    }
	
				   
	public static Date getYesterday() {
		Date today = new Date(System.currentTimeMillis());
	 	long t = today.getTime();
	 	Date date = new Date(t - 24 * 60 * 60 *1000l );
	 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    try{
	    	date = sdf.parse(sdf.format(date));
	    }catch (ParseException e) {

	    }
	    return date;
	}
	
	 //毫秒转换成分或者小时
    public static String MillisecondToDate(long time){
        StringBuilder buf = new StringBuilder();
        long h = time/(60*60*1000);
        long mm = time%(60*60*1000);
        long m = mm/(60*1000);
        long ss = mm%(60*1000);
        long s = ss/1000;
        if(h>0){
        	if(h<10){
        		buf.append("0"+h).append(":");
        	}else{
        		buf.append(h).append(":");
        	}
            
        }
        if(m>0){
        	if(m<10){
        		buf.append("0"+m).append(":");
        	}else{
        		buf.append(m).append(":");
        	}
        	if(s<=0){
        		buf.append("00");
        	}
            
        }
        if(s>0){
        	if(s<10){
        		if(m<=0){
        			buf.append("00:0"+s);
            	}else{
            		buf.append("0"+s);
            	}
        		
        	}else{
        		if(m<=0){
            		buf.append("00:"+s);
            	}else{
            		buf.append(s);
            	}
        		
        	}
        	
            
        }
        return buf.toString();
    }
    
    /**
	 * 
	 * @param date1 <String>
	 * @param date2 <String>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthInterval(String date1, String date2)  {
		int result = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
			result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
			return result == 0 ? 1 : Math.abs(result);
		} catch (Exception e) { }
		return result;

	}

    /**
	 * 增加不同年份的月份相差，必须date1<date2
	 * @param date1 <Date>
	 * @param date2 <Date>
	 * @return int
	 * @throws ParseException
	 */
	public static int getMonthInterval(Date date1, Date date2)  {
        if(date1.after(date2))throw new RuntimeException("date1 must be before date2 ");
		int result = 0;
		try {
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(date1);
			c2.setTime(date2);
			result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
            int years = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            if(years > 0){
                result = result + 12*years;
            }
			return result;
		} catch (Exception e) { }
		return result;

	}

    /**
     * 取得当前日期所在周的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    //获取当前月的第一天
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //获取当前月的最后一天
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * 格式化日期
     * @param date
     * @param patten
     * @return
     */
    public static String format(Date date ,String patten){
    	SimpleDateFormat sdf = new SimpleDateFormat(patten);
    	String dates=sdf.format(date);
		return dates;
    }   
   
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }


    public static String formatDetail(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }
       
    public static String formatYYMMDDHM(Date date) {
        return format(date, "yyyy-MM-dd HH:mm");
    }

    public static String formatMMDD(Date date) {
        return format(date, "MM-dd");
    }

    public static int formatData2YMD(Date date) {
        return NumberUtils.toInt(format(date, "yyyyMMdd"));
    }

 
    public static Date parseDate(String dateStr, String fmt) {
        DateFormat formatter = new SimpleDateFormat(fmt);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException ex) {
            return null;
        }
    }


    /**
     * ***时间相关函数开始*****
     */
   

    /**
     * 得到某一天的23：59 时间
     *
     * @param date
     * @return
     */
    public static Date getTodayLast(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

   

    private DateUtils() {
    }

    /**
     *
     * @return example. 20140410
     */
    public static int getTodayYmd() {
        SimpleDateFormat ymdSdf = new SimpleDateFormat("yyyyMMdd");
        int todayYmd = Integer.parseInt(ymdSdf.format(new Date()));
        return todayYmd;
    }

    /**
     * 取从startYm开始到当前月份结束的月份列表
     *
     * @param startYm
     * @return
     */
    public static List<Integer> getMonthList(int startYm) {
        return getMonthList(startYm, getTodayYmd() / 100);
    }

    public static List<Integer> getMonthList(int startYm, int endYm) {
        List<Integer> ymList = new ArrayList<Integer>();
        for (int ym = startYm; ym <= endYm; ym = getNextYm(ym)) {
            ymList.add(ym);
        }
        return ymList;
    }

    /**
     * 取指定ym的下一月，如201201 -> 201202, 201212 -> 201301
     *
     * @param ym
     * @return
     */
    public static int getNextYm(int ym) {
        int y = ym / 100;
        int m = ym % 100;
        if (m + 1 < 13) {
            return y * 100 + (m + 1);
        } else {
            return (y + 1) * 100 + 1;
        }
    }

    public static String getFormatedTime(Date date) {
        String result = "";
        if (null != date) {
            Date now = new Date();
            long betweenTime = (now.getTime() - date.getTime()) / 1000;  //两个时间之间的差距,单位为秒
            long min60 = 60 * 60;  //60分钟之内
            long hour24 = 24 * 60 * 60; //24小时
            long day3 = 3 * 24 * 60 * 60; // 3天之内
            long sec60 = 60;//60秒

            if (betweenTime <= 0) {
                result = "刚刚";//如果小于0秒，则显示刚刚
            } else if (betweenTime < sec60) {
                result = betweenTime + "秒前";//如果小于60秒，则显示分钟
            } else if (betweenTime >= sec60 && betweenTime <= min60) { //如果小于60分钟，则显示分钟
                result = (Math.round((float)betweenTime / 60) <= 0 ? 0 : Math.round((float)betweenTime / 60)) + "分钟前";
            } else if (betweenTime > min60 && betweenTime <= hour24) { //如果大于60分钟，小于24，则显示小时
                result = Math.round((float)betweenTime / 60 / 60) + "小时前";
            } else if (betweenTime > hour24 && betweenTime <= day3) { //如果大于24，小于3天，则显示天
                result = Math.round((float)betweenTime / 60 / 60 / 24) + "天前";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                result = sdf.format(date);
            }
        }
        return result;
    }

    public static String fromateDate(Date date) {
        String time = "";
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int dd = date.getDate();
        return year + "年" + month + "月" + dd + "日";
    }

    public static Date createDate(int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        return c.getTime();
    }

    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获得两时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return -1;
        }
        long intervalMilli = date2.getTime() - date1.getTime();
        if (intervalMilli < 0) {
            return -1;
        }
        if (intervalMilli % (24 * 60 * 60 * 1000) == 0) {
            return (int) (intervalMilli / (24 * 60 * 60 * 1000)) + 1;
        }
        return (int) ((intervalMilli + (24 * 60 * 60 * 1000 - 1)) / (24 * 60 * 60 * 1000));
    }

    /**
     * 获得几天后的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDateAfter(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
        return c.getTime();
    }

    /**
     * 获得各时间后的具体时间
     *
     * @param date 日期
     * @param day 天数
     * @param hour	小时数
     * @param minute分钟数
     * @param second秒数
     * @return
     */
    public static Date getDateAfter(Date date, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + day);
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + hour);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minute);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + second);
        return c.getTime();
    }

    /**
     * 获取几天前的所有日期（包括当天） 格式：20141231,
     *
     * @param date
     * @param fDay
     * @return
     */
    public static List<Integer> getDateBefore(Date date, int fDay) {
        List<Integer> list = new ArrayList<Integer>();
        if (fDay < 1) {
            return list;
        }
        Calendar c = Calendar.getInstance();
        for (int i = fDay - 1; i >= 0; i--) {
            c.setTime(date);
            c.set(Calendar.DATE, c.get(Calendar.DATE) - i);
            int day = formatData2YMD(c.getTime());
            list.add(day);

        }
        return list;
    }

    /**
     * 获取几天前的所有日期（包括当天）
     *
     * @param date
     * @param num
     * @return
     */
    public static List<Date> getDateListBefore(Date fromDate, int num) {
        List<Date> list = new ArrayList<Date>();
        if (num < 1) {
            return list;
        }
        Calendar c = Calendar.getInstance();
        for (int i = num - 1; i >= 0; i--) {
            c.setTime(fromDate);
            c.set(Calendar.DATE, c.get(Calendar.DATE) - i);
            list.add(c.getTime());
        }
        return list;
    }

    /**
     * 获得几天前的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getSingleDateBefore(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, c.get(Calendar.DATE) - day);
        return c.getTime();
    }
    
    /**
     * 根据当前时间获取年月
     * 
     * @return
     */
    public static int getYearMonth() {
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH) + 1;
    	
    	return NumberUtils.toInt(String.valueOf(year) + (month >= 10 ? String.valueOf(month) : ("0"+month)));
    }
    
    /**
     * 根据Date获取时分秒 格式: HH:mm:SS
     * @param date
     * @return
     */
    public static String getHMS(Date date){   	
    	 String hours = date.getHours() < 10? "0"+date.getHours() : ""+date.getHours();
    	 String minutes = date.getMinutes() < 10? "0"+date.getMinutes() : ""+date.getMinutes();
    	 String seconds = date.getSeconds() < 10? "0"+date.getSeconds() : ""+date.getSeconds();   	
    	 return hours+":"+minutes+":"+seconds;
    }
    
    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(StringUtils.isBlank(seconds)){  
            return "";  
        }  
        if(StringUtils.isBlank(format)) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds)));  
    } 
    
    public static void main(String[] args) {
        System.out.println(getYearMonth());
    }
    
    public static Date parseDate(String date) {
        try {
            if (date.length() == 10) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(date);
            }
            if (date.length() == 19) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            }
            if (date.length() == 16) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException(new StringBuilder(64).append(date).append("is not an valid format for: [yyyy-MM-dd] or [yyyy-MM-dd HH:mm:ss]  or [yyyy-MM-dd HH:mm]").toString());
        }
        throw new IllegalArgumentException(new StringBuilder(64).append(date).append("is not an valid format for: [yyyy-MM-dd] or [yyyy-MM-dd HH:mm:ss] or [yyyy-MM-dd HH:mm]").toString());
    }
    
    /**
     * 计算2个时间相差多久(天,时,分,秒),返回map
     * @param d1
     * @param d2
     * @return
     */
    public static  Map<String,Long> getInterval(Date d1,Date d2){
    	long day,hour,min,sec;
    	day=hour=min=sec = 0;
    	Map<String,Long> date = new HashMap<String,Long>();
    	if(null==d1 || null==d2) return date;
    	long l=d1.getTime()-d2.getTime();
    	day=l/(24*60*60*1000);
    	hour=(l/(60*60*1000)-day*24);
    	min=((l/(60*1000))-day*24*60-hour*60);
    	sec=(l/1000-day*24*60*60-hour*60*60-min*60);
    	date.put("day", day);
    	date.put("hour", hour);
    	date.put("min", min);
    	date.put("sec", sec);
    	return date;
    	
    }
}
