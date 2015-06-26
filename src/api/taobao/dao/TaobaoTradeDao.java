package api.taobao.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api.taobao.entity.Trade;

public interface TaobaoTradeDao extends JpaSpecificationExecutor<Trade>, JpaRepository<Trade, Long>
{
	public List<Trade> findByUpdateTimeBetween(String startTime, String endTime);
	
	public List<Trade> findByUpdateTimeBetweenAndBrandId(String startTime, String endTime,Long brandId);
	
	public List<Trade> findByUpdateTimeBetweenAndStatusInAndBrandId(String startTime, String endTime, List<String> statusList,Long brandId);
}
