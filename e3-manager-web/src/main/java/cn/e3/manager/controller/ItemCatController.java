package cn.e3.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.manager.service.ItemCatService;
import cn.e3.utils.TreeNode;

@Controller
public class ItemCatController {
	
	//注入商品类目服务对象
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 需求:根据父id查询子节点
	 * 请求:/item/cat/list
	 * 参数:Long parentId
	 * 返回值:json格式List<TreeNode>
	 * 思考:服务引入?
	 * 业务思考:
	 * 1,前端框架easyUI tree规定查询子节点传递参数是id,前台参数和方法中参数名称不一致,导致传递不成功
	 * 2,初始化加载树形节点时没有传递参数,一定必须先初始化出树形节点顶级节点,设置默认值=0
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<TreeNode> findItemCatTreeNodeList(
			@RequestParam(defaultValue="0",value="id") Long parentId){
		//调用远程service服务方法,查询树形节点
		List<TreeNode> list = itemCatService.findItemCatTreeNodeList(parentId);
		return list;
	}

}
