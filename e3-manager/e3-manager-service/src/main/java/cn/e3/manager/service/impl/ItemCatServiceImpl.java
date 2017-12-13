package cn.e3.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.manager.service.ItemCatService;
import cn.e3.mapper.TbItemCatMapper;
import cn.e3.pojo.TbItemCat;
import cn.e3.pojo.TbItemCatExample;
import cn.e3.pojo.TbItemCatExample.Criteria;
import cn.e3.utils.TreeNode;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	//注入itemcat的mapper接口代理对象
	@Autowired
	private TbItemCatMapper itemCatMapper;

	/**
	 * 需求:根据父id查询子节点
	 * 参数:Long parentId
	 * 返回值:List<TreeNode>
	 * 思考:服务发布?
	 */
	public List<TreeNode> findItemCatTreeNodeList(Long parentId) {
		//创建树形节点集合对象,封装树形节点值
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		
		// 创建example对象
		TbItemCatExample example = new TbItemCatExample();
		// 创建criteria对象
		Criteria createCriteria = example.createCriteria();
		//设置查询参数:根据父id查询子节点
		createCriteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> catList = itemCatMapper.selectByExample(example);
		
		//循环商品类目集合,把商品类目属性值封装到树形节点对象中
		for (TbItemCat tbItemCat : catList) {
			//创建TreeNode对象,封装树形节点值
			TreeNode tn = new TreeNode();
			//设置id
			tn.setId(tbItemCat.getId());
			//设置树形节点名称
			tn.setText(tbItemCat.getName());
			//设置树形节点状态
			//is_parent=1,表示此节点有子节点,state="closed",表示可打开状态
			//is_parent=0,表示子节点没有子节点,state="open",表示此节点已经打开,不能再打开
			tn.setState(tbItemCat.getIsParent()?"closed":"open");
			//把树形节点放入树形节点集合对象中
			treeNodeList.add(tn);
		}
		return treeNodeList;
	}

}
