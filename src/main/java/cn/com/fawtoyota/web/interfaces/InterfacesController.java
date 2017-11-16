package cn.com.fawtoyota.web.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.web.EnvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.danga.MemCached.MemCachedClient;

import cn.com.fawtoyota.entity.City;
import cn.com.fawtoyota.entity.Province;
import cn.com.fawtoyota.service.admin.RegionService;
import cn.com.fawtoyota.web.WebEnv;


@Controller
@RequestMapping("/api")
public class InterfacesController {
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
    
    @RequestMapping(value = "/province/list" , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String provinces(HttpServletRequest request,HttpServletResponse response){
    	String cacheKey = "InterfacesController_provinces_";
    	WebEnv env = new WebEnv();
    	String callback = env.param("callback", "");
    	JSONArray result = (JSONArray) mcc.get(cacheKey);
    	if(result == null){
    		RegionService regionService = EnvUtils.getEnv().getBean(RegionService.class);
    		result = new JSONArray();
        	List<Province> list = regionService.listProvince();
        	
    		JSONObject json = null;
        	if(null != list && list.size() > 0){
           	 	for(Province p : list){
    	       		json = new JSONObject();
    	       		json.put("pId",p.getId());
    	       		json.put("name",p.getLetter()+" "+p.getName());
    	       		json.put("letter",p.getLetter());
    	       		result.add(json);
                }
        	}
        	mcc.set(cacheKey, result, TWO_HOUR);
    	} 
    	
    	if(!"".equals(callback) && callback != null){
    		return callback + "(" + JSONArray.toJSONString(result) + ")";
    	} else {
    		return JSONArray.toJSONString(result);
    	}
    }
    
    @RequestMapping(value = "/city/list" , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String citys(HttpServletRequest request,HttpServletResponse response){
    	WebEnv env = new WebEnv();
    	String callback = env.param("callback", "");
    	long pid = env.paramLong("pid",-1l);
    	
    	String cacheKey = "InterfacesController_citys_" + pid;
    	JSONArray result = (JSONArray) mcc.get(cacheKey);
    	if(result == null){
    		RegionService regionService = EnvUtils.getEnv().getBean(RegionService.class);
    		result = new JSONArray();

    		List<City> list = regionService.listCityByPro(pid);
        	
        	JSONObject json = null;
        	if(null != list && list.size() > 0){
        		for(City c : list){
        			json = new JSONObject();
        			json.put("cId",c.getId());
        			json.put("name",c.getLetter()+" "+c.getName());
        			json.put("letter",c.getLetter());
        			result.add(json);
        		}
        	}
        	mcc.set(cacheKey, result, TWO_HOUR);
    	}
    	
    	if(!"".equals(callback) && callback != null){
    		return callback + "(" + JSONArray.toJSONString(result) + ")";
    	} else {
    		return JSONArray.toJSONString(result);
    	}
    }
    
    @RequestMapping(value = "/iparea/list" , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String ipAreas(HttpServletRequest request,HttpServletResponse response){
    	WebEnv env = new WebEnv();
    	String callback = env.param("callback", "");
    	
    	String cacheKey = "InterfacesController_ipAreas_";
    	JSONObject result = (JSONObject) mcc.get(cacheKey);
    	if(result == null){
    		result = new JSONObject();
    		JSONArray cityData = new JSONArray();
    		JSONObject cityObj = null;
    		RegionService regionService = EnvUtils.getEnv().getBean(RegionService.class);
    		
    		List<Map<String,Object>> ipareaList = regionService.listIpArea();
    		for(Map<String,Object> r : ipareaList ){
    			cityObj = new JSONObject();
    			cityObj.put("proId",r.get("pid"));
    			cityObj.put("pcProId",r.get("pcpid"));
    			cityObj.put("pro",r.get("pname"));
    			
    			cityObj.put("cityId",r.get("cid"));
    			cityObj.put("pcCityId",r.get("pccid"));
    			cityObj.put("name",r.get("cname"));
    			cityObj.put("pinyin",r.get("cpinyin"));
    			cityObj.put("cityLetter",r.get("cletter"));
    			cityObj.put("cityCode",r.get("citycode"));
    			cityData.add(cityObj);
    		}
    		result.put("result", true);
    		result.put("cityData", cityData);
    		
    		mcc.set(cacheKey, result, TWO_HOUR);
    	}
    	
    	if(!"".equals(callback) && callback != null){
    		return callback + "(" + JSONArray.toJSONString(result) + ")";
    	} else {
    		return JSONArray.toJSONString(result);
    	}
    }
    
    
    @RequestMapping(value = "/iparea" , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String ipAreaById(HttpServletRequest request,HttpServletResponse response){
    	WebEnv env = new WebEnv();
    	String callback = env.param("callback", "");
    	long cid = env.paramLong("cid",0l);
    	String cityCode = env.param("cityCode","");
    	String cacheKey = "InterfacesController_ipAreaById_" + cid + "_" + cityCode;
    	JSONObject result = (JSONObject) mcc.get(cacheKey);
    	if(result == null){
    		result = new JSONObject();
    		JSONObject cityObj = null;
    		RegionService regionService = EnvUtils.getEnv().getBean(RegionService.class);
    		
    		Map<String,Object> iparea = regionService.getIpAreaById(cid, cityCode);
    		if(iparea != null){
    			cityObj = new JSONObject();
    			cityObj.put("proId",iparea.get("pid"));
    			cityObj.put("pcProId",iparea.get("pcpid"));
    			cityObj.put("pro",iparea.get("pname"));
    			
    			cityObj.put("cityId",iparea.get("cid"));
    			cityObj.put("pcCityId",iparea.get("pccid"));
    			cityObj.put("name",iparea.get("cname"));
    			cityObj.put("pinyin",iparea.get("cpinyin"));
    			cityObj.put("cityLetter",iparea.get("cletter"));
    			cityObj.put("cityCode",iparea.get("citycode"));
    			
    			result.put("result", true);
        		result.put("cityData", cityObj);
    		}
    		
    		mcc.set(cacheKey, result, TWO_HOUR);
    	}
    	
    	if(!"".equals(callback) && callback != null){
    		return callback + "(" + JSONArray.toJSONString(result) + ")";
    	} else {
    		return JSONArray.toJSONString(result);
    	}
    }
    

}
