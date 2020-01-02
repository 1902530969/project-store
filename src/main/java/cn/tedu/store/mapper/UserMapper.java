package cn.tedu.store.mapper;

import java.util.Date;

import javax.xml.crypto.Data;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

//在持久层 接口中定义抽象方法
public interface UserMapper {
	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer insert(User user);
	
	
	
	
	
	
	
	


	/**
	 * 根据uid更新用户的密码
	 * @param user 用户数据 
	 * 多个参数需要加param 注解
	 * @return 受影响的行数
	 */

	Integer updatePasswordbyUid(
			@Param("uid") Integer uid, 
			@Param("password") String password, 
			@Param("modifiedUser") String modifiedUser, 
			@Param("modifiedTime") Date modifiedTime);

	

	
	
	/**
	 * 开始执行更改用户信息操作 1
	 * @param uid 根据当前用户uid查询用户信息
	 * @return   User  
	 */
	
	/**
	 * 更改用户基本信息 
	 * @param user封装了新的用户数据
	 * @return
	 */
	Integer updateinfoByuid(User user);
	
	
	/**
	 * 
	 * 
	 * @param uid 根据uid更新用户的头像
	 * @param avatar 用户上传的新头像的路径
	 * @param modifiedUser
	 * @param modifiedTime
	 * @return 
	 */
	Integer updateAvater(
			@Param("uid") Integer uid,
			@Param("avatar") String avatar,
			@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime") Date modifiedTime
			
			);
	
	
	
	
	

	

	/**
	 * 根据uid查询用户信息
	 * @param  当前登录的用户新的uid
	 * @return   User
	 */
	User findByUid(Integer uid);




	/**
	 * 根据用户名查询用户数据
	 * @param username 根据用户名查询用户信息
	 * @return 匹配的用户数据user，如果没有匹配的数据，则返回null
	 */
	User findByUserName(String username);




}
