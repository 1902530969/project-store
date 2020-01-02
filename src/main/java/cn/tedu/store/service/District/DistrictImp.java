package cn.tedu.store.service.District;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DisrectMapper;

/**
 * 再实现类找那个添加service注解
 * @author Administrator
 *
 */

@Service
public class DistrictImp implements DistrictService  {

	@Autowired
	private	DisrectMapper disMapper;


	@Override
	public List<District> findByParent(String parent) {
		
		List<District> list =disMapper.findByParent(parent);
		for (District district : list) {
			
		    district.setId(null);
	        district.setParent(null);

			
			
		}
		
		
		
		return list;
		
		
	}


	@Override
	public String getNameByCode(String code) {
		
		String result=disMapper.findByName(code);
		
		
		return result;
	}






}
