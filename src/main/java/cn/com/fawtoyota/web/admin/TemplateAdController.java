package cn.com.fawtoyota.web.admin;

import cn.com.fawtoyota.entity.Model;
import cn.com.fawtoyota.entity.TemplateAd;
import cn.com.fawtoyota.entity.TemplateAdCity;
import cn.com.fawtoyota.entity.TemplateAdDetail;
import cn.com.fawtoyota.entity.TemplateAdModel;
import cn.com.fawtoyota.entity.TemplateAdOrderform;
import cn.com.fawtoyota.entity.TemplateAdSerialGroup;
import cn.com.fawtoyota.entity.OrderCode;
import cn.com.fawtoyota.entity.SerialGroup;
import cn.com.fawtoyota.entity.User;
import cn.com.fawtoyota.resource.auth.AdminSecurity;
import cn.com.fawtoyota.resource.log.LogFacade;
import cn.com.fawtoyota.service.admin.AdService;
import cn.com.fawtoyota.service.admin.OrderCodeService;
import cn.com.fawtoyota.service.admin.RegionService;
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
@RequestMapping("/admin/ad/template")
public class TemplateAdController {

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

    @RequestMapping(value="/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type= TemplateAd.class;
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
        		return "admin/ad/template/list";
    		}
    	}
        
        long sgId = env.paramLong("sgId",0l);
        if(sgId > 0){
        	sql.appendSql(" and exists(select 1 from ft_template_ad_serial_group sg where sg.template_ad_id=t.id and sg.serial_group_id=").appendValue(sgId).appendSql(") ");
        	sqlCount.appendSql(" and exists(select 1 from ft_template_ad_serial_group sg where sg.template_ad_id=t.id and sg.serial_group_id=").appendValue(sgId).appendSql(") ");
        }
        
        long codeId = env.paramLong("codeId",0l);
        if(sgId > 0){
        	sql.appendSql(" and exists(select 1 from ft_order_code c where c.order_code=t.order_code and c.id=").appendValue(codeId).appendSql(") ");
        	sqlCount.appendSql(" and exists(select 1 from ft_order_code c where c.order_code=t.order_code and c.id=").appendValue(sgId).appendSql(") ");
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
        req.setAttribute("lastYear", DateUtils.format(DateUtils.addYears(new Date(), -1), "yyyy-MM-dd 00:00:00"));
        req.setAttribute("list", list);
        return "admin/ad/template/list";
    }

    @RequestMapping(value="/select.do")
    public String select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type= TemplateAd.class;
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
        		return "admin/ad/template/list";
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
        return "admin/ad/template/select";
    }

    @RequestMapping(value="/createbasis.do", method = RequestMethod.GET)
    public String showCreatebasis(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        
        try {
        	List<SerialGroup> sgList = serialGroupService.listNormal();
            req.setAttribute("sgList", sgList);
            
            List<Object[]> areaTree = regionService.listAllProvinceAndCity();
            req.setAttribute("areaTree", areaTree);
            
            Map<String, List<Model>> modelMap = serialGroupService.getAllModleGroupBySg();
            req.setAttribute("modelMap", JSONArray.toJSON(modelMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",TemplateAd.class);
        return "admin/ad/template/detailbasis";
    }
    
    @RequestMapping(value="/showdetail.do", method = RequestMethod.GET)
    public String showDetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
    		
    		List<Map<String,Object>> serialGroups = adService.findTemplateAdSerialGroup(obj.getId());
            req.setAttribute("serialGroups", JSONArray.toJSON(serialGroups));
            
            Map<String, List<Model>> modelMap = serialGroupService.getAllModleGroupBySg();
            req.setAttribute("modelMap", JSONArray.toJSON(modelMap));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	req.setAttribute("method", "showDetail");
    	req.setAttribute("domain_clazz",TemplateAd.class);
    	req.setAttribute("entity", obj);
    	return "admin/ad/template/detailshow";
    }
    
    @RequestMapping(value="/audit.do", method = RequestMethod.GET)
    public String showAudit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
    	req.setAttribute("method", "audit");
    	req.setAttribute("domain_clazz",TemplateAd.class);
    	req.setAttribute("entity", obj);
    	return "admin/ad/template/audit";
    }

    @RequestMapping(value="/updatebasis.do", method = RequestMethod.GET)
    public String showUpdatebasis(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
            
            List<TemplateAdModel> models = adService.findTemplateAdModel(obj.getId());
            req.setAttribute("models", JSONArray.toJSON(models));
            
            TemplateAdOrderform orderform = adService.findTemplateAdOrderform(obj.getId());
            req.setAttribute("orderform", orderform);
            
            Map<String, List<Model>> modelMap = serialGroupService.getAllModleGroupBySg();
            req.setAttribute("modelMap", JSONArray.toJSON(modelMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",TemplateAd.class);
        req.setAttribute("entity", obj);
        return "admin/ad/template/detailbasis";
    }

    @RequestMapping(value="/createbasis.do", method = RequestMethod.POST)
    public String createbasis(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        
        String city = env.param("city","");
        String sgIds = env.param("sgIds","");
        String mIds = env.param("mIds","");
        String aname = env.param("aname","");
        
        Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        TemplateAd entity = env.bind(type);
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
            	TemplateAdCity adCity = null;
            	String[] cityArr = city.split(",");
            	for(int i=0;i < cityArr.length;i++){
            		String[] proCity = cityArr[i].split("-");
            		if(proCity.length == 2){
            			adCity = new TemplateAdCity();
            			adCity.setProvinceId(Long.valueOf(proCity[0]));
            			adCity.setCityId(Long.valueOf(proCity[1]));
            			adCity.setCreateTime(new Date());
            			adCity.setUpdateTime(new Date());
            			adCity.setTemplateAdId(entity.getId());
            			geliDao.create(adCity);
            		}
            	}
            }
            //保存关联车系
            if(!"".equals(sgIds)){
            	String[] sgIdArr = sgIds.split(",");
            	TemplateAdSerialGroup adsg = null;
            	for(int i=0 ;i < sgIdArr.length;i++){
            		adsg = new TemplateAdSerialGroup();
            		adsg.setTemplateAdId(entity.getId());
            		adsg.setSerialGroupId(Long.valueOf(sgIdArr[i]));
            		adsg.setCreateTime(new Date());
            		adsg.setUpdateTime(new Date());
            		geliDao.create(adsg);
            	}
            }
            
            //保存关联车型
            if(!"".equals(mIds)){
            	TemplateAdModel adModel = null;
            	String[] mIdsArr = mIds.split(",");
            	for(int i=0;i < mIdsArr.length;i++){
            		String[] sgm = mIdsArr[i].split("-");
            		if(sgm.length == 2){
            			adModel = new TemplateAdModel();
            			adModel.setSerialGroupId(Long.valueOf(sgm[0]));
            			adModel.setModelId(Long.valueOf(sgm[1]));
            			adModel.setCreateTime(new Date());
            			adModel.setUpdateTime(new Date());
            			adModel.setTemplateAdId(entity.getId());
            			geliDao.create(adModel);
            		}
            	}
            }
            
        	Class<TemplateAdOrderform> type2 = cn.com.fawtoyota.entity.TemplateAdOrderform.class;
            TemplateAdOrderform orderform = env.bind(type2);
            int oname = env.paramInt("oname",0);
            orderform.setName(oname);
        	orderform.setTemplateAdId(entity.getId());
            orderform.setCreateTime(new Date());
            geliDao.create(orderform);
            
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
                .put("adId", entity.getId())
                .toString());
        return null;
    }

    @RequestMapping(value="/updatebasis.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        TemplateAd entity = geliDao.findDb(type, id);
        
        String city = env.param("city","");
        String sgIds = env.param("sgIds","");
        String aname = env.param("aname","");
        String mIds = env.param("mIds","");

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
        adService.deleteCityByTemplateAdId(entity.getId());
        if(!"".equals(city)){
        	TemplateAdCity adCity = null;
        	String[] cityArr = city.split(",");
        	for(int i=0;i < cityArr.length;i++){
        		String[] proCity = cityArr[i].split("-");
        		if(proCity.length == 2){
        			adCity = new TemplateAdCity();
        			adCity.setProvinceId(Long.valueOf(proCity[0]));
        			adCity.setCityId(Long.valueOf(proCity[1]));
        			adCity.setCreateTime(new Date());
        			adCity.setUpdateTime(new Date());
        			adCity.setTemplateAdId(entity.getId());
        			geliDao.create(adCity);
        		}
        	}
        }
        //保存关联车系
        adService.deleteSerialGroupByTemplateAdId(entity.getId());
        if(!"".equals(sgIds)){
        	String[] sgIdArr = sgIds.split(",");
        	TemplateAdSerialGroup adsg = null;
        	for(int i=0 ;i < sgIdArr.length;i++){
        		adsg = new TemplateAdSerialGroup();
        		adsg.setTemplateAdId(entity.getId());
        		adsg.setSerialGroupId(Long.valueOf(sgIdArr[i]));
        		adsg.setCreateTime(new Date());
        		adsg.setUpdateTime(new Date());
        		geliDao.create(adsg);
        	}
        }
        
        //保存关联车型
        adService.deleteModelByTemplateAdId(entity.getId());
        if(!"".equals(mIds)){
        	TemplateAdModel adModel = null;
        	String[] mIdsArr = mIds.split(",");
        	for(int i=0;i < mIdsArr.length;i++){
        		String[] sgm = mIdsArr[i].split("-");
        		if(sgm.length == 2){
        			adModel = new TemplateAdModel();
        			adModel.setSerialGroupId(Long.valueOf(sgm[0]));
        			adModel.setModelId(Long.valueOf(sgm[1]));
        			adModel.setCreateTime(new Date());
        			adModel.setUpdateTime(new Date());
        			adModel.setTemplateAdId(entity.getId());
        			geliDao.create(adModel);
        		}
        	}
        }
        
    	adService.deleteOrderformByTemplateAdId(entity.getId());
    	TemplateAdOrderform orderform = new TemplateAdOrderform();
    	int oname = env.paramInt("oname",0);
    	env.bind(TemplateAdOrderform.class, orderform);
    	orderform.setTemplateAdId(entity.getId());
    	orderform.setName(oname);
    	orderform.setCreateTime(new Date());
    	orderform.setUpdateTime(new Date());
    	geliDao.create(orderform);
        
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 200)
                .put("message", "操作成功")
                .put("navTabId", "list-ad-template")
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/delete.do", method = RequestMethod.POST)
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
        Class<TemplateAd> type = TemplateAd.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        TemplateAd entity = geliDao.find(type, id);
        
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
    	
    	adService.deleteCityByTemplateAdId(entity.getId());
    	adService.deleteSerialGroupByTemplateAdId(entity.getId());
    	adService.deleteOrderformByTemplateAdId(entity.getId());
    	adService.deleteModelByTemplateAdId(entity.getId());
    	
    	resp.setCharacterEncoding("UTF-8");
    	resp.getWriter().println(
    			new JSONBuilder()
    			.put("statusCode", 200)
    			.put("message", "操作成功")
    			.put("navTabId", "list-ad-template")
    			.toString()
    			);
    	
    	return null;
    }
    
    @RequestMapping(value="/audit.do", method = RequestMethod.POST)
    public String audit(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        TemplateAd entity = geliDao.findDb(type, id);

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
                .put("navTabId", "list-ad-template")
                .put("callbackType", "closeCurrent")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/shiftStatus.do", method = RequestMethod.POST)
    public String shiftStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        TemplateAd entity = geliDao.findDb(type, id);

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
                .put("navTabId", "list-ad-template")
                .toString()
                );

        return null;
    }
    
    @RequestMapping(value="/updateSeq.do", method = RequestMethod.POST)
    public String updateSeq(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<TemplateAd> type = cn.com.fawtoyota.entity.TemplateAd.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        TemplateAd entity = geliDao.findDb(type, id);

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
                .put("navTabId", "list-ad-template")
                .toString()
                );

        return null;
    }
    
    
    @RequestMapping(value="/createdetail.do", method = RequestMethod.GET)
    public String showCreatedetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        
        req.setAttribute("adId", env.param("adId"));
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",TemplateAd.class);
        return "admin/ad/template/detailcontent";
    }
    
    @RequestMapping(value="/updatedetail.do", method = RequestMethod.GET)
    public String showUpdatedetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	Env env = EnvUtils.getEnv();
    	long adId = env.paramLong("adId",-1l);
    	TemplateAdDetail detail = adService.findTemplateAdDetail(adId);
    	
    	req.setAttribute("entity", detail);
    	req.setAttribute("adId", env.param("adId"));
    	req.setAttribute("method", "update");
    	req.setAttribute("domain_clazz",TemplateAd.class);
    	return "admin/ad/template/detailcontent";
    }
    
    
    @RequestMapping(value="/createdetail.do", method = RequestMethod.POST)
    public String createdetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();

        Class<TemplateAdDetail> type = cn.com.fawtoyota.entity.TemplateAdDetail.class;
        TemplateAdDetail entity = env.bind(type);
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
                .put("navTabId", "list-ad-template")
                .put("callbackType", "closeCurrent")
                .toString());
        return null;
    }
    
    @RequestMapping(value="/updatedetail.do", method = RequestMethod.POST)
    public String updatedetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	
    	Env env = EnvUtils.getEnv();
    	
    	long id = env.paramLong("id",-1l);
    	long templateAdId = env.paramLong("templateAdId",-1l);
    	if(templateAdId < 0){
    		resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(
                new JSONBuilder()
                .put("statusCode", 300)
                .put("message", "关联模板ID不能为空")
                .toString()
                );
            return null;
    	}
    	
    	try {
    		Class<TemplateAdDetail> type = cn.com.fawtoyota.entity.TemplateAdDetail.class;
    		if(id > 0){
                TemplateAdDetail entity = geliDao.findDb(type, id);
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
    		} else {
    			TemplateAdDetail entity = new TemplateAdDetail();
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
                
                geliDao.create(entity);
        		LogFacade.log(entity);
    		}
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
    	.put("navTabId", "list-ad-template")
    	.put("callbackType", "closeCurrent")
    	.toString());
    	return null;
    }
    
    @RequestMapping(value="/copybasis.do", method = RequestMethod.GET)
    public String showCopybasis(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
            
            List<TemplateAdModel> models = adService.findTemplateAdModel(obj.getId());
            req.setAttribute("models", JSONArray.toJSON(models));
            
            TemplateAdOrderform orderform = adService.findTemplateAdOrderform(obj.getId());
            req.setAttribute("orderform", orderform);
            
            Map<String, List<Model>> modelMap = serialGroupService.getAllModleGroupBySg();
            req.setAttribute("modelMap", JSONArray.toJSON(modelMap));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",TemplateAd.class);
        req.setAttribute("entity", obj);
        return "admin/ad/template/detailbasis";
    }
}


