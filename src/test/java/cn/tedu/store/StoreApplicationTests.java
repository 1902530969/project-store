package cn.tedu.store;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.CartService.CartService;
import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderTeam;
import cn.tedu.store.entity.Product;
import cn.tedu.store.entity.User;
import cn.tedu.store.entity.UserAddress;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.product.service.ProductService;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.cart.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	DataSource	dataSource;
	@Test
	public void getColl() throws SQLException{
		Connection conn=dataSource.getConnection();
		System.out.println(conn);

	}

	@Autowired
	private	UserMapper userMapper;

	@Test
	public void insert(){
		User user=new User();
		user.setUsername("张三");
		user.setPassword("616515");
		Integer rows=userMapper.insert(user);
		System.out.println(rows);


	}

	@Test
	public void findByUsername() {
		String username = "张三";
		User result = userMapper.findByUserName(username);
		System.err.println(result);
	}
	@Autowired
	AddressMapper mapper;


	@Test
	public void insertadd(){

		UserAddress add=new UserAddress();
		add.setUid(1);
		add.setName("王八");
		add.setTag("66666");

		Integer rows=mapper.insert(add);
		System.out.println(rows);



	}



	@Autowired
	ProductMapper Pmapper;

	@Test
	public void findB() {
		/**
		 * 查询热门商品列表
		 */
		List<Product> result = Pmapper.findHostList();

		for (Product product : result) {
			System.out.println(result);


		}


	}

	@Test
	public void findc() {
		Integer id=1000;

		Product result=Pmapper.findById(id);
		System.out.println(result);

	}
	@Autowired
	ProductService pService;



	@Test
	public void findcd() {
		Integer id=10000005;

		Product result=pService.getById(id);
		System.err.println(result);

	}


	@Test
	public void findcdefg() {
		Integer id=10000017;

		Product result=pService.getById(id);
		System.err.println(result);

	}







	@Autowired
	CartMapper cMapper;

	@Test
	public void findcdd() {
		Cart cart=new Cart();
		cart.setUid(2);
		cart.setPid(3);
		cart.setCreatedTime(new Date());

		Integer result=cMapper.insert(cart);
		System.err.println(result);

	}



	@Test
	public void findcde() {
		Integer uid=2;
		Integer pid=3;
		Cart result=cMapper.findByUidAndPid(uid, pid);
		System.err.println(result);

	}
	@Test
	public void findcdec() {
		Integer cid=2;
		Integer num=5;
		Integer result=cMapper.updateNumbyCid(cid, num);
		System.err.println(result);

	}
	@Autowired
	CartService cService;
	@Test
	public void inserts() {
		Cart cart = new Cart();
		cart.setUid(21);
		cart.setPid(10000017);
		cart.setNum(5);
		cart.setPrice(5L);
		Integer rows = cMapper.insert(cart);
		System.err.println("rows=" + rows);
	}



	@Test
	public void findcdece() {
		try {
			Integer pid = 10000015;
			Integer amount = 3;
			Integer uid = 6;
			String username = "不知道";
			cService.addToCart(pid, amount, uid, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getSimpleName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getVOByUid() {
		Integer uid = 21;
		List<CartVO> list = cService.getVOByUid(uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	@Test
	public void findddd(){
		try {
			Integer cid = 12;
			Integer uid = 21;
			String username = "不知道";
			Integer num = cService.addNum(cid, uid, username);
			System.err.println("OK. New num=" + num);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getSimpleName());
			System.err.println(e.getMessage());
		}

	}

	@Test
	public void getVOByCidss() {
		Integer[] cids = {12,13};
		List<CartVO> list = cMapper.findVOByCids(cids);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	
	
	@Test
	public void getVOByCids() {
		Integer[] cids = {12,13,14};
		Integer uid = 21;
		List<CartVO> list = cService.getVOByCids(cids, uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	@Autowired
	private OrderMapper omapper;
	
	@Test
	public void insertOrder() {
		Order order = new Order();
		order.setUid(1);
		order.setRecvName("小王");
		Integer rows = omapper.insert(order);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void insertOrderItem() {
		OrderTeam orderItem = new OrderTeam();
		orderItem.setOid(1);
		orderItem.setPid(2);
		orderItem.setTitle("高档铅笔");
		Integer rows = omapper.insertOrderItem(orderItem);
		System.err.println("rows=" + rows);
	}
}
