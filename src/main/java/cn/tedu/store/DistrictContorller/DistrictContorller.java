package cn.tedu.store.DistrictContorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.entity.District;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.service.District.DistrictService;

@RequestMapping("districts")
@RestController
public class DistrictContorller extends BaseContorller {


	@Autowired
	private DistrictService disService;


	@GetMapping({"","/"})
	public JsonResult<List<District>> findparent(String parent){


		List<District> data=disService.findByParent(parent);



		return new JsonResult<>(OK,data);





	}



}
