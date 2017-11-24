package cn.com.kuaidian.service.staff;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.staff.Staff;

@Service
public class StaffService {
	@Autowired
	private GeliDao geliDao;
	
	public Staff getStaff(long staffId){
		return geliDao.find(Staff.class, staffId);
	}
	
	public Staff getStaffByStaffName(String StaffName){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from kd_user where username = ").appendValue(StaffName);
		return geliDao.findFirst(Staff.class,sql.getSql(),sql.getValues());
	}
}
