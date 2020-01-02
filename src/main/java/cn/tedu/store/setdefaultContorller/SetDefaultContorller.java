package cn.tedu.store.setdefaultContorller;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.service.address.AddService;




@RestController
@RequestMapping("addresses")
public class SetDefaultContorller extends BaseContorller {

	@Autowired
	AddService addService;


@RequestMapping("{aid}/is_default")
	public JsonResult<Void> setDefault(Integer aid,
			HttpSession session){
		Integer uid=getUidFromSession(session);
		String username=getUserNameFromSession(session);
		addService.setDefault(aid, uid, username);

		return new JsonResult<>(OK);




	}


}
