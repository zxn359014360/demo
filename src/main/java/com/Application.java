package com;

import com.freemarker.FreeMarkerView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 增加自定义视图变量和方法
	 *
	 * @return
	 */
	@Bean
	public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				//增加视图
				resolver.setViewClass(FreeMarkerView.class);
				//添加自定义解析器
//				Map map = resolver.getAttributesMap();
//				map.put("conver", new MyConver());
			}
		};
	}
}
