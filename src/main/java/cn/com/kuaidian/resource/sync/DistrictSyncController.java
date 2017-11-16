package cn.com.kuaidian.resource.sync;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.kuaidian.entity.City;
import cn.com.kuaidian.entity.Province;
import cn.com.kuaidian.entity.Region;
import cn.com.kuaidian.util.DateUtils;
import cn.com.kuaidian.util.OutInterface;
import cn.com.kuaidian.util.PropertiesUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 基础数据同步任务
 * @author huangeilian
 * 2016年5月30日上午11:33:47
 */
@Controller
@RequestMapping("/timer/base")
public class DistrictSyncController {
	private static Logger logger =  LoggerFactory.getLogger(DistrictSyncController.class);
	@Autowired
	private PropertiesUtils propertiesUtils;
	@Autowired
	private GeliDao geliDao;
	
	/**
	 * 获取时间范围内的省份更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/provinceSync.do")
	public void provinceSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
		long startTime = System.currentTimeMillis();
		logger.info("================== Province基础数据同步开始 ==================");
		String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
		String endDate = env.param("endDate" , DateUtils.format(new Date()));
		int pageNo = 1 ;
		int pageSize = 1000 ;
		StringBuffer params = null;
		int backTotal = 0; //已返回条数
		int total = 0;
		int success = 0;
		Province pro = null;
		 Province province = null;
		JSONObject temp = null;
		do{
			params = new StringBuffer("/dealer/interface/kuaidian/get_province.jsp");
			params.append("?pageNo=").append(pageNo++)
			.append("&pageSize=").append(pageSize)
			.append("&startDate=").append(startDate)
			.append("&endDate=").append(endDate);
			String result = OutInterface.get(propertiesUtils.getRootPropertiesVal("sys.price.root") + params.toString(),null,"GBK");
			JSONObject json = JSONObject.parseObject(result);
	        total = json.getIntValue("total");
	        JSONArray rows = JSONArray.parseArray(json.getString("rows"));
	        backTotal += rows.size();
	        for( int i=0 ; i < rows.size() ; i++ ){
	        	try{
	        		temp = rows.getJSONObject(i);
	        		pro = new Province();
	        		if(isAllowed(temp.getString("id"))) pro.setId(temp.getLong("id"));
	        		if(isAllowed(temp.getString("name"))) pro.setName(temp.getString("name"));
	        		if(isAllowed(temp.getString("letter"))) pro.setLetter(temp.getString("letter"));
	        		if(isAllowed(temp.getString("capitalCityId"))) pro.setCapitalCityId(temp.getLongValue("capitalCityId"));
	        		if(isAllowed(temp.getString("pcProvinceId"))) pro.setPcProvinceId(temp.getLongValue("pcProvinceId"));
	        		if(isAllowed(temp.getString("deleted"))) pro.setDeleted(temp.getInteger("deleted"));
	        		if(isAllowed(temp.getString("seq"))) pro.setSeq(temp.getInteger("seq"));
	        		if(isAllowed(temp.getString("createTime"))) pro.setCreateTime(temp.getDate("createTime"));
	        		if(isAllowed(temp.getString("updateTime"))) pro.setUpdateTime(temp.getDate("updateTime"));
	        		try {
	        			province = geliDao.find(Province.class, pro.getId());
					} catch (Exception e) {
						logger.debug("not found exception, entity:{} id:{}", Province.class.getSimpleName() , pro.getId());
					}
			       	if (province != null) {
						 geliDao.update(pro);
					} else {
						 geliDao.create(pro);
					}
		        	success++;
	        	}catch(Exception e){
	        		logger.info("=== Province基础数据同步异常eL{}, json:{}",e.getMessage(),rows.getJSONObject(i).toJSONString());
	        	}
	        }
	        logger.info("Province定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
		}while (backTotal < total);
		
		long endTime = System.currentTimeMillis();
		logger.info("Province总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
	    logger.info("================== Province基础数据同步结束 ==================");
	}
	
	/**
	 * 获取时间范围内的城市更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/citySync.do")
	public void citySync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
		long startTime = System.currentTimeMillis();
		logger.info("================== City基础数据同步开始 ==================");
		String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
		String endDate = env.param("endDate" , DateUtils.format(new Date()));
		int pageNo = 1 ;
		int pageSize = 1000 ;
		StringBuffer params = null;
		int backTotal = 0; //已返回条数
		int total = 0;
		int success = 0;
		City city = null;
		City cityDb = null;
		JSONObject temp = null;
		do{
			params = new StringBuffer("/dealer/interface/kuaidian/get_city.jsp");
			params.append("?pageNo=").append(pageNo++)
			.append("&pageSize=").append(pageSize)
			.append("&startDate=").append(startDate)
			.append("&endDate=").append(endDate);
			String result = OutInterface.get(propertiesUtils.getRootPropertiesVal("sys.price.root") + params.toString(),null,"GBK");
			JSONObject json = JSONObject.parseObject(result);
			total = json.getIntValue("total");
			JSONArray rows = JSONArray.parseArray(json.getString("rows"));
			backTotal += rows.size();
			for( int i=0 ; i < rows.size() ; i++ ){
				try{
					temp = rows.getJSONObject(i);
					city = new City();
					if(isAllowed(temp.getString("id"))) city.setId(temp.getLong("id"));
					if(isAllowed(temp.getString("name"))) city.setName(temp.getString("name"));
					if(isAllowed(temp.getString("provinceId"))) city.setProvinceId(temp.getLong("provinceId"));
					if(isAllowed(temp.getString("pcProvinceId"))) city.setPcProvinceId(temp.getLong("pcProvinceId"));
					if(isAllowed(temp.getString("pcCityId"))) city.setPcCityId(temp.getLong("pcCityId"));
					if(isAllowed(temp.getString("cityCode"))) city.setCityCode(temp.getString("cityCode"));
					if(isAllowed(temp.getString("letter"))) city.setLetter(temp.getString("letter"));
					if(isAllowed(temp.getString("pinyin"))) city.setPinyin(temp.getString("pinyin"));
					if(isAllowed(temp.getString("seq"))) city.setSeq(temp.getInteger("seq"));
					if(isAllowed(temp.getString("deleted"))) city.setDeleted(temp.getInteger("deleted"));
					if(isAllowed(temp.getString("createTime"))) city.setCreateTime(temp.getDate("createTime"));
					if(isAllowed(temp.getString("updateTime"))) city.setUpdateTime(temp.getDate("updateTime"));
					try {
						cityDb = geliDao.find(City.class, city.getId());
					} catch (Exception e) {
						logger.debug("not found exception, entity:{} id:{}", City.class.getSimpleName() , city.getId());
					}
			       	if (cityDb != null) {
						 geliDao.update(city);
					} else {
						 geliDao.create(city);
					}
					success++;
				}catch(Exception e){
					logger.info("=== City基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
				}
			}
			logger.info("City定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
		}while (backTotal < total);
		
		long endTime = System.currentTimeMillis();
		logger.info("City总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
		logger.info("================== City基础数据同步结束 ==================");
		
	}
	
	/**
	 * 获取时间范围内的地区更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/regionSync.do")
	public void regionSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
		long startTime = System.currentTimeMillis();
		logger.info("================== Region基础数据同步开始 ==================");
		String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
		String endDate = env.param("endDate" , DateUtils.format(new Date()));
		int pageNo = 1 ;
		int pageSize = 1000 ;
		StringBuffer params = null;
		int backTotal = 0; //已返回条数
		int total = 0;
		int success = 0;
		Region region = null;
		Region regionDb =null;
		JSONObject temp = null;
		do{
			params = new StringBuffer("/dealer/interface/kuaidian/get_region.jsp");
			params.append("?pageNo=").append(pageNo++)
			.append("&pageSize=").append(pageSize)
			.append("&startDate=").append(startDate)
			.append("&endDate=").append(endDate);
			String result = OutInterface.get(propertiesUtils.getRootPropertiesVal("sys.price.root") + params.toString(),null,"GBK");
			JSONObject json = JSONObject.parseObject(result);
			total = json.getIntValue("total");
			JSONArray rows = JSONArray.parseArray(json.getString("rows"));
			backTotal += rows.size();
			for( int i=0 ; i < rows.size() ; i++ ){
				try{
					temp = rows.getJSONObject(i);
					region = new Region();
					if(isAllowed(temp.getString("id"))) region.setId(temp.getLong("id"));
					if(isAllowed(temp.getString("name"))) region.setName(temp.getString("name"));
					if(isAllowed(temp.getString("cityId"))) region.setCityId(temp.getLong("cityId"));
					if(isAllowed(temp.getString("pcRegionId"))) region.setPcRegionId(temp.getLong("pcRegionId"));
					if(isAllowed(temp.getString("letter"))) region.setLetter(temp.getString("letter"));
					if(isAllowed(temp.getString("deleted"))) region.setDeleted(temp.getInteger("deleted"));
					if(isAllowed(temp.getString("seq"))) region.setSeq(temp.getInteger("seq"));
					if(isAllowed(temp.getString("createTime"))) region.setCreateTime(temp.getDate("createTime"));
	        		if(isAllowed(temp.getString("updateTime"))) region.setUpdateTime(temp.getDate("updateTime"));
	        		try {
	        			regionDb = geliDao.find(Region.class, region.getId());
					} catch (Exception e) {
						logger.debug("not found exception, entity:{} id:{}", Region.class.getSimpleName() , region.getId());
					}
			       	if (regionDb != null) {
						 geliDao.update(region);
					} else {
						 geliDao.create(region);
					}
					success++;
				}catch(Exception e){
					logger.info("=== Region基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
				}
			}
			logger.info("Region定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
		}while (backTotal < total);
		
		long endTime = System.currentTimeMillis();
		logger.info("Region总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
		logger.info("================== Region基础数据同步结束 ==================");
		
	}
	

	
	/**
	 * 检测str是否为 null 、""、"null" 
	 * @param str
	 * @return
	 */
	private boolean isAllowed(String str){
		if( null == str ) return false;
		if( "".equals(str.trim()) ) return false;
		if( "null".equals(str.trim()) ) return false;
		return true;
	}
}
