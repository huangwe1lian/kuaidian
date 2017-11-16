package cn.com.kuaidian.resource.sync;

import java.net.URLEncoder;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.kuaidian.entity.Model;
import cn.com.kuaidian.entity.SerialGroup;
import cn.com.kuaidian.util.DateUtils;
import cn.com.kuaidian.util.OutInterface;
import cn.com.kuaidian.util.PropertiesUtils;

@Controller
@RequestMapping("/timer/base")
public class CarSyncController {
	private static Logger logger =  LoggerFactory.getLogger(CarSyncController.class);
	@Autowired
	private PropertiesUtils propertiesUtils;
	@Autowired
	private GeliDao geliDao;
	
	/**
	 * 获取时间范围内的车系更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/serialGroupSync.do")
	public void serialGroupSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
		long startTime = System.currentTimeMillis();
		logger.info("================== SerialGroup基础数据同步开始 ==================");
		String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
		String endDate = env.param("endDate" , DateUtils.format(new Date()));
		int pageNo = 1 ;
		int pageSize = 1000 ;
		StringBuffer params = null;
		int backTotal = 0; //已返回条数
		int total = 0;
		int success = 0;
		SerialGroup serialGroup = null;
		SerialGroup serialGroupDb = null;
		JSONObject temp = null;
		do{
			params = new StringBuffer("/dealer/interface/kuaidian/get_serial_group.jsp");
			params.append("?pageNo=").append(pageNo++)
			.append("&pageSize=").append(pageSize)
			.append("&startDate=").append(URLEncoder.encode(startDate, "GBK"))
			.append("&endDate=").append(URLEncoder.encode(endDate, "GBK") );
			String result =OutInterface.get(propertiesUtils.getRootPropertiesVal("sys.price.root") + params.toString(),null,"GBK");
			JSONObject json = JSONObject.parseObject(result);
			total = json.getIntValue("total");
			JSONArray rows = JSONArray.parseArray(json.getString("rows"));
			backTotal += rows.size();
			for( int i=0 ; i < rows.size() ; i++ ){
				try{
					temp = rows.getJSONObject(i);
					serialGroup = new SerialGroup();
					if(isAllowed(temp.getString("id"))) serialGroup.setId(temp.getLong("id"));
					if(isAllowed(temp.getString("name"))) serialGroup.setName(temp.getString("name"));
					if(isAllowed(temp.getString("ename"))) serialGroup.setEname(temp.getString("ename"));
					if(isAllowed(temp.getString("photo"))) serialGroup.setLogo(temp.getString("photo"));
					if(isAllowed(temp.getString("status"))) serialGroup.setStatus((temp.getInteger("status")));
					if(isAllowed(temp.getString("deleted"))) serialGroup.setDeleted(temp.getInteger("deleted"));
					if(isAllowed(temp.getString("createTime"))) serialGroup.setCreateTime(temp.getDate("createTime"));
	        		if(isAllowed(temp.getString("updateTime"))) serialGroup.setUpdateTime(temp.getDate("updateTime"));
	        		try {
	        			serialGroupDb = geliDao.find(SerialGroup.class, serialGroup.getId());
					} catch (Exception e) {
						logger.debug("not found exception, entity:{} id:{}", SerialGroup.class.getSimpleName() , serialGroup.getId());
					}
			       	if (serialGroupDb != null) {
						 geliDao.update(serialGroup);
					} else {
						 geliDao.create(serialGroup);
					}
					success++;
				}catch(Exception e){
					logger.info("=== SerialGroup基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
				}
			}
			logger.info("SerialGroup定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
		}while (backTotal < total);
		
		long endTime = System.currentTimeMillis();
		logger.info("SerialGroup总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
		logger.info("================== SerialGroup基础数据同步结束 ==================");
		
	}
	
	
	/**
	 * 获取时间范围内的车型更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/modelSync.do")
	public void modelSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
		long startTime = System.currentTimeMillis();
		logger.info("================== Model基础数据同步开始 ==================");
		String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
		String endDate = env.param("endDate" , DateUtils.format(new Date()));
		int pageNo = 1 ;
		int pageSize = 1000 ;
		StringBuffer params = null;
		int backTotal = 0; //已返回条数
		int total = 0;
		int success = 0;
		Model model = null;
		Model modelDb = null;
		JSONObject temp = null;
		do{
			params = new StringBuffer("/dealer/interface/kuaidian/get_model.jsp");
			params.append("?pageNo=").append(pageNo++)
			.append("&pageSize=").append(pageSize)
			.append("&startDate=").append(URLEncoder.encode(startDate, "GBK"))
			.append("&endDate=").append(URLEncoder.encode(endDate, "GBK") );
			String result = OutInterface.get(propertiesUtils.getRootPropertiesVal("sys.price.root") + params.toString(),null,"GBK");
			JSONObject json = JSONObject.parseObject(result);
			total = json.getIntValue("total");
			JSONArray rows = JSONArray.parseArray(json.getString("rows"));
			backTotal += rows.size();
			for( int i=0 ; i < rows.size() ; i++ ){
				try{
					temp = rows.getJSONObject(i);
					model = new Model();
					if(isAllowed(temp.getString("id"))) model.setId(temp.getLong("id"));
					if(isAllowed(temp.getString("name"))) model.setName(temp.getString("name"));
					if(isAllowed(temp.getString("serialGroupId"))) model.setSerialGroupId(temp.getLong("serialGroupId")); 
					if(isAllowed(temp.getString("status"))) model.setStatus((temp.getInteger("status")));
					if(isAllowed(temp.getString("price"))) model.setPrice(temp.getFloat(("price")));
					if(isAllowed(temp.getString("deleted"))) model.setDeleted(temp.getInteger("deleted"));
					if(isAllowed(temp.getString("createTime"))) model.setCreateTime(temp.getDate("createTime"));
	        		if(isAllowed(temp.getString("updateTime"))) model.setUpdateTime(temp.getDate("updateTime"));
	        		try {
	        			modelDb = geliDao.find(Model.class, model.getId());
					} catch (Exception e) {
						logger.debug("not found exception, entity:{} id:{}", Model.class.getSimpleName() , model.getId());
					}
			       	if (modelDb != null) {
						 geliDao.update(model);
					} else {
						 geliDao.create(model);
					}
					success++;
				}catch(Exception e){
					logger.info("=== Model基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
				}
			}
			logger.info("Model定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
		}while (backTotal < total);
		
		long endTime = System.currentTimeMillis();
		logger.info("Model总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
		logger.info("================== Model基础数据同步结束 ==================");
		
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
