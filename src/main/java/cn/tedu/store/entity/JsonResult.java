package cn.tedu.store.entity;

public class JsonResult<E> {
	private Integer state;//状态
	private String  message;//错误描述
	private E data; //数据
	
	
	
	
	
	public JsonResult() {
		super();
	}
	//添加构造法法   参数Throwable e 更简洁显示注册过程中出现的异常信息
	/**
	 * 修改ExceptionHandler
	 * @param e
	 */
	public JsonResult(Throwable e) {
		super();
		this.message = e.getMessage();
		
	}
	/**
	 * 创建state的无参构造  修改contorller中的
	 * public JsonResult<Void> reg方法
	 * @param state
	 */
	public JsonResult(Integer state) {
		super();
		this.state = state;
		
		
	}
	/**
	 * 
	 * 处理的登录的构造发方法
	 * @param state
	 * @param data
	 */
	public JsonResult(Integer state, E data) {
		super();
		this.state = state;
		this.data = data;
	}
	
	
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
	}
	

}
