package cn.com.fawtoyota.service.admin;

import java.util.List;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.fawtoyota.entity.City;
import cn.com.fawtoyota.entity.DealerNewsSerialGroup;
import cn.com.fawtoyota.entity.MediaNewsCity;
import cn.com.fawtoyota.entity.MediaNewsSerialGroup;
import cn.com.fawtoyota.entity.SerialGroup;
import cn.com.fawtoyota.util.StringUtils;

/** 
 * 
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-6 下午2:11:44
 * @version 1.0
 */
@Service
public class MediaNewsService {
	@Autowired
	GeliDao geliDao;
	
	@Autowired
	GeliOrm geliOrm;
	
	/**
	 * 根据媒体资讯ID获取媒体资讯关联车系
	 * @param id 媒体资讯ID
	 * @return
	 */
	public List<Map<String,Object>> findMediaNewsSerialGroup(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select sg.id,sg.name from ")
		   .appendSql(geliOrm.getTableName(MediaNewsSerialGroup.class))
		   .appendSql(" asg,").appendSql(geliOrm.getTableName(SerialGroup.class)).appendSql(" sg ")
		   .appendSql(" where sg.id=asg.serial_group_id and media_news_id=").appendValue(id);
		return geliDao.getJdbcTemplate().queryForList(sql.getSql(), sql.getValues());
	}
	
	/**
	 * 根据媒体资讯ID获取媒体资讯关联城市
	 * @param id 媒体资讯ID
	 * @return
	 */
	public List<MediaNewsCity> findMediaNewsCity(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(MediaNewsCity.class))
		   .appendSql(" where media_news_id=").appendValue(id);
		return geliDao.list(MediaNewsCity.class, sql.getSql(), sql.getValues());
	}
	
	/**
	 * 列出媒体资讯关联城市
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> listMediaNewsCityName(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select nc.province_id provinceId,nc.city_id cityId,c.name from ").appendSql(geliOrm.getTableName(MediaNewsCity.class))
		   .appendSql(" nc left join ").appendSql(geliOrm.getTableName(City.class)).appendSql(" c on c.id=nc.city_id ")
		   .appendSql(" where nc.media_news_id=").appendValue(id);
		List<Map<String,Object>> list = geliDao.getJdbcTemplate().queryForList(sql.getSql(),sql.getValues());
		if(list.size() == 1 
				&& (StringUtils.longValue(String.valueOf(list.get(0).get("cityId")), -1) == 0 
					&& StringUtils.longValue(String.valueOf(list.get(0).get("provinceId")), -1) == 0)){
			list.get(0).put("name", "全国");
		} else {
			for(Map<String,Object> map : list){
				if(StringUtils.longValue(String.valueOf(map.get("provinceId")), -1) == 0){
					list.remove(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据媒体资讯ID删除关联城市
	 * @param id 媒体资讯ID
	 * @return
	 */
	public int deleteCityByMedisNewsId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(MediaNewsCity.class))
		   .appendSql(" where media_news_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	/**
	 * 根据媒体资讯ID删除关联车系
	 * @param id 媒体资讯ID
	 * @return
	 */
	public int deleteSerialGroupByMedisNewsId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(MediaNewsSerialGroup.class))
		   .appendSql(" where media_news_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
}
