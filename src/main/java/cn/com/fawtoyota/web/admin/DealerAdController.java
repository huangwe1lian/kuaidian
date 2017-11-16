package cn.com.fawtoyota.web.admin;

import cn.com.fawtoyota.entity.DealerNews;
import cn.com.fawtoyota.entity.DealerNewsPosition;
import cn.com.fawtoyota.entity.DealerNewsSerialGroup;
import cn.com.fawtoyota.entity.MediaNews;
import cn.com.fawtoyota.entity.MediaNewsSerialGroup;
import cn.com.fawtoyota.entity.SerialGroup;
import cn.com.fawtoyota.entity.TemplateAd;
import cn.com.fawtoyota.entity.User;
import cn.com.fawtoyota.resource.auth.AdminSecurity;
import cn.com.fawtoyota.resource.log.LogFacade;
import cn.com.fawtoyota.service.admin.AdService;
import cn.com.fawtoyota.service.serialgroup.SerialGroupService;
import cn.com.fawtoyota.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.dao.ValueSetter;
import org.gelivable.log.GeliLogFacade;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.gelivable.web.GeliFunctions;
import org.gelivable.webmvc.JSONBuilder;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("/admin/ad/dealer")
public class DealerAdController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;
    
    @Autowired
    SerialGroupService serialGroupService;
    
    @Autowired
    AdService adService;

    @RequestMapping(value="/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class type= DealerNews.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection","desc"));
        String _p = env.param("_p", "");
        SqlBuilder sql = new SqlBuilder();
        SqlBuilder sqlCount = new SqlBuilder();
        sql.appendSql("select * from ").appendSql(geliOrm.getTableName(type)).appendSql(" t where type=2 ");
        sqlCount.appendSql("select count(*) from ").appendSql(geliOrm.getTableName(type)).appendSql(" t where type=2 ");

        List<String> fieldList = geliOrm.getFieldList( type);
        if( fieldList != null && fieldList.size() >0){
    		try{
	        	for( String fieldName : fieldList){
	    			ValueSetter  vs = geliOrm.getFieldSetter( type, fieldName);
	    			String value = env.param( fieldName, "");
	    			if( StringUtils.isBlank( value)) value = env.param( vs.getFieldPath(), "");
	    			if( !StringUtils.isBlank( value)){
	    				req.setAttribute( fieldName, value);
	    				String pcolumn = geliOrm.getColumnByField(type, fieldName);
	    				sql.appendSql( " and ").appendSql(pcolumn);
	                    sqlCount.appendSql( " and ").appendSql(pcolumn);
	    				if( vs.getFieldType() == int.class ||  vs.getFieldType() == Integer.class){
	        				sql.appendSql("=").appendValue(new Integer(value));
	                        sqlCount.appendSql("=").appendValue(new Integer(value));
	    				}else if( vs.getFieldType() == long.class ||  vs.getFieldType() == Long.class){
	    					sql.appendSql("=").appendValue(new Long(value));
	                        sqlCount.appendSql("=").appendValue(new Long(value));
	    				}else if( vs.getFieldType() == double.class ||  vs.getFieldType() == Double.class){
	    					sql.appendSql("=").appendValue(new Double(value));
	                        sqlCount.appendSql("=").appendValue(new Double(value));
	    				}else{
	    					sql.appendSql(" like ").appendValue("%" + value + "%");
	                        sqlCount.appendSql(" like ").appendValue("%" + value + "%");
	    				}
	    			}
	    		}
    		}catch( Exception e){
        		return "admin/ad/dealer/list";
    		}
    	}
        
        long sgId = env.paramLong("sgId",0l);
        if(sgId > 0){
        	sql.appendSql(" and exists(select 1 from ft_dealer_news_serial_group sg where sg.dealer_news_id=t.id and sg.serial_group_id=").appendValue(sgId).appendSql(") ");
        	sqlCount.appendSql(" and exists(select 1 from ft_dealer_news_serial_group sg where sg.dealer_news_id=t.id and sg.serial_group_id=").appendValue(sgId).appendSql(") ");
        }
        
        String dealerShortName = env.param("dealerShortName","");
        if(!"".equals(dealerShortName)){
        	dealerShortName = "%" + dealerShortName + "%";
        	sql.appendSql(" and exists(select 1 from ft_dealer d where d.id=t.dealer_id and d.short_name like ").appendValue(dealerShortName).appendSql(") ");
        	sqlCount.appendSql(" and exists(select 1 from ft_dealer d where d.id=t.dealer_id and d.short_name like ").appendValue(dealerShortName).appendSql(") ");
        }
        
        String createTime1 = env.param("createTime1","");
        String createTime2 = env.param("createTime2","");
        if(!"".equals(createTime1)){
        	sql.appendSql(" and create_time >= cast(").appendValue(createTime1).appendSql(" as timestamp)");
            sqlCount.appendSql(" and create_time >= cast(").appendValue(createTime1).appendSql(" as timestamp)");
        }
        if(!"".equals(createTime2)){
        	sql.appendSql(" and create_time <= cast(").appendValue(createTime2).appendSql(" as timestamp)");
        	sqlCount.appendSql(" and create_time <= cast(").appendValue(createTime2).appendSql(" as timestamp)");
        }
        
        if (!"".equals(orderField)) {
            sql.appendSql(" order by ").appendSql(
                    geliOrm.getColumnByField(type, orderField)).appendSql(" ").appendSql(orderDirection);
        }
        int total;
        if (sqlCount.hasValue()) {
            total = geliDao.count(sqlCount.getSql(), sqlCount.getValues());
        } else {
            total = geliDao.count(sqlCount.getSql());
        }
        int pageNum = env.paramInt("pageNum", 1);
        int numPerPage = env.paramInt("numPerPage", 20);
        List list;
        if (sql.hasValue()) {
            list = geliDao.page(type, sql.getSql(), pageNum, numPerPage, sql.getValues());
        } else {
            list = geliDao.page(type, sql.getSql(), pageNum, numPerPage);
        }
        req.setAttribute("_p", _p);
        List<SerialGroup> sgList = serialGroupService.listNormal();
        req.setAttribute("sgList", sgList);
        req.setAttribute("total", total);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("numPerPage", numPerPage);
        req.setAttribute("orderField", orderField);
        req.setAttribute("orderDirection", orderDirection);
        req.setAttribute("list", list);
        req.setAttribute("lastYear", DateUtils.format(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd 00:00:00"));
        return "admin/ad/dealer/list";
    }
    
    
    @RequestMapping(value="/shifthidden.do", method = RequestMethod.POST)
    public String shiftStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<DealerNews> type = cn.com.fawtoyota.entity.DealerNews.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        DealerNews entity = geliDao.findDb(type, id);

        env.bind(type, entity);
        String error = env.validateAll(entity);
        if (error != null) {
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 300)
                .put("message", GeliFunctions.fmtValidateErrorMessage(error))
                .toString()
                );
            return null;

        }
        try {
        	entity.setUpdateTime(new Date());
        	LogFacade.log(entity);
            geliDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
       
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 200)
                .put("message", "操作成功")
                .put("navTabId", "list-ad-dealer")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/showdetail.do", method = RequestMethod.GET)
    public String showDetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<DealerNews> type = DealerNews.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        DealerNews obj = geliDao.find(type, id);
        try {
            List<Map<String,Object>> serialGroups = adService.findDealerAdSerialGroup(obj.getId());
            req.setAttribute("serialGroups", JSONArray.toJSON(serialGroups));
		} catch (Exception e) {
			e.printStackTrace();
		}
        req.setAttribute("method", "showdetail");
        req.setAttribute("domain_clazz",DealerNews.class);
        req.setAttribute("entity", obj);
        return "admin/ad/dealer/detailshow";
    }
    
    @RequestMapping(value="/recommend.do", method = RequestMethod.GET)
    public String showRecommend(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	Class<DealerNews> type = DealerNews.class;
    	String keyField = geliOrm.getKeyField(type);
    	Object id = geliOrm.parseObjectId(type, env.param(keyField));
    	DealerNews obj = geliDao.find(type, id);

    	try {
			List<DealerNewsPosition> positions = adService.findDealerAdPosition(obj.getId());
			for(int i=0 ;i < positions.size();i++){
				req.setAttribute("position" + positions.get(i).getType(), positions.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	req.setAttribute("method", "recommend");
    	req.setAttribute("domain_clazz",DealerNews.class);
    	req.setAttribute("entity", obj);
    	return "admin/ad/dealer/recommend";
    }
    
    
    @RequestMapping(value="/recommend.do", method = RequestMethod.POST)
    public String recommend(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<DealerNews> type = cn.com.fawtoyota.entity.DealerNews.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        DealerNews entity = geliDao.findDb(type, id);
        int position1 = env.paramInt("position1",0);
        int position2 = env.paramInt("position2",0);

        try {
        	User user = AdminSecurity.getCurrentUser(req);
        	adService.deletePositionByDealerAdId(entity.getId());
        	if(position1 > 0 || position2 > 0){
        		entity.setIsRecommend(1);
        		entity.setRecommendUserId(user.getId());
        		entity.setRecommendTime(new Date());
        		
        		DealerNewsPosition position = null;
        		if(position1 > 0){
        			position = new DealerNewsPosition();
        			position.setDealerNewsId(entity.getId());
        			position.setType(1);
        			position.setPosition(1);
        			position.setCreateTime(new Date());
        			position.setUpdateTime(new Date());
        			geliDao.create(position);
        		}
        		if(position2 > 0){
        			position = new DealerNewsPosition();
        			position.setDealerNewsId(entity.getId());
        			position.setType(2);
        			position.setPosition(2);
        			position.setCreateTime(new Date());
        			position.setUpdateTime(new Date());
        			geliDao.create(position);
        		}
        	} else {
        		entity.setIsRecommend(0);
        	}
        	
        	entity.setUpdateTime(new Date());
        	LogFacade.log(entity);
            geliDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
       
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 200)
                .put("message", "操作成功")
                .put("navTabId", "list-ad-dealer")
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
}

