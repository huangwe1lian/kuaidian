package cn.com.fawtoyota.service.serialgroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gelivable.dao.GeliDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;

import cn.com.fawtoyota.entity.Model;
import cn.com.fawtoyota.entity.SerialGroup;

/** 
 * 车系相关服务类
 * @author  作者： Lin Ruichang E-mail:
 * @date 创建时间：2016-6-6 下午12:14:52
 * @version 1.0
 */
@Service
public class SerialGroupService {
	@Autowired
	GeliDao geliDao;
	
	@Autowired
	MemCachedClient mcc;
	
	public List<SerialGroup> listNormal(){
		String sql = "select * from ft_serial_group where deleted=0 order by create_time desc";
		List<SerialGroup> result = geliDao.list(SerialGroup.class, sql);
		return result;
	}
	
	public Map<String,List<Model>> getAllModleGroupBySg(){
		String sql = "select * from ft_model where deleted=0 and status > 0 order by serial_group_id,create_time desc";
		List<Model> models = geliDao.list(Model.class, sql);
		long tempSgId = -1;
		Map<String,List<Model>> result = new HashMap<String, List<Model>>();
		List<Model> list = null;
		for(int i = 0; i < models.size();i++){
			if(tempSgId == models.get(i).getSerialGroupId()){
				list.add(models.get(i));
			} else {
				if(i != 0){
					result.put("sg_" + tempSgId, list);
				}
				list = new ArrayList<Model>();
				list.add(models.get(i));
				tempSgId = models.get(i).getSerialGroupId();
			}
		}
		result.put("sg_" +  tempSgId, list);
		return result;
	}
}
