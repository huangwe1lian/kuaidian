package cn.com.fawtoyota.web.admin;

import cn.com.fawtoyota.entity.Order;
import cn.com.fawtoyota.service.order.ViewExcelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.GeliUtils;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.gelivable.web.GeliFunctions;
import org.gelivable.webmvc.JSONBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class OrderController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;

    @RequestMapping(value="/order/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Order> type= Order.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection","desc"));
        String _p = env.param("_p", "");
        
        //角色控制，某角色只能看到该角色所对应的城市线索量
        long city_id = 0;
        try{
        	 HttpSession httpsession = req.getSession();
        	 Long userId = (Long)httpsession.getAttribute("userId");
             SqlBuilder role_sql = new SqlBuilder();
             role_sql.appendSql("select src.city_id from sec_role_city src where src.role_id = ");
             role_sql.appendSql("(select sur.role_id from sec_user_role sur where sur.user_id = ?)");	
             city_id = geliDao.getJdbcTemplate().queryForLong(role_sql.getSql(), new Object[]{userId});
        }catch(Exception e){
        	e.printStackTrace();
        }
       
        SqlBuilder sql = new SqlBuilder();
        SqlBuilder sqlCount = new SqlBuilder();
        sql.appendSql("select * from ").appendSql(geliOrm.getTableName(type)).appendSql(" where 1 = 1 ");
        sqlCount.appendSql("select count(*) from ").appendSql(geliOrm.getTableName(type)).appendSql(" where 1 = 1 ");
        if (!"".equals(_p)) {
            int p = _p.indexOf(':');
            if (p > 1) {
                String pfield = geliOrm.ensureField(type, _p.substring(0, p));
                if (!"".equals(pfield)) {
                    String pcolumn = geliOrm.getColumnByField(type, pfield);
                    sql.appendSql(" and ").appendSql(pcolumn).appendSql("=");
                    sqlCount.appendSql(" and ").appendSql(pcolumn).appendSql("=");
                    sql.appendValue(_p.substring(p + 1));
                    sqlCount.appendValue(_p.substring(p + 1));
                }
            }
        }
        
        //cityID 角色
        if( city_id != 0L ){
        	sql.appendSql( " and city_id =" ).appendValue( city_id );
        	sqlCount.appendSql( " and city_id =" ).appendValue( city_id );
        	req.setAttribute( "city_id", city_id );
        }
        
        
        //姓名
        String name = env.param( "name", "" );
        if( !name.equals("") ){
        	sql.appendSql( " and name =" ).appendValue( name );
        	sqlCount.appendSql( " and name =" ).appendValue( name );
        	req.setAttribute( "name", name );
        }
        //手机
        String mobile = env.param( "mobile", "" );
        if( !mobile.equals("") ){
        	sql.appendSql( " and mobile =" ).appendValue( mobile );
        	sqlCount.appendSql( " and mobile =" ).appendValue( mobile );
        	req.setAttribute( "mobile", mobile );
        }
        //来源
        String refer = env.param( "refer", "" );
        if( !refer.equals("") ){
        	sql.appendSql( " and refer =" ).appendValue( refer );
        	sqlCount.appendSql( " and refer =" ).appendValue( refer );
        	req.setAttribute( "refer", refer );
        }
        //平台
        int origin = env.paramInt( "origin", -1 );
        if( origin != -1 ){
        	sql.appendSql( " and origin =" ).appendValue( origin );
        	sqlCount.appendSql( " and origin =" ).appendValue( origin );
        	req.setAttribute( "origin", origin );
        }
        //是否重复
        int isRepeat = env.paramInt( "isRepeat", -1 );
        if( isRepeat == 1 ){
        	sql.appendSql( " and repeat > 1 " );
        	sqlCount.appendSql( " and repeat > 1 " );
        	req.setAttribute( "isRepeat", isRepeat );
        } else if(isRepeat == 0){
        	sql.appendSql( " and repeat = 1 " );
        	sqlCount.appendSql( " and repeat = 1 " );
        	req.setAttribute( "isRepeat", isRepeat );
        }
        //车系id
        long serialGroupId = env.paramLong("serialGroupId",-1);
        if(serialGroupId >0){
        	sql.appendSql( " and serial_group_id =" ).appendValue( serialGroupId );
        	sqlCount.appendSql( " and serial_group_id =" ).appendValue( serialGroupId );
        	req.setAttribute( "serialGroupId", serialGroupId );
        }
        //city_id 搜索筛选
        long cityIdChoose = env.paramLong("cityId",0L);
        if(cityIdChoose != 0L){
        	sql.appendSql( " and city_id =" ).appendValue( cityIdChoose );
        	sqlCount.appendSql( " and city_id =" ).appendValue( cityIdChoose );
        	req.setAttribute( "cityIdChoose", cityIdChoose );
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
       
        
        //获取车系
        List<Map<String,Object>> sgs = new ArrayList<Map<String,Object>>();
    	SqlBuilder sg_sql = new SqlBuilder();
    	try{
    		 sg_sql.appendSql("select id,name from ft_serial_group where hidden = 0 and deleted = 0");
             sgs = geliDao.getJdbcTemplate().queryForList(sg_sql.getSql());
    	}catch(Exception e){
    		 e.printStackTrace();
    	}
            
       
        req.setAttribute("sgs", sgs);
        req.setAttribute("_p", _p);
        req.setAttribute("total", total);
        req.setAttribute("pageNum", pageNum);
        req.setAttribute("numPerPage", numPerPage);
        req.setAttribute("orderField", orderField);
        req.setAttribute("orderDirection", orderDirection);
        req.setAttribute("list", list);
        return "admin/order/orderList";
    }
    @RequestMapping(value="/order/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Order> type = cn.com.fawtoyota.entity.Order.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",Order.class);
        req.setAttribute("entity", obj);
        return "admin/order/orderDetail";
    }
    @RequestMapping(value="/order/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Order> type = cn.com.fawtoyota.entity.Order.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        Object entity = geliDao.findDb(type, id);
        
        env.bind(type);
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
        //GeliLogFacade.log();
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
    
    @RequestMapping(value="/order/showDetail.do", method = RequestMethod.GET)
    public String showDetail(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<Order> type = cn.com.fawtoyota.entity.Order.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Order obj = geliDao.find(type, id);
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",Order.class);
        req.setAttribute("entity", obj);
        return "admin/Order/detailshow";
    }
    
    @RequestMapping(value="/order/createExcel.do",method=RequestMethod.GET)
    public ModelAndView exportExcel(ModelMap model, HttpServletRequest request){

        ViewExcelService viewExcel = new ViewExcelService();
        Env env = EnvUtils.getEnv();
        String name = env.param("name", "").trim();
        String mobile = env.param("mobile", "");
        String refer = env.param("refer", "");
        long origin = env.paramInt("origin",-1);

        SqlBuilder sql = new SqlBuilder();
        sql.appendSql("select * ")
           .appendSql("from ft_order fo ")
           .appendSql("where 1=1 ");

        if (!name.equals("")) {
            sql.appendSql(" and fo.name = ").appendValue(name);
        }
        if (!mobile.equals("")) {
            sql.appendSql(" and fo.mobile = ").appendValue(mobile);
        }
        if (!refer.equals("")) {
            sql.appendSql(" and fo.refer = ").appendValue(refer);
        }
        if (origin != -1) {
            sql.appendSql(" and fo.origin = ").appendValue(origin);
        }

        Class<Order> type= Order.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection"));
        if (!"".equals(orderField)) {
            sql.appendSql(" order by ").appendSql(
                    geliOrm.getColumnByField(type, orderField)).appendSql(" ").appendSql(orderDirection);
        }
        
        GeliDao dao = GeliUtils.getDao();
        List<Order> orderList = dao.list(Order.class, sql.getSql(), sql.getValues());

        viewExcel.setOrderList(orderList);

        return new ModelAndView(viewExcel, model);
    }
}

