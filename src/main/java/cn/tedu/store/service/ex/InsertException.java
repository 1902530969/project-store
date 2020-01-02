package cn.tedu.store.service.ex;
/**
 *执行数据库操作时,会执行数据库操作 这个操作可能会失败,则创建InsertException.继承自ServiceException
 * 
 */
public class InsertException extends ServiceException {

	public InsertException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InsertException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public InsertException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InsertException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InsertException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
