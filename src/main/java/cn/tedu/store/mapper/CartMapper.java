package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.cart.CartVO;

public interface CartMapper {

	/**
	 * 购物车持久层接口
	 */


	Integer insert(Cart cart);
	
	/**
	 * 
	 * @param cid
	 * @param uid
	 * @return
	 */
	Integer updateNumbyCid(
			@Param("cid")Integer cid,
			@Param("num")Integer num);
		
	Cart findByUidAndPid(
			@Param("uid")Integer uid,
			@Param("pid")Integer pid
			);
	
	
	
	
	/**
	 * 查询目前购物车的信息
	 * @param uid
	 * @return
	 */
	List<CartVO> findVOByUid(Integer uid);
	
	
	
	
	Cart finByCid(Integer cid);
	
	
	
	
	List<CartVO> findVOByCids(Integer[] cids);

	
}
