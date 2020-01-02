package cn.tedu.AddressTset;

import org.apache.tomcat.jni.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.UserAddress;
import cn.tedu.store.mapper.AddressMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tset {

	@Autowired
	AddressMapper mapper;


	@Test
	public void insert(){

		UserAddress add=new UserAddress();
		add.setUid(1);
		add.setName("王八");
		add.setTag("66666");

		Integer rows=mapper.insert(add);
		System.out.println(rows);



	}




}
