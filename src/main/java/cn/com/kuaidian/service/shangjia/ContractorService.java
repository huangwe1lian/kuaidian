package cn.com.kuaidian.service.shangjia;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.shangjia.Contractor;
import cn.com.kuaidian.entity.user.User;

@Service
public class ContractorService {
	@Autowired
	private GeliDao geliDao;
	
	public Contractor getContractor(long contractorId){
		return geliDao.find(Contractor.class, contractorId);
	}
	
	public Contractor getContractorByContractorName(String ContractorName){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_contractor where username = ").appendValue(ContractorName);
		return geliDao.findFirst(Contractor.class,sql.getSql(),sql.getValues());
	}
}
