package cn.tedu.store.mapper;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderTeam;

public interface OrderMapper {



	/**
	 * 杀入商品信息
	 */
	Integer insert(Order order);
	
	Integer insertOrderItem(OrderTeam orderteam);
}
