package cn.e3.manager.service;

import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.PageResult;

public interface ItemService {
	
	/**
	 * 需求:根据id查询商品数据
	 * 参数:Long itemId
	 * 返回值:TbItem
	 */
	public TbItem findItemByID(Long itemId);

	public PageResult findItemListByPage(Integer page, Integer rows);

	public E3mallResult saveItem(TbItem item, TbItemDesc itemDesc);

}
