package com.alok.proxy.restproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan({"com.alok.proxy.restproxy.config"})
@SpringBootApplication
public class RestProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestProxyApplication.class, args);
	}

}
