package com.bigmwaj.tuto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RefreshScope
@RestController
public class MgsController {
	
	@Value("${msg:Config Server is not working! Please check...}")
	private String msg;

	@GetMapping("/msg")
	public String getMsg() {
		return String.format("From config server:%s", this.msg);
	}
}
