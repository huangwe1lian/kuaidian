package cn.com.fawtoyota.web.admin;

import cn.com.fawtoyota.entity.Ad;
import cn.com.fawtoyota.entity.AdCity;
import cn.com.fawtoyota.entity.AdOrderform;
import cn.com.fawtoyota.entity.AdSerialGroup;
import cn.com.fawtoyota.entity.MediaNewsCity;
import cn.com.fawtoyota.entity.MediaNewsSerialGroup;
import cn.com.fawtoyota.entity.OrderCode;
import cn.com.fawtoyota.entity.Role;
import cn.com.fawtoyota.entity.SerialGroup;
import cn.com.fawtoyota.entity.TemplateAd;
import cn.com.fawtoyota.entity.TemplateAdCity;
import cn.com.fawtoyota.entity.TemplateAdOrderform;
import cn.com.fawtoyota.entity.User;
import cn.com.fawtoyota.resource.auth.AdminSecurity;
import cn.com.fawtoyota.resource.log.LogFacade;
import cn.com.fawtoyota.service.admin.AdService;
import cn.com.fawtoyota.service.admin.OrderCodeService;
import cn.com.fawtoyota.service.admin.RegionService;
import cn.com.fawtoyota.service.admin.UserService;
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
import org.gelivable.dao.Select;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.dao.ValueSetter;
import org.gelivable.log.GeliLogFacade;
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

@Controller
@RequestMapping("/admin/ad/manufac")
public class ManufacAdController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;
    
    @Autowired
    AdService adService;
    
    @Autowired
    Select select;
    
    @Autowired
    SerialGroupService serialGroupService;
    
    @Autowired
    OrderCodeService orderCodeService;
    
    @Autowired
    RegionService regionService;
    
    @Autowired
    UserService userService;

    @RequestMapping(value="/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type= Ad.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
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
        		return "admin/ad/manufac/list";
    		}
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
        
        User user = AdminSecurity.getCurrentUser(req);
        List<Role> roles = userService.findRoleByUserId(user.getId());
        int topTyp = 99;
        for(Role r : roles){
        	if(topTyp > r.getTyp()){
        		topTyp = r.getTyp();
        	}
        }
        
        req.setAttribute("_p", _p);
        req.setAttribute("total", total);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("numPerPage", numPerPage);
        req.setAttribute("orderField", orderField);
        req.setAttribute("orderDirection", orderDirection);
        req.setAttribute("lastYear", DateUtils.format(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd 00:00:00"));
        req.setAttribute("list", list);
        req.setAttribute("topTyp", topTyp);
        return "admin/ad/manufac/list";
    }

    @RequestMapping(value="/select.do")
    public String select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type= Ad.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection"));
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
        		return "admin/ad/manufac/list";
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
        return "admin/ad/manufac/select";
    }

    @RequestMapping(value="/create.do", method = RequestMethod.GET)
    public String showCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        
        try {
        	List<SerialGroup> sgList = serialGroupService.listNormal();
            req.setAttribute("sgList", sgList);
            
            List<Object[]> areaTree = regionService.listAllProvinceAndCity();
            req.setAttribute("areaTree", areaTree);
		} catch (Exception e) {
			e.printStackTrace();
		}
        req.setAttribute("type", env.param("type"));
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",Ad.class);
        return "admin/ad/manufac/detail";
    }
    
    @RequestMapping(value="/showdetail.do", method = RequestMethod.GET)
    public String showDetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Ad obj = geliDao.find(type, id);
    	
    	try {
    		List<Map<String,Object>> serialGroups = adService.findAdSerialGroup(obj.getId());
            req.setAttribute("serialGroups", serialGroups);
            
            List<Map<String,Object>> citys = adService.listAdCityName(obj.getId(),AdCity.class,"ad_id");
            req.setAttribute("citys", citys);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	req.setAttribute("type", env.param("type"));
    	req.setAttribute("method", "showDetail");
    	req.setAttribute("domain_clazz",Ad.class);
    	req.setAttribute("entity", obj);
    	return "admin/ad/manufac/detailshow";
    }
    
    @RequestMapping(value="/audit.do", method = RequestMethod.GET)
    public String showAudit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
    	req.setAttribute("method", "audit");
    	req.setAttribute("domain_clazz",Ad.class);
    	req.setAttribute("entity", obj);
    	return "admin/ad/manufac/audit";
    }

    @RequestMapping(value="/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Ad obj = geliDao.find(type, id);
        
        try {
        	List<SerialGroup> sgList = serialGroupService.listNormal();
            req.setAttribute("sgList", sgList);
            
            List<Object[]> areaTree = regionService.listAllProvinceAndCity();
            req.setAttribute("areaTree", areaTree);
            
            List<AdCity> citys = adService.findAdCity(obj.getId());
            if(citys.size() == 1 && citys.get(0).getProvinceId() == 0){
            	req.setAttribute("isAllCity", true);
            } else {
            	req.setAttribute("isAllCity", false);
            }
            req.setAttribute("citys", JSONArray.toJSON(citys));
            
            List<Map<String,Object>> serialGroups = adService.findAdSerialGroup(obj.getId());
            req.setAttribute("serialGroups", JSONArray.toJSON(serialGroups));
            
            if(serialGroups.size() == sgList.size()){
            	req.setAttribute("isAllSg", true);
            } else {
            	req.setAttribute("isAllSg", false);
            }
            
            AdOrderform orderform = adService.findAdOrderform(obj.getId());
            req.setAttribute("orderform", orderform);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",Ad.class);
        req.setAttribute("entity", obj);
        return "admin/ad/manufac/detail";
    }

    @RequestMapping(value="/create.do", method = RequestMethod.POST)
    public String create(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        
        String city = env.param("city","");
        String sgIds = env.param("sgIds","");
        String aname = env.param("aname","");
        
        Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        Ad entity = env.bind(type);
        entity.setName(aname);
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
        	entity.setCreateUserId(user.getId());
        	entity.setCreateTime(new Date());
        	
        	OrderCode orderCode = new OrderCode();
        	orderCode.setOrderCode(orderCodeService.createOrderCode(4));
        	orderCode.setDescription(entity.getName());
        	orderCode.setCreateTime(new Date());
        	orderCode.setUpdateTime(new Date());
        	orderCode.setCreateTime(new Date());
        	orderCode.setUpdateTime(new Date());
        	orderCode.setStatus(1);
        	orderCode.setCreateTime(new Date());
        	orderCode.setCreateUserId(user.getId());
        	geliDao.create(orderCode);
        	
        	entity.setOrderCode(orderCode.getOrderCode());
        	entity.setUpdateTime(new Date());
        	entity.setUpdateUserId(user.getId());
            geliDao.create(entity);
            
            //保存关联城市
            if(!"".equals(city)){
            	AdCity adCity = null;
            	String[] cityArr = city.split(",");
            	for(int i=0;i < cityArr.length;i++){
            		String[] proCity = cityArr[i].split("-");
            		if(proCity.length == 2){
            			adCity = new AdCity();
            			adCity.setProvinceId(Long.valueOf(proCity[0]));
            			adCity.setCityId(Long.valueOf(proCity[1]));
            			adCity.setCreateTime(new Date());
            			adCity.setUpdateTime(new Date());
            			adCity.setAdId(entity.getId());
            			geliDao.create(adCity);
            		}
            	}
            }
            //保存关联车系
            if(!"".equals(sgIds)){
            	String[] sgIdArr = sgIds.split(",");
            	AdSerialGroup adsg = null;
            	for(int i=0 ;i < sgIdArr.length;i++){
            		adsg = new AdSerialGroup();
            		adsg.setAdId(entity.getId());
            		adsg.setSerialGroupId(Long.valueOf(sgIdArr[i]));
            		adsg.setCreateTime(new Date());
            		adsg.setUpdateTime(new Date());
            		geliDao.create(adsg);
            	}
            }
            if(entity.getType() == 2 && entity.getIsOrder() == 1){
            	Class<AdOrderform> type2 = cn.com.fawtoyota.entity.AdOrderform.class;
                AdOrderform orderform = env.bind(type2);
                int oname = env.paramInt("oname",0);
                orderform.setName(oname);
            	orderform.setAdId(entity.getId());
                orderform.setCreateTime(new Date());
                geliDao.create(orderform);
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
                .put("navTabId", "list-ad-manufac")
                .toString());
        return null;
    }

    @RequestMapping(value="/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        Ad entity = geliDao.findDb(type, id);
        
        String city = env.param("city","");
        String sgIds = env.param("sgIds","");
        String aname = env.param("aname","");

        env.bind(type, entity);
        entity.setName(aname);
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
        
        //保存关联城市
        adService.deleteCityByAdId(entity.getId());
        if(!"".equals(city)){
        	AdCity adCity = null;
        	String[] cityArr = city.split(",");
        	for(int i=0;i < cityArr.length;i++){
        		String[] proCity = cityArr[i].split("-");
        		if(proCity.length == 2){
        			adCity = new AdCity();
        			adCity.setProvinceId(Long.valueOf(proCity[0]));
        			adCity.setCityId(Long.valueOf(proCity[1]));
        			adCity.setCreateTime(new Date());
        			adCity.setUpdateTime(new Date());
        			adCity.setAdId(entity.getId());
        			geliDao.create(adCity);
        		}
        	}
        }
        //保存关联车系
        adService.deleteSerialGroupByAdId(entity.getId());
        if(!"".equals(sgIds)){
        	String[] sgIdArr = sgIds.split(",");
        	AdSerialGroup adsg = null;
        	for(int i=0 ;i < sgIdArr.length;i++){
        		adsg = new AdSerialGroup();
        		adsg.setAdId(entity.getId());
        		adsg.setSerialGroupId(Long.valueOf(sgIdArr[i]));
        		adsg.setCreateTime(new Date());
        		adsg.setUpdateTime(new Date());
        		geliDao.create(adsg);
        	}
        }
        
        if(entity.getType() == 2 && entity.getIsOrder() == 1){
        	adService.deleteOrderformByAdId(entity.getId());
        	AdOrderform orderform = new AdOrderform();
        	int oname = env.paramInt("oname",0);
        	env.bind(AdOrderform.class, orderform);
        	orderform.setAdId(entity.getId());
        	orderform.setName(oname);
        	orderform.setCreateTime(new Date());
        	orderform.setUpdateTime(new Date());
        	geliDao.create(orderform);
        }
        
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 200)
                .put("message", "操作成功")
                .put("navTabId", "list-ad-manufac")
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/delete.do", method = RequestMethod.POST)
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
        Class<Ad> type = Ad.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Ad entity = geliDao.find(type, id);
        
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
    	
    	adService.deleteCityByAdId(entity.getId());
    	adService.deleteSerialGroupByAdId(entity.getId());
    	adService.deleteOrderformByAdId(entity.getId());
    	
    	
    	resp.setCharacterEncoding("UTF-8");
    	resp.getWriter().println(
    			new JSONBuilder()
    			.put("statusCode", 200)
    			.put("message", "操作成功")
    			.put("navTabId", "list-ad-manufac")
    			.toString()
    			);
    	
    	return null;
    }
    
    @RequestMapping(value="/audit.do", method = RequestMethod.POST)
    public String audit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        Ad entity = geliDao.findDb(type, id);

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
        	User user = AdminSecurity.getCurrentUser(req);
        	entity.setAuditTime(new Date());
        	entity.setAuditUserId(user.getId());
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
                .put("navTabId", "list-ad-manufac")
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/shiftStatus.do", method = RequestMethod.POST)
    public String shiftStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        Ad entity = geliDao.findDb(type, id);

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
        	if(entity.getStatus() == 2){
        		Date now = new Date();
        		if(DateUtils.compareDay(entity.getBeginTime(), now) < 0) {
        			entity.setStatus(1);
        		} else if (DateUtils.compareDay(entity.getEndTime(), now) > 0){
        			entity.setStatus(4);
        		}
        	}
        	
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
                .put("navTabId", "list-ad-manufac")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/updateSeq.do", method = RequestMethod.POST)
    public String updateSeq(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        Ad entity = geliDao.findDb(type, id);

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
                .put("navTabId", "list-ad-manufac")
                .toString()
                );

        return null;
    }
    
    
    @RequestMapping(value="/changetype.do", method = RequestMethod.GET)
    public String showChangeType(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	req.setAttribute("method", "changetype");
    	req.setAttribute("domain_clazz",Ad.class);
    	return "admin/ad/manufac/changetype";
    }
    
    @RequestMapping(value="/reason.do", method = RequestMethod.GET)
    public String showReason(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	Class<Ad> type = cn.com.fawtoyota.entity.Ad.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
    	req.setAttribute("method", "reason");
    	req.setAttribute("domain_clazz",Ad.class);
    	req.setAttribute("entity", obj);
    	return "admin/ad/manufac/reason";
    }
    
    @RequestMapping(value="/copycreate.do", method = RequestMethod.GET)
    public String copyCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        TemplateAd obj = geliDao.find(type, id);
        
        try {
        	List<SerialGroup> sgList = serialGroupService.listNormal();
            req.setAttribute("sgList", sgList);
            
            List<Object[]> areaTree = regionService.listAllProvinceAndCity();
            req.setAttribute("areaTree", areaTree);
            
            List<TemplateAdCity> citys = adService.findTemplateAdCity(obj.getId());
            if(citys.size() == 1 && citys.get(0).getProvinceId() == 0){
            	req.setAttribute("isAllCity", true);
            } else {
            	req.setAttribute("isAllCity", false);
            }
            req.setAttribute("citys", JSONArray.toJSON(citys));
            
            List<Map<String,Object>> serialGroups = adService.findTemplateAdSerialGroup(obj.getId());
            req.setAttribute("serialGroups", JSONArray.toJSON(serialGroups));
            
            if(serialGroups.size() == sgList.size()){
            	req.setAttribute("isAllSg", true);
            } else {
            	req.setAttribute("isAllSg", false);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        req.setAttribute("method", "create");
        req.setAttribute("type", 2);
        req.setAttribute("domain_clazz",Ad.class);
        req.setAttribute("copy", obj);
        return "admin/ad/manufac/detail";
    }
}


