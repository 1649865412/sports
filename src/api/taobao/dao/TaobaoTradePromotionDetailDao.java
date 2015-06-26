package api.taobao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api.taobao.entity.TradePromotionDetail;

public interface TaobaoTradePromotionDetailDao extends JpaRepository<TradePromotionDetail, Long>,
		JpaSpecificationExecutor<TradePromotionDetail>
{
	
}
