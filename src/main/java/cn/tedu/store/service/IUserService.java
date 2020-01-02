package cn.tedu.store.service;

import cn.tedu.store.entity.User;

/**
 * 业务处理
 * 处理用户注册的接口
 * 定义UserImp实现类  实现类需要重写接口中的方法
 * @author Administrator
 *
 */

public interface IUserService {

	void reg(User user);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return  登录成功的用户数据
	 */
	User login(String username,String password);


	/**
	 * 修改密码操作
	 * @param uid 当前用户id
	 * @param username 
	 * @param oldPassword  原来密码
	 * @param newPassword  新的密码
	 */
	void changedPw(Integer uid,String username,

			String oldPassword,String newPassword);

	

	/**
	 * 开始执行更改用户信息操作 1
	 * @param uid 根据当前用户uid查询用户信息
	 * @return   User  
	 */
	User getByUid(Integer uid);
	
	void changeInfo(Integer uid,String username,User user);
	
	
	
	void changeAvatar(Integer uid,String username,String avatar);
	
	
	
	
	
	
	
	
	
	
	
	

}
