package com.innshine.orderhelp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.base.dao.SqlDao;
import com.innshine.orderhelp.entity.Branch;
import com.innshine.orderhelp.entity.SdbOmeDelivery;
import com.innshine.orderhelp.entity.SdbOmeDlyCorp;
import com.innshine.orderhelp.entity.SdbOmeIostock;
import com.innshine.orderhelp.entity.SdbOmeOrderItems;
import com.innshine.orderhelp.entity.SdbOmeOrders;
import com.innshine.orderhelp.entity.SdbOmeSales;
import com.innshine.orderhelp.model.OrderObj;
import com.utils.DateUtils;
import com.utils.StringHelper;
import com.utils.excel.InFileBean;

@Service
@Transactional
public class OrderhelpService {
	@Autowired
	private SqlDao sqlDao;

	public void helpOrder(MultipartFile[] files) throws Exception {
		try {
			sqlDao.setOcsEm();
			if (files.length == 0) {
				throw new Exception("请选择文件!");
			}

			List<OrderObj> dataList = new ArrayList<OrderObj>();
			Vector<String> set = new Vector<String>();

			set.add("orderBn");
			set.add("sendBn");
			set.add("expressBn");
			set.add("expressCorp");
			InFileBean<OrderObj> fileBean = InFileBean.parseData(set,
					InFileBean.excelType2007, OrderObj.class, files[0]
							.getInputStream());

			dataList = fileBean.fileDataList(1);
			String findBranchSql = "select branch_id,name from sdb_ome_branch";
			List<Branch> branchList = sqlDao.queryListBySql(findBranchSql, Branch.class);
			
			for (OrderObj oo : dataList) {
				String orderBn = oo.getOrderBn();
				String sendBn = oo.getSendBn();
				if (StringUtils.isNotBlank(orderBn)
						&& StringUtils.isNotBlank(sendBn)) {
					StringBuilder findOrderSql = new StringBuilder();
					findOrderSql.append("SELECT order_id, ");
					findOrderSql.append("order_bn, ");
					findOrderSql.append("relate_order_bn, ");
					findOrderSql.append("member_id, ");
					findOrderSql.append("confirm, confirm_allow, process_status, status, pay_status, pay_time,"); 
					findOrderSql.append("ship_status,stock_shortage, is_delivery, shipping, ");
					findOrderSql.append("pay_bn, payment, weight, tostr, itemnum, createtime, download_time, ");
					findOrderSql.append("finish_time, last_modified, outer_lastmodify, shop_id, shop_type, ip, ship_name,"); 
					findOrderSql.append("ship_area, ship_addr, ship_zip, ship_tel, ");
					findOrderSql.append("ship_email, ship_time, ship_mobile, consigner_name, consigner_area, ");
					findOrderSql.append("consigner_addr,  consigner_zip,  consigner_email,  consigner_mobile, ");
					findOrderSql.append("consigner_tel,cost_item, is_tax, cost_tax, tax_company, cost_freight, ");
					findOrderSql.append("is_protect, cost_protect, is_cod, is_fail, edit_status, cost_payment, ");
					findOrderSql.append("currency, cur_rate, score_u, score_g, discount,pmt_goods, ");
					findOrderSql.append("pmt_order, total_amount, final_amount, payed, custom_mark, mark_text, ");
					findOrderSql.append("op_id, dispatch_time,source, pause, is_modify, old_amount, order_type, ");
					findOrderSql.append("trade_type, order_job_no, is_auto, is_merge_delivery, control_status, dispatch_status,"); 
					findOrderSql.append("sync_status ");
					findOrderSql.append("FROM   sdb_ome_orders a WHERE a.order_bn = '" + orderBn+"'");
					SdbOmeOrders order = sqlDao.querySingleObj(findOrderSql.toString(),
							SdbOmeOrders.class);
					String findCorp = "select * from sdb_ome_dly_corp where wms_code like '%"
							+ oo.getExpressCorp()
							+ "%' or name like '%"
							+ oo.getExpressCorp() + "%'";
					SdbOmeDlyCorp dlyCorp = sqlDao.querySingleObj(findCorp,
							SdbOmeDlyCorp.class);
					if (order == null) {
						continue;
					}
                      
					if ("0".equals(order.getPayStatus())
							|| "8".equals(order.getPayStatus())
							|| "2".equals(order.getPayStatus())
							) {
						continue;
					}
					 
					String shop = order.getTostr();
					String branchID = shopMap().get(shop);

					String findDeleverOrderId = "SELECT delivery_id  FROM  sdb_ome_delivery_order WHERE order_id = "
							+ order.getOrderId();
					String deleverId = sqlDao.queryString(findDeleverOrderId);

					String shipArea = order.getShipArea();
					String province = "";
					String city = "";
					String distinct = "";
					if (shipArea != null) {
						String[] area = shipArea.split(":");
						if (area.length == 3) {
							String[] adds = area[1].split("/");
							if (adds.length == 1) {
								province = adds[0];
							} else if (adds.length == 2) {
								province = adds[0];
								city = adds[1];
							} else if (adds.length == 3) {
								province = adds[0];
								city = adds[1];
								distinct = adds[2];
							}

						}
					}
					// 在关系表中没有相关的记录， 生成新的发货单记录
					StringBuilder insertDelevery = new StringBuilder();
					insertDelevery.append("INSERT INTO  sdb_ome_delivery ");
					insertDelevery.append("(delivery_bn,");
					if (deleverId != null) {
						insertDelevery.append("delivery_id, ");
					}
					insertDelevery.append("member_id, ");
					insertDelevery.append("cost_protect, ");
					insertDelevery.append("delivery, ");
					insertDelevery.append("logi_id, ");
					insertDelevery.append("logi_code, ");
					insertDelevery.append("logi_name, ");
					insertDelevery.append("logi_no, ");
					insertDelevery.append("ship_name, ");
					insertDelevery.append("ship_area, ");
					insertDelevery.append("ship_province, ");
					insertDelevery.append("ship_city, ");
					insertDelevery.append("ship_district, ");
					insertDelevery.append("ship_addr, ");
					insertDelevery.append("ship_zip, ");
					insertDelevery.append("ship_tel, ");
					insertDelevery.append("ship_mobile, ");
					insertDelevery.append("ship_email, ");
					insertDelevery.append("create_time, ");
					insertDelevery.append("status,  ");
					insertDelevery.append("branch_id, ");
					insertDelevery.append("stock_status, ");
					insertDelevery.append("deliv_status, ");
					insertDelevery.append("expre_status, ");
					insertDelevery.append("verify, ");
					insertDelevery.append("package, ");
					insertDelevery.append("picking, ");
					insertDelevery.append("process, ");
					insertDelevery.append("net_weight, ");
					insertDelevery.append("weight,  ");
					insertDelevery.append("last_modified,");
					insertDelevery.append("delivery_time,   ");
					insertDelevery.append("shop_id, ");
					insertDelevery.append("order_createtime,  ");
					insertDelevery.append("op_id, ");
					insertDelevery.append("op_name,  ");
					insertDelevery.append("sync_status ");
					insertDelevery.append(")");
					insertDelevery.append(" VALUES ");
					insertDelevery.append("(" + oo.getSendBn() + ",  ");
					if (deleverId != null) {
						insertDelevery.append("'" + deleverId + "', ");
					}
					insertDelevery.append("'" + order.getMemberId() + "',  ");
					insertDelevery.append("0.000,  ");
					insertDelevery.append("'快递', ");
					if (dlyCorp == null) {
						insertDelevery.append("'', ");
						insertDelevery.append("'', ");
						insertDelevery.append("'', ");
					} else {
						insertDelevery
								.append("'" + dlyCorp.getCorpId() + "', ");
						insertDelevery.append("'" + dlyCorp.getWmsCode()
								+ "', ");
						insertDelevery.append("'" + dlyCorp.getName() + "', ");
					}
					insertDelevery.append("'" + oo.getExpressBn() + "', ");
					insertDelevery.append("'" + order.getShipName() + "', ");

					insertDelevery.append("'" + order.getShipArea() + "', ");
					insertDelevery.append("'" + province + "', ");
					insertDelevery.append("'" + city + "', ");
					insertDelevery.append("'" + distinct + "', ");
					insertDelevery.append("'" + order.getShipAddr() + "', ");
					insertDelevery.append("'" + order.getShipZip() + "', ");
					insertDelevery.append("'" + order.getShipTel() + "', ");
					insertDelevery.append("'" + order.getShipMobile() + "', ");
					insertDelevery.append("'" + order.getShipEmail() + "', ");
					insertDelevery.append("UNIX_TIMESTAMP(NOW()), ");
					insertDelevery.append("'succ',   ");
					insertDelevery.append("'" + branchID + "', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'true', ");
					insertDelevery.append("'0.00', ");
					insertDelevery.append("'0.00', ");
					insertDelevery.append("UNIX_TIMESTAMP(NOW()), ");
					insertDelevery.append("UNIX_TIMESTAMP(NOW()),  ");
					insertDelevery.append("'" + order.getShopId() + "', ");
					insertDelevery.append("'" + order.getCreatetime() + "',  ");
					insertDelevery.append("'1', ");
					insertDelevery.append("'admin', ");
					insertDelevery.append("'success'");
					insertDelevery.append(")");
					// 查出最后一个ID
					String lastIdSql = "SELECT max(delivery_id) AS id from sdb_ome_delivery ";
					if (deleverId == null) {
						// 插入发货单、更改关系表
						sqlDao.updateBySql(insertDelevery.toString());

						deleverId = sqlDao.queryString(lastIdSql);

						StringBuilder insertDeliveryOrderSql = new StringBuilder();
						insertDeliveryOrderSql
								.append("INSERT INTO  sdb_ome_delivery_order ");
						insertDeliveryOrderSql.append("(order_id, ");
						insertDeliveryOrderSql.append("delivery_id");
						insertDeliveryOrderSql.append(")");
						insertDeliveryOrderSql.append("VALUES");
						insertDeliveryOrderSql.append("('" + order.getOrderId()
								+ "', ");
						insertDeliveryOrderSql.append("'" + deleverId + "'");
						insertDeliveryOrderSql.append(")");
						sqlDao.updateBySql(insertDeliveryOrderSql.toString());
					} else {
						/****
						 * 关联关系存在
						 */
						String findDelivery = "select * from sdb_ome_delivery where delivery_id = "
								+ deleverId;
						SdbOmeDelivery delivery = sqlDao.querySingleObj(
								findDelivery, SdbOmeDelivery.class);
						if (delivery == null) {
							sqlDao.updateBySql(insertDelevery.toString());
						}
					}
					//出入明细
					String findioStock = "select * from sdb_ome_iostock where original_id = "+deleverId;
					List<SdbOmeIostock> ioStocks  = sqlDao.queryListBySql(findioStock, SdbOmeIostock.class);
					boolean io = (ioStocks.size() > 0);
					// 插入明细
					List<SdbOmeOrderItems> itemList = sqlDao.queryListBySql("select * from sdb_ome_order_items where order_id = '"+order.getOrderId()+"'", SdbOmeOrderItems.class);
					for (SdbOmeOrderItems oi : itemList) {
						StringBuilder deliveryItemsSql = new StringBuilder();
						deliveryItemsSql
								.append("INSERT INTO  sdb_ome_delivery_items ");
						deliveryItemsSql.append("(");
						deliveryItemsSql.append("delivery_id, ");
						deliveryItemsSql.append("product_id, ");
						deliveryItemsSql.append("shop_product_id, ");
						deliveryItemsSql.append("bn, ");
						deliveryItemsSql.append("product_name, ");
						deliveryItemsSql.append("number, ");
						deliveryItemsSql.append("verify, ");
						deliveryItemsSql.append("verify_num");
						deliveryItemsSql.append(")");
						deliveryItemsSql.append("VALUES");
						deliveryItemsSql.append("( ");
						deliveryItemsSql.append("'" + deleverId + "', ");
						deliveryItemsSql
								.append("'" + oi.getProductId() + "', ");
						deliveryItemsSql.append("'" + oi.getShopProductId()
								+ "', ");
						deliveryItemsSql.append("'" + oi.getBn() + "', ");
						deliveryItemsSql.append("'" + oi.getName() + "', ");
						deliveryItemsSql.append("'" + oi.getNums() + "', ");
						deliveryItemsSql.append("true, ");
						deliveryItemsSql.append("'" + oi.getNums() + "'");
						deliveryItemsSql.append(")");

						String findItemSql = "select count(1) as pSize from sdb_ome_delivery_items where delivery_id = '"
								+ deleverId + "'";
						String itemSize = sqlDao.queryString(findItemSql);
						if (StringUtils.isBlank(itemSize) || "0".equals(itemSize)) {
							sqlDao.updateBySql(deliveryItemsSql.toString());
						}

						String lastItemId = sqlDao.queryString("SELECT max(item_id) AS id from sdb_ome_delivery_items ");
						StringBuilder insertDetailSql = new StringBuilder();
						insertDetailSql
								.append("INSERT INTO sdb_ome_delivery_items_detail ");
						insertDetailSql.append("( ");
						insertDetailSql.append("delivery_id, ");
						insertDetailSql.append("delivery_item_id, ");
						insertDetailSql.append("order_id, ");
						insertDetailSql.append("order_item_id, ");
						insertDetailSql.append("order_obj_id, ");
						insertDetailSql.append("item_type, ");
						insertDetailSql.append("product_id, ");
						insertDetailSql.append("bn, ");
						insertDetailSql.append("number, ");
						insertDetailSql.append("price, ");
						insertDetailSql.append("amount ");
						insertDetailSql.append(")");
						insertDetailSql.append("VALUES");
						insertDetailSql.append("(");
						insertDetailSql.append("'" + deleverId + "', ");
						insertDetailSql.append("'" + lastItemId + "', ");
						insertDetailSql
								.append("'" + order.getOrderId() + "', ");
						insertDetailSql.append("'" + oi.getItemId() + "', ");
						insertDetailSql
								.append("'"+order.getOrderId()+"', ");
						insertDetailSql.append("'product', ");
						insertDetailSql.append("'" + oi.getProductId() + "', ");
						insertDetailSql.append("'" + oi.getBn() + "', ");
						insertDetailSql.append("'" + oi.getNums() + "', ");
						insertDetailSql.append("'" + oi.getPrice() + "', ");
						insertDetailSql.append("'" + oi.getAmount() + "'");
						insertDetailSql.append(")");
						findItemSql = "select count(1) as pSize from sdb_ome_delivery_items_detail where delivery_id = '"
								+ deleverId + "'";
						itemSize = sqlDao.queryString(findItemSql);
						if (StringUtils.isBlank(itemSize)||"0".equals(itemSize)) {
							sqlDao.updateBySql(insertDetailSql.toString());
						}
						
						//不存在出入库记录
						if(io == false){
							//插入出入库记录
							long iostockid = System.currentTimeMillis();
							String iostockbn = "O"+DateUtils.getNow("yyyyMMdd")+StringHelper.randomNum(6); 
							StringBuilder sbioStockSql = new StringBuilder();
							sbioStockSql.append("INSERT INTO  sdb_ome_iostock ");
							sbioStockSql.append("(iostock_id, ");
							sbioStockSql.append("iostock_bn, ");
							sbioStockSql.append("type_id, ");
							sbioStockSql.append("branch_id, ");
							sbioStockSql.append("original_bn, ");
							sbioStockSql.append("original_id, ");
							sbioStockSql.append("original_item_id,  ");
							sbioStockSql.append("bn,   ");
							sbioStockSql.append("now_num, ");
							sbioStockSql.append("nums,  ");
							sbioStockSql.append("oper, ");
							sbioStockSql.append("create_time, ");
							sbioStockSql.append("iotime, ");
							sbioStockSql.append("operator,  ");
							sbioStockSql.append("memo");
							sbioStockSql.append(")");
							sbioStockSql.append("VALUES");
							sbioStockSql.append(" ( "+iostockid+" , ");
							sbioStockSql.append("'"+iostockbn+"', ");
							sbioStockSql.append("'3', ");
							sbioStockSql.append("'"+branchID+"', ");
							sbioStockSql.append("'"+oo.getSendBn()+"', ");
							sbioStockSql.append("'"+deleverId+"', ");
							sbioStockSql.append("'"+lastItemId+"',  ");
							sbioStockSql.append("'"+oi.getBn()+"',   ");
							sbioStockSql.append("(SELECT store FROM sdb_ome_products WHERE bn = '"+oi.getBn()+"'), ");
							sbioStockSql.append("'1',  ");
							sbioStockSql.append("'system', ");
							sbioStockSql.append("UNIX_TIMESTAMP(NOW()), ");
							sbioStockSql.append("UNIX_TIMESTAMP(NOW()), ");
							sbioStockSql.append("'system',  ");
							sbioStockSql.append("'补单'");
							sbioStockSql.append(")"); 
							sqlDao.updateBySql(sbioStockSql.toString());
							
							excuteSaveSale(Long.toString(iostockid),iostockbn,order,oi,branchID,branchList);
							
						}
						if(io){
							/**************
							 * 存在出入库记录
							 */
							for(SdbOmeIostock iostock : ioStocks){
								//找销售细则
								String findSellSql = "select * from sdb_ome_sales where iostock_bn = '"+iostock.getIostockBn()+"'";
								List<SdbOmeSales> omeSales = sqlDao.queryListBySql(findSellSql, SdbOmeSales.class);
								if(omeSales.size() == 0){
									//不存在销售,补全
									excuteSaveSale(iostock.getIostockId(),iostock.getIostockBn(),order,oi,branchID,branchList);
								}
							}
						}
						
					}
					
					//更新订单发货状态 
					String payStatus = order.getPayStatus();
					String ship_status = "1";
					if("5".equals(payStatus)){
						ship_status = "4";
					}
					 
					String updateStutus = "update sdb_ome_orders set ship_status = '"+ship_status+"' where order_bn='"+order.getOrderBn()+"'";
					sqlDao.updateBySql(updateStutus);

				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			sqlDao.resetEm();
		}
	}
	
	private void excuteSaveSale(String iostockid,String iostockbn,SdbOmeOrders order,SdbOmeOrderItems oi,String branchID,List<Branch> branchList) throws Exception{
		/**
		 * 出库销售及明细
		 */
		long sellid = System.currentTimeMillis();
		String sellbn = "S"+DateUtils.getNow("yyyyMMdd")+StringHelper.randomNum(6); 
		StringBuilder sbSellSql = new StringBuilder();
		sbSellSql.append("INSERT INTO  sdb_ome_sales ");
		sbSellSql.append("(sale_id, ");
		sbSellSql.append("sale_bn, ");
		sbSellSql.append("iostock_bn, ");
		sbSellSql.append("sale_time, ");
		sbSellSql.append("sale_amount, ");
		sbSellSql.append("cost, ");
		sbSellSql.append("goods_amount, ");
		sbSellSql.append("goods_discount,  ");
		sbSellSql.append("operator,  ");
		sbSellSql.append("member_id, ");
		sbSellSql.append("branch_id, ");
		sbSellSql.append("pay_status, ");
		sbSellSql.append("shop_id, ");
		sbSellSql.append("create_time");
		sbSellSql.append(")");
		sbSellSql.append("VALUES");
		sbSellSql.append("("+sellid+", ");
		sbSellSql.append("'"+sellbn+"', ");
		sbSellSql.append("'"+iostockbn+"', ");
		sbSellSql.append("'"+order.getCreatetime()+"', ");
		sbSellSql.append("'"+oi.getAmount()+"', ");
		sbSellSql.append("'0.000', ");
		sbSellSql.append("'"+oi.getPrice()+"', ");
		sbSellSql.append("'"+oi.getPmtPrice()+"',  ");
		sbSellSql.append("'system',");  
		sbSellSql.append("'"+order.getMemberId()+"', ");
		sbSellSql.append("'"+branchID+"', ");
		sbSellSql.append("'1', ");
		sbSellSql.append("'"+order.getShopId()+"', ");
		sbSellSql.append("UNIX_TIMESTAMP(NOW())");
		sbSellSql.append(")");
		sqlDao.updateBySql(sbSellSql.toString());
		//明细
		 
		String branchName = branchName(branchID,branchList);
		StringBuilder sbdetailSql = new StringBuilder();
		sbdetailSql.append("INSERT INTO  sdb_ome_sales_items ");
		sbdetailSql.append("( ");
		sbdetailSql.append("sale_id, ");
		sbdetailSql.append("iostock_id, ");
		sbdetailSql.append("shop_id, ");
		sbdetailSql.append("shop_name, ");
		sbdetailSql.append("order_id, ");
		sbdetailSql.append("order_bn, ");
		sbdetailSql.append("product_id,  ");
		sbdetailSql.append("brand_name, ");
		sbdetailSql.append("bn, ");
		sbdetailSql.append("name, ");
		sbdetailSql.append("nums,  ");
		sbdetailSql.append("gross_sales, ");
		sbdetailSql.append("gross_sales_rate, ");
		sbdetailSql.append("price, ");
		sbdetailSql.append("amount, ");
		sbdetailSql.append("pmt_price, ");
		sbdetailSql.append("discount_amount, ");
		sbdetailSql.append("additional_costs, ");
		sbdetailSql.append("sales_price, ");
		sbdetailSql.append("sales_amount, ");
		sbdetailSql.append("branch_id, ");
		sbdetailSql.append("branch_name, ");
		sbdetailSql.append("sales_time, ");
		sbdetailSql.append("sales_type, ");
		sbdetailSql.append("create_time, ");
		sbdetailSql.append("STATUS, ");
		sbdetailSql.append("actual_status");
		sbdetailSql.append(")");
		sbdetailSql.append("VALUES");
		sbdetailSql.append("(");
		sbdetailSql.append("'"+sellid+"', ");
		sbdetailSql.append("'"+iostockid+"', ");
		sbdetailSql.append("'"+order.getShopId()+"', ");
		sbdetailSql.append("(SELECT name FROM  sdb_ome_shop WHERE shop_id = '"+order.getShopId()+"'), ");
		sbdetailSql.append("'"+order.getOrderId()+"', ");
		sbdetailSql.append("'"+order.getOrderBn()+"', ");
		sbdetailSql.append("'"+oi.getProductId()+"', ");
		sbdetailSql.append("(SELECT brand_name FROM sdb_ome_brand a , sdb_ome_products b WHERE a.brand_id = b.brand_id ");
		sbdetailSql.append("AND b.product_id = "+oi.getProductId()+"), ");
		sbdetailSql.append("'"+oi.getBn()+"', ");
		sbdetailSql.append("'"+oi.getName()+"', ");
		sbdetailSql.append("'"+oi.getNums()+"',  ");
		sbdetailSql.append("'"+oi.getPrice()+"', ");
		sbdetailSql.append("'100', ");
		sbdetailSql.append("'"+oi.getPrice()+"', ");
		sbdetailSql.append("'"+oi.getAmount()+"', ");
		sbdetailSql.append("'"+oi.getPmtPrice()+"', ");
		sbdetailSql.append("'"+oi.getPmtPrice()+"', ");
		sbdetailSql.append("'0.00', ");
		sbdetailSql.append("'"+oi.getSalePrice()+"', ");
		sbdetailSql.append("'"+oi.getAmount()+"', ");
		sbdetailSql.append("'"+branchID+"', ");
		sbdetailSql.append("'"+branchName+"', ");
		sbdetailSql.append("UNIX_TIMESTAMP(NOW()),  ");
		sbdetailSql.append("'0', ");
		sbdetailSql.append("UNIX_TIMESTAMP(NOW()), ");
		sbdetailSql.append("'2', ");
		sbdetailSql.append("'2'");
		sbdetailSql.append(")");
		sqlDao.updateBySql(sbdetailSql.toString());
	}

	private Map<String, String> shopMap() {
		Map<String, String> map = new HashedMap();
		map.put("Onitsuka Tiger官方旗舰店", "3");
		map.put("asics专卖店", "4");
		map.put("New Balance旗舰店", "1");
		return map;
	}
	
	private String branchName(String branchId,List<Branch> branchList){
		for(Branch branch : branchList){
			if(branchId.equals(branch.getBranchId())){
				return branch.getName();
			}
		}
		return null;
	}
}
