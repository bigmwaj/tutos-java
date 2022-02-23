package com.bigmwaj.mavencargodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MavenCargoDemoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MavenCargoDemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MavenCargoDemoApplication.class, args);
	}

}
