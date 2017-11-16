package cn.com.kuaidian.service.order;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.kuaidian.web.WebEnv;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danga.MemCached.MemCachedClient;


@Service
public class OrderService {
	@Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;
    
    @Autowired
    MemCachedClient mcc;
    
    private final static int ONE_HOUR = 1000 * 60 * 60;
    private final static int TWO_HOUR = 1000 * 60 * 60 * 2;
    private final static int TEN_MINUTE = 1000 * 60 * 10;
    
    public final static String APPLICATION_JSON_VALUE = "application/json; charset=utf-8";
	
    /**
     * 名单线索授权code检查
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/order/order_code_verify" , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String orderCodeVerify(String orderCode){
    	WebEnv env = new WebEnv();
    	String callback = env.param("callback", "");
    	String cacheKey = "InterfacesController_order_order_code_verify_" + orderCode;
    	JSONObject result =  (JSONObject)mcc.get(cacheKey);
    	if(result == null){
    		result = new JSONObject();
    		SqlBuilder sql = new SqlBuilder();
    		sql.appendSql("select count(1) from ft_order_code where order_code=? and status = 1 ");
            int count = geliDao.count(sql.getSql(), new Object[]{orderCode});
            result.put("hasOrderCode", count>0?true:false);
        	mcc.set(cacheKey, result, TEN_MINUTE);
    	}
    	
    	if(!"".equals(callback)){
    		return callback + "(" + JSONArray.toJSONString(result) + ")";
    	} else {
    		return JSONArray.toJSONString(result);
    	}
    }
    
    /**
     * 重复线索检查
     * @param createDate
     * @param serialGroupId
     * @param mobile
     * @param dealerId
     * @return
     */
    @RequestMapping(value = "/order/repeat_verify" , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String repeatVerify(Date createDate,long serialGroupId,String mobile,long dealerId){
    	WebEnv env = new WebEnv();
    	String callback = env.param("callback", "");
		JSONObject result = new JSONObject();
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select count(id) from ft_order where create_time >= ? and serial_group_id = ? and mobile = ? and dealer_id = ? ");
        int count = geliDao.count(sql.getSql(), new Object[]{createDate,serialGroupId,mobile,dealerId});
        result.put("repeat", count+1);
    	
    	if(!"".equals(callback)){
    		return callback + "(" + JSONArray.toJSONString(result) + ")";
    	} else {
    		return JSONArray.toJSONString(result);
    	}
    }
    
    //获取当天起始时间
    public Date getTodayStartTime(){  
	   long current=System.currentTimeMillis();//当前时间毫秒数
       long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
       return new Timestamp(zero);  
    }  
	
	
}
