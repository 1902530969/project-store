package cn.tedu.store.OrderService;

import cn.tedu.store.entity.Order;

public interface OrderService {
	
	/**
	 * 用户订单处理
	 * @param aid
	 * @param cids
	 * @param uid
	 * @param username
	 * @return
	 */
	Order create(Integer aid, Integer[] cids, Integer uid, String username);

}
