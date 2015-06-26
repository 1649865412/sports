package api.taobao.entity;

import java.io.Serializable;

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

/**
 * 商城虚拟服务子订单数据结构 <code>ServiceOrder.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Entity
@Table(name = "t_service_order")
public class ServiceOrder implements Serializable, Idable<Long>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6396078916684599499L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 虚拟服务子订单订单号
	 */
	@Column(name = "oid")
	private Long oid;
	
	/** 服务所属的交易订单号。如果服务为一年包换，则item_oid这笔订单享受改服务的保护 */
	@Column(name = "item_oid")
	private Long itemOid;
	
	/** 服务数字id */
	@Column(name = "service_id")
	private Long serviceId;
	
	/** 服务详情的URL地址 */
	
	@Column(name = "service_detail_url")
	private String serviceDetailUrl;
	
	/** 购买数量，取值范围为大于0的整数 */
	@Column(name = "num")
	private Long num;
	
	/** 服务价格，精确到小数点后两位：单位:元 */
	@Column(name = "price")
	private String price;
	
	/** 子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。 */
	@Column(name = "payment")
	private String payment;
	
	/** 商品名称 */
	@Column(name = "title")
	private String title;
	
	/** 服务子订单总费用 */
	@Column(name = "total_fee")
	private String totalFee;
	
	/** 卖家昵称 */
	@Column(name = "buyer_nick")
	private String buyerNick;
	
	/** 最近退款的id */
	@Column(name = "refund_id")
	private Long refundId;
	
	/** 卖家昵称 */
	@Column(name = "seller_nick")
	private String sellerNick;
	
	/** 服务图片地址 */
	@Column(name = "pic_path")
	private String picPath;
	
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
	
	public Trade getTradeInfo()
	{
		return tradeInfo;
	}
	
	public void setTradeInfo(Trade tradeInfo)
	{
		this.tradeInfo = tradeInfo;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getItemOid()
	{
		return itemOid;
	}
	
	public void setItemOid(Long itemOid)
	{
		this.itemOid = itemOid;
	}
	
	public Long getServiceId()
	{
		return serviceId;
	}
	
	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}
	
	public String getServiceDetailUrl()
	{
		return serviceDetailUrl;
	}
	
	public void setServiceDetailUrl(String serviceDetailUrl)
	{
		this.serviceDetailUrl = serviceDetailUrl;
	}
	
	public Long getNum()
	{
		return num;
	}
	
	public void setNum(Long num)
	{
		this.num = num;
	}
	
	public String getPrice()
	{
		return price;
	}
	
	public void setPrice(String price)
	{
		this.price = price;
	}
	
	public String getPayment()
	{
		return payment;
	}
	
	public void setPayment(String payment)
	{
		this.payment = payment;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTotalFee()
	{
		return totalFee;
	}
	
	public void setTotalFee(String totalFee)
	{
		this.totalFee = totalFee;
	}
	
	public String getBuyerNick()
	{
		return buyerNick;
	}
	
	public void setBuyerNick(String buyerNick)
	{
		this.buyerNick = buyerNick;
	}
	
	public Long getRefundId()
	{
		return refundId;
	}
	
	public void setRefundId(Long refundId)
	{
		this.refundId = refundId;
	}
	
	public String getSellerNick()
	{
		return sellerNick;
	}
	
	public void setSellerNick(String sellerNick)
	{
		this.sellerNick = sellerNick;
	}
	
	public String getPicPath()
	{
		return picPath;
	}
	
	public void setPicPath(String picPath)
	{
		this.picPath = picPath;
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
	
	public Long getOid()
	{
		return oid;
	}
	
	public void setOid(Long oid)
	{
		this.oid = oid;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
