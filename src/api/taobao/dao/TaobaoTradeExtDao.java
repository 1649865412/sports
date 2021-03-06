package api.taobao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api.taobao.entity.TradeExt;

public interface TaobaoTradeExtDao extends JpaRepository<TradeExt, Long>, JpaSpecificationExecutor<TradeExt>
{
	
}
