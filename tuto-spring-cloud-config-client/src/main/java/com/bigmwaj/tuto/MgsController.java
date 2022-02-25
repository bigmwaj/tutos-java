package com.bigmwaj.tuto;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RefreshScope
@RestController
public class MgsController {
	
	@Value("${msg:Config Server is not working! Please check...}")
	private String msg;

	@GetMapping("/msg")
	public String getMsg() {
		Logger logger = Logger.getLogger(MgsController.class.getName());
		logger.info("MgsController.setEnv() " + e.getProperty("msg"));
		logger.info(msg);
		return String.format("%s-%s", this.msg,  e.getProperty("msg"));
	}
	
	@Autowired
	Environment e;
}
