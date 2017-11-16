package cn.com.kuaidian.resource.sync;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.kuaidian.entity.Dealer;
import cn.com.kuaidian.entity.DealerIntro;
import cn.com.kuaidian.util.DateUtils;
import cn.com.kuaidian.util.OutInterface;
import cn.com.kuaidian.util.PropertiesUtils;
import cn.com.kuaidian.web.WebEnv;

@Controller
@RequestMapping("/timer/base")
public class DealerSyncController {
	private static Logger logger =  LoggerFactory.getLogger(DealerSyncController.class);
	@Autowired
	private PropertiesUtils propertiesUtils;
	@Autowired
	private GeliDao geliDao;
	
	/**
	 * 获取时间范围内的经销商更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerSync.do")
	public void dealerSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			WebEnv env = new WebEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== Dealer基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			Dealer dealer = null;
			Dealer dealerDb = null;
			JSONObject temp = null;
			do{
				params = new StringBuffer("/dealer/interface/kuaidian/get_dealer.jsp");
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
						dealer = new Dealer();
						if(isAllowed(temp.getString("id"))) dealer.setId(temp.getLong("id"));
						if(isAllowed(temp.getString("dealerCode"))) dealer.setDealerCode(temp.getString("dealerCode"));
						if(isAllowed(temp.getString("name"))) dealer.setName(temp.getString("name"));
						if(isAllowed(temp.getString("shortName"))) dealer.setShortName(temp.getString("shortName"));
						if(isAllowed(temp.getString("provinceId"))) dealer.setProvinceId(temp.getLongValue("provinceId"));
						if(isAllowed(temp.getString("cityId"))) dealer.setRegionId(temp.getLongValue("cityId"));
						if(isAllowed(temp.getString("regionId"))) dealer.setCityId(temp.getLongValue("regionId"));
						if(isAllowed(temp.getString("phone"))) dealer.setPhone(temp.getString("phone"));
						if(isAllowed(temp.getString("email"))) dealer.setEmail(temp.getString("email"));
						if(isAllowed(temp.getString("address"))) dealer.setAddress(temp.getString("address"));
						if(isAllowed(temp.getString("agentService"))) dealer.setAgentService(temp.getString("agentService"));
						if(isAllowed(temp.getString("mapLon"))) dealer.setMapLon(temp.getString("mapLon"));
						if(isAllowed(temp.getString("mapLat"))) dealer.setMapLat(temp.getString("mapLat"));
						if(isAllowed(temp.getString("deleted"))) dealer.setDeleted(temp.getInteger("deleted"));
						if(isAllowed(temp.getString("createTime"))) dealer.setCreateTime(temp.getDate("createTime"));
		        		if(isAllowed(temp.getString("updateTime"))) dealer.setUpdateTime(temp.getDate("updateTime"));
		        		try {
		        			dealerDb = geliDao.find(Dealer.class, dealer.getId());
						} catch (Exception e) {
							logger.debug("not found exception, entity:{} id:{}", Dealer.class.getSimpleName() , dealer.getId());
						}
				       	if (dealerDb != null) {
							 geliDao.update(dealer);
						} else {
							 geliDao.create(dealer);
						}
						success++;
					}catch(Exception e){
						logger.info("=== Dealer基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("Dealer定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("Model总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== Dealer基础数据同步结束 ==================");
	}
	
	
	
	/**
	 * 获取时间范围内的经销商简介更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerIntroSync.do")
	public void dealerIntroSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			WebEnv env = new WebEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerIntro基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerIntro dealerIntro = null;
			DealerIntro dealerIntroDb = null;
			JSONObject temp = null;
			do{
				params = new StringBuffer("/dealer/interface/kuaidian/get_dealer_intro.jsp");
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
						dealerIntro = new DealerIntro();
						//if(isAllowed(temp.getString("id"))) dealerIntro.setId(temp.getLong("id"));
						if(isAllowed(temp.getString("dealerId"))) dealerIntro.setDealerId(temp.getLongValue("dealerId"));
						if(isAllowed(temp.getString("content"))) dealerIntro.setContent(temp.getString("content"));
						dealerIntro.setCreateTime(new Date());
		        		dealerIntro.setUpdateTime(new Date());
		        		try {
		        			dealerIntroDb = geliDao.find(DealerIntro.class, dealerIntro.getId());
						} catch (Exception e) {
							logger.debug("not found exception, entity:{} id:{}", DealerIntro.class.getSimpleName() , dealerIntro.getId());
						}
				       	if (dealerIntroDb != null) {
							 geliDao.update(dealerIntro);
						} else {
							 geliDao.create(dealerIntro);
						}
						success++;
					}catch(Exception e){
						logger.info("=== DealerIntro基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerIntro定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerIntro总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerIntro基础数据同步结束 ==================");
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
