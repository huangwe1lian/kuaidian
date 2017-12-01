package cn.com.kuaidian.service;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.Session;

/** 
 * sec_session服务类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-3 下午3:05:40
 * @version 1.0
 */
@Service
public class SessionService {
	@Autowired
	GeliDao geliDao;
	
	/**
	 * 通过session获取session对象
	 * @param sessionId
	 * @return
	 */
	public Session findBySessionId(String sessionId) {
		String sql = "select * from gl_session where session_id = ? order by create_time desc";
		List<Session> list = geliDao.list(Session.class,sql, sessionId);
        if(list.size() > 0){
        	return list.get(0);
        }
		return null;
    }
	
	/**
	 * 通过sessionID清楚session
	 * @param sessionId
	 */
	public void removeSession(String sessionId){
		Session session = findBySessionId(sessionId);
		geliDao.delete(session, session.getId());
	}
}
