package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.UserAddress;

public interface AddressMapper {
	/**
	 * 插入用户地址数据
	 * 
	 * @param address
	 * @return
	 */
	Integer insert(UserAddress address);


	Integer countByUid(Integer uid);



	/**
	 * 
	 * @param uid 根据uid查询客户的地址信息
	 * @return
	 */
	List<UserAddress> findByUid(Integer uid);


	/**
	 * 修改默认地址信息
	 * 
	 * @param uid
	 * @return
	 */
	Integer UpDdteDefaultbyUid(Integer uid);

	/**
	 * 将置顶的收货地址设置为默认
	 * 
	 * @param aid 收货人的uid
	 * @param modifiedUser
	 * @param date
	 * @return
	 */
	Integer UpDdteDefaultbyAid(
			@Param("aid")Integer aid,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date date

			);



	
	/**
	 * 用过aid查询客户地址数据
	 * @param aid
	 * @return
	 */
	UserAddress findbyAid(Integer aid);


/**
 * 用过aid删除用户信息
 * @param aid 
 * @return
 */
	Integer deleteByAid(Integer aid);
	
	/**
	 * 通过uid将地址按照修改时间拍降序
	 * @param uid
	 * @return
	 */
	UserAddress  findLastModifiledTime(Integer uid);
	
	
	
	
	
}
