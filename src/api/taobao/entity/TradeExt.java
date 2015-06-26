package api.taobao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

/**
 * 交易扩展表信息 <code>TradeExt.java</code>
 * <p>
 * <p>
 * Copyright 2014 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Entity
@Table(name = "t_trade_ext")
public class TradeExt implements Idable<Long>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/** enable前扩展标识位 */
	private Long beforeEnableFlag;
	/** 关闭订单前扩展标识位 */
	private Long beforeCloseFlag;
	/** 付款前扩展标识位 */
	private Long beforePayFlag;
	/** 发货前扩展标识位 */
	private Long beforeShipFlag;
	/** 确认收货前扩展标识位 */
	private Long beforeConfirmFlag;
	/** 评价前扩展标识位 */
	private Long beforeRateFlag;
	/** 退款前扩展标识位 */
	private Long beforeRefundFlag;
	/** 修改前扩展标识位 */
	private Long beforeModifyFlag;
	/** 第三方状态，第三方自由定义 */
	private Long thirdPartyStatus;
	/** 第三方个性化数据 */
	private String extraData;
	/** attributes标记 */
	private String extAttributes;
	
	/** 交易编号 (父订单的交易编号) */
	private Long tid;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getBeforeEnableFlag()
	{
		return beforeEnableFlag;
	}
	
	public void setBeforeEnableFlag(Long beforeEnableFlag)
	{
		this.beforeEnableFlag = beforeEnableFlag;
	}
	
	public Long getBeforeCloseFlag()
	{
		return beforeCloseFlag;
	}
	
	public void setBeforeCloseFlag(Long beforeCloseFlag)
	{
		this.beforeCloseFlag = beforeCloseFlag;
	}
	
	public Long getBeforePayFlag()
	{
		return beforePayFlag;
	}
	
	public void setBeforePayFlag(Long beforePayFlag)
	{
		this.beforePayFlag = beforePayFlag;
	}
	
	public Long getBeforeShipFlag()
	{
		return beforeShipFlag;
	}
	
	public void setBeforeShipFlag(Long beforeShipFlag)
	{
		this.beforeShipFlag = beforeShipFlag;
	}
	
	public Long getBeforeConfirmFlag()
	{
		return beforeConfirmFlag;
	}
	
	public void setBeforeConfirmFlag(Long beforeConfirmFlag)
	{
		this.beforeConfirmFlag = beforeConfirmFlag;
	}
	
	public Long getBeforeRateFlag()
	{
		return beforeRateFlag;
	}
	
	public void setBeforeRateFlag(Long beforeRateFlag)
	{
		this.beforeRateFlag = beforeRateFlag;
	}
	
	public Long getBeforeRefundFlag()
	{
		return beforeRefundFlag;
	}
	
	public void setBeforeRefundFlag(Long beforeRefundFlag)
	{
		this.beforeRefundFlag = beforeRefundFlag;
	}
	
	public Long getBeforeModifyFlag()
	{
		return beforeModifyFlag;
	}
	
	public void setBeforeModifyFlag(Long beforeModifyFlag)
	{
		this.beforeModifyFlag = beforeModifyFlag;
	}
	
	public Long getThirdPartyStatus()
	{
		return thirdPartyStatus;
	}
	
	public void setThirdPartyStatus(Long thirdPartyStatus)
	{
		this.thirdPartyStatus = thirdPartyStatus;
	}
	
	public String getExtraData()
	{
		return extraData;
	}
	
	public void setExtraData(String extraData)
	{
		this.extraData = extraData;
	}
	
	public String getExtAttributes()
	{
		return extAttributes;
	}
	
	public void setExtAttributes(String extAttributes)
	{
		this.extAttributes = extAttributes;
	}
	
	public Long getTid()
	{
		return tid;
	}
	
	public void setTid(Long tid)
	{
		this.tid = tid;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
	
}
