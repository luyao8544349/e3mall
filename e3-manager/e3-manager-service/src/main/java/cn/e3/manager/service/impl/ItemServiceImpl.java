package cn.e3.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemService;
import cn.e3.mapper.TbItemDescMapper;
import cn.e3.mapper.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.pojo.TbItemExample;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.IDUtils;
import cn.e3.utils.PageResult;

@Service
public class ItemServiceImpl implements ItemService {
	
	//注入商品mapper接口代理对象
	@Autowired
	private TbItemMapper itemMapper;
	
	//注入商品描述mapper接口代理对象
	@Autowired
	private TbItemDescMapper itemDescMapper;

	/**
	 * 需求:根据id查询商品数据
	 * 参数:Long itemId
	 * 返回值:TbItem
	 */
	public TbItem findItemByID(Long itemId) {
		// 根据id查询商品数据
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}

	/**
	 * 需求:分页商品列表进行分页展示
	 * 参数:Integer page,Integer rows
	 * 返回值:PageResult
	 * 思考: 服务是否已经发布?
	 */
	public PageResult findItemListByPage(Integer page, Integer rows) {
		// 创建example对象
		TbItemExample example = new TbItemExample();
		
		//在执行查询之前,设置分页查询
		PageHelper.startPage(page, rows);		
		//执行查询,自动进行分页查询
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//创建PageInfo对象,解析list集合中Page属性,获取分页属性值
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		
		//创建分页包装类对象PageResult
		PageResult pr = new PageResult();
		//设置总记录数
		pr.setTotal(pageInfo.getTotal());
		//设置查询记录
		pr.setRows(list);
		
		
		return pr;
	}

	/**
	 * 需求:保存商品对象
	 * 参数:TbItem item,TbItemDesc itemDesc
	 * 返回值:E3mallResult
	 */
	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc) {
		// 保存商品对象
		//毫秒+随机数
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte)1);
		//时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//保存
		itemMapper.insertSelective(item);
		
		//补全商品描述属性
		itemDesc.setItemId(itemId);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		
		//保存商品描述
		itemDescMapper.insertSelective(itemDesc);
		
		//E3mallResult
		return E3mallResult.ok();
	}

}
