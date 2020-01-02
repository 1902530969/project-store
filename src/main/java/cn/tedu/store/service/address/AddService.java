package cn.tedu.store.service.address;

import java.util.List;

import cn.tedu.store.entity.UserAddress;

public interface AddService {
	/**
	 * 
	 * @param uid
	 * @param name
	 * @param address
	 * 没有返回值  调用方法直接调用  不需要接受
	 */

	void addNew(Integer uid,
			String username,UserAddress address);
	
	
	List<UserAddress> getByUid(Integer uid);
	
	
	
	void setDefault(Integer aid, Integer uid, String username);
	
	/**
	 * 删除地址信息
	 * @param aid
	 * @return
	 */
	void deleteByAid(Integer aid,Integer uid,String username);

	
	
	/**
	 * 用户订单的处理  在创建Order后处理
	 * @param aid
	 * @param uid
	 * @return
	 */
	UserAddress getByAid(Integer aid, Integer uid);
	
}
