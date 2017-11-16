package cn.com.fawtoyota.resource.sync;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.fawtoyota.entity.DealerAddModel;
import cn.com.fawtoyota.entity.DealerModel;
import cn.com.fawtoyota.entity.DealerPriceLimit;
import cn.com.fawtoyota.entity.DealerSerialGroup;
import cn.com.fawtoyota.entity.PcautoDealerModel;
import cn.com.fawtoyota.service.dealer.DealerSerialGroupService;
import cn.com.fawtoyota.util.DateUtils;
import cn.com.fawtoyota.util.OutInterface;
import cn.com.fawtoyota.util.PropertiesUtils;

@Controller
@RequestMapping("/timer/base")
public class DealerCarSyncController {
	private static Logger logger =  LoggerFactory.getLogger(DealerCarSyncController.class);
	@Autowired
	private PropertiesUtils propertiesUtils;
	@Autowired
	private GeliDao geliDao;
	@Autowired
	private DealerSerialGroupService dealerSerialGroupService;
	
	
	/**
	 * 获取时间范围内的报价车型更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerModelSync.do")
	public void dealerModelSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerModel基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerModel dealerModel = null;
			PcautoDealerModel pcautoDealerModel =null;
			JSONObject temp = null;
			SqlBuilder sql;
			DealerPriceLimit dealerPriceLimit;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_model.jsp");
				params.append("?pageNo=").append(pageNo++)
				.append("&pageSize=").append(pageSize)
				.append("&startDate=").append(URLEncoder.encode(startDate,"GBK"))
				.append("&endDate=").append(URLEncoder.encode(endDate,"GBK") );
				String result = OutInterface.get(propertiesUtils.getRootPropertiesVal("sys.price.root") + params.toString(),null,"GBK");
				JSONObject json = JSONObject.parseObject(result);
				total = json.getIntValue("total");
				JSONArray rows = JSONArray.parseArray(json.getString("rows"));
				backTotal += rows.size();
				for( int i=0 ; i < rows.size() ; i++ ){
					try{
						temp = rows.getJSONObject(i);
						dealerModel = new DealerModel();
						sql = new SqlBuilder();
						sql.appendSql(" select dpl.* from ft_dealer_price_limit dpl left join ft_dealer d on d.province_id  = dpl.province_id and d.city_id = dpl.city_id ")
						.appendSql(" where dpl.status = 1 and d.id = ").appendValue(temp.getLongValue("dealerId"))
						.appendSql(" and dpl.model_id = ").appendValue(temp.getLongValue("modelId"))
						.appendSql(" order by dpl.update_time desc ");
						dealerPriceLimit = geliDao.findFirst(DealerPriceLimit.class, sql.getSql(), sql.getValues());
						
					    if (isAllowed(temp.getString("dealerId"))) dealerModel.setDealerId(temp.getLongValue("dealerId"));
						if (isAllowed(temp.getString("serialGroupId"))) dealerModel.setSerialGroupId(temp.getLongValue("serialGroupId"));
						if (isAllowed(temp.getString("modelId"))) dealerModel.setModelId(temp.getLongValue("modelId"));
						if (isAllowed(temp.getString("price"))){
							if (dealerPriceLimit!=null && dealerPriceLimit.getId()>0) {
								dealerModel.setPrice(dealerPriceLimit.getMinPrice()-temp.getFloatValue("price")>0?dealerPriceLimit.getMinPrice():temp.getFloatValue("price"));
							}else {
								dealerModel.setPrice(temp.getFloatValue("price"));
							}
						} 
						if (isAllowed(temp.getString("createTime"))) dealerModel.setCreateTime(temp.getDate("createTime"));
						if (isAllowed(temp.getString("updateTime"))) dealerModel.setUpdateTime(temp.getDate("updateTime"));
					    
						sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_model where pc_dealer_model_id=").appendValue(temp.getLong("id"));
					    pcautoDealerModel = geliDao.findFirst(PcautoDealerModel.class,sql.getSql(),sql.getValues());
						if (null!=pcautoDealerModel && pcautoDealerModel.getId()>0) {
							dealerModel.setId(pcautoDealerModel.getDealerModelId());
							geliDao.update(dealerModel);
						}else {
							geliDao.create(dealerModel);
							pcautoDealerModel = new PcautoDealerModel();
							pcautoDealerModel.setDealerModelId(dealerModel.getId());
							pcautoDealerModel.setPcDealerModelId(temp.getLongValue("id"));
							pcautoDealerModel.setCreateTime(new Date());
							pcautoDealerModel.setUpdateTime(new Date());
							geliDao.create(pcautoDealerModel);
						}
						success++;
					}catch(Exception e){
						logger.info("=== DealerModel基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerModel定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerModel总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerModel基础数据同步结束 ==================");
	}
	
	
	
	/**
	 * 获取时间范围内的报价车系更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerSerialGroupSync.do")
	public void dealerSerialGroupSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerSerialGroup基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 50 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerSerialGroup dealerSerialGroup = null;
			JSONObject temp = null;
			JSONObject rs = null;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_serial_group.jsp");
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
						if (temp.getLongValue("dealerId") > 0) {
							List<DealerSerialGroup> dealerSerialGroups = dealerSerialGroupService.findDealerSerialGroups(temp.getLongValue("dealerId"));
							JSONArray results = JSONArray.parseArray(temp.getString("results"));
							for(int j=0 ; j<results.size() ; j++){
								rs = results.getJSONObject(j);
								boolean flat = false;
								for(DealerSerialGroup dsg:dealerSerialGroups){
									if (dsg.getDealerId() == rs.getLongValue("DEALERID")
										&& dsg.getSerialGroupId() == rs.getLongValue("SERIALGROUPID")) {
										flat =true;
										break;
									}
								}
								
								if (!flat) {
									dealerSerialGroup = new DealerSerialGroup();
									if(isAllowed(rs.getString("DEALERID"))) dealerSerialGroup.setDealerId(rs.getLongValue("DEALERID"));
									if(isAllowed(rs.getString("SERIALGROUPID"))) dealerSerialGroup.setSerialGroupId(rs.getLongValue("SERIALGROUPID"));
									if(isAllowed(rs.getString("CREATETIME"))) dealerSerialGroup.setCreateTime(rs.getDate("CREATETIME"));
									if(isAllowed(rs.getString("UPDATETIME"))) dealerSerialGroup.setUpdateTime(rs.getDate("UPDATETIME"));
									geliDao.create(dealerSerialGroup);
								}
								
							}
							
							for (DealerSerialGroup dsg : dealerSerialGroups) {
								boolean flag = false;
								for(int t=0 ; t < results.size() ; t++){
									rs = results.getJSONObject(t);
									if (dsg.getDealerId() == rs.getLongValue("DEALERID")
											&& dsg.getSerialGroupId() == rs.getLongValue("SERIALGROUPID")){
										flag = true;
										break;
									}
								}
								
								if (!flag) {
									geliDao.delete(dsg, dsg.getId());
								}
								
							}
						}
						success++;
					}catch(Exception e){
						logger.info("=== DealerSerialGroup基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerSerialGroup定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerSerialGroup总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerSerialGroup基础数据同步结束 ==================");
	}
	
	
	
	
	/**
	 * 获取时间范围内的加装车更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerAddModelSync.do")
	public void dealerAddModelSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerAddModel基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerAddModel dealerAddModel = null;
			DealerAddModel dealerAddModelDb = null;
			JSONObject temp = null;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_add_model.jsp");
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
						dealerAddModel = new DealerAddModel();
						if(isAllowed(temp.getString("id"))) dealerAddModel.setId(temp.getLong("id"));
						if(isAllowed(temp.getString("dealerId"))) dealerAddModel.setDealerId(temp.getLongValue("dealerId"));
						if(isAllowed(temp.getString("serialGroupId"))) dealerAddModel.setSerialGroupId(temp.getLongValue("serialGroupId"));
						if(isAllowed(temp.getString("modelId"))) dealerAddModel.setModelId(temp.getLongValue("modelId"));
						if(isAllowed(temp.getString("price"))) dealerAddModel.setPrice(temp.getFloatValue("price"));
						if(isAllowed(temp.getString("addPrice"))) dealerAddModel.setPrice(temp.getFloatValue("addPrice"));
						if(isAllowed(temp.getString("config"))) dealerAddModel.setConfig(temp.getString("config"));
						if(isAllowed(temp.getString("createTime"))) dealerAddModel.setCreateTime(temp.getDate("createTime"));
		        		if(isAllowed(temp.getString("updateTime"))) dealerAddModel.setUpdateTime(temp.getDate("updateTime"));
		        		try {
		        			dealerAddModelDb = geliDao.find(DealerAddModel.class, dealerAddModel.getId());
						} catch (Exception e) {
							logger.debug("not found exception, entity:{} id:{}", DealerAddModel.class.getSimpleName() , dealerAddModel.getId());
						}
				       	if (dealerAddModelDb != null) {
							 geliDao.update(dealerAddModel);
						} else {
							 geliDao.create(dealerAddModel);
						}
						success++;
					}catch(Exception e){
						logger.info("=== DealerAddModel基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerAddModel定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerAddModel总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerAddModel基础数据同步结束 ==================");
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
