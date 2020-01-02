package cn.tedu.store.contorller.ex;
/**
 * 所有有关上传文件异常的基类
 * @author Administrator
 *
 */
public class FileUploadException extends RuntimeException{

	public FileUploadException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public FileUploadException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
