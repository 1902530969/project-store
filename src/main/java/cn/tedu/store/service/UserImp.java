package cn.tedu.store.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PassWordNotFoundException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.userNameDuplicateException;
/**
 * UserImp是个实现类   需要添加Service注解 才能实现 usermapper的自动装配
 * @author Administrator
 *
 */

@Service
public class UserImp implements IUserService {

	@Autowired
	private UserMapper userMapper;


	//开始业务处理
	@Override
	public void reg(User user) {
		//通过用户的注册信息获取对应的用户名字
		String username=user.getUsername();

		System.out.println("用户名是"+username);
		//调用usermapper中的findbyusernam方法查询数据库.看看是否有重复
		User result=userMapper.findByUserName(username);

		if(result!=null){
			//用户名重复 不能注册 抛出异常
			throw new userNameDuplicateException("用户名"+username+"被占用");

		}
		//插入成功,补全剩余数据
		//创建时间对象

		Date now =new Date();
		//补全salt,密码加密
		String salt=UUID.randomUUID().toString().toUpperCase();
		String md5Password = getMd5Password(user.getPassword(), salt);
		System.out.println("注册的盐值"+md5Password);
		//注入盐值
		user.setPassword(md5Password);
		user.setSalt(salt);

		user.setIsDelete(0);
		//补全剩余的四项日志
		user.setCreatedUser(username);
		user.setCreatedTime(now);
		user.setModifiedUser(username);
		user.setModifiedTime(now);

		//System.out.println("注册成功");

		Integer rows=userMapper.insert(user);
		//rows表示受影响的行数
		if(rows!=1){
			//插入数据失败 抛出异常,
			throw new InsertException("出现错误,请联系客服人员");


		}

	}
	/**
	 * 
	 * 登录
	 * @param password
	 * @param salt
	 * @return 加密后的密文
	 */


	@Override
	public User login(String username, String password) {


		// 调用userMapper的findByUsername()，根据参数username查询用户数据
		User result=userMapper.findByUserName(username);
		System.out.println("用户信息"+result);

		// 判断查询结果是否为null
		if(result==null){
			//用户净为空,抛出异常
			throw new UserNotFoundException("用户名为空");
		}

		// 判断查询结果中的isDelete是否为1
		if(result.getIsDelete()==1){
			throw new PassWordNotFoundException("未知异常");

		}
		//获取盐值
		String salt=result.getSalt();
		// 调用getMd5Password()将参数password和salt结合起来加密
		String md5Password = getMd5Password(password, salt);

		System.err.println("md5"+md5Password);


		// 判断查询结果中的密码，与以上加密得到的密码，是否不一致
		if (!result.getPassword().equals(md5Password)) {
			// 是：抛出PasswordNotMatchException
			throw new PassWordNotFoundException("密码错误");
		}

		// 创建新的User对象
		User user = new User();
		// 将查询结果中的uid、username、avatar封装到新User对象中
		user.setUid(result.getUid());
		user.setUsername(result.getUsername());
		user.setAvatar(result.getAvatar());
		// 返回新User对象
		return user;
	}

	private String getMd5Password(String password, String salt) {
		/**
		 * 密码加密  加密规则
		 * 1.无视原始密码的强度
		 * 2.使用uuid作为盐值,在原始密码左右拼接
		 * 3.循环三次加密
		 * 
		 */
		for (int i = 0; i < 3; i++) {
			password = DigestUtils
					.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
		}

		return password;
	}
	/**
	 * 
	 * 改密码的过程
	 * 
	 */

	@Override
	public void changedPw(Integer uid, String username, String oldPassword, String newPassword) {

		//先根据uid回去用户的基本信息
		User result=userMapper.findByUid(uid);
		//判断结果是否为null
		if(result==null){
			//是  抛出异常
			throw new UserNotFoundException("用户数据不存在");


		}
		//从查询结果中得到isDelete
		Integer isDelete=result.getIsDelete();
		//判断用户信息的isDelete是否为1
		if(isDelete==1){
			//是  就抛出异常
			throw new UserNotFoundException("还是没找到用户信息");
		}

		//从结果中得到盐值
		String salt=result.getSalt();
		//将盐值与旧密码结合
		String oldmd5password=getMd5Password(oldPassword, salt);

		//判断结果中的原密码password是否跟md5password不一致 用equals

		if(result.getPassword().equals(oldmd5password)){
			//是  就抛出异常
			throw new PassWordNotFoundException("原密码错误");

		}

		//将新密码跟盐值结合起来,得到新的密码加盐的密码newmd5pw
		String newmd5pw=getMd5Password(newPassword, salt);
		//获取当前时间   就是修改时间
		Date date=new Date();
		//开始执行业务  调用updatePasswordbyUid方法执行修改,rows表示受影响行数  1则为成功
		Integer rows=userMapper.updatePasswordbyUid(uid, newmd5pw, username, date);

		System.err.println("受影响行数为"+rows);
		//判断受影响行数是否不为1 
		if(rows!=1){
			//是  就抛出异常
			throw new UpdateException("更改密码失败");

		}



	}

	/**
	 * 开始执行更改用户信息操作
	 * @param uid 根据uid查询用户信息
	 * @return
	 */
	public User getByUid(Integer uid){
		// 根据参数uid，调用userMapper的findByUid()，查询数据
		User result=userMapper.findByUid(uid);
		System.out.println("21号的信息"+result);
		// 判断查询结果是否为null
		if(result==null){
			// 是：抛出UserNotFoundException

			throw new UserNotFoundException("该用户信息未找到");
		}
		// 判断查询结果中的isDelete是否为1
		if(result.getIsDelete()==1){

			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("用户信息未找到");
		}
		// 创建新的User对象
		User user=new User();
		// 将以上查询结果中的username/phone/email/gender封装到新User对象中

		user.setUsername(result.getUsername());
		user.setPhone(result.getPhone());
		user.setEmail(result.getEmail());
		user.setGender(result.getGender());

		System.out.println("21号的信息user"+user);
		// 返回新User对象
		return user;
	}


	/**
	 * 这个操作是修改用户信息
	 *  uid, username 为已有的客户信息
	 *  user封装新的用户信息  最后调用updateinfoByuid进行修改
	 */


	@Override
	public void changeInfo(Integer uid, String username, User user) {

		// 根据参数uid，调用userMapper的findByUid()，查询数据
		User result=userMapper.findByUid(uid);

		if(result==null){
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("用户未找到");

		}
		// 判断查询结果中的isDelete是否为1
		if(result.getIsDelete()==1){
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("未找到用户信息");


		}
		// 向参数user中补全数据:uid
		// 向参数user中补全数据:modifiedUser(username)
		// 向参数user中补全数据:modifiedTime(new Date())

		user.setUid(uid);
		user.setModifiedUser(username);
		user.setModifiedTime(new Date());

		// 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
		Integer rows=userMapper.updateinfoByuid(user);
		// 判断以上返回的受影响行数是否不为1
		if(rows!=1){
			//是  就抛出异常
			throw new UpdateException("更改密码失败");

		}


	}
	
	
	
	@Override
	public void changeAvatar(Integer uid, String username, String avatar) {
		//根据uid获得用户信息  
		User result=userMapper.findByUid(uid);

		if(result==null){

			throw new UserNotFoundException("未找到用户信息");
		}
		if(result.getIsDelete()==1){

			throw new UserNotFoundException("未找到用户信息");

		}


		Integer rows=userMapper.updateAvater(uid, avatar, username, new Date());
		if(rows!=1){
			throw new UpdateException("修改数据异常");

		}



	}










}
