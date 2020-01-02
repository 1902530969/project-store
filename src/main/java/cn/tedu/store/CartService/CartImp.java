package cn.tedu.store.CartService;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.address.ex.AccessDeniedException;
import cn.tedu.store.address.ex.CartNotFoundException;
import cn.tedu.store.address.ex.InsertException;
import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.product.service.ProductService;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.cart.CartVO;

@Service
public class CartImp implements CartService{


	@Autowired
	private CartMapper mapper;
	@Autowired
	private ProductService service;



	/**
	 * pid  商品的id
	 * uid  用户的id
	 * amount 新增商品的数量
	 */
	@Override
	public void addToCart(Integer pid, Integer amount, Integer uid, String username) {
		System.out.println("开始执行购物车操作");

		// 根据参数pid和uid查询购物车中原有的的数据
		Cart result=findByUidAndPid(uid, pid);
		// 判断查询结果是否为null
		Cart cart=new Cart();
		if(result==null){
			// 是：表示该用户并未将该商品添加到购物车
			System.out.println("开始把商品加到购物车");
			// -- 创建Cart对象// -- 封装数据：uid,pid,amount

			cart.setUid(uid);
			cart.setPid(pid);
			cart.setNum(amount);//传过来的新数量
			System.out.println("插入成功");
			// -- 调用productService.getById()查询商品数据，得到商品价格
			Product product=service.getById(pid);

			//Product product= service.getById(id);
			//从结果中获取price// -- 封装数据：price
			cart.setPrice(product.getPrice());
			// -- 封装数据：4个日志
			cart.setCreatedUser(username);
			cart.setCreatedTime(new Date());
			cart.setModifiedUser(username);
			cart.setModifiedTime(new Date());
			// -- 调用insert(cart)执行将数据插入到数据表中
			insert(cart);

		}else{

			//取出原来购物车的数据
			// -- 从查询结果中取出原数量，与参数amount相加，得到新的数量
			//得到原来购物出的数量
			Integer oldCount=result.getNum();

			System.out.println("原来的数量是"+oldCount);

			Integer NewNum=amount+oldCount;
			System.out.println("总的数量是"+NewNum);
			Integer cid=result.getCid();
			// -- 执行更新数量
			updateNumbyCid(cid,NewNum);

			System.out.println("执行完毕");
		}
	}

	/**
	 * 以下三个是持久层的方法  需要定义为私有的
	 * @param cart
	 * @return
	 * 数据插入到数据表中
	 */
	private	void insert(Cart cart){

		Integer rows=mapper.insert(cart);
		if(rows!=1){

			throw new InsertException("插入数据失败");
		}



	}
	/**
	 * 修改
	 * @param cid
	 * @param num
	 * @return
	 */
	private void updateNumbyCid(Integer cid,Integer num){


		Integer rows=mapper.updateNumbyCid(cid, num);
		if(rows!=1){

			throw new UpdateException("修改数量数据数据失败");
		}


	}
	/*
	 * //取出购物车的数据
	 */
	private	Cart findByUidAndPid(Integer uid,Integer pid){


		return mapper.findByUidAndPid(uid, pid);



	}

	@Override
	public List<CartVO> getVOByUid(Integer uid) {

		return findVOByUid(uid);
	}


	/**
	 *将持久层的方法赋值过来  私有化 方便调用
	 * @param uid
	 * @return
	 */
	private	List<CartVO> findVOByUid(Integer uid){


		return mapper.findVOByUid(uid);

	}




	/**
	 * 购物车的操作
	 * ------------------------------------------------------
	 */
	public Cart findByCid(Integer cid){

		return mapper.finByCid(cid);


	}

	@Override
	public Integer addNum(Integer cid, Integer uid, String username) {
		// 调用findByCid(cid)根据参数cid查询购物车数据
				Cart result = findByCid(cid);
				// 判断查询结果是否为null
				if (result == null) {
					// 是：抛出CartNotFoundException
					throw new CartNotFoundException(
						"尝试访问的购物车数据不存在");
				}
				// 判断查询结果中的uid与参数uid是否不一致
				if (!result.getUid().equals(uid)) {
					// 是：抛出AccessDeniedException
					throw new AccessDeniedException("非法访问");
				}

				// 可选：检查商品的数量是否大于多少(适用于增加数量)或小于多少(适用于减少数量)
				// 根据查询结果中的原数量增加1得到新的数量num
				Integer num = result.getNum() + 1;

				// 创建当前时间对象，作为modifiedTime
				Date now = new Date();
				// 调用updateNumByCid(cid, num)执行修改数量
				updateNumbyCid(cid, num);
				// 返回新的数量
				return num;
}

		
		
		
	


	@Override
	public List<CartVO> getVOByCids(Integer[] cids,Integer uid) {
		
		List<CartVO> list = findVOByCids(cids);
		
		Iterator<CartVO> it=list.iterator();
		while (it.hasNext()) {
			CartVO cart = it.next();
			if (!cart.getUid().equals(uid)) {
				it.remove();
			}
		}
		
		
		return  list;
	}
	/**
	 * 根据若干个购物车数据id查询详情的列表
	 * @param cids 若干个购物车数据id
	 * @return 匹配的购物车数据详情的列表
	 */


	private List<CartVO> findVOByCids(Integer[] cids){
		
		return mapper.findVOByCids(cids);
		


	}

}
