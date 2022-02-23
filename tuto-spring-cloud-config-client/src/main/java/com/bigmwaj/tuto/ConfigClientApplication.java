package com.bigmwaj.tuto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ConfigClientApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigClientApplication.class, args);
	}

	@Autowired
	public void setEnv(Environment e) {
		System.out.println("ConfigClientApplication.setEnv()");
		System.out.println(e.getProperty("msg"));
	}
}
