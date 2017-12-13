package cn.e3.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	
	/**
	 * 页面跳转:
	 * 首页: localhost:8081/index
	 * 商品添加:localhost:8081/item-add
	 * 商品列表:localhost:8081/item-list
	 * 内容页面:localhost:8081/content
	 * ..........................
	 * 页面跳转地址规律:
	 * 页面跳转地址和页面名称相同
	 * 能否把页面地址作为参数传递,返回参数作为页面逻辑视图
	 */
	@RequestMapping("{page}")
	public String showPage(@PathVariable String page){
		return page;
	}

}
