package com.qf.shop_miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
public class ShopMiaoshaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopMiaoshaApplication.class, args);
	}

}
