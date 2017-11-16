package cn.com.kuaidian.web.admin.datastat;

import cn.com.kuaidian.service.admin.datastat.OrderStatService;
import cn.com.kuaidian.util.DateUtils;
import cn.com.kuaidian.web.WebEnv;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 线索量统计Controller
 * Created by ilinfei on 16/6/13.
 */
@Controller
@RequestMapping("/admin/datastat")
public class OrderStatController {

    private SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    @Autowired
    private OrderStatService orderStatService;

    @RequestMapping("/dayOrderStat")
    public ModelAndView dayOrderStat(){
        WebEnv env = new WebEnv();
        String beginTime = env.param("beginTime", null);
        String endTime = env.param("endTime", null);
        
        Date now = new Date();
        if(beginTime == null){
            beginTime = dayFormat.format(DateUtils.addDays(now, -6));
        }
        if(endTime == null){
            endTime = dayFormat.format(now);
        }

        Map<String, Long> dayStatMap = orderStatService.dayOrderStat(beginTime, endTime);

        ModelAndView mv = new ModelAndView("admin/datastat/orderStat");
        this.addOrderStatModel(mv, dayStatMap, beginTime, endTime);
        mv.addObject("beginTime", beginTime);
        mv.addObject("endTime", endTime);
        mv.addObject("statType", "day");
        return mv;
    }

    @RequestMapping("/weekOrderStat")
    public ModelAndView weekOrderStat(){
        WebEnv env = new WebEnv();
        String beginTime = env.param("beginTime", null);
        String endTime = env.param("endTime", null);
        
        Date now = new Date();
        if(beginTime == null){
            beginTime = dayFormat.format(DateUtils.getFirstDayOfWeek(DateUtils.addWeeks(now, -3)));
        }
        if(endTime == null){
            endTime = dayFormat.format(DateUtils.getLastDayOfWeek(now));
        }

        Map<String, Long> weekStatMap = orderStatService.weekOrderStat(beginTime, endTime);

        ModelAndView mv = new ModelAndView("admin/datastat/orderStat");
        this.addOrderStatModel(mv, weekStatMap, beginTime, endTime);
        mv.addObject("beginTime", beginTime);
        mv.addObject("endTime", endTime);
        mv.addObject("statType", "week");
        return mv;
    }

    @RequestMapping("/monthOrderStat")
    public ModelAndView monthOrderStat(){
        WebEnv env = new WebEnv();
        String beginTime = env.param("beginTime");
        String endTime = env.param("endTime");
        if(beginTime == null && endTime == null){
            Date now = new Date();
            beginTime = dayFormat.format(DateUtils.getFirstDayOfMonth(DateUtils.addMonths(now, -1)));
            endTime = dayFormat.format(DateUtils.getLastDayOfMonth(now));
        } else {
            try {
                beginTime = beginTime + "-01";
                endTime = dayFormat.format(DateUtils.getLastDayOfMonth(monthFormat.parse(endTime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Map<String, Long> monthStatMap = orderStatService.monthOrderStat(beginTime, endTime);

        ModelAndView mv = new ModelAndView("admin/datastat/orderStat");
        this.addOrderStatModel(mv, monthStatMap, beginTime, endTime);
        mv.addObject("beginTime", beginTime.substring(0, 7));
        mv.addObject("endTime", endTime.substring(0, 7));
        mv.addObject("statType", "week");
        mv.addObject("statType", "month");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/dealerOrderStat")
    public JSONObject dealerOrderStat(){
        WebEnv env = new WebEnv();
        String beginTime = env.param("beginTime");
        String endTime = env.param("endTime");
        long provinceId = env.paramLong("provinceId", 0);
        long cityId = env.paramLong("cityId", 0);

        Map<String, Long> dealerStatMap = orderStatService.dealerOrderStat(beginTime, endTime, provinceId, cityId);

        JSONObject result = new JSONObject();
        result.put("dealerOrderxAxis", JSON.toJSON(dealerStatMap.keySet()));
        result.put("dealerOrderSeries", JSON.toJSON(dealerStatMap.values()));
        return result;
    }

    private void addOrderStatModel(ModelAndView mv, Map<String, Long> statMap, String beginTime, String endTime){
        //线索量走势数据
        mv.addObject("orderxAxis", JSON.toJSON(statMap.keySet()).toString());
        mv.addObject("orderSeries", JSON.toJSON(statMap.values()).toString());

        //各车系线索占比数据
        Map<String, Long> sgStatMap = orderStatService.serialGroupOrderStat(beginTime, endTime);
        JSONArray sgOrderLegend = new JSONArray();
        JSONArray sgOrderSeries = new JSONArray();
        for (Map.Entry<String, Long> entry : sgStatMap.entrySet()) {
            String key = entry.getKey();
            long value = entry.getValue();

            sgOrderLegend.add(key);

            JSONObject jo = new JSONObject();
            jo.put("value", value);
            jo.put("name", key);
            sgOrderSeries.add(jo);
        }
        mv.addObject("sgOrderLegend", sgOrderLegend.toJSONString());
        mv.addObject("sgOrderSeries", sgOrderSeries.toJSONString());

        //线索前十经销商数据
        Map<String, Long> dealerStatMap = orderStatService.dealerOrderStat(beginTime, endTime, 0, 0);
        mv.addObject("dealerOrderxAxis", JSON.toJSON(dealerStatMap.keySet()).toString());
        mv.addObject("dealerOrderSeries", JSON.toJSON(dealerStatMap.values()).toString());
    }
}
