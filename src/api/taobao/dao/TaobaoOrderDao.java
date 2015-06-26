package api.taobao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api.taobao.entity.Order;

public interface TaobaoOrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>
{
	
}
