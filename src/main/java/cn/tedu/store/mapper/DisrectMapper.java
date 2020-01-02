package cn.tedu.store.mapper;

import java.util.List;


import cn.tedu.store.entity.District;

public interface DisrectMapper {

	/**
	 * 
	 * @param district
	 * @return
	 */
	List<District> findByParent(String parent);


	String findByName(String code);


}
