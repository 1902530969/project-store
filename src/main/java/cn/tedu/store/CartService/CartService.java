package cn.tedu.store.CartService;

import java.util.List;

import cn.tedu.store.vo.cart.CartVO;

public interface CartService {
	
	
	
	void addToCart(Integer pid, Integer amount, Integer uid, String username);
	
	
	List<CartVO> getVOByUid(Integer uid);
	
	Integer addNum(Integer cid, Integer uid, String username);

	/**
	 * 根据若干个购物车数据id查询详情的列表
	 * @param cids 若干个购物车数据id
	 * @param uid 当前登录的用户的id
	 * @return 匹配的购物车数据详情的列表
	 */
	
	List<CartVO> getVOByCids(Integer[] cids, Integer uid);
	
	
}
