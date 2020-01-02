package cn.tedu.store.product.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.entity.Product;
import cn.tedu.store.product.service.ProductService;



@RestController
@RequestMapping("products")
public class ProductContorller extends BaseContorller {

	@Autowired
	private ProductService service;
	
	@RequestMapping("hot_list")
	public JsonResult<List<Product>> show(){

	List<Product> data=service.getHostList();

		return new JsonResult<>(OK,data);
	}

	@RequestMapping("{id}/details") 
	public JsonResult<Product> findByid(@PathVariable("id") Integer id){
		
		Product	data=service.getById(id);
		
		
		
		return 	new JsonResult<Product>(OK,data);
		
		
		
		
	}
	
	
	
}
