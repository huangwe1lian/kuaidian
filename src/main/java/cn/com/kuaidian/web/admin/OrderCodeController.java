package cn.com.kuaidian.web.admin;

import cn.com.kuaidian.entity.OrderCode;
import cn.com.kuaidian.service.admin.OrderCodeService;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
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
public class OrderCodeController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;
    
    @Autowired
    OrderCodeService orderCodeService;

    @RequestMapping(value="/ordercode/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<OrderCode> type= OrderCode.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection","desc"));
        String _p = env.param("_p", "");
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
        //授权代码
        String order_code = env.param( "order_code", "" );
        if( !order_code.equals("") ){
        	sql.appendSql( " and order_code =" ).appendValue( order_code );
        	sqlCount.appendSql( " and order_code =" ).appendValue( order_code );
        	req.setAttribute( "order_code", order_code );
        }
        //状态
        int status = env.paramInt( "status", -1 );
        if( status != -1 ){
        	sql.appendSql( " and status =" ).appendValue( status );
        	sqlCount.appendSql( " and status =" ).appendValue( status );
        	req.setAttribute( "status", status );
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
        return "admin/ordercode/orderCodeList";
    }

    @RequestMapping(value="/ordercode/select.do")
    public String select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<OrderCode> type= OrderCode.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection"));
        String _p = env.param("_p", "");
        SqlBuilder sql = new SqlBuilder();
        SqlBuilder sqlCount = new SqlBuilder();
        sql.appendSql("select * from ").appendSql(geliOrm.getTableName(type));
        sqlCount.appendSql("select count(*) from ").appendSql(geliOrm.getTableName(type));
        if (!"".equals(_p)) {
            int p = _p.indexOf(':');
            if (p > 1) {
                String pfield = geliOrm.ensureField(type, _p.substring(0, p));
                if (!"".equals(pfield)) {
                    String pcolumn = geliOrm.getColumnByField(type, pfield);
                    sql.appendSql(" where ").appendSql(pcolumn).appendSql("=");
                    sqlCount.appendSql(" where ").appendSql(pcolumn).appendSql("=");
                    sql.appendValue(_p.substring(p + 1));
                    sqlCount.appendValue(_p.substring(p + 1));
                }
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
        return "admin/ordercode/orderCodeSelect";
    }

    @RequestMapping(value="/ordercode/create.do", method = RequestMethod.GET)
    public String showCreate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        req.setAttribute("method", "create");
        req.setAttribute("domain_clazz",OrderCode.class);
        return "admin/ordercode/orderCodeDetail";
    }

    @RequestMapping(value="/ordercode/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<OrderCode> type = cn.com.kuaidian.entity.OrderCode.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",OrderCode.class);
        req.setAttribute("entity", obj);
        return "admin/ordercode/orderCodeDetail";
    }

    @RequestMapping(value="/ordercode/create.do", method = RequestMethod.POST)
    public String create(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Env env = EnvUtils.getEnv();
        
        Class<OrderCode> type = cn.com.kuaidian.entity.OrderCode.class;
        OrderCode entity = env.bind(type);
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
            //GeliLogFacade.log();
        	entity.setOrderCode(orderCodeService.createOrderCode(4));;
        	entity.setCreateTime(new Date());
        	entity.setUpdateTime(new Date());
            geliDao.create(entity);
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

    @RequestMapping(value="/ordercode/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<OrderCode> type = cn.com.kuaidian.entity.OrderCode.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        OrderCode entity = geliDao.findDb(type, id);

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
        HttpSession httpsession = req.getSession();
   	 	Long userId = (Long)httpsession.getAttribute("userId");
        String description = env.param("description","");
        String url = env.param("url","");
        int status = env.paramInt("status",1);
        entity.setDescription(description);
        entity.setUrl(url);
        entity.setStatus(status);
        entity.setUpdateTime(new Date());
        entity.setUpdateUserId(userId);
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
    
    @RequestMapping(value="/ordercode/delete.do", method = RequestMethod.POST)
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<OrderCode> type = cn.com.kuaidian.entity.OrderCode.class;
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
        geliDao.delete(entity, id);
        
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
    
}

