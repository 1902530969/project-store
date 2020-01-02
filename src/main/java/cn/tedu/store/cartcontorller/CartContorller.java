package cn.tedu.store.cartcontorller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.CartService.CartService;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.vo.cart.CartVO;

@RestController
@RequestMapping("carts")
public class CartContorller extends BaseContorller {

	@Autowired
	CartService service;

	@RequestMapping("add_to_cart")
	public JsonResult<Void> addCart(Integer pid, Integer amount, HttpSession session){

		Integer uid=getUidFromSession(session);
		System.out.println("uid是"+uid);
		String username=getUserNameFromSession(session);

		service.addToCart(pid, amount, uid, username);

		return new JsonResult<Void>(OK);
	}


	@RequestMapping("{cid}/num/add")
	public JsonResult<Integer> addNum(
			@PathVariable("cid") Integer cid,
			HttpSession session){

		Integer uid=getUidFromSession(session);

		String username=getUserNameFromSession(session);

		Integer data=	service.addNum(cid, uid, username);

		return new JsonResult<>(OK,data);



	}
	@GetMapping("list")
	public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
		// 从Session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务对象执行查询数据
		List<CartVO> data = service.getVOByCids(cids, uid);
		// 返回成功与数据
		return new JsonResult<>(OK, data);
	}






}
