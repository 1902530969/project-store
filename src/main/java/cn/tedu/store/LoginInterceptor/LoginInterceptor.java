package cn.tedu.store.LoginInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	//开始编辑拦截器
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//判断用户是否登录
		if(request.getSession().getAttribute("uid")==null){
			//重新定向到登录页
			response.sendRedirect(request.getContextPath()+"/web/login.html");
			
			return  false;
		}
		
		return true;
	}
	
	
	

}
