package cn.com.kuaidian.service.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;

import cn.com.kuaidian.entity.City;
import cn.com.kuaidian.entity.Province;

/** 
 * 地区相关服务类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-4 下午3:26:15
 * @version 1.0
 */
@Service
public class RegionService {
	@Autowired
	GeliDao geliDao;
	
	@Autowired
	GeliOrm geliOrm;
	
	@Autowired
	MemCachedClient mcc;
	
	/**
	 * 获取省份
	 * @return
	 */
	public List<Province> listProvince(){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from ")
	 	   .appendSql(geliOrm.getTableName(Province.class))
	 	   .appendSql(" where deleted=0 order by seq,letter");
	 	List<Province> list = geliDao.list(Province.class, sql.getSql());
	 	return list;
	}
	
	/**
	 * 获取所有城市
	 * @return
	 */
	public List<City> listCity(){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from ")
		.appendSql(geliOrm.getTableName(City.class))
		.appendSql(" where deleted=0 order by seq,letter");
		List<City> list = geliDao.list(City.class, sql.getSql());
		return list;
	}
	
	/**
	 * 根据省份获取城市
	 * @param pid
	 * @return
	 */
	public List<City> listCityByPro(long pid){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql("select * from ")
	 	   .appendSql(geliOrm.getTableName(City.class))
	 	   .appendSql(" where deleted=0 and province_id=").appendValue(pid)
	 	   .appendSql(" order by letter,seq");
	 	List<City> list = geliDao.list(City.class, sql.getSql(),sql.getValues());
	 	return list;
	}
	
	/**
	 * 获取省份地区
	 * @return
	 */
	public List<Map<String,Object>> listIpArea(){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select c.id cid,c.name cname,c.letter cletter,c.pinyin cpinyin,")
		   .appendSql(" p.id pid,p.name pname, ")
		   .appendSql(" c.pc_province_id pcpid,c.pc_city_id pccid, c.city_code citycode ")
		   .appendSql(" from ft_city c,ft_province p ")
		   .appendSql(" where c.province_id = p.id ")
		   .appendSql(" order by p.id,c.letter ");
		
		List<Map<String,Object>> list = geliDao.getJdbcTemplate().queryForList(sql.getSql());
		return list;
	}
	
	/**
	 * 获取省份地区
	 * @return
	 */
	public Map<String,Object> getIpAreaById(long cid, String cityCode){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select c.id cid,c.name cname,c.letter cletter,c.pinyin cpinyin,")
		   .appendSql(" p.id pid,p.name pname, ")
		   .appendSql(" c.pc_province_id pcpid,c.pc_city_id pccid, c.city_code citycode ")
		   .appendSql(" from ft_city c,ft_province p ")
		   .appendSql(" where c.province_id = p.id ");
		if(cid > 0){
			sql.appendSql("and c.id = ").appendValue(cid);
		}else{
			sql.appendSql("and c.city_code = ").appendValue(cityCode);
		}
		sql.appendSql(" order by p.id,c.letter ");
		
		List<Map<String,Object>> list = geliDao.getJdbcTemplate().queryForList(sql.getSql(),sql.getValues());
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
	/**
     * 获取所有省份、城市信息
     * @return
     */
    public List<Object[]> listAllProvinceAndCity() {
        List<Object[]> result = new ArrayList<Object[]>();

        List<Province> provinces = listProvince();
        List<City> citys = listCity();

        Map<Long, List<City>> cityMap = new HashMap<Long, List<City>>();
        for (City city : citys) {
            long provinceId = city.getProvinceId();
            List<City> cityList = cityMap.get(provinceId);
            if (cityList == null) {
            	cityList = new ArrayList<City>();
            }
            cityList.add(city);
            cityMap.put(provinceId, cityList);
        }

        for (Province p : provinces) {
            Object[] obj = new Object[2];
            obj[0] = p;
            List<City> obj1 = cityMap.get(p.getId());
            obj[1] = obj1;
            result.add(obj);
        }
        return result;
    }
}
