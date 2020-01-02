package cn.tedu.store.service.District;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;

/**
 * 省市区业务处理
 * @author Administrator
 *
 */


public interface DistrictService {

/**
 * 查询所有的省份信息
 * @param parent
 * @return
 */
	List<District> findByParent(String parent);
	
	
	
	
	String getNameByCode(String code);
	
	
}
