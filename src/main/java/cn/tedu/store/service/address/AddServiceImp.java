package cn.tedu.store.service.address;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cn.tedu.store.address.ex.AccessDeniedException;
import cn.tedu.store.address.ex.AddressCountLimitException;
import cn.tedu.store.address.ex.AddressNotFoundException;
import cn.tedu.store.address.ex.DeleteFalseException;
import cn.tedu.store.address.ex.InsertException;
import cn.tedu.store.entity.UserAddress;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.District.DistrictService;
import cn.tedu.store.service.ex.UpdateException;
@Service
public class AddServiceImp implements AddService {


	@Value("${project.max_count}")
	public int countMax;//在配置文件中设置地址的上限

	@Autowired
	private	AddressMapper mapper;
	@Autowired
	private DistrictService disService;

	@Override
	public void addNew(Integer uid, String username, UserAddress address) {

		System.out.println("开始添加数据");

		// 根据参数uid调用addressMapper的countByUid()方法，统计当前用户的收货地址数据的数量
		Integer count =mapper.countByUid(uid);
		System.out.println(count);
		// 判断数量是否达到上限值
		if(count>=countMax){
			// 是：抛出AddressCountLimitException
			throw new AddressCountLimitException("地址已经达到上限");

		}


		// 补全数据：省、市、区的名称
		String provinceName=disService.getNameByCode(address.getAreaCode());
		String cityName=disService.getNameByCode(address.getAreaCode());
		String areaName=disService.getNameByCode(address.getAreaCode());

		address.setProvinceName(provinceName);
		address.setCityName(cityName);
		address.setAreaName(areaName);

		// 补全数据：将参数uid封装到参数address中
		address.setUid(uid);
		// 补全数据：根据以上统计的数量，得到正确的isDefault值，并封装
		Integer isdelete=count==0?1:0;
		address.setIsDefault(isdelete);
		System.out.println("IsDefault已经添加");

		// 补全数据：4项日志
		Date date=new Date();

		address.setCreatedUser(username);
		address.setCreatedTime(date);
		address.setModifiedUser(username);
		address.setModifiedTime(date);
		// 调用addressMapper的insert()方法插入收货地址数据，并获取返回的受影响行数
		Integer rows=mapper.insert(address);
		// 判断受影响行数是否不为1
		if(rows!=1){
			// 是：抛出InsertException
			throw new InsertException("插入数据异常");

		}





	}

	@Override
	public List<UserAddress> getByUid(Integer uid) {

		List<UserAddress> list=mapper.findByUid(uid);
		for (UserAddress userAddress : list) {
			userAddress.setUid(uid);
			userAddress.setProvinceCode(null);
			userAddress.setCityCode(null);
			userAddress.setAreaCode(null);
			userAddress.setCreatedUser(null);
			userAddress.setCreatedTime(null);
			userAddress.setModifiedUser(null);
			userAddress.setModifiedTime(null);




		}
		return list;
	}



	/**
	 * 
	 * 
	 * 
	 */
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {


		// 根据参数aid，调用addressMapper中的findByAid()查询收货地址数据
		UserAddress result=mapper.findbyAid(aid);
		// 判断查询结果是否为null
		if(result==null){
			// 是：抛出AddressNotFoundException

			throw new AddressNotFoundException("未找到用户地址");
		}


		// 判断查询结果中的uid与参数uid是否不一致(使用equals()判断)
		Integer getUid=result.getUid();

		if(!getUid.equals(uid)){

			// 是：抛出AccessDeniedException：非法访问
			throw new AccessDeniedException("非法访问");
		}



		// 调用addressMapepr的updateNonDefaultByUid()将该用户的所有收货地址全部设置为非默认，并获取返回的受影响的行数
		Integer rows=mapper.UpDdteDefaultbyUid(uid);




		// 判断受影响的行数是否小于1(不大于0)
		if(rows<1){
			// 是：抛出UpdateException

			throw new UpdateException("未找到用户西信息1");
		}


		Date date=new Date();//获取当前时间
		// 调用addressMapepr的updateDefaultByAid()将指定aid的收货地址设置为默认，并获取返回的受影响的行数
		Integer row=mapper.UpDdteDefaultbyAid(aid, username, date);
		// 判断受影响的行数是否不为1
		if(rows!=1){
			// 是：抛出UpdateException

			throw new UpdateException("未找到用户西信息");
		}



	}

	/**
	 * 删除地址的操作
	 * 
	 */
	@Override
	@Transactional
	public void deleteByAid(Integer aid, Integer uid, String username) {

		// 根据参数aid，调用持久层的findByAid()查询收货地址数据
		UserAddress result=mapper.findbyAid(aid);

		// 判断查询结果是否为null
		if(result==null){
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException("未找到地址信息");
		}
		// 判断查询结果中的uid与参数uid是否不同(使用equals()对比)
		if(!result.getUid().equals(uid)){
			// 是：抛出AccessDeniedException
			throw new AccessDeniedException("非法访问");
		}// 根据参数aid，调用持久层的deleteByAid()执行删除，并获取返回的受影响的行数

		Integer rows=mapper.deleteByAid(aid);
		// 判断受影响的行数是否不为1
		if(rows!=1){
			// 是：抛出DeleteException
			throw new DeleteFalseException("删除失败");

		}
		// 判断查询结果中的isDefault是否为0

		if(result.getIsDefault()==0){

			return;

		}
		// 调用持久层的countByUid()统计目前还有多少收货地址
		Integer count=mapper.countByUid(uid);
		// 判断目前的收货地址的数量是否为0
		if(count==0){
			return;

		}

		// 调用持久层的findLastModified()找出最近修改的收货地址数据
		result=mapper.findLastModifiledTime(uid);

		// 从以上查询结果中找出aid属性值
		Integer lastAid=result.getAid();
		// 根据以上aid，调用持久层的updateDefaultByAid()把这条收货地址设置为默认，并获取返回的受影响的行数

		Integer row=mapper.UpDdteDefaultbyAid(lastAid, username, new Date());
		// 判断受影响的行数是否不为1
		if(row!=1){
			// 是：抛出UpdateException
			throw new UpdateException("修改失败异常");
		}
	}

	@Override
	public UserAddress getByAid(Integer aid, Integer uid) {
		
		UserAddress address= mapper.findbyAid(aid);
				
		if(address==null){
			throw new AccessDeniedException("尝试访问的地址不存在");
			
		}
		if (!address.getUid().equals(uid)) {
			throw new AccessDeniedException("非法访问");
		}
		
		address.setProvinceCode(null);
		address.setCityCode(null);
		address.setAreaCode(null);
		address.setCreatedUser(null);
		address.setCreatedTime(null);
		address.setModifiedUser(null);
		address.setModifiedTime(null);
		
		
		
		
		
		
		return address;
	}

}
