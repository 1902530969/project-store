package cn.tedu.store.service.ex;

public class PassWordNotFoundException extends ServiceException{

	/**
	 * 处理登录
	 * 密码错误
	 */
	private static final long serialVersionUID = 6414135208067832029L;

	public PassWordNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PassWordNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public PassWordNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public PassWordNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PassWordNotFoundException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
  
}
