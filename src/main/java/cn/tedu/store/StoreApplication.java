package cn.tedu.store;

import javax.servlet.MultipartConfigElement;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;


/**
 * MapperScan为自动扫描组件mapper类持久层所在的包
 * 注意  虽有持久层的Mapper文件都需要添加在cn.tedu.store.mapper包下
 * 不然扫描不到接口文件
 * @author Administrator
 *
 */
@SpringBootApplication
@Configuration
@MapperScan("cn.tedu.store.mapper")
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);



	}
	/**
	 * 设置上传图片的大小
	 * 
	 * @return
	 */
	@Bean
	public MultipartConfigElement a(){

		MultipartConfigFactory f=new MultipartConfigFactory();
		DataSize d=DataSize.ofMegabytes(300);
		f.setMaxFileSize(d);
		f.setMaxRequestSize(d);

		return f.createMultipartConfig();




	}




}
