package cn.com.fawtoyota.web.admin;

import cn.com.fawtoyota.entity.SerialGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
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

@Controller
@RequestMapping("/admin")
public class SerialGroupController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;

    @RequestMapping(value="/serialgroup/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<SerialGroup> type= SerialGroup.class;
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
        return "admin/serialgroup/serialgrouplist";
    }

    @RequestMapping(value="/serialgroup/update.do", method = RequestMethod.GET)
    public String showUpdate(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class type = cn.com.fawtoyota.entity.SerialGroup.class;
        String keyField = geliOrm.getKeyField(type);
        Object id = geliOrm.parseObjectId(type, env.param(keyField));
        Object obj = geliDao.find(type, id);
        req.setAttribute("method", "update");
        req.setAttribute("domain_clazz",SerialGroup.class);
        req.setAttribute("entity", obj);
        return "admin/serialgroup/serialgroupdetail";
    }


    @RequestMapping(value="/serialgroup/update.do", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<SerialGroup> type = cn.com.fawtoyota.entity.SerialGroup.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        SerialGroup entity = geliDao.findDb(type, id);

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
        String name = env.param( "name", "" );
        String ename = env.param( "ename", "" );
        String logo = env.param( "logo", "" );
        int status = env.paramInt( "status", -1 );
        int deleted = env.paramInt( "deleted", -1 );
        String logo1 = env.param( "logo1", "" );
        String logo2 = env.param( "logo2", "" );
        long nav_seq = env.paramLong( "navSeq", -1 );
        long hot_seq = env.paramLong( "hotSeq", -1 );
        int hidden = env.paramInt( "hidden", 0 );
        HttpSession httpsession = req.getSession();
        Long userId = (Long)httpsession.getAttribute("userId");
        
        entity.setName(name);
        entity.setEname(ename);
        entity.setLogo(logo);
        entity.setStatus(status);
        entity.setDeleted(deleted);
        entity.setLogo1(logo1);
        entity.setLogo2(logo2);
        entity.setNavSeq(nav_seq);
        entity.setHotSeq(hot_seq);
        entity.setHidden(hidden);
        entity.setUpdateUserId(userId);
    	entity.setUpdateTime(new Date());
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
    
    @RequestMapping(value="/serialgroup/updatefromlist.do")
    public String updatefromlist(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<SerialGroup> type = cn.com.fawtoyota.entity.SerialGroup.class;
        Object id = geliOrm.parseObjectId(type, 
                env.param(geliOrm.getKeyField(type)));

        SerialGroup entity = geliDao.findDb(type, id);

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
        String from = env.param("from","");
        //操作中的保存
        if(from.equals("seq")){
        	 long nav_seq = env.paramLong( "nav_seq", -1 );
             long hot_seq = env.paramLong( "hot_seq", -1 );
             entity.setNavSeq(nav_seq);
             entity.setHotSeq(hot_seq);
        }
       //操作中的隐藏
        if(from.equals("hidden")){
        	int hidden = env.paramInt( "hidden", 0 );
            entity.setHidden(hidden);
        }
        HttpSession httpsession = req.getSession();
        Long userId = (Long)httpsession.getAttribute("userId");
        entity.setUpdateUserId(userId);
    	entity.setUpdateTime(new Date());
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
    
    @RequestMapping(value="/serialgroup/setRecommendModel.do", method = RequestMethod.GET)
    public String setRecommendModel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        long sgid = env.paramLong("sgid",-1);
        List<Map<String,Object>> models = new ArrayList<Map<String,Object>>();
        if(sgid != -1){
        	 SqlBuilder model_sql = new SqlBuilder();
        	 try{
        		 model_sql.appendSql("select id,name,price from ft_model where serial_group_id = ? and deleted = 0 and status = 1");
                 models = geliDao.getJdbcTemplate().queryForList(model_sql.getSql(),new Object[]{sgid});
        	 }catch(Exception e){
        		 e.printStackTrace();
        	 }
            
        }
       
        req.setAttribute("models", models);
        req.setAttribute("sgid", sgid);
        return "admin/serialgroup/setrecommendmodel";
    }
    
    @RequestMapping(value="/serialgroup/saveRecommendModel.do", method = RequestMethod.GET)
    public String saveRecommendModel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        long sgid = env.paramLong("sgid",-1);
        long hotModelId = env.paramLong("modelId",-1);
        
        if((sgid != -1)&&(hotModelId != -1)){
        	 try{
        		 Class<SerialGroup> type = cn.com.fawtoyota.entity.SerialGroup.class;
        	     Object id = sgid;
        	     SerialGroup entity = geliDao.findDb(type, id);
        	       
        	     HttpSession httpsession = req.getSession();
            	 Long userId = (Long)httpsession.getAttribute("userId");
        	     entity.setHotModelId(hotModelId);
        	     entity.setUpdateTime(new Date());
        	     entity.setUpdateUserId(userId);
        	     geliDao.update(entity);
        		 
        	 }catch(Exception e){
        		 e.printStackTrace();
        	 }
            
        }
       
        return "admin/serialgroup/serialgrouplist";
    }
}

