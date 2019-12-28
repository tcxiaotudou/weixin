package cn.fciasth.weixin;

import cn.fciasth.weixin.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WeixinApplication {

	@Bean
	public SpringUtil getSpingUtil() {
		return new SpringUtil();
	}

	public static void main(String[] args) {
		SpringApplication.run(WeixinApplication.class, args);
	}

}
