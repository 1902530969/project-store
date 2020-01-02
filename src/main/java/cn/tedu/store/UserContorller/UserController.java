package cn.tedu.store.UserContorller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.BaseContorller.BaseContorller;
import cn.tedu.store.contorller.ex.FileEmptyException;
import cn.tedu.store.contorller.ex.FileSizeException;
import cn.tedu.store.contorller.ex.FileStateException;
import cn.tedu.store.contorller.ex.FileTyeException;
import cn.tedu.store.contorller.ex.FileUploadException;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;

@RequestMapping("users")
@RestController
public class UserController extends BaseContorller{

	@Autowired
	private IUserService service;

	@RequestMapping("reg")
	public JsonResult<Void> reg(User user){
		/*JsonResult<Void> res =new JsonResult<>();
		try{
			//响应成功
			service.reg(user);
			res.setState(1);

		}catch(userNameDuplicateException e){
			//用户名冲突被占用  异常的信息需要在业务层UserImp类中表现
			res.setState(2);
			res.setMessage(e.getMessage());

		}catch(InsertException e){
			//插入数据异常   异常的信息需要在业务层UserImp类中表现
			res.setState(3);
			res.setMessage(e.getMessage());
		}*/
		//调取业务对象执行注册
		service.reg(user);
		//返回
		return  new JsonResult<>(OK);
	}
	/**
	 * 有关ServiceException异常的统一处理 这个是原本是处理注册异常
	 * 转换为BaseContorller根控制器  这样登录的时候也可以调取使用
	 * @param e 
	 * @return
	 * 
	 * 将此段代码转移到BaseContorller中 并让UserController继承BaseContorller
	 */
	/*@ExceptionHandler(ServiceException.class)
	public JsonResult<Void> exception(Throwable e){
		JsonResult<Void> jr=new JsonResult<>();

		if(e instanceof userNameDuplicateException){
			jr.setState(2000);
			//jr.setMessage(e.getMessage());
		}else if(e instanceof InsertException){

			jr.setState(3000);
			//jr.setMessage(e.getMessage());
		}

		return 	jr;
	}
	 */

	/**
	 * 当用户发出login请求 这边就会收到
	 * String username,String password两个参数,就可以开始执行service业务
	 * 当执行业务中出现异常,则会运行baseConller中的错误执行代码
	 * 
	 * @param username
	 * @param password
	 * @return JsonResult
	 */
	@RequestMapping("login")
	public JsonResult<User> login(String username,String password,
			HttpSession seccion){
		//	//调取业务对象开始执行登录程序,早jsonresult中添加state data的构造方法
		service.login(username, password);//调取service接口中的方法
		//调取业务对象的方法执行登录,并且获得返回值
		User data=service.login(username, password);
		System.out.println("data的值"+data);
		//登录成功,将uid和username存入到seccion中
		seccion.setAttribute("uid", data.getUid());
		seccion.setAttribute("username", data.getUsername());

		//将以上返回值和状态码ok封装到响应结果中,并返回
		return new JsonResult<>(OK,data);
	}
	/**
	 * 执行修改密码的任务
	 * 
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @param session  将存进去的uid跟username取出来  需要强制转换
	 * @return
	 */


	@RequestMapping("change_pw")
	public JsonResult<Void> changePassW(String oldPassword,
			String newPassword,HttpSession session	){
		// 调用session.getAttribute()获取uid和username
		//转到BaseContorler 对以后涉及uid的使用方便
		Integer uid=getUidFromSession(session);
		String username=getUserNameFromSession(session);
		// 调用业务对象执行修改密码  调取service接口中的方法
		service.changedPw(uid, username, 
				oldPassword, newPassword);
		// 返回成功
		return new JsonResult<Void>(OK);

	}

	/**
	 * 
	 * @param session 已经在BaseContorller定义了简单的从session中获取lid的方法
	 * @return
	 */
	@RequestMapping("get_by_uid")
	public JsonResult<User> getUserInfo(HttpSession session){
		//已经在BaseContorller定义了简单的从session中获取lid的方法
		Integer uid=getUidFromSession(session);
		// 调用业务对象执行,调取service接口中的方法
		User data=service.getByUid(uid);

		return new JsonResult<>(OK,data);

	}

	@RequestMapping("change_info")
	public JsonResult<User> changeInfoM(HttpSession session,User user){
		//已经在BaseContorller定义了简单的从session中获取uid的方法

		Integer uid=getUidFromSession(session);
		String username = getUserNameFromSession(session);
		// 调用业务对象执行,调取service接口中的方法+

		service.changeInfo(uid, username, user);

		return new JsonResult<>(OK);

	}
	/**
	 * 上传头像的操作
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException 
	 */

	public static final	int AVATAR_MAXSIZE=600*1024;//上传文件头像大小的上限值
	public static final List<String> AVATAR_TYPE=new ArrayList<String>();
	//只需要执行一次 用静态块 或者用构造方法  也是执行一次
	static{

		AVATAR_TYPE.add("image/jpg");
		AVATAR_TYPE.add("image/png");
		AVATAR_TYPE.add("image/bmp");
		AVATAR_TYPE.add("image/gif");
		AVATAR_TYPE.add("image/jpeg");

	}



	@PostMapping("change_avatar")
	public JsonResult<String> changeAvatar(
			@RequestParam("file") MultipartFile file,
			HttpSession session
			) throws IOException{
	/*	System.out.println("开始执行11111111111111");
		// 判断上传的文件是否为空
		if (file.isEmpty()) {
			// 是：抛出异常
			throw new FileEmptyException("上传的头像文件不允许为空");
		}
		System.out.println("开始执行22222222");
		// 判断上传的文件大小是否超出限制值
		if (file.getSize() > AVATAR_MAXSIZE) {
			// 是：抛出异常
			throw new FileSizeException("不允许上传超过" + (AVATAR_MAXSIZE / 1024) + "KB的头像文件");
		}
		System.out.println("开始执行3333333333");
		// 判断上传的文件类型是否超出限制
		String contentType = file.getContentType();
		if (!AVATAR_TYPE.contains(contentType)) {
			// 是：抛出异常
			throw new FileTyeException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPE);
		}


*/

		System.out.println("开始执行上产头像操作");

		// 保存头像的文件夹
		String parent=session.getServletContext().getRealPath("upload");
		System.out.println("开始新建文件夹");
		//判断当前文件夹是否存在
		File dir = new File(parent);
		if (!dir.exists()) {
			//不存在则创建一个upload
			dir.mkdirs();
		}
		String suffix="";
		//得到上传文件的原始名字
		String originalFilename = file.getOriginalFilename();
		System.out.println("originalFilename"+originalFilename);
		//截取文件后缀
		int i = originalFilename.lastIndexOf(".");
		if (i > 0) {//i>.0保证第一个.的位置不在下标为0的位置 就是文件长度的至少第1位
			suffix = originalFilename.substring(i);
		}
		String filename = UUID.randomUUID().toString() + suffix;


		// 将头像文件保存到哪里
		File dest = new File(dir, filename);

		try {
			// 执行头像文件保存操作
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			// 抛出异常
			throw new FileStateException(
					"文件状态异常，可能文件已被移动或删除");

		} catch (IOException e) {
			throw new FileUploadException("上传文件时读写错误，请稍后再次尝试");
		}

		// 头像路径
		String avatar = "/upload/" + filename;
		// 从Session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUserNameFromSession(session);
		// 将头像更新到数据库中
		service.changeAvatar(uid, username, avatar);
		System.out.println("上传成功了");
		// 返回
		return new JsonResult<>(OK, avatar);






	}

}
