package cn.com.kuaidian.web.admin;

import java.util.Date;
import java.util.List;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;

import cn.com.kuaidian.entity.Role;
import cn.com.kuaidian.entity.RoleCity;
import cn.com.kuaidian.resource.log.LogFacade;
import cn.com.kuaidian.service.admin.RegionService;
import cn.com.kuaidian.service.admin.UserService;

/** 
 * 
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-5-30 下午4:17:20
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;
    
    @Autowired
    RegionService regionService;
    
    @Autowired
    UserService userService;

    @RequestMapping(value="/role/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Role> type= Role.class;
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
        		return "admin/function/list";
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
        return "admin/user/role/list";
    }

    @RequestMapping(value="/role/select.do")
    public String select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Role> type= Role.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection"));
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
        		return "admin/function/list";
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
        return "admin/user/role/select";
    }

    @RequestMapping(value="/role/create.do", method = RequestMethod.GET)
    public String showCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        List<Object[]> areaTree = regionService.listAllProvinceAndCity();
        req.setAttribute("areaTree", areaTree);
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",Role.class);
        return "admin/user/role/detail";
    }

    @RequestMapping(value="/role/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Role> type = Role.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Role obj = geliDao.find(type, id);
        List<Object[]> areaTree = regionService.listAllProvinceAndCity();
        req.setAttribute("areaTree", areaTree);
        
        List<RoleCity> citys = userService.findRoleCity(obj.getId());
        if(citys != null && citys.size() == 1 && citys.get(0).getProvinceId() == 0){
        	req.setAttribute("isAllCity", true);
        } else {
        	req.setAttribute("isAllCity", false);
        }
        req.setAttribute("citys", JSONArray.toJSON(citys));
        
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",Role.class);
        req.setAttribute("entity", obj);
        return "admin/user/role/detail";
    }

    @RequestMapping(value="/role/create.do", method = RequestMethod.POST)
    public String create(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        
        String city = env.param("city","");
        
        Class<Role> type = Role.class;
        Role entity = env.bind(type);
        entity.setCreateTime(new Date());
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
            geliDao.create(entity);
            
            //保存关联城市
            if(!"".equals(city)){
            	RoleCity rc = null;
            	String[] cityArr = city.split(",");
            	for(int i=0;i < cityArr.length;i++){
            		String[] proCity = cityArr[i].split("-");
            		if(proCity.length == 2){
            			rc = new RoleCity();
            			rc.setProvinceId(Long.valueOf(proCity[0]));
            			rc.setCityId(Long.valueOf(proCity[1]));
            			rc.setCreateTime(new Date());
            			rc.setUpdateTime(new Date());
            			rc.setRoleId(entity.getId());
            			geliDao.create(rc);
            		}
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

    @RequestMapping(value="/role/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        
        String city = env.param("city","");
        
        Class<Role> type = Role.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        Role entity = geliDao.findDb(type, id);
        entity.setUpdateTime(new Date());
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
        
        userService.deleteCityByRoleId(entity.getId());
        //保存关联城市
        if(!"".equals(city)){
        	RoleCity rc = null;
        	String[] cityArr = city.split(",");
        	for(int i=0;i < cityArr.length;i++){
        		String[] proCity = cityArr[i].split("-");
        		if(proCity.length == 2){
        			rc = new RoleCity();
        			rc.setProvinceId(Long.valueOf(proCity[0]));
        			rc.setCityId(Long.valueOf(proCity[1]));
        			rc.setCreateTime(new Date());
        			rc.setUpdateTime(new Date());
        			rc.setRoleId(entity.getId());
        			geliDao.create(rc);
        		}
        	}
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
    
    @RequestMapping(value="/role/delete.do", method = RequestMethod.POST)
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
        Class<Role> type = Role.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Role entity = geliDao.find(type, id);
        
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
    	userService.deleteCityByRoleId(entity.getId());
    	geliDao.delete(entity, id);
    	
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
}


