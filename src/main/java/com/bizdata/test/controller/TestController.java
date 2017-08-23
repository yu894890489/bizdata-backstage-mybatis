package com.bizdata.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bizdata.framework.annotation.AppAuth;

@Controller
@RequestMapping(value = "/test")
public class TestController {

	@AppAuth
	@RequestMapping(value = "/one")
	@ResponseBody
	public void one(HttpServletRequest request,HttpServletResponse response,String param) {
		System.out.println("被切方法执行...");
	}

	@RequestMapping(value = "/two")
	@ResponseBody
	public void two(HttpServletRequest request) {
		System.out.println("方法二...");
	}

}
