package cn.com.fawtoyota.resource.sync;

import java.net.URLEncoder;
import java.util.Date;

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

import cn.com.fawtoyota.entity.DealerNews;
import cn.com.fawtoyota.entity.DealerNewsAddModel;
import cn.com.fawtoyota.entity.DealerNewsExt;
import cn.com.fawtoyota.entity.DealerNewsModel;
import cn.com.fawtoyota.entity.DealerNewsSerialGroup;
import cn.com.fawtoyota.entity.DealerNewsText;
import cn.com.fawtoyota.entity.PcautoDealerNews;
import cn.com.fawtoyota.util.DateUtils;
import cn.com.fawtoyota.util.OutInterface;
import cn.com.fawtoyota.util.PropertiesUtils;

@Controller
@RequestMapping("/timer/base")
public class DealerNewsSyncController {
	private static Logger logger =  LoggerFactory.getLogger(DealerNewsSyncController.class);
	@Autowired
	private PropertiesUtils propertiesUtils;
	@Autowired
	private GeliDao geliDao;
	
	
	/**
	 * 获取时间范围内的文章更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerNewsSync.do")
	public void dealerNewsSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerNews基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerNews dealerNews = null;
			PcautoDealerNews pcautoDealerNews = null;
			JSONObject temp = null;
			SqlBuilder sql;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_news.jsp");
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
						dealerNews = new DealerNews();
						if(isAllowed(temp.getString("name"))) dealerNews.setName(temp.getString("name"));
						if(isAllowed(temp.getString("shortName"))) dealerNews.setShortName(temp.getString("shortName"));
						if(isAllowed(temp.getString("dealerId"))) dealerNews.setDealerId(temp.getLongValue("dealerId"));
						if(isAllowed(temp.getString("type"))) dealerNews.setType(temp.getIntValue("type"));
						if(isAllowed(temp.getString("smallType"))) dealerNews.setSmallType(temp.getString("smallType"));
						if(isAllowed(temp.getString("pic"))) dealerNews.setPic(temp.getString("pic"));
						if(isAllowed(temp.getString("status"))) dealerNews.setStatus(temp.getIntValue("status"));
						if(isAllowed(temp.getString("giftPackage"))) dealerNews.setGiftPackage(temp.getString("giftPackage"));
						if(isAllowed(temp.getString("firstPassTime"))) dealerNews.setFirstPassTime(temp.getDate("firstPassTime"));
						if(isAllowed(temp.getString("newsDesc"))) dealerNews.setNewsDesc(temp.getString("newsDesc"));
						if(isAllowed(temp.getString("auditMemo"))) dealerNews.setAuditMemo(temp.getString("auditMemo"));
						if(isAllowed(temp.getString("beginTime"))) dealerNews.setBeginTime(temp.getDate("beginTime"));
						if(isAllowed(temp.getString("endTime"))) dealerNews.setEndTime(temp.getDate("endTime"));
						if(isAllowed(temp.getString("deleted"))) dealerNews.setDeleted(temp.getInteger("deleted"));
						if(isAllowed(temp.getString("createTime"))) dealerNews.setCreateTime(temp.getDate("createTime"));
		        		if(isAllowed(temp.getString("updateTime"))) dealerNews.setUpdateTime(temp.getDate("updateTime"));
		        		sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_news where pc_dealer_news_id=").appendValue(temp.getLong("id"));
						pcautoDealerNews = geliDao.findFirst(PcautoDealerNews.class,sql.getSql(),sql.getValues());
						if (null!=pcautoDealerNews && pcautoDealerNews.getId()>0) {
							dealerNews.setId(pcautoDealerNews.getDealerNewsId());
							pcautoDealerNews.setType(temp.getIntValue("type"));
							geliDao.update(dealerNews);
							geliDao.update(pcautoDealerNews);
						}else {
							geliDao.create(dealerNews);
							pcautoDealerNews = new PcautoDealerNews();
							pcautoDealerNews.setDealerNewsId(dealerNews.getId());
							pcautoDealerNews.setPcDealerNewsId(temp.getLong("id"));
							pcautoDealerNews.setType(temp.getIntValue("type"));
							pcautoDealerNews.setCreateTime(new Date());
							pcautoDealerNews.setUpdateTime(new Date());
							geliDao.create(pcautoDealerNews);
						}
						success++;
					}catch(Exception e){
						logger.info("=== DealerNews基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerNews定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerNews总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerNews基础数据同步结束 ==================");
	}
	
	/**
	 *  获取时间范围内的文章额外信息更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerNewsExtSync.do")
	public void dealerNewsExtSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerNewsExt基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerNewsExt dealerNewsExt = null;
			DealerNewsExt dealerNewsExtDb = null;
			PcautoDealerNews pcautoDealerNews = null;
			JSONObject temp = null;
			SqlBuilder sql ;
			String updateStr;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_news_ext.jsp");
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
						dealerNewsExt = new DealerNewsExt();
						updateStr="";
						sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_news where pc_dealer_news_id=").appendValue(temp.getLong("dealerNewsId"));
		        		pcautoDealerNews = geliDao.findFirst(PcautoDealerNews.class,sql.getSql(),sql.getValues());
						
						if(isAllowed(temp.getString("dealerId"))){
							dealerNewsExt.setDealerId(temp.getLong("dealerId"));
							updateStr+="dealer_id,";
						}
						if(null != pcautoDealerNews){
							dealerNewsExt.setDealerNewsId(pcautoDealerNews.getDealerNewsId());
							updateStr+="dealer_news_id,";
						}
						if(isAllowed(temp.getString("loanCompany"))){
							dealerNewsExt.setLoanCompany(temp.getString("loanCompany"));
							updateStr+="loan_company,";
						}
						if(isAllowed(temp.getString("loanMinPay"))){
							dealerNewsExt.setLoanMinPay(temp.getIntValue("loanMinPay"));
							updateStr+="loan_min_pay,";
						}
						if(isAllowed(temp.getString("loanInterest"))){
							dealerNewsExt.setLoanInterest(temp.getIntValue("loanInterest"));
							updateStr+="loan_interest,";
						}
						if(isAllowed(temp.getString("loanMaxMonth"))){
							dealerNewsExt.setLoanMaxMonth(temp.getIntValue("loanMaxMonth"));
							updateStr+="loan_max_month,";
						}
						if(isAllowed(temp.getString("loanDesc"))){
							dealerNewsExt.setLoanDesc(temp.getString("loanDesc"));
							updateStr+="loan_desc,";
						}
						if(isAllowed(temp.getString("replaceDesc"))){
							dealerNewsExt.setReplaceDesc(temp.getString("replaceDesc"));
							updateStr+="replace_desc,";
						}
						if(isAllowed(temp.getString("replaceDiscount"))){
							dealerNewsExt.setReplaceDiscount(temp.getFloatValue("replaceDiscount"));
							updateStr+="replace_discount,";
						}
						if(isAllowed(temp.getString("createTime"))){
							dealerNewsExt.setCreateTime(temp.getDate("createTime"));
							updateStr+="create_time,";
						} 
		        		if(isAllowed(temp.getString("updateTime"))){
		        			dealerNewsExt.setUpdateTime(temp.getDate("updateTime"));
		        			updateStr+="update_time,";
		        		} 
		        		if (dealerNewsExt.getDealerNewsId() > 0) {
			        		sql = new SqlBuilder();
			        		sql.appendSql(" select * from ft_dealer_news_ext dne where dne.dealer_news_id=").appendValue(dealerNewsExt.getDealerNewsId());
			        		dealerNewsExtDb = geliDao.findFirst(DealerNewsExt.class, sql.getSql(), sql.getValues());
			        		
					       	if (null != dealerNewsExtDb && dealerNewsExtDb.getId()>0) {
					       		dealerNewsExt.setId(dealerNewsExtDb.getId());
								 geliDao.update(dealerNewsExt,updateStr.substring(0, updateStr.length()-1));
							} else {
								 geliDao.create(dealerNewsExt);
							}
					       	success++;
				       	}else {
				       		logger.info("=== DealerNewsExt基础数据同步异常 e:{} json:{}", "找不到pc_dealer_news_id 对应的 dealer_news_id." , rows.getJSONObject(i).toJSONString());
						}
					}catch(Exception e){
						logger.info("=== DealerNewsExt基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerNewsExt定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerNewsExt总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerNewsExt基础数据同步结束 ==================");
	}
	
	/**
	 * 获取时间范围内的文章车型更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerNewsModelSync.do")
	public void dealerNewsModelSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerNewsModel基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerNewsModel dealerNewsModel = null;
			DealerNewsModel dealerNewsModelDb = null;
			PcautoDealerNews pcautoDealerNews = null;
			SqlBuilder sql = null;
			JSONObject temp = null;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_news_model.jsp");
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
						dealerNewsModel = new DealerNewsModel();
						sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_news where pc_dealer_news_id=").appendValue(temp.getLong("dealerNewsId"));
						pcautoDealerNews = geliDao.findFirst(PcautoDealerNews.class,sql.getSql(),sql.getValues());
						if(null != pcautoDealerNews) dealerNewsModel.setDealerNewsId(pcautoDealerNews.getDealerNewsId());
						if(isAllowed(temp.getString("modelId"))) dealerNewsModel.setModelId(temp.getLongValue("modelId"));
						if(isAllowed(temp.getString("discount"))) dealerNewsModel.setDiscount(temp.getFloatValue("discount"));
						if(isAllowed(temp.getString("discountPrice"))) dealerNewsModel.setDiscountPrice(temp.getFloatValue("discountPrice"));
						if(isAllowed(temp.getString("discountType"))) dealerNewsModel.setDiscountType(temp.getIntValue("discountType"));
						if(isAllowed(temp.getString("type"))) dealerNewsModel.setType(temp.getIntValue("type"));
						if(isAllowed(temp.getString("price"))) dealerNewsModel.setPrice(temp.getFloatValue("price"));
						if(isAllowed(temp.getString("decline"))) dealerNewsModel.setDecline(temp.getFloatValue("decline"));
						if(isAllowed(temp.getString("isReplace"))) dealerNewsModel.setIsReplace(temp.getIntValue("isReplace"));
						if(isAllowed(temp.getString("createTime"))) dealerNewsModel.setCreateTime(temp.getDate("createTime"));
		        		if(isAllowed(temp.getString("updateTime"))) dealerNewsModel.setUpdateTime(temp.getDate("updateTime"));
		        		if (dealerNewsModel.getDealerNewsId() > 0) {
		        			sql = new SqlBuilder();
	        				sql.appendSql(" select dnm.* from ft_dealer_news_model  dnm  ")
	        				.appendSql(" where 1=1 and dnm.dealer_news_id = ").appendValue(dealerNewsModel.getDealerNewsId())
	        				.appendSql(" and dnm.model_id = ").appendValue(dealerNewsModel.getModelId());
	        				dealerNewsModelDb = geliDao.findFirst(DealerNewsModel.class, sql.getSql(), sql.getValues());
	        				if (dealerNewsModelDb != null) {
	        					dealerNewsModel.setId(dealerNewsModelDb.getId());
	        					geliDao.update(dealerNewsModel);
	        				} else {
	        					geliDao.create(dealerNewsModel);
	        				}
	        				success++;
						}else {
							logger.info("=== DealerNewsModel基础数据同步异常 e:{} json:{}", "找不到pc_dealer_news_id 对应的 dealer_news_id." , rows.getJSONObject(i).toJSONString());
						}
					}catch(Exception e){
						logger.info("=== DealerNewsModel基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerNewsModel定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerNewsModel总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerNewsModel基础数据同步结束 ==================");
	}
	
	
	
	/**
	 * 获取时间范围内的文章加装车更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerNewsAddModelSync.do")
	public void dealerNewsAddModelSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerNewsAddModel基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerNewsAddModel dealerNewsAddModel = null;
			DealerNewsAddModel dealerNewsAddModelDb = null;
			PcautoDealerNews pcautoDealerNews = null;
			SqlBuilder sql = null;
			JSONObject temp = null;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_news_add_model.jsp");
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
						dealerNewsAddModel = new DealerNewsAddModel();
						sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_news where pc_dealer_news_id=").appendValue(temp.getLong("dealerNewsId"));
						pcautoDealerNews = geliDao.findFirst(PcautoDealerNews.class,sql.getSql(),sql.getValues());
						if(null != pcautoDealerNews) dealerNewsAddModel.setDealerNewsId(pcautoDealerNews.getDealerNewsId());
						if(isAllowed(temp.getString("dealerId"))) dealerNewsAddModel.setDealerId(temp.getLongValue("dealerId"));
						if(isAllowed(temp.getString("dealerAddModelId"))) dealerNewsAddModel.setDealerAddModelId(temp.getLongValue("dealerAddModelId"));
						if(isAllowed(temp.getString("createTime"))) dealerNewsAddModel.setCreateTime(temp.getDate("createTime"));
		        		if(isAllowed(temp.getString("updateTime"))) dealerNewsAddModel.setUpdateTime(temp.getDate("updateTime"));
		        		if (dealerNewsAddModel.getDealerNewsId() > 0 ) {
		        			sql = new SqlBuilder();
		        			sql.appendSql(" select dnam.* from ft_dealer_news_add_model  dnam  ")
		        			.appendSql(" where 1=1 and dnam.dealer_news_id = ").appendValue(dealerNewsAddModel.getDealerNewsId()) 
		        			.appendSql(" and dnam.dealer_add_model_id = ").appendValue(dealerNewsAddModel.getDealerAddModelId());
		        			dealerNewsAddModelDb = geliDao.findFirst(DealerNewsAddModel.class, sql.getSql(),sql.getValues());
		        			if (dealerNewsAddModelDb != null) {
		        				dealerNewsAddModel.setId(dealerNewsAddModelDb.getId());
		        				geliDao.update(dealerNewsAddModel);
		        			} else {
		        				geliDao.create(dealerNewsAddModel);
		        			}
		        			success++;
						}else {
							logger.info("=== DealerNewsAddModel基础数据同步异常 e:{} json:{}", "找不到pc_dealer_news_id 对应的 dealer_news_id." , rows.getJSONObject(i).toJSONString());
						}
					}catch(Exception e){
						logger.info("=== DealerNewsAddModel基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerNewsAddModel定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerNewsAddModel总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerNewsAddModel基础数据同步结束 ==================");
	}
	
	
	/**
	 * 获取时间范围内的文章车系更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerNewsSerialGroupSync.do")
	public void dealerNewsSerialGroupSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerNewsSerialGroup基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerNewsSerialGroup dealerNewsSerialGroup = null;
			DealerNewsSerialGroup dealerNewsSerialGroupDb = null;
			PcautoDealerNews pcautoDealerNews = null;
			SqlBuilder sql = null;
			JSONObject temp = null;
			
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_news_serial_group.jsp");
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
						dealerNewsSerialGroup = new DealerNewsSerialGroup();
						//if(isAllowed(temp.getString("id"))) dealerNewsSerialGroup.setId(temp.getLong("id"));
						if(isAllowed(temp.getString("dealerId"))) dealerNewsSerialGroup.setDealerId(temp.getLongValue("dealerId"));
						if(isAllowed(temp.getString("serialGroupId"))) dealerNewsSerialGroup.setSerialGroupId(temp.getLongValue("serialGroupId"));
						sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_news pdn where pdn.pc_dealer_news_id = ").appendValue(temp.getLongValue("dealerNewsId"));
						pcautoDealerNews = geliDao.findFirst(PcautoDealerNews.class, sql.getSql(),sql.getValues());
						if(null != pcautoDealerNews) dealerNewsSerialGroup.setDealerNewsId(pcautoDealerNews.getDealerNewsId());
						if(isAllowed(temp.getString("createTime"))) dealerNewsSerialGroup.setCreateTime(temp.getDate("createTime"));
		        		if(isAllowed(temp.getString("updateTime"))) dealerNewsSerialGroup.setUpdateTime(temp.getDate("updateTime"));
		        		if (dealerNewsSerialGroup.getDealerNewsId() > 0) {
		        			sql = new SqlBuilder();
		        			sql.appendSql(" select * from ft_dealer_news_serial_group dnsg where 1=1 ")
		        			.appendSql(" and dnsg.dealer_id = ").appendValue(dealerNewsSerialGroup.getDealerId())
		        			.appendSql(" and dnsg.dealer_news_id = ").appendValue(dealerNewsSerialGroup.getDealerNewsId())
		        			.appendSql(" and dnsg.serial_group_id = ").appendValue(dealerNewsSerialGroup.getSerialGroupId());
		        			dealerNewsSerialGroupDb = geliDao.findFirst(DealerNewsSerialGroup.class, sql.getSql(),sql.getValues());
		        			if (dealerNewsSerialGroupDb != null) {
		        				dealerNewsSerialGroup.setId(dealerNewsSerialGroupDb.getId());
		        				geliDao.update(dealerNewsSerialGroup);
		        			} else {
		        				geliDao.create(dealerNewsSerialGroup);
		        			}
		        			success++;
						}else {
							logger.info("=== DealerNewsSerialGroup基础数据同步异常 e:{} json:{}", "找不到pc_dealer_news_id 对应的 dealer_news_id." , rows.getJSONObject(i).toJSONString());
						}
					}catch(Exception e){
						logger.info("=== DealerNewsSerialGroup基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerNewsSerialGroup定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerNewsSerialGroup总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerNewsSerialGroup基础数据同步结束 ==================");
	}
	

	/**
	 * 获取时间范围内的文章内容更新数据,更新/创建 至本地
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/dealerNewsTextSync.do")
	public void dealerNewsTextSync(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Env env = EnvUtils.getEnv();
			long startTime = System.currentTimeMillis();
			logger.info("================== DealerNewsText基础数据同步开始 ==================");
			String startDate = env.param("startDate" ,DateUtils.format(DateUtils.getBeginLastDay(1)));
			String endDate = env.param("endDate" , DateUtils.format(new Date()));
			int pageNo = 1 ;
			int pageSize = 1000 ;
			StringBuffer params = null;
			int backTotal = 0; //已返回条数
			int total = 0;
			int success = 0;
			DealerNewsText dealerNewsText = null;
			DealerNewsText dealerNewsTextDb = null;
			PcautoDealerNews pcautoDealerNews = null;
			SqlBuilder sql = null;
			JSONObject temp = null;
			do{
				params = new StringBuffer("/dealer/interface/fawtoyota/get_dealer_news_text.jsp");
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
						dealerNewsText = new DealerNewsText();
						sql = new SqlBuilder();
						sql.appendSql(" select * from ft_pcauto_dealer_news pdn where pdn.pc_dealer_news_id = ").appendValue(temp.getLongValue("dealerNewsId"));
						pcautoDealerNews = geliDao.findFirst(PcautoDealerNews.class, sql.getSql(),sql.getValues());
						if(null != pcautoDealerNews) dealerNewsText.setDealerNewsId(pcautoDealerNews.getDealerNewsId());
						if(isAllowed(temp.getString("content"))) dealerNewsText.setContent(temp.getString("content"));
		        		if (dealerNewsText.getDealerNewsId() > 0) {
		        			sql = new SqlBuilder();
		        			sql.appendSql(" select * from ft_dealer_news_text  dnt where 1=1 ")
		        			.appendSql(" and dnt.dealer_news_id = ").appendValue(dealerNewsText.getDealerNewsId());
		        			dealerNewsTextDb = geliDao.findFirst(DealerNewsText.class, sql.getSql(), sql.getValues());
		        			if (dealerNewsTextDb != null) {
		        				geliDao.update(dealerNewsText);
		        			} else {
		        				geliDao.create(dealerNewsText);
		        			}
		        			success++;
						}
					}catch(Exception e){
						logger.info("=== DealerNewsText基础数据同步异常 e:{} json:{}", e.getMessage() , rows.getJSONObject(i).toJSONString());
					}
				}
				logger.info("DealerNewsText定时任务进行中：当前第 {} 页，已获取条数：{},成功条数：{}." ,pageNo - 1,backTotal,success);
			}while (backTotal < total);
			
			long endTime = System.currentTimeMillis();
			logger.info("DealerNewsText总更新条数：{},成功条数：{},耗时：{}毫秒",total,success, endTime-startTime);
			logger.info("================== DealerNewsText基础数据同步结束 ==================");
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
