package cn.e3.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.content.service.ContentService;
import cn.e3.mapper.TbContentMapper;
import cn.e3.pojo.TbContent;
import cn.e3.pojo.TbContentExample;
import cn.e3.pojo.TbContentExample.Criteria;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.PageResult;

@Service
public class ContentServiceImpl implements ContentService {
	
	//注入广告内容mapper接口代理对象
	@Autowired
	private TbContentMapper contentMapper;

	/**
	 * 需求:根据分类id查询广告内容数据
	 * 参数:Long categoryId,Integer page,Integer rows
	 * 返回值:PageResult
	 */
	public PageResult findContentListByPage(Long categoryId, Integer page,
			Integer rows) {
		// 创建广告内容example对象
		TbContentExample example = new TbContentExample();
		//创建criteria对象,设置查询参数
		Criteria createCriteria = example.createCriteria();
		//设置查询参数:根据分类id查询广告内容数据
		createCriteria.andCategoryIdEqualTo(categoryId);
		
		//在执行查询之前,设置分页查询
		PageHelper.startPage(page, rows);
		
		//执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//创建pageInfo分页包装类对象,获取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		
		//创建PageResult分页包装类对象,封装分页数据
		PageResult result = new PageResult();
		//设置总记录数
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		
		return result;
	}
	
	/**
	 * 需求:保存广告内容
	 * 参数:TbContent content
	 * 返回值:E3mallResult
	 */
	public E3mallResult saveContent(TbContent content) {
		// 补全时间属性
		Date date = new Date();
		content.setUpdated(date);
		content.setCreated(date);
		contentMapper.insertSelective(content);
		//返回值
		return E3mallResult.ok();
	}

}
