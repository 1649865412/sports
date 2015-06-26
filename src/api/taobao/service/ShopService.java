package api.taobao.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.taobao.dao.TaobaoShopDao;
import api.taobao.entity.Shop;

import com.base.util.dwz.Page;
import com.base.util.dwz.PageUtils;

@Service
@Transactional
public class ShopService
{
	
	@Autowired
	private TaobaoShopDao shopDao;
	
	public List<Shop> findAll(Page page)
	{
		if (null != page)
		{
			page.setOrderDirection(Page.ORDER_DIRECTION_DESC);
		}
		org.springframework.data.domain.Page<Shop> springDataPage = shopDao.findAll(PageUtils.createPageable(page));
		page.setTotalCount(springDataPage.getTotalElements());
		return springDataPage.getContent();
	}
	
	public List<Shop> findAll()
	{
		return shopDao.findAll();
	}
	
	public void saveOrUpdate(Shop shop)
	{
		if (null != shop)
		{
			shopDao.save(shop);
		}
	}
	
	public void saveOrUpdate(List<Shop> shops)
	{
		if (CollectionUtils.isNotEmpty(shops))
		{
			shopDao.save(shops);
		}
	}
	
	public void delete(List<Shop> shops)
	{
		if (CollectionUtils.isNotEmpty(shops))
		{
			shopDao.delete(shops);
		}
	}
	
	public void delete(Shop shop)
	{
		if (null != shop)
		{
			shopDao.delete(shop);
		}
	}
	
}
