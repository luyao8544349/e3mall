package cn.e3.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/**
	 * 需求:跳转门户系统首页
	 * 请求:locolhost:8082/index.html(省略)
	 */
	@RequestMapping("index")
	public String showIndex(){
		return "index";
	}

}
