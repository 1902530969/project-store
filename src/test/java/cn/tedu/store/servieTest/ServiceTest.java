package cn.tedu.store.servieTest;



import java.util.Date;
import java.util.List;

import org.apache.tomcat.jni.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;
import cn.tedu.store.entity.User;
import cn.tedu.store.entity.UserAddress;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.mapper.DisrectMapper;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.District.DistrictService;
import cn.tedu.store.service.address.AddService;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

	@Autowired
	AddService addService;


	@Autowired
	private IUserService service;
	/**
	 *建议声明接口  就可以直接调用接口下边的实现的方法
	 *例如  在这声明IUserService接口,调用UserImp实现类的reg方法
	 * 
	 */



	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("王八蛋");
			user.setPassword("1234");
			service.reg(user);
			System.err.println("注册成功！");
		} catch (ServiceException e) {
			System.err.println("注册失败！" + e.getClass().getSimpleName());
		}

	}
	@Autowired
	private UserMapper userMapper;



	@Test
	public void updatePasswordByUid() {
		Integer uid = 13;
		String password = "1234";
		String modifiedUser = "超级管理员";
		Date modifiedTime = new Date();
		Integer rows = userMapper.updatePasswordbyUid(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}

	@Test
	public void findByUid() {
		Integer uid = 17;
		User result =userMapper.findByUid(uid);
		System.err.println(result);
	}	
	/**
	 * 修改密码测试
	 */
	@Test
	public void changePassword() {
		try {
			Integer uid = 13;
			String username = "密码管理员";
			String oldPassword = "111111";
			String newPassword = "8888";
			service.changedPw(uid, username, oldPassword, newPassword);
			System.err.println("OK");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getSimpleName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void updateInfoByUid() {
		User user = new User();
		user.setUid(21);
		user.setPhone("13800138019");
		user.setEmail("root@tedu.cn");
		user.setGender(1);
		user.setModifiedUser("系统管理员");
		user.setModifiedTime(new Date());
		Integer rows = userMapper.updateinfoByuid(user);
		System.err.println("rows=" + rows);
	}

	@Test
	public void findUsers() {
		Integer uid = 21;
		User rows = userMapper.findByUid(uid);
		System.err.println("rows=" + rows);
	}


	@Test
	public void findUss() {
		Integer uid = 21;
		User rows = service.getByUid(uid);
		System.err.println("rows=" + rows);
	}


	@Test
	public void findUsert() {

		Integer uid = 21;
		String avatar="6546468466";
		String modifiedUser="系统";
		Date modifiedTime=new Date();


		Integer rows=userMapper.updateAvater(uid, avatar, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}


	@Test
	public void addUsertAddress() {

		Integer uid =1;
		String username="天使";

		UserAddress address=new UserAddress();
		address.setTag("1545");
		address.setCityCode("河南");
		addService.addNew(uid, username, address);
		System.out.println("添加成功");

	}


	@Test
	public void addnew() {
		try {
			Integer uid = 1;
			String username = "管理";
			UserAddress address=new UserAddress();
			address.setName("小新");
			addService.addNew(uid, username, address);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getSimpleName());
			System.err.println(e.getMessage());
		}
	}

	@Autowired
	AddressMapper addMapper;


	@Test
	public void insert() {
		UserAddress address=new UserAddress();
		address.setUid(3);
		address.setName("花公公");
		Integer rows = addMapper.insert(address);
		System.err.println("rows=" + rows);
	}

	@Autowired
	private	DisrectMapper disMapper;

	@Test
	public void insertDis() {


		String parent="86";
		List<District> list=disMapper.findByParent(parent);
		System.err.println("rows=" +  list);
	}


	@Autowired
	private	DistrictService disService;

	@Test
	public void getByParent() {
		try {
			String parent = "86";
			List<District> list =disService.findByParent(parent);
			System.err.println("count=" + list.size());
			for (District item : list) {
				System.err.println(item);
			}
		} catch (ServiceException e) {
			System.err.println(e.getClass().getSimpleName());
			System.err.println(e.getMessage());
		}
	}



	@Test
	public void findaddByUid() {
		Integer uid = 1;
		List<UserAddress> list = addMapper.findByUid(uid);
		System.err.println("count=" + list.size());
		for (UserAddress userAddress : list) {
			System.out.println(userAddress);
		}
	}

	@Test
	public void getByUid() {
		Integer uid = 19;
		List<UserAddress> list =addService.getByUid(uid);
		System.err.println("count=" + list.size());
		for (UserAddress userAddress : list) {
			System.err.println(list);


		}


	}



	@Test
	public void findNameByCode() {
		String code = "540000";
		String name = disMapper.findByName(code);
		System.err.println(name);
	}

	@Test
	public void deletAdd(){
		Integer aid=4;

		Integer rows=	addMapper.deleteByAid(aid);
		System.err.println(rows);	

	}


	@Test
	public void deletAdds(){
		Integer uid=21;

		UserAddress rows=	addMapper.findLastModifiledTime(uid);
		System.err.println(rows);	

	}

	
}







