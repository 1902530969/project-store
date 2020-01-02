package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Product;


public interface ProductMapper {

	
	
	/**查询商品列表的持久层
	 * 查询商品列表的前四名
	 * @return
	 */
	List<Product> findHostList();
	
	
	Product findById(Integer id);
	
}
