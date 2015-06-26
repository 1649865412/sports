package api.taobao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api.taobao.entity.Shop;

public interface TaobaoShopDao extends JpaRepository<Shop, Long>, JpaSpecificationExecutor<Shop>
{
	
}
