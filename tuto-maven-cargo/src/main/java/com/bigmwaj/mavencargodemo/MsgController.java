package com.bigmwaj.mavencargodemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {
	
	@Value("${msg:Message from static! Please check...}")
	private String msg;

	@GetMapping("/msg")
	public String getMsg() {
		System.out.println("MsgController.getMsg()");
		return this.msg;
	}
}
