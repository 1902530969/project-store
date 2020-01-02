package cn.tedu.store.product.service;

import java.util.List;

import cn.tedu.store.entity.Product;

public interface ProductService {
	
	
	
	List<Product> getHostList();
	
	Product getById(Integer id);
	
}
