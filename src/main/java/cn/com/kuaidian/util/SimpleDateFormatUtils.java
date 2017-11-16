package cn.com.kuaidian.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangqinghe on 2016/3/11.
 * 解决SimpleDateFormat的多线程安全问题
 */
public class SimpleDateFormatUtils {

    public static final String DATE_YMD_FORMAT_PATTERN = "yyyyMMdd";
    public static final String DATE_DAY_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MINUTE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";

    private static final Object lock = new Object();

    private static Map<String, ThreadLocal<SimpleDateFormat>> simpleDateFormatMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    /**
     * 返回一个ThreadLocal的SimpleDateFormat,每个线程只会new一次SimpleDateFormat
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSimpleDateFormat(final String pattern) {
        ThreadLocal<SimpleDateFormat> threadLocal = simpleDateFormatMap.get(pattern);

        // 此处的双重判断和同步是为了防止simpleDateFormatMap这个单例被多次put重复的SimpleDateFormat
        if (threadLocal == null) {
            synchronized (lock) {
                threadLocal = simpleDateFormatMap.get(pattern);
                if (threadLocal == null) {
                    // 使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat，解决多线程安全问题
                    threadLocal = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                    simpleDateFormatMap.put(pattern, threadLocal);
                }
            }
        }

        return threadLocal.get();
    }

    /**
     * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return getSimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        return getSimpleDateFormat(pattern).parse(dateStr);
    }
}
