package cn.com.fawtoyota.web.admin;


import cn.com.fawtoyota.entity.UserLog;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.gelivable.web.Env;
import org.gelivable.web.EnvUtils;
import org.gelivable.web.GeliFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UserLogController {

    @Autowired
    GeliOrm geliOrm;

    @Autowired
    GeliDao geliDao;

    @RequestMapping(value="/userlog/list.do")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Env env = EnvUtils.getEnv();
        Class<UserLog> type= UserLog.class;
        String orderField = geliOrm.ensureField(type, env.param("orderField","id"));
        String orderDirection = GeliFunctions.orderDirection(env.param("orderDirection","desc"));
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
        return "admin/user/userlog/list";
    }

}

