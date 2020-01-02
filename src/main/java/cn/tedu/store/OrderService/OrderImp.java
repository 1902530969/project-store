package cn.tedu.store.OrderService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.CartService.CartService;
import cn.tedu.store.address.ex.InsertException;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderTeam;
import cn.tedu.store.entity.UserAddress;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.address.AddService;
import cn.tedu.store.vo.cart.CartVO;
@Service
public class OrderImp implements OrderService{
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private AddService aService;
	@Autowired
	private CartService cartService;


	@Override
	public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
		// 创建当前时间对象
				Date now = new Date();
				
				// 根据cids查询所勾选的购物车列表中的数据
				List<CartVO> carts = cartService.getVOByCids(cids, uid);
				
				// 计算这些商品的总价
				long totalPrice = 0;
				for (CartVO cart : carts) {
					totalPrice += cart.getRealPrice() * cart.getNum();
				}
				
				// 创建订单数据对象
				Order order = new Order();
				// 补全数据：uid
				order.setUid(uid);
				// 查询收货地址数据
				UserAddress address = aService.getByAid(aid, uid);
				// 补全数据：收货地址相关的6项
				order.setRecvName(address.getName());
				order.setRecvPhone(address.getPhone());
				order.setRecvProvince(address.getProvinceName());
				order.setRecvCity(address.getCityName());
				order.setRecvArea(address.getAreaName());
				order.setRecvAddress(address.getAddress());
				// 补全数据：totalPrice
				order.setTotalPrice(totalPrice);
				// 补全数据：status
				order.setStatus(0);
				// 补全数据：下单时间
				order.setOrderTime(now);
				// 补全数据：日志
				order.setCreatedUser(username);
				order.setCreatedTime(now);
				order.setModifiedUser(username);
				order.setModifiedTime(now);
				// 插入订单数据
				insert(order);
				
				// 遍历carts，循环插入订单商品数据
				for (CartVO cart : carts) {
					// 创建订单商品数据
					OrderTeam item = new OrderTeam();
					// 补全数据：oid(order.getOid())
					item.setOid(order.getOid());
					// 补全数据：pid, title, image, price, num
					item.setPid(cart.getPid());
					item.setTitle(cart.getTitle());
					item.setImage(cart.getImage());
					item.setPrice(cart.getRealPrice());
					item.setNum(cart.getNum());
					// 补全数据：4项日志
					item.setCreatedUser(username);
					item.setCreatedTime(now);
					item.setModifiedUser(username);
					item.setModifiedTime(now);
					// 插入订单商品数据
					insertOrderItem(item);
				}
		return order;
	}




	private void insert(Order order){
		Integer rows = orderMapper.insert(order);
		if (rows != 1) {
			throw new InsertException("插入订单数据时出现未知错误，请联系系统管理员");
		}



	}

	private void insertOrderItem(OrderTeam orderteam){
		Integer rows = orderMapper.insertOrderItem(orderteam);
		if (rows != 1) {
			throw new InsertException("插入订单商品数据时出现未知错误，请联系系统管理员");
		}



	}







}
