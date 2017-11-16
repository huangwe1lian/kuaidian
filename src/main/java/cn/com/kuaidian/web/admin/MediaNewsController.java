package cn.com.kuaidian.web.admin;

import cn.com.kuaidian.entity.MediaNews;
import cn.com.kuaidian.entity.MediaNewsCity;
import cn.com.kuaidian.entity.MediaNewsSerialGroup;
import cn.com.kuaidian.entity.SerialGroup;
import cn.com.kuaidian.entity.User;
import cn.com.kuaidian.resource.auth.AdminSecurity;
import cn.com.kuaidian.resource.log.LogFacade;
import cn.com.kuaidian.service.admin.MediaNewsService;
import cn.com.kuaidian.service.admin.RegionService;
import cn.com.kuaidian.service.serialgroup.SerialGroupService;
import cn.com.kuaidian.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.Select;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.dao.ValueSetter;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.gelivable.web.GeliFunctions;
import org.gelivable.webmvc.JSONBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;

/** 
 * 媒体资讯控制器
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-6 上午11:03:21
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class MediaNewsController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;
    
    @Autowired
    SerialGroupService serialGroupService;
    
    @Autowired
    RegionService regionService;
    
    @Autowired
    MediaNewsService mediaNewsService;
    
    @Autowired
    Select select;

    @RequestMapping(value="/medianews/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class type= MediaNews.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
        long sgId = env.paramLong("sgId",0l);
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection","desc"));
        String _p = env.param("_p", "");
        SqlBuilder sql = new SqlBuilder();
        SqlBuilder sqlCount = new SqlBuilder();
        sql.appendSql("select * from ").appendSql(geliOrm.getTableName(type)).appendSql(" t where 1=1 ");
        sqlCount.appendSql("select count(*) from ").appendSql(geliOrm.getTableName(type)).appendSql(" t where 1=1 ");

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
        		return "admin/medianews/list";
    		}
    	}
        if(sgId > 0){
        	sql.appendSql(" and exists(select 1 from ft_media_news_serial_group sg where sg.media_news_id=t.id and sg.serial_group_id=").appendValue(sgId).appendSql(") ");
        	sqlCount.appendSql(" and exists(select 1 from ft_media_news_serial_group sg where sg.media_news_id=t.id and sg.serial_group_id=").appendValue(sgId).appendSql(") ");
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
        Map<String, String> mediaNewsKind = select.select("mediaNewsKind");
        List<SerialGroup> sgList = serialGroupService.listNormal();
        req.setAttribute("sgList", sgList);
        req.setAttribute("mediaNewsKind", mediaNewsKind);
        req.setAttribute("_p", _p);
        req.setAttribute("total", total);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("numPerPage", numPerPage);
        req.setAttribute("orderField", orderField);
        req.setAttribute("orderDirection", orderDirection);
        req.setAttribute("lastYear", DateUtils.format(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd 00:00:00"));
        req.setAttribute("list", list);
        return "admin/medianews/list";
    }

    @RequestMapping(value="/medianews/select.do")
    public String select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class type= MediaNews.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection","desc"));
        String _p = env.param("_p", "");
        SqlBuilder sql = new SqlBuilder();
        SqlBuilder sqlCount = new SqlBuilder();
        sql.appendSql("select * from ").appendSql(geliOrm.getTableName(type)).appendSql(" where 1=1 ");
        sqlCount.appendSql("select count(*) from ").appendSql(geliOrm.getTableName(type)).appendSql(" where 1=1 ");

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
        		return "admin/medianews/select";
    		}
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
        req.setAttribute("total", total);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("numPerPage", numPerPage);
        req.setAttribute("orderField", orderField);
        req.setAttribute("orderDirection", orderDirection);
        req.setAttribute("list", list);
        return "admin/medianews/select";
    }

    @RequestMapping(value="/medianews/create.do", method = RequestMethod.GET)
    public String showCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        try {
        	List<SerialGroup> sgList = serialGroupService.listNormal();
            req.setAttribute("sgList", sgList);
            
            List<Object[]> areaTree = regionService.listAllProvinceAndCity();
            req.setAttribute("areaTree", areaTree);
            
            Map<String, String> mediaNewsKind = select.select("mediaNewsKind");
            req.setAttribute("mediaNewsKind", mediaNewsKind);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",MediaNews.class);
        return "admin/medianews/detail";
    }

    @RequestMapping(value="/medianews/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<MediaNews> type = MediaNews.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        MediaNews obj = geliDao.find(type, id);
        try {
        	List<SerialGroup> sgList = serialGroupService.listNormal();
            req.setAttribute("sgList", sgList);
            
            List<Object[]> areaTree = regionService.listAllProvinceAndCity();
            req.setAttribute("areaTree", areaTree);
            
            List<MediaNewsCity> citys = mediaNewsService.findMediaNewsCity(obj.getId());
            if(citys.size() == 1 && citys.get(0).getProvinceId() == 0){
            	req.setAttribute("isAllCity", true);
            } else {
            	req.setAttribute("isAllCity", false);
            }
            req.setAttribute("citys", JSONArray.toJSON(citys));
            
            List<Map<String,Object>> serialGroups = mediaNewsService.findMediaNewsSerialGroup(obj.getId());
            req.setAttribute("serialGroups", JSONArray.toJSON(serialGroups));
            
            if(serialGroups.size() == sgList.size()){
            	req.setAttribute("isAllSg", true);
            } else {
            	req.setAttribute("isAllSg", false);
            }
            
            Map<String, String> mediaNewsKind = select.select("mediaNewsKind");
            req.setAttribute("mediaNewsKind", mediaNewsKind);
		} catch (Exception e) {
			e.printStackTrace();
		}
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",MediaNews.class);
        req.setAttribute("entity", obj);
        return "admin/medianews/detail";
    }

    @RequestMapping(value="/medianews/create.do", method = RequestMethod.POST)
    public String create(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        
        Class<MediaNews> type = MediaNews.class;
        MediaNews entity = env.bind(type);
        
        String city = env.param("city","");
        String sgIds = env.param("sgIds","");
        
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
        	User user = AdminSecurity.getCurrentUser(req);
        	entity.setCreateTime(new Date());
        	entity.setCreateUserId(user.getId());
        	entity.setUpdateTime(new Date());
        	entity.setUpdateUserId(user.getId());
            geliDao.create(entity);
            //保存关联城市
            if(!"".equals(city)){
            	MediaNewsCity mediaNewsCity = null;
            	String[] cityArr = city.split(",");
            	for(int i=0;i < cityArr.length;i++){
            		String[] proCity = cityArr[i].split("-");
            		if(proCity.length == 2){
            			mediaNewsCity = new MediaNewsCity();
            			mediaNewsCity.setProvinceId(Long.valueOf(proCity[0]));
            			mediaNewsCity.setCityId(Long.valueOf(proCity[1]));
            			mediaNewsCity.setCreateTime(new Date());
            			mediaNewsCity.setUpdateTime(new Date());
            			mediaNewsCity.setMediaNewsId(entity.getId());
            			geliDao.create(mediaNewsCity);
            		}
            	}
            }
            //保存关联车系
            if(!"".equals(sgIds)){
            	String[] sgIdArr = sgIds.split(",");
            	MediaNewsSerialGroup mnsg = null;
            	for(int i=0 ;i < sgIdArr.length;i++){
            		mnsg = new MediaNewsSerialGroup();
            		mnsg.setMediaNewsId(entity.getId());
            		mnsg.setSerialGroupId(Long.valueOf(sgIdArr[i]));
            		mnsg.setCreateTime(new Date());
            		mnsg.setUpdateTime(new Date());
            		geliDao.create(mnsg);
            	}
            }
            LogFacade.log(entity);
        } catch (DuplicateKeyException ex) {
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 300)
                .put("message", "记录重复!")
                .toString()
                );
            return null;
        }
        
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(new JSONBuilder()
                .put("statusCode", 200)
                .put("message", "操作成功")
                .put("navTabId", "list-" + type.getSimpleName().toLowerCase())
                .toString());
        return null;
    }

    @RequestMapping(value="/medianews/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<MediaNews> type = cn.com.kuaidian.entity.MediaNews.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        MediaNews entity = geliDao.findDb(type, id);
        
        User user = AdminSecurity.getCurrentUser(req);
    	entity.setUpdateTime(new Date());
    	entity.setUpdateUserId(user.getId());

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
        String city = env.param("city","");
        String sgIds = env.param("sgIds","");
        mediaNewsService.deleteCityByMedisNewsId(entity.getId());
        mediaNewsService.deleteSerialGroupByMedisNewsId(entity.getId());
        //保存关联城市
        if(!"".equals(city)){
        	MediaNewsCity mediaNewsCity = null;
        	String[] cityArr = city.split(",");
        	for(int i=0;i < cityArr.length;i++){
        		String[] proCity = cityArr[i].split("-");
        		if(proCity.length == 2){
        			mediaNewsCity = new MediaNewsCity();
        			mediaNewsCity.setProvinceId(Long.valueOf(proCity[0]));
        			mediaNewsCity.setCityId(Long.valueOf(proCity[1]));
        			mediaNewsCity.setCreateTime(new Date());
        			mediaNewsCity.setUpdateTime(new Date());
        			mediaNewsCity.setMediaNewsId(entity.getId());
        			geliDao.create(mediaNewsCity);
        		}
        	}
        }
        //保存关联车系
        if(!"".equals(sgIds)){
        	String[] sgIdArr = sgIds.split(",");
        	MediaNewsSerialGroup mnsg = null;
        	for(int i=0 ;i < sgIdArr.length;i++){
        		mnsg = new MediaNewsSerialGroup();
        		mnsg.setMediaNewsId(entity.getId());
        		mnsg.setSerialGroupId(Long.valueOf(sgIdArr[i]));
        		mnsg.setCreateTime(new Date());
        		mnsg.setUpdateTime(new Date());
        		geliDao.create(mnsg);
        	}
        }
        
        LogFacade.log(entity);
        geliDao.update(entity);
        
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 200)
                .put("message", "操作成功")
                .put("navTabId", "list-" + type.getSimpleName().toLowerCase())
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/medianews/shiftStatus.do", method = RequestMethod.POST)
    public String shiftStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<MediaNews> type = MediaNews.class;
        try {
        	int status = env.paramInt("status",0);
        	long id = env.paramLong("id",-1);
            MediaNews entity = geliDao.findDb(type, id);
            entity.setStatus(status);
            User user = AdminSecurity.getCurrentUser(req);
        	entity.setUpdateTime(new Date());
        	entity.setUpdateUserId(user.getId());

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
                .put("navTabId", "list-" + type.getSimpleName().toLowerCase())
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/medianews/delete.do", method = RequestMethod.POST)
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
        Class<MediaNews> type = MediaNews.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        MediaNews entity = geliDao.find(type, id);
        
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
    	LogFacade.log(entity);
    	geliDao.delete(entity, id);
    	
    	mediaNewsService.deleteCityByMedisNewsId(entity.getId());
    	mediaNewsService.deleteSerialGroupByMedisNewsId(entity.getId());
    	
    	
    	resp.setCharacterEncoding("UTF-8");
    	resp.getWriter().println(
    			new JSONBuilder()
    			.put("statusCode", 200)
    			.put("message", "操作成功")
    			.put("navTabId", "list-" + type.getSimpleName().toLowerCase())
    			.toString()
    			);
    	
    	return null;
    }
    
    
    @RequestMapping(value="/medianews/recommend.do", method = RequestMethod.GET)
    public String showRecommend(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<MediaNews> type = MediaNews.class;
        Object id = geliOrm.parseObjectId(type, env.param("id"));
        MediaNews obj = geliDao.find(type, id);
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",MediaNews.class);
        req.setAttribute("entity", obj);
        return "admin/medianews/recommend";
    }
    
    
    @RequestMapping(value="/medianews/recommend.do", method = RequestMethod.POST)
    public String recommend(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
        Class<MediaNews> type = MediaNews.class;
    	try {
            Object id = geliOrm.parseObjectId(type, 
                    env.param(geliOrm.getKeyField(type)));

            MediaNews entity = geliDao.findDb(type, id);
            
            User user = AdminSecurity.getCurrentUser(req);
        	entity.setUpdateTime(new Date());
        	entity.setUpdateUserId(user.getId());

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
                .put("navTabId", "list-" + type.getSimpleName().toLowerCase())
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
    
    
    @RequestMapping(value="/medianews/showDetail.do", method = RequestMethod.GET)
    public String showDetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<MediaNews> type = MediaNews.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        MediaNews obj = geliDao.find(type, id);
        try {
            List<Map<String,Object>> serialGroups = mediaNewsService.findMediaNewsSerialGroup(obj.getId());
            req.setAttribute("serialGroups", serialGroups);
            List<Map<String,Object>> citys = mediaNewsService.listMediaNewsCityName(obj.getId());
            req.setAttribute("citys", citys);
		} catch (Exception e) {
			e.printStackTrace();
		}
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",MediaNews.class);
        req.setAttribute("entity", obj);
        return "admin/medianews/detailshow";
    }
}


