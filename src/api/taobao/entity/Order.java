package api.taobao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "t_orders")
public class Order implements Serializable, Idable<Long>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/** 套餐的值。如：M8原装电池:便携支架:M8专用座充:莫凡保护袋 */
	@Column(name = "item_meal_name")
	private String itemMealName;
	/** 商品图片的绝对路径 */
	@Column(name = "pic_path")
	private String picPath;
	/** 卖家昵称 */
	@Column(name = "seller_nick")
	private String sellerNick;
	/** 买家昵称 */
	@Column(name = "buyer_nick")
	private String buyerNick;
	/**
	 * 退款状态。退款状态。可选值 WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意)
	 * WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货)
	 * WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) SELLER_REFUSE_BUYER(卖家拒绝退款)
	 * CLOSED(退款关闭) SUCCESS(退款成功)
	 */
	@Column(name = "refund_status")
	private String refundStatus;
	/**
	 * 商家外部编码(可与商家外部系统对接)。外部商家自己定义的商品Item的id，可以通过taobao.items.custom.
	 * get获取商品的Item的信息
	 */
	@Column(name = "outer_iid")
	private String outerIid;
	/** 订单快照URL */
	@Column(name = "snapshot_url")
	private String snapshotUrl;
	/** 订单快照详细信息 */
	@Column(name = "snapshot")
	private String snapshot;
	
	/** 订单超时到期时间。格式:yyyy-MM-dd HH:mm:ss */
	@Column(name = "timeout_action_time")
	private Date timeoutActionTime;
	
	/** 买家是否已评价。可选值：true(已评价)，false(未评价) */
	@Column(name = "buyer_rate")
	private Boolean buyerRate;
	
	/** 卖家是否已评价。可选值：true(已评价)，false(未评价) */
	@Column(name = "seller_rate")
	private Boolean sellerRate;
	
	/** 卖家类型，可选值为：B（商城商家），C（普通卖家） */
	@Column(name = "seller_type")
	private String sellerType;
	
	/** 交易商品对应的类目ID */
	@Column(name = "cid")
	private Long cid;
	/** 子订单编号 */
	@Column(name = "oid")
	private Long oid;
	
	/** 订单状态（请关注此状态，如果为TRADE_CLOSED_BY_TAOBAO状态，则不要对此订单进行发货，切记啊！）。可选值: */
	@Column(name = "STATUS")
	private String status;
	
	/** 商品的字符串编号(注意：iid近期即将废弃，请用num_iid参数) */
	@Column(name = "iid")
	private String iid;
	
	/** 商品标题 */
	@Column(name = "title")
	private String title;
	
	/** 商品价格。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
	@Column(name = "price")
	private String price;
	
	/** 商品数字ID */
	@Column(name = "num_iid")
	private Long numIid;
	
	/** 套餐ID */
	@Column(name = "item_meal_id")
	private Long itemMealId;
	
	/** 商品的最小库存单位Sku的id.可以通过taobao.item.sku.get获取详细的Sku信息 */
	@Column(name = "sku_id")
	private String skuId;
	
	/** 购买数量。取值范围:大于零的整数 */
	@Column(name = "num")
	private Long num;
	
	/** 外部网店自己定义的Sku编号 */
	@Column(name = "outer_sku_id")
	private String outerSkuId;
	
	/** 子订单来源,如jhs(聚划算)、taobao(淘宝)、wap(无线) */
	@Column(name = "order_from")
	private String orderFrom;
	
	/** 应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
	@Column(name = "total_fee")
	private String totalFee;
	/**
	 * 子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。对于多子订单的交易，计算公式如下：payment = price
	 * * num + adjust_fee - discount_fee
	 * ；单子订单交易，payment与主订单的payment一致，对于退款成功的子订单
	 * ，由于主订单的优惠分摊金额，会造成该字段可能不为0.00元。建议使用退款前的实付金额减去退款单中的实际退款金额计算。
	 */
	@Column(name = "payment")
	private String payment;
	
	/** 子订单级订单优惠金额。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
	@Column(name = "discount_fee")
	private String discountFee;
	
	/** 手工调整金额.格式为:1.01;单位:元;精确到小数点后两位. */
	@Column(name = "adjust_fee")
	private String adjustFee;
	
	/** 订单修改时间，目前只有taobao.trade.ordersku.update会返回此字段。 */
	@Column(name = "modified")
	private Date modified;
	
	/** SKU的值。如：机身颜色:黑色;手机套餐:官方标配 */
	@Column(name = "sku_properties_name")
	private String skuPropertiesName;
	
	/** 最近退款ID */
	@Column(name = "refund_id")
	private Long refundId;
	
	/** 是否超卖 */
	@Column(name = "is_oversold")
	private Boolean isOversold;
	
	/** 是否是服务订单，是返回true，否返回false。 */
	@Column(name = "is_service_order")
	private Boolean isServiceOrder;
	/**
	 * 子订单的交易结束时间 说明：子订单有单独的结束时间，与主订单的结束时间可能有所不同，在有退款发起的时候或者是主订单分阶段付款的时候，
	 * 子订单的结束时间会早于主订单的结束时间，所以开放这个字段便于订单结束状态的判断
	 */
	@Column(name = "end_time")
	private Date endTime;
	
	/**
	 * 子订单发货时间，当卖家对订单进行了多次发货，子订单的发货时间和主订单的发货时间可能不一样了，那么就需要以子订单的时间为准。（没有进行多次发货的订单
	 * ，主订单的发货时间和子订单的发货时间都一样）
	 */
	@Column(name = "consign_time")
	private String consignTime;
	
	/**
	 * 子订单的运送方式（卖家对订单进行多次发货之后，一个主订单下的子订单的运送方式可能不同，用order.
	 * shipping_type来区分子订单的运送方式）
	 */
	@Column(name = "shipping_type")
	private String shippingType;
	
	/** 捆绑的子订单号，表示该子订单要和捆绑的子订单一起发货，用于卖家子订单捆绑发货 */
	@Column(name = "bind_oid")
	private Long bindOid;
	
	/** 子订单发货的快递公司名称 */
	@Column(name = "logistics_company")
	private String logisticsCompany;
	
	/** 子订单所在包裹的运单号 */
	@Column(name = "invoice_no")
	private String invoiceNo;
	
	/** 表示订单交易是否含有对应的代销采购单。 如果该订单中存在一个对应的代销采购单，那么该值为true；反之，该值为false。 */
	@Column(name = "is_daixiao")
	private Boolean isDaixiao;
	
	/** 分摊之后的实付金额 */
	@Column(name = "divide_order_fee")
	private String divideOrderFee;
	
	/** 优惠分摊 */
	@Column(name = "part_mjz_discount")
	private String partMjzDiscount;
	
	/** 对应门票有效期的外部id */
	@Column(name = "ticket_outer_id")
	private String ticketOuterId;
	
	/** 门票有效期的key */
	@Column(name = "ticket_expdate_key")
	private String ticketExpdateKey;
	
	/** 发货的仓库编码 */
	@Column(name = "store_code")
	private String storeCode;
	
	/** 子订单是否是www订单 */
	@Column(name = "is_www")
	private Boolean isWww;
	
	/** 支持家装类物流的类型 */
	@Column(name = "tmser_spu_code")
	private String tmserSpuCode;
	
	/**
	 * 交易编号 (父订单的交易编号)
	 */
	@Column(nullable = false, insertable = false, updatable = false)
	private Long tid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tid")
	private Trade tradeInfo;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getPicPath()
	{
		return picPath;
	}
	
	public String getItemMealName()
	{
		return itemMealName;
	}
	
	public void setItemMealName(String itemMealName)
	{
		this.itemMealName = itemMealName;
	}
	
	public void setPicPath(String picPath)
	{
		this.picPath = picPath;
	}
	
	public String getSellerNick()
	{
		return sellerNick;
	}
	
	public void setSellerNick(String sellerNick)
	{
		this.sellerNick = sellerNick;
	}
	
	public String getBuyerNick()
	{
		return buyerNick;
	}
	
	public void setBuyerNick(String buyerNick)
	{
		this.buyerNick = buyerNick;
	}
	
	public String getRefundStatus()
	{
		return refundStatus;
	}
	
	public void setRefundStatus(String refundStatus)
	{
		this.refundStatus = refundStatus;
	}
	
	public String getOuterIid()
	{
		return outerIid;
	}
	
	public void setOuterIid(String outerIid)
	{
		this.outerIid = outerIid;
	}
	
	public String getSnapshotUrl()
	{
		return snapshotUrl;
	}
	
	public void setSnapshotUrl(String snapshotUrl)
	{
		this.snapshotUrl = snapshotUrl;
	}
	
	public String getSnapshot()
	{
		return snapshot;
	}
	
	public void setSnapshot(String snapshot)
	{
		this.snapshot = snapshot;
	}
	
	public Date getTimeoutActionTime()
	{
		return timeoutActionTime;
	}
	
	public void setTimeoutActionTime(Date timeoutActionTime)
	{
		this.timeoutActionTime = timeoutActionTime;
	}
	
	public Boolean getBuyerRate()
	{
		return buyerRate;
	}
	
	public void setBuyerRate(Boolean buyerRate)
	{
		this.buyerRate = buyerRate;
	}
	
	public Boolean getSellerRate()
	{
		return sellerRate;
	}
	
	public void setSellerRate(Boolean sellerRate)
	{
		this.sellerRate = sellerRate;
	}
	
	public String getSellerType()
	{
		return sellerType;
	}
	
	public void setSellerType(String sellerType)
	{
		this.sellerType = sellerType;
	}
	
	public Long getCid()
	{
		return cid;
	}
	
	public void setCid(Long cid)
	{
		this.cid = cid;
	}
	
	public Long getOid()
	{
		return oid;
	}
	
	public void setOid(Long oid)
	{
		this.oid = oid;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getIid()
	{
		return iid;
	}
	
	public void setIid(String iid)
	{
		this.iid = iid;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getPrice()
	{
		return price;
	}
	
	public void setPrice(String price)
	{
		this.price = price;
	}
	
	public Long getNumIid()
	{
		return numIid;
	}
	
	public void setNumIid(Long numIid)
	{
		this.numIid = numIid;
	}
	
	public Long getItemMealId()
	{
		return itemMealId;
	}
	
	public void setItemMealId(Long itemMealId)
	{
		this.itemMealId = itemMealId;
	}
	
	public String getSkuId()
	{
		return skuId;
	}
	
	public void setSkuId(String skuId)
	{
		this.skuId = skuId;
	}
	
	public Long getNum()
	{
		return num;
	}
	
	public void setNum(Long num)
	{
		this.num = num;
	}
	
	public String getOuterSkuId()
	{
		return outerSkuId;
	}
	
	public void setOuterSkuId(String outerSkuId)
	{
		this.outerSkuId = outerSkuId;
	}
	
	public String getOrderFrom()
	{
		return orderFrom;
	}
	
	public void setOrderFrom(String orderFrom)
	{
		this.orderFrom = orderFrom;
	}
	
	public String getTotalFee()
	{
		return totalFee;
	}
	
	public void setTotalFee(String totalFee)
	{
		this.totalFee = totalFee;
	}
	
	public String getPayment()
	{
		return payment;
	}
	
	public void setPayment(String payment)
	{
		this.payment = payment;
	}
	
	public String getDiscountFee()
	{
		return discountFee;
	}
	
	public void setDiscountFee(String discountFee)
	{
		this.discountFee = discountFee;
	}
	
	public String getAdjustFee()
	{
		return adjustFee;
	}
	
	public void setAdjustFee(String adjustFee)
	{
		this.adjustFee = adjustFee;
	}
	
	public Date getModified()
	{
		return modified;
	}
	
	public void setModified(Date modified)
	{
		this.modified = modified;
	}
	
	public String getSkuPropertiesName()
	{
		return skuPropertiesName;
	}
	
	public void setSkuPropertiesName(String skuPropertiesName)
	{
		this.skuPropertiesName = skuPropertiesName;
	}
	
	public Long getRefundId()
	{
		return refundId;
	}
	
	public void setRefundId(Long refundId)
	{
		this.refundId = refundId;
	}
	
	public Boolean getIsOversold()
	{
		return isOversold;
	}
	
	public void setIsOversold(Boolean isOversold)
	{
		this.isOversold = isOversold;
	}
	
	public Boolean getIsServiceOrder()
	{
		return isServiceOrder;
	}
	
	public void setIsServiceOrder(Boolean isServiceOrder)
	{
		this.isServiceOrder = isServiceOrder;
	}
	
	public Date getEndTime()
	{
		return endTime;
	}
	
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	
	public String getConsignTime()
	{
		return consignTime;
	}
	
	public void setConsignTime(String consignTime)
	{
		this.consignTime = consignTime;
	}
	
	public String getShippingType()
	{
		return shippingType;
	}
	
	public void setShippingType(String shippingType)
	{
		this.shippingType = shippingType;
	}
	
	public Long getBindOid()
	{
		return bindOid;
	}
	
	public void setBindOid(Long bindOid)
	{
		this.bindOid = bindOid;
	}
	
	public String getLogisticsCompany()
	{
		return logisticsCompany;
	}
	
	public void setLogisticsCompany(String logisticsCompany)
	{
		this.logisticsCompany = logisticsCompany;
	}
	
	public String getInvoiceNo()
	{
		return invoiceNo;
	}
	
	public void setInvoiceNo(String invoiceNo)
	{
		this.invoiceNo = invoiceNo;
	}
	
	public Boolean getIsDaixiao()
	{
		return isDaixiao;
	}
	
	public void setIsDaixiao(Boolean isDaixiao)
	{
		this.isDaixiao = isDaixiao;
	}
	
	public String getDivideOrderFee()
	{
		return divideOrderFee;
	}
	
	public void setDivideOrderFee(String divideOrderFee)
	{
		this.divideOrderFee = divideOrderFee;
	}
	
	public String getPartMjzDiscount()
	{
		return partMjzDiscount;
	}
	
	public void setPartMjzDiscount(String partMjzDiscount)
	{
		this.partMjzDiscount = partMjzDiscount;
	}
	
	public String getTicketOuterId()
	{
		return ticketOuterId;
	}
	
	public void setTicketOuterId(String ticketOuterId)
	{
		this.ticketOuterId = ticketOuterId;
	}
	
	public String getTicketExpdateKey()
	{
		return ticketExpdateKey;
	}
	
	public void setTicketExpdateKey(String ticketExpdateKey)
	{
		this.ticketExpdateKey = ticketExpdateKey;
	}
	
	public String getStoreCode()
	{
		return storeCode;
	}
	
	public void setStoreCode(String storeCode)
	{
		this.storeCode = storeCode;
	}
	
	public Boolean getIsWww()
	{
		return isWww;
	}
	
	public void setIsWww(Boolean isWww)
	{
		this.isWww = isWww;
	}
	
	public String getTmserSpuCode()
	{
		return tmserSpuCode;
	}
	
	public void setTmserSpuCode(String tmserSpuCode)
	{
		this.tmserSpuCode = tmserSpuCode;
	}
	
	public Long getTid()
	{
		return tid;
	}
	
	public void setTid(Long tid)
	{
		this.tid = tid;
	}
	
	public Trade getTradeInfo()
	{
		return tradeInfo;
	}
	
	public void setTradeInfo(Trade tradeInfo)
	{
		this.tradeInfo = tradeInfo;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
