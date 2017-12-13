package cn.e3.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentCategoryService;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

@Controller
public class ContentCategoryController {
	
	//注入服务对象
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 需求:根据父id查询子节点
	 * 请求:/content/category/list
	 * 参数:Long parentId
	 * 返回值:json格式List<TreeNode>
	 * 业务:
	 * easyui 树形节点默认传递参数是id
	 * 初始化树形节点:设置默认值为0
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> findContentCategoryTreeNodeList(
			@RequestParam(defaultValue="0",value="id") Long parentId){
		List<TreeNode> list = contentCategoryService.findContentCategoryTreeNodeList(parentId);
		return list;
	}
	
	/**
	 * 需求:新建广告分类
	 * 请求:/content/category/create
	 * 参数:Long parentId,String name
	 * 返回值:E3mallResult.ok(object)
	 * 业务:
	 * 1,如果新建节点父节点原来是一个子节点,修改is_parent=1
	 * 2,如果新建节点父节点原来就是一个父节点,直接添加即可
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3mallResult createNode(Long parentId,String name){
		//调用远程服务
		E3mallResult result = contentCategoryService.createNode(parentId, name);
		return result;
	}

}
