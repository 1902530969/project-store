package cn.tedu.store.AddressContorller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.entity.District;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.entity.UserAddress;
import cn.tedu.store.service.address.AddService;
@RequestMapping("addresses")
@RestController
public class AddressContorller extends BaseContorller{


	@Autowired
	private AddService addService;

	@RequestMapping("addNew")
	public JsonResult<Void> newAddress(UserAddress address
			,HttpSession session){
		Integer uid=getUidFromSession(session);
		String username=getUserNameFromSession(session);

		addService.addNew(uid, username, address);

		return  new JsonResult<Void>(OK);




	}
	@GetMapping({"","/"})
	public JsonResult<List<UserAddress>> getByUid(HttpSession session) {
		Integer uid = getUidFromSession(session);
		List<UserAddress> data = addService.getByUid(uid);
		return new JsonResult<>(OK, data);
	}
/**
 * 删除地址操作
 * @param aid
 * @param session
 * @return
 */
	@RequestMapping("{aid}/delete")
	public JsonResult<Void> delete(
			@PathVariable("aid") Integer aid,
			HttpSession session){

		String username=getUserNameFromSession(session);
		Integer uid=getUidFromSession(session);
		addService.deleteByAid(aid, uid, username);
		return new JsonResult<>(OK);

	}
}

