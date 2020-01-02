package cn.tedu.store.cartVOcontorller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.CartService.CartService;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.vo.cart.CartVO;

@RestController
@RequestMapping("carts")
public class CartVOContorller extends BaseContorller {
@Autowired
	 private CartService service;

	
	@GetMapping({"", "/"})
	public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
		// 从Session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务对象执行查询数据
		List<CartVO> data = service.getVOByUid(uid);
		// 返回成功与数据
		return new JsonResult<>(OK, data);
	}
	
	
	
	
	
}
