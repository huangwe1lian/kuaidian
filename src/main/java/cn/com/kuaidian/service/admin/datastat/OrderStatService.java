package cn.com.kuaidian.service.admin.datastat;

import cn.com.kuaidian.entity.Dealer;
import cn.com.kuaidian.entity.SerialGroup;
import cn.com.kuaidian.util.DateUtils;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 线索统计
 * Created by ilinfei on 16/6/12.
 */
@Service
public class OrderStatService {

    private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat weekFormat = new SimpleDateFormat("MM.dd");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");

    @Autowired
    private GeliDao dao;

    /**
     * 获取每天的订单量
     * @param beginTime 开始时间;格式:yyyy-MM-dd
     * @param endTime 结束时间;格式:yyyy-MM-dd
     */
    private List<Map<String, Object>> getOrderCounter(String beginTime, String endTime){
        String sql = "SELECT to_char(o.create_time,'yyyy-MM-dd')as time,count(id)as count FROM ft_order o"
                + " WHERE o.create_time>=to_date(?,'yyyy-MM-dd HH24:mi:ss') AND o.create_time<=to_date(?,'yyyy-MM-dd HH24:mi:ss')"
                + " GROUP BY to_char(o.create_time,'yyyy-MM-dd') ORDER BY time";

        return dao.getJdbcTemplate().queryForList(sql, beginTime + " 00:00:00", endTime + " 23:59:59");
    }

    /**
     * 获取日纬度订单量统计
     * @param beginTime 开始时间;格式:yyyy-MM-dd
     * @param endTime 结束时间;格式:yyyy-MM-dd
     */
    public Map<String, Long> dayOrderStat(String beginTime, String endTime){
        Map<String, Long> dayStatMap = new HashMap<String, Long>();
        List<Map<String, Object>> list = this.getOrderCounter(beginTime, endTime);
        for (Map<String, Object> map : list) {
            String time = map.get("TIME").toString();
            long count = Long.valueOf(map.get("COUNT").toString());

            dayStatMap.put(time, count);
        }

        Map<String, Long> result = new LinkedHashMap<String, Long>();
        try {
            Date date = dayFormat.parse(beginTime);
            int days = DateUtils.diffDay(beginTime, endTime);
            for (int i = 0; i <= days; i++) {
                String dayKey = dayFormat.format(date);
                result.put(dayKey, 0L);
                if(dayStatMap.containsKey(dayKey)){
                    result.put(dayKey, dayStatMap.get(dayKey));
                }

                date = DateUtils.addDays(date, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取周纬度订单量统计
     * @param beginTime 开始时间;格式:yyyy-MM-dd
     * @param endTime 结束时间;格式:yyyy-MM-dd
     */
    public Map<String, Long> weekOrderStat(String beginTime, String endTime){
        Map<String, Long> weekStatMap = new HashMap<String, Long>();
        List<Map<String, Object>> list = this.getOrderCounter(beginTime, endTime);
        for (Map<String, Object> map : list) {
            String time = map.get("TIME").toString();
            long count = Long.valueOf(map.get("COUNT").toString());

            try {
                Date date = dayFormat.parse(time);
                Date weekBeginDate = DateUtils.getFirstDayOfWeek(date);
                Date weekEndDate = DateUtils.getLastDayOfWeek(date);
                String weekKey = weekFormat.format(weekBeginDate) + "-" + weekFormat.format(weekEndDate);
                if(!weekStatMap.containsKey(weekKey)){
                    weekStatMap.put(weekKey, 0L);
                }
                weekStatMap.put(weekKey, weekStatMap.get(weekKey) + count);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Map<String, Long> result = new LinkedHashMap<String, Long>();
        try {
            Date weekBeginDate = DateUtils.getFirstDayOfWeek(dayFormat.parse(beginTime));
            Date weekEndDate;
            do {
                weekEndDate = DateUtils.getLastDayOfWeek(weekBeginDate);

                String weekKey = weekFormat.format(weekBeginDate) + "-" + weekFormat.format(weekEndDate);
                result.put(weekKey, 0L);
                if(weekStatMap.containsKey(weekKey)){
                    result.put(weekKey, weekStatMap.get(weekKey));
                }

                weekBeginDate = DateUtils.addWeeks(weekBeginDate, 1);
            } while (weekEndDate.getTime() < dayFormat.parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取月纬度订单量统计
     * @param beginTime 开始时间;格式:yyyy-MM-dd
     * @param endTime 结束时间;格式:yyyy-MM-dd
     */
    public Map<String, Long> monthOrderStat(String beginTime, String endTime){
        Map<String, Long> monthStatMap = new HashMap<String, Long>();
        List<Map<String, Object>> list = this.getOrderCounter(beginTime, endTime);
        for (Map<String, Object> map : list) {
            String time = map.get("TIME").toString();
            long count = Long.valueOf(map.get("COUNT").toString());

            try {
                Date date = dayFormat.parse(time);
                String monthKey = monthFormat.format(date);
                if(!monthStatMap.containsKey(monthKey)){
                    monthStatMap.put(monthKey, 0L);
                }
                monthStatMap.put(monthKey, monthStatMap.get(monthKey) + count);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        Map<String, Long> result = new LinkedHashMap<String, Long>();
        try {
            Date monthBeginDate = DateUtils.getFirstDayOfMonth(dayFormat.parse(beginTime));
            Date monthEndDate;
            do{
                monthEndDate = DateUtils.getLastDayOfMonth(monthBeginDate);

                String monthKey = monthFormat.format(monthEndDate);
                result.put(monthKey, 0L);
                if(monthStatMap.containsKey(monthKey)){
                    result.put(monthKey, monthStatMap.get(monthKey));
                }

                monthBeginDate = DateUtils.addMonths(monthBeginDate, 1);
            } while (monthEndDate.getTime() < dayFormat.parse(endTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 各车系订单量统计
     * @param beginTime 开始时间;格式:yyyy-MM-dd
     * @param endTime 结束时间;格式:yyyy-MM-dd
     */
    public Map<String, Long> serialGroupOrderStat(String beginTime, String endTime){
        Map<Long, Long> sgStatMap = new HashMap<Long, Long>();
        String sql = "SELECT o.serial_group_id,count(id)as count FROM ft_order o"
                + " WHERE o.create_time>=to_date(?,'yyyy-MM-dd HH24:mi:ss') AND o.create_time<=to_date(?,'yyyy-MM-dd HH24:mi:ss')"
                + " GROUP BY o.serial_group_id";
        List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sql, beginTime + " 00:00:00", endTime + " 23:59:59");
        for (Map<String, Object> map : list) {
            long serialGroupId = Long.valueOf(map.get("SERIAL_GROUP_ID").toString());
            long count = Long.valueOf(map.get("COUNT").toString());

            sgStatMap.put(serialGroupId, count);
        }

        Map<String, Long> result = new LinkedHashMap<String, Long>();
        List<SerialGroup> serialGroupList = dao.list(SerialGroup.class, "SELECT * FROM ft_serial_group");
        for (SerialGroup serialGroup : serialGroupList) {
            long id = serialGroup.getId();
            String name = serialGroup.getName();

            result.put(name, 0L);
            if(sgStatMap.containsKey(id)){
                result.put(name, sgStatMap.get(id));
            }
        }
        return result;
    }

    /**
     * 经销商订单量统计
     * @param beginTime 开始时间;格式:yyyy-MM-dd
     * @param endTime 结束时间;格式:yyyy-MM-dd
     */
    public Map<String, Long> dealerOrderStat(String beginTime, String endTime, long provinceId, long cityId){
        SqlBuilder sqlBuilder = new SqlBuilder();
        sqlBuilder.appendSql("SELECT o.dealer_id,count(o.id)as count")
                .appendSql(" FROM ft_order o");
        if(cityId > 0){
            sqlBuilder.appendSql(" WHERE o.city_id=").appendValue(cityId)
                    .appendSql(" AND");
        } else if(provinceId > 0){
            sqlBuilder.appendSql(",ft_city c")
                    .appendSql(" WHERE o.city_id=c.id AND c.province_id=").appendValue(provinceId)
                    .appendSql(" AND");
        } else {
            sqlBuilder.appendSql(" WHERE");
        }
        sqlBuilder.appendSql(" o.create_time>=to_date(").appendValue(beginTime + " 00:00:00").appendSql(",'yyyy-MM-dd HH24:mi:ss')")
                .appendSql(" AND o.create_time<=to_date(").appendValue(endTime + " 23:59:59").appendSql(",'yyyy-MM-dd HH24:mi:ss')")
                .appendSql(" GROUP BY o.dealer_id ORDER BY count DESC LIMIT 10");

        List<Map<String, Object>> list = dao.getJdbcTemplate().queryForList(sqlBuilder.getSql(), sqlBuilder.getValues());
        Map<String, Long> result = new LinkedHashMap<String, Long>();
        for (Map<String, Object> map : list) {
            long dealerId = Long.valueOf(map.get("DEALER_ID").toString());
            long count = Long.valueOf(map.get("COUNT").toString());

            try {
                Dealer dealer = dao.find(Dealer.class, dealerId);
                if(dealer != null){
                    result.put(dealer.getShortName(), count);
                }
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
