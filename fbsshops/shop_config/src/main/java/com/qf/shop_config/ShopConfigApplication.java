package com.qf.shop_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * (exclude= DataSourceAutoConfiguration.class)
 * 数据源自动配置
 * 添加数据源，在一开始运行的时候就要添加，不然会报以下错误：
 * 整合mybatis找不到datasource：Failed to determine a suitable driver class
 * 解决网站：
 * https://blog.csdn.net/ht_kasi/article/details/82259860
 */
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableConfigServer
public class ShopConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopConfigApplication.class, args);
	}

}
