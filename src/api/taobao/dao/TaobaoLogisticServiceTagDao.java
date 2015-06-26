package api.taobao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import api.taobao.entity.LogisticServiceTag;

public interface TaobaoLogisticServiceTagDao extends JpaRepository<LogisticServiceTag, Long>,
		JpaSpecificationExecutor<LogisticServiceTag>
{
	
}
