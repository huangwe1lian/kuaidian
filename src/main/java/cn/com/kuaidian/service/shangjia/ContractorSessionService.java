package cn.com.kuaidian.service.shangjia;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.shangjia.ContractorSession;

/** 
 * sec_session服务类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-3 下午3:05:40
 * @version 1.0
 */
@Service
public class ContractorSessionService {
	@Autowired
	GeliDao geliDao;
	
	/**
	 * 通过session获取session对象
	 * @param sessionId
	 * @return
	 */
	public ContractorSession findBySessionId(String sessionId) {
		String sql = "select * from gl_contractor_session where session_id = ? order by create_time desc";
		List<ContractorSession> list = geliDao.list(ContractorSession.class,sql, sessionId);
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
		ContractorSession session = findBySessionId(sessionId);
		geliDao.delete(session, session.getId());
	}
}
