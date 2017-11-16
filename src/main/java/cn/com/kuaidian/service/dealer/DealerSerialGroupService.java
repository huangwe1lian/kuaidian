package cn.com.kuaidian.service.dealer;

import java.util.List;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.DealerSerialGroup;
import cn.com.kuaidian.entity.MediaNewsSerialGroup;

/**
 * 主营车系相关服务类
 * @author huangeilian
 * 2016年6月12日下午12:17:27
 */
@Service
public class DealerSerialGroupService {
	@Autowired
	GeliDao geliDao;
	
	@Autowired
	GeliOrm geliOrm;
	
	
	/**
	 * 根据经销商ID获得主营车系
	 * @param dealerId
	 * @return
	 */
	public List<DealerSerialGroup> findDealerSerialGroups(long dealerId){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(DealerSerialGroup.class)) 
		.appendSql(" where dealer_id = ").appendValue(dealerId);
		return geliDao.list(DealerSerialGroup.class, sql.getSql(), sql.getValues());
	}
}
