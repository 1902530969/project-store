package cn.tedu.store.product.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.address.ex.ProductNotFoundException;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.ProductMapper;


@Service
public class ProductImp implements ProductService{

	@Autowired
	private	ProductMapper mapper;
	


	@Override
	public Product getById(Integer id) {
		System.out.println("开始查询");
		// 根据参数id调用私有方法执行查询，获取商品数据
		Product product=findById(id);

		System.out.println(product+"11111111111111111");
		// 判断查询结果是否为null
		if(product==null){
			// 是：抛出ProductNotFoundException
			throw new ProductNotFoundException("商品信息未找到");
		}
		product.setPriority(null);
		product.setCreatedUser(null);
		product.setCreatedTime(null);
		product.setModifiedUser(null);
		product.setModifiedTime(null);

		System.out.println("判断结束了,开始返回");
		// 返回查询结果
		return product;
	}

	public List<Product> getHostList() {

		List<Product> list= findHostList();

		for (Product product : list) {
			product.setCreatedUser(null);
			product.setCreatedTime(null);
			product.setModifiedUser(null);
			product.setModifiedTime(null);
		}


		return list;
	}





	/**
	 * 
	 * 商品查询
	 * @param id
	 * @return
	 */
	private Product findById(Integer id){

		return mapper.findById(id);

	}
	/**
	 * 私有方法调用持久层的
	 * @return
	 */
	private List<Product> findHostList() {

		return mapper.findHostList();
	}




}
