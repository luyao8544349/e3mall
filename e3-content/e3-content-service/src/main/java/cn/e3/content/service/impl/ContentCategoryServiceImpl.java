package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.content.service.ContentCategoryService;
import cn.e3.mapper.TbContentCategoryMapper;
import cn.e3.pojo.TbContentCategory;
import cn.e3.pojo.TbContentCategoryExample;
import cn.e3.pojo.TbContentCategoryExample.Criteria;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	//注入广告分类mapper接口代理对象
	@Autowired
	private TbContentCategoryMapper categoryMapper;

	/**
	 * 需求:根据父id查询子节点
	 * 参数:Long parentId
	 * 返回值:List<TreeNode>
	 */
	public List<TreeNode> findContentCategoryTreeNodeList(Long parentId) {
		
		//创建树形节点集合,封装树形节点
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		
		// 创建example对象
		TbContentCategoryExample example = new TbContentCategoryExample();
		
		//创建criteria对象
		Criteria createCriteria = example.createCriteria();
		
		//设置查询参数:根据父id查询子节点
		createCriteria.andParentIdEqualTo(parentId);
		
		//执行查询
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		
		//循环遍历广告分类集合,把广告数据封装树形节点
		for (TbContentCategory tbContentCategory : list) {
			//创建树形节点对象,封装广告分类
			TreeNode node = new TreeNode();
			//封装树形节点id
			node.setId(tbContentCategory.getId());
			//封装树形节点名称
			node.setText(tbContentCategory.getName());
			//封装树形节点状态
			//is_parent=1,表示此节点有子节点,state="closed",表示可打开状态
			//is_parent=0,表示子节点没有子节点,state="open",表示此节点已经打开,不能再打开
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			
			//把单个树形节点对象添加到集合
			treeNodeList.add(node);
		}
		
		return treeNodeList;
	}
	
	/**
	 * 需求:新建广告分类
	 * 参数:Long parentId,String name
	 * 返回值:E3mallResult.ok(object)
	 * 业务:
	 * 1,如果新建节点父节点原来是一个子节点,修改is_parent=1
	 * 2,如果新建节点父节点原来就是一个父节点,直接添加即可
	 */
	public E3mallResult createNode(Long parentId, String name) {
		// 创建广告分类对象
		TbContentCategory contentCategory = new TbContentCategory();
		//设置广告分类数据
		contentCategory.setParentId(parentId);
		//设置树形节点名称
		contentCategory.setName(name);
		//状态。可选值:1(正常),2(删除)'
		contentCategory.setStatus(1);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		contentCategory.setSortOrder(1);
		//新建节点一定是子节点
		contentCategory.setIsParent(false);
		//时间
		Date date = new Date();
		contentCategory.setCreated(date);
		contentCategory.setUpdated(date);
		//保存
		categoryMapper.insertSelective(contentCategory);
		
		//判断新建节点父节点是否是子节点
		//关系:新建节点parentId就是父节点id,因此根据主键查询父节点对象
		TbContentCategory category = categoryMapper.selectByPrimaryKey(parentId);
		//判断
		if(!category.getIsParent()){
			//表示父节点原来是子节点,修改状态
			category.setIsParent(true);
			//修改
			categoryMapper.updateByPrimaryKeySelective(category);
		}
		
		
		//返回值
		return E3mallResult.ok(contentCategory);
	}

}
