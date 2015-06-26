package api.taobao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.base.entity.Idable;

/**
 * 交易的优惠信息详情 <code>TradePromotionDetail.java</code>
 * 
 * @version 1.0 </br>最后修改人 无
 */
@Entity
@Table(name = "t_trade_promotion_detail")
public class TradePromotionDetail implements Idable<Long>
{
	/** 交易的主订单或子订单号 */
	@Id
	private Long id;
	/** 优惠信息的名称 */
	private String promotionName;
	/** 优惠金额（免运费、限时打折时为空）,单位：元 */
	private String discountFee;
	/** 满就送商品时，所送商品的名称 */
	private String giftItemName;
	/** 赠品的宝贝id */
	private String giftItemId;
	/** 满就送礼物的礼物数量 */
	private String giftItemNum;
	/** 优惠活动的描述 */
	private String promotionDesc;
	/**
	 * 优惠id，(由营销工具id、优惠活动id和优惠详情id组成，结构为：营销工具id-优惠活动id_优惠详情id，如mjs-123024_211143
	 * ）
	 */
	private String promotionId;
	
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
	
	public Long getTid()
	{
		return tid;
	}
	
	public void setTid(Long tid)
	{
		this.tid = tid;
	}
	
	public String getPromotionName()
	{
		return promotionName;
	}
	
	public void setPromotionName(String promotionName)
	{
		this.promotionName = promotionName;
	}
	
	public String getDiscountFee()
	{
		return discountFee;
	}
	
	public void setDiscountFee(String discountFee)
	{
		this.discountFee = discountFee;
	}
	
	public String getGiftItemName()
	{
		return giftItemName;
	}
	
	public void setGiftItemName(String giftItemName)
	{
		this.giftItemName = giftItemName;
	}
	
	public String getGiftItemId()
	{
		return giftItemId;
	}
	
	public void setGiftItemId(String giftItemId)
	{
		this.giftItemId = giftItemId;
	}
	
	public String getGiftItemNum()
	{
		return giftItemNum;
	}
	
	public void setGiftItemNum(String giftItemNum)
	{
		this.giftItemNum = giftItemNum;
	}
	
	public String getPromotionDesc()
	{
		return promotionDesc;
	}
	
	public void setPromotionDesc(String promotionDesc)
	{
		this.promotionDesc = promotionDesc;
	}
	
	public String getPromotionId()
	{
		return promotionId;
	}
	
	public void setPromotionId(String promotionId)
	{
		this.promotionId = promotionId;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
