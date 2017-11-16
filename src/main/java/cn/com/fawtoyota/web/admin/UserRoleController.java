package cn.com.fawtoyota.web.admin;

import cn.com.fawtoyota.entity.UserRole;
import cn.com.fawtoyota.resource.log.LogFacade;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
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

@Controller
@RequestMapping("/admin")
public class UserRoleController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;

    @RequestMapping(value="/userrole/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<UserRole> type= UserRole.class;
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
        		return "admin/user/userrole/list";
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
        return "admin/user/userrole/list";
    }

    @RequestMapping(value="/userrole/select.do")
    public String select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<UserRole> type= UserRole.class;
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
        		return "admin/user/userrole/list";
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
        return "admin/user/userrole/select";
    }

    @RequestMapping(value="/userrole/create.do", method = RequestMethod.GET)
    public String showCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",UserRole.class);
        return "admin/user/userrole/detail";
    }

    @RequestMapping(value="/userrole/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<UserRole> type = cn.com.fawtoyota.entity.UserRole.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",UserRole.class);
        req.setAttribute("entity", obj);
        return "admin/user/userrole/detail";
    }

    @RequestMapping(value="/userrole/create.do", method = RequestMethod.POST)
    public String create(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        
        Class<UserRole> type = cn.com.fawtoyota.entity.UserRole.class;
        UserRole entity = env.bind(type);
        entity.setRoleId(env.paramLong( "role.roleId", 0L));
        entity.setUserId(env.paramLong( "user.userId", 0L));
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

    @RequestMapping(value="/userrole/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<UserRole> type = cn.com.fawtoyota.entity.UserRole.class;
        Object id = geliOrm.parseObjectId(type, env.param(geliOrm.getKeyField(type)));

        UserRole entity = geliDao.findDb(type, id);
        
        entity.setRoleId(env.paramLong( "role.roleId", 0L));
        entity.setUserId(env.paramLong( "user.userId", 0L));
        
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
    
    @RequestMapping(value="/userrole/delete.do", method = RequestMethod.POST)
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
        Class<UserRole> type = UserRole.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        UserRole entity = geliDao.find(type, id);
        
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

