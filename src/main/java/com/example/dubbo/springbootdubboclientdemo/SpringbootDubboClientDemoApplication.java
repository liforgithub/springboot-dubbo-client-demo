package com.example.dubbo.springbootdubboclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
		@PropertySource("classpath:/config.properties"),
		@PropertySource(value = "file:${user.home}/conf/config.properties", ignoreResourceNotFound = true),
})
@SpringBootApplication
public class SpringbootDubboClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboClientDemoApplication.class, args);
	}
}
