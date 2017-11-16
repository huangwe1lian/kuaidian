package cn.com.kuaidian.web.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.dao.ValueSetter;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.gelivable.web.GeliFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.kuaidian.entity.Dealer;
import cn.com.kuaidian.entity.User;
import cn.com.kuaidian.resource.auth.AdminAuthFacade;
import cn.com.kuaidian.resource.auth.AdminSecurity;
import cn.com.kuaidian.service.admin.UserService;
import cn.com.kuaidian.util.StringUtils;
import cn.com.kuaidian.util.dwz.DwzUtils;

@Controller
public class DealerController {
	@Autowired
	GeliOrm geliOrm;

	@Autowired
	GeliDao geliDao;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/admin/dealer/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response) {
		Env env = EnvUtils.getEnv();
		Class<Dealer> type= Dealer.class;
		String orderField = geliOrm.ensureField(type, env.param("orderField"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection"));
		int pageNum = env.paramInt("pageNum", 1);
		int numPerPage = env.paramInt("numPerPage", 20);
		
		SqlBuilder sql = new SqlBuilder();
		SqlBuilder sqlCount = new SqlBuilder();
		List<Long> dealerIds = null;
		
		AdminAuthFacade authFacade = EnvUtils.getEnv().getBean(AdminAuthFacade.class);
		User user = AdminSecurity.getCurrentUser(request);
		boolean isAdmin = authFacade.isAdmin();
		boolean isManufacturer =userService.isManufacturer(user.getId());
		
 		if (!isAdmin && !isManufacturer) {
			dealerIds = userService.findDealerIds(user.getId());
		}
 		
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(Dealer.class)).appendSql(" where 1=1 and deleted = 0 ");
		if (null != dealerIds && dealerIds.size() > 0) {
			sql.appendSql(" and id in ").appendValues(dealerIds.toArray());
		}
		if (null == dealerIds && !isAdmin && !isManufacturer){
			sql.appendSql(" and id = 0 ");
		}
		
		List<String> fieldList = geliOrm.getFieldList(type);
        if( fieldList != null && fieldList.size() >0){
    		try{
	        	for( String fieldName : fieldList){
	    			ValueSetter  vs = geliOrm.getFieldSetter( type, fieldName);
	    			String value = env.param( fieldName, "");
	    			if( StringUtils.isBlank(value)) value = env.param( vs.getFieldPath(), "");
	    			if( !StringUtils.isBlank(value)){
	    				request.setAttribute( fieldName, value);
	    				String pcolumn = geliOrm.getColumnByField(type, fieldName);
	    				sql.appendSql( " and ").appendSql(pcolumn);
	    				if( vs.getFieldType() == int.class ||  vs.getFieldType() == Integer.class){
	        				sql.appendSql("=").appendValue(new Integer(value));
	    				}else if( vs.getFieldType() == long.class ||  vs.getFieldType() == Long.class){
	    					sql.appendSql("=").appendValue(new Long(value));
	    				}else if( vs.getFieldType() == double.class ||  vs.getFieldType() == Double.class){
	    					sql.appendSql("=").appendValue(new Double(value));
	    				}else{
	    					sql.appendSql(" like ").appendValue("%" + value + "%");
	    				}
	    			}
	    		}
    		}catch( Exception e){
        		return "admin/dealer/list";
    		}
    	}
        if (!"".equals(orderField)) {
            sql.appendSql(" order by ").appendSql(
                    geliOrm.getColumnByField(type, orderField)).appendSql(" ").appendSql(orderDirection);
        }else {
        	sql.appendSql(" order by create_time desc");
		}
        sqlCount.appendSql(" select count(*) from ( ").appendSql(sql.getSql()).appendSql(" ) t").appendSql(" where 1=1 ");
        
        List<Dealer> list = geliDao.page(Dealer.class, sql.getSql(), pageNum, numPerPage, sql.getValues());
        int total = geliDao.count(sqlCount.getSql(), sql.getValues());

		request.setAttribute("total", total);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("numPerPage", numPerPage);
		request.setAttribute("orderField", orderField);
		request.setAttribute("orderDirection", orderDirection);
		request.setAttribute("list", list);
		return "admin/dealer/list";
	}
	
	@ResponseBody
	@RequestMapping(value="/admin/dealer/update.do")
	public String update(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Env env = EnvUtils.getEnv();
		Class<Dealer> type= Dealer.class;
		String keyField = geliOrm.getKeyField(type);
		int seq = env.paramInt("seq",-1);
		int hidden = env.paramInt("hidden",-1);
		Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Dealer obj = geliDao.find(type, id);
        
        List<Long> dealerIds = null;
        AdminAuthFacade authFacade = EnvUtils.getEnv().getBean(AdminAuthFacade.class);
        User user = AdminSecurity.getCurrentUser(request);
		boolean isAdmin = authFacade.isAdmin();
		boolean isManufacturer =userService.isManufacturer(user.getId());
		if (!isAdmin && !isManufacturer) {
			dealerIds = userService.findDealerIds(user.getId());
			if (null == dealerIds) {
				return DwzUtils.error("操作失败！");
			}
			if (!dealerIds.contains(obj.getId())) {
				return DwzUtils.error("操作失败！");
			}
		}
		
        if (seq>=0) {
        	obj.setSeq(seq);
		}
        if (hidden>=0) {
        	obj.setHidden(obj.getHidden()==0?1:0);
		}
        obj.setUpdateTime(new Date());
        String error = env.validateAll(obj);
        
        if (error != null) {
        	return DwzUtils.error(GeliFunctions.fmtValidateErrorMessage(error));
        }
        
        geliDao.update(obj);
        //LogFacade.log(obj);
        
        return DwzUtils.success("操作成功");
	}
	
	@RequestMapping(value="/admin/dealer/detail.do")
	public String detail(HttpServletRequest request,HttpServletResponse response){
		Env env = EnvUtils.getEnv();
	    Class<Dealer> type = Dealer.class;
	    String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Dealer entity = geliDao.find(type, id);
        request.setAttribute("entity", entity);
		return "admin/dealer/detailshow";
	}
}
