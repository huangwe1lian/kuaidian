package cn.com.kuaidian.service.admin;

import java.util.List;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.kuaidian.entity.AdCity;
import cn.com.kuaidian.entity.AdOrderform;
import cn.com.kuaidian.entity.AdSerialGroup;
import cn.com.kuaidian.entity.City;
import cn.com.kuaidian.entity.DealerNewsPosition;
import cn.com.kuaidian.entity.DealerNewsSerialGroup;
import cn.com.kuaidian.entity.MediaNewsCity;
import cn.com.kuaidian.entity.SerialGroup;
import cn.com.kuaidian.entity.TemplateAdCity;
import cn.com.kuaidian.entity.TemplateAdDetail;
import cn.com.kuaidian.entity.TemplateAdModel;
import cn.com.kuaidian.entity.TemplateAdOrderform;
import cn.com.kuaidian.entity.TemplateAdSerialGroup;
import cn.com.kuaidian.util.StringUtils;

/** 
 * 广告管理服务类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-12 上午10:05:32
 * @version 1.0
 */
@Service
public class AdService {
	@Autowired
	GeliOrm geliOrm;
	
	@Autowired
	GeliDao geliDao;
	
	/**
	 * 根据广告ID获厂商广告关联车系
	 * @param id 广告ID
	 * @return
	 */
	public List<Map<String,Object>> findAdSerialGroup(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select sg.id,sg.name from ")
		   .appendSql(geliOrm.getTableName(AdSerialGroup.class))
		   .appendSql(" asg,").appendSql(geliOrm.getTableName(SerialGroup.class)).appendSql(" sg ")
		   .appendSql(" where sg.id=asg.serial_group_id and ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().queryForList(sql.getSql(), sql.getValues());
	}
	
	/**
	 * 根据广告ID获取厂商广告关联城市
	 * @param id 广告ID
	 * @return
	 */
	public List<AdCity> findAdCity(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(AdCity.class))
		   .appendSql(" where ad_id=").appendValue(id);
		return geliDao.list(AdCity.class, sql.getSql(), sql.getValues());
	}
	
	/**
	 * 列出媒体资讯关联城市
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> listAdCityName(long id,Class type,String field){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select nc.province_id provinceId,nc.city_id cityId,c.name from ").appendSql(geliOrm.getTableName(type))
		   .appendSql(" nc left join ").appendSql(geliOrm.getTableName(City.class)).appendSql(" c on c.id=nc.city_id ")
		   .appendSql(" where nc.").appendSql(field).appendSql("=").appendValue(id);
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
	 * 根据广告ID删除关联城市
	 * @param id 广告ID
	 * @return
	 */
	public int deleteCityByAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(AdCity.class))
		   .appendSql(" where ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	/**
	 * 根据广告ID删除关联车系
	 * @param id 广告ID
	 * @return
	 */
	public int deleteSerialGroupByAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(AdSerialGroup.class))
		   .appendSql(" where ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	public AdOrderform findAdOrderform(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(AdOrderform.class))
		   .appendSql(" where ad_id=").appendValue(id);
		List<AdOrderform> list = geliDao.list(AdOrderform.class, sql.getSql(), sql.getValues());
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public int deleteOrderformByAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(AdOrderform.class))
		   .appendSql(" where ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	/**
	 * 根据广告ID获取媒体资讯关联车系
	 * @param id 广告ID
	 * @return
	 */
	public List<Map<String,Object>> findTemplateAdSerialGroup(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select sg.id,sg.name from ")
		   .appendSql(geliOrm.getTableName(TemplateAdSerialGroup.class))
		   .appendSql(" asg,").appendSql(geliOrm.getTableName(SerialGroup.class)).appendSql(" sg ")
		   .appendSql(" where sg.id=asg.serial_group_id and template_ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().queryForList(sql.getSql(), sql.getValues());
	}
	
	/**
	 * 根据广告ID获取媒体资讯关联城市
	 * @param id 广告ID
	 * @return
	 */
	public List<TemplateAdCity> findTemplateAdCity(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(TemplateAdCity.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.list(TemplateAdCity.class, sql.getSql(), sql.getValues());
	}
	
	/**
	 * 根据广告ID删除关联城市
	 * @param id 广告ID
	 * @return
	 */
	public int deleteCityByTemplateAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(TemplateAdCity.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	/**
	 * 根据广告ID删除关联车系
	 * @param id 广告ID
	 * @return
	 */
	public int deleteSerialGroupByTemplateAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(TemplateAdSerialGroup.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	public TemplateAdOrderform findTemplateAdOrderform(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(TemplateAdOrderform.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		List<TemplateAdOrderform> list = geliDao.list(TemplateAdOrderform.class, sql.getSql(), sql.getValues());
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public int deleteOrderformByTemplateAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(TemplateAdOrderform.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	public List<TemplateAdModel> findTemplateAdModel(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(TemplateAdModel.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.list(TemplateAdModel.class, sql.getSql(), sql.getValues());
	}
	
	public int deleteModelByTemplateAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(TemplateAdModel.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	public TemplateAdDetail findTemplateAdDetail(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(TemplateAdDetail.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		List<TemplateAdDetail> list = geliDao.list(TemplateAdDetail.class, sql.getSql(), sql.getValues());
		return list.size() > 0 ? list.get(0) : null;
	}
	
	public int deleteDetailByTemplateAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(TemplateAdDetail.class))
		   .appendSql(" where template_ad_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
	
	/**
	 * 根据广告ID获取经销商广告关联车系
	 * @param id 广告ID
	 * @return
	 */
	public List<Map<String,Object>> findDealerAdSerialGroup(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select sg.id,sg.name from ")
		   .appendSql(geliOrm.getTableName(DealerNewsSerialGroup.class))
		   .appendSql(" asg,").appendSql(geliOrm.getTableName(SerialGroup.class)).appendSql(" sg ")
		   .appendSql(" where sg.id=asg.serial_group_id and dealer_news_id=").appendValue(id);
		return geliDao.getJdbcTemplate().queryForList(sql.getSql(), sql.getValues());
	}
	
	/**
	 * 根据广告ID获取经销商广告推荐位置
	 * @param id 广告ID
	 * @return
	 */
	public List<DealerNewsPosition> findDealerAdPosition(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" select * from ").appendSql(geliOrm.getTableName(DealerNewsPosition.class))
		   .appendSql(" where dealer_news_id=").appendValue(id);
		return geliDao.list(DealerNewsPosition.class, sql.getSql(), sql.getValues());
	}
	
	public int deletePositionByDealerAdId(long id){
		SqlBuilder sql = new SqlBuilder();
		sql.appendSql(" delete from ").appendSql(geliOrm.getTableName(DealerNewsPosition.class))
		   .appendSql(" where dealer_news_id=").appendValue(id);
		return geliDao.getJdbcTemplate().update(sql.getSql(),sql.getValues());
	}
}
