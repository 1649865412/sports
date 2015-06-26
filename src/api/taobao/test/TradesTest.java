package api.taobao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import api.taobao.entity.LogisticsTag;
import api.taobao.entity.ServiceOrder;
import api.taobao.entity.TradePromotionDetail;
import api.taobao.service.TradesSoldGetService;

import com.taobao.api.domain.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext*.xml" })
@TestExecutionListeners( { TransactionalTestExecutionListener.class })
public class TradesTest extends AbstractJUnit4SpringContextTests
{
	// private static final Logger LOG =
	// LoggerFactory.getLogger(TradesTest.class);
	@Autowired(required = true)
	private TradesSoldGetService tradesSoldGetService;
	
	public void testUpdateTimeByData()
	{
		List<api.taobao.entity.Trade> trades = tradesSoldGetService.findByUpdateTimeBetween("2014-12-29 14:00:00",
				"2014-12-29 15:00:00");
		
		System.out.println(trades);
	}
	
	public void testTradesSoldGetService()
	{
		com.taobao.api.domain.Trade trade = new com.taobao.api.domain.Trade();
		
		trade.setAlipayId(113311L);
		trade.setTid(44433L);
		trade.setCreated(new Date());
		
		List<Order> orders = new ArrayList<Order>();
		Order order = new Order();
		order.setCid(111L);
		order.setTotalFee("test 1");
		orders.add(order);
		trade.setOrders(orders);
		
		List<com.taobao.api.domain.ServiceOrder> serviceOrders = new ArrayList<com.taobao.api.domain.ServiceOrder>();
		com.taobao.api.domain.ServiceOrder serviceOrder = new com.taobao.api.domain.ServiceOrder();
		serviceOrder.setItemOid(111L);
		
		serviceOrder.setSellerNick("testest ");
		serviceOrders.add(serviceOrder);
		trade.setServiceOrders(serviceOrders);
		
		JSONArray jsonArray = JSONArray.fromObject(trade);
		
		// List<api.taobao.entity.Trade> list =
		// (List<api.taobao.entity.Trade>)
		// JSONArray.toCollection(jsonArray,
		// api.taobao.entity.Trade.class);
		
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		
		classMap.put("orders", api.taobao.entity.Order.class);
		classMap.put("serviceOrders", ServiceOrder.class);
		classMap.put("serviceTags", LogisticsTag.class);
		classMap.put("promotionDetails", TradePromotionDetail.class);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setClassMap(classMap);
		jsonConfig.setRootClass(api.taobao.entity.Trade.class);
		
		// classMap.put("dynamicConFieldList", ConditionField.class);
		
		// classMap.put("resultField", ResultField.class);
		
		List<api.taobao.entity.Trade> objList = JSONArray.toList(jsonArray, jsonConfig);
		
		// System.out.println(objList);
		// System.out.println(list);
		
		// System.out.println(list);
		
		// objList.get(0).getOrders().get(0).setTradeInfo(objList.get(0));
		tradesSoldGetService.saveOrUpdate(objList);
		
	}
	
	@Test
	public void testDoPullTradeInfo()
	{
		
		// tradesSoldGetService.getTaobaoTradeData();
		
		// tradesSoldGetService.checkNonPaymentTradeInfo();
		
		tradesSoldGetService.queryDiffDataAndGeneratorExcel();
		
		// String freshUrl =
		// "https://oauth.taobao.com/token?client_secret=e9a15d6c7c55dfcb253a82ac3c2247bd&grant_type=authorization_code&code=wK4QCs2sXy9wVoHAqbNIemfw1482937&client_id=21310496&redirect_uri=http://121.199.175.66/esystem/esystem.jsp";
		// String freshUrl =
		// "https://oauth.tbsandbox.com/token?client_secret=sandbox4a3d0a1b3d82a2d1e5f49dc00&grant_type=authorization_code&code=wK4QCs2sXy9wVoHAqbNIemfw1482937&client_id=1021310496&redirect_uri=http://www.youruijin.cn/esystem/esystem.jsp";
		// try
		// {
		// String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60, 30
		// * 1000 * 60);
		//		
		// System.out.println(response);
		// }
		// catch (IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
