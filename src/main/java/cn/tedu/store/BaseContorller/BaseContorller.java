package cn.tedu.store.BaseContorller;


import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;


import cn.tedu.store.contorller.ex.FileEmptyException;
import cn.tedu.store.contorller.ex.FileSizeException;
import cn.tedu.store.contorller.ex.FileStateException;
import cn.tedu.store.contorller.ex.FileTyeException;
import cn.tedu.store.contorller.ex.FileUploadException;
import cn.tedu.store.entity.JsonResult;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordExpiredException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.userNameDuplicateException;




public class BaseContorller {
	/**
	 * 注册成功的状态码
	 */
	public static final int OK=2000;
	
	
	/**
	 * 
	 * 
	 * @param session httpSession对象
	 * @return uid  写到这里方便调用
	 */
	protected Integer getUidFromSession(HttpSession session){
//从session中获取uid
		return Integer.valueOf(session.getAttribute("uid").toString());

	}
	
	
	/**
	 * 
	 * @param session httpSession对象
	 * @return username  写到这里方便调用获取uid跟username
	 */
	protected String getUserNameFromSession(HttpSession session){
		//从session中获取username
	return session.getAttribute("username").toString();
	
	}
	
	/**
	 * 出现异常就会自动运行这里的代码
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ServiceException.class,FileUploadException.class})
	public JsonResult<Void> exception(Throwable e){
		JsonResult<Void> jr=new JsonResult<>();

		if(e instanceof userNameDuplicateException){
			jr.setState(4000);
			//jr.setMessage(e.getMessage());
			//省略jr.setMessage(e.getMessage())前提需要在
			//jsonresult类中添加关于state的构造方法
			
		}else if(e instanceof userNameDuplicateException){
			/**
			 * 添加data跟state双参数的构造方法
			 */
			jr.setState(4001);
			
		}else if(e instanceof PasswordExpiredException){
			
			jr.setState(4002);
		
		
		}else if(e instanceof InsertException){

			jr.setState(5000);
			//jr.setMessage(e.getMessage());

		}else if(e instanceof UpdateException){
			jr.setState(5001);
			
		}
		
		
		
		else if(e instanceof FileEmptyException){
			jr.setState(6000);
			
		}else if(e instanceof FileSizeException){
	
			jr.setState(6001);
			
			
		}else if(e instanceof FileTyeException){
			jr.setState(6002);
			
		}else if(e instanceof FileStateException){
			jr.setState(6003);
			
		}else if(e instanceof FileUploadException){
			jr.setState(6004);
			
		}
		
		
		
		
		
		
		
		
		

		return 	jr;

	}


	
	
	
	
}
