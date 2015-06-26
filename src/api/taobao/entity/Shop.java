package api.taobao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.base.entity.Idable;

@Entity
@Table(name = "t_shop")
public class Shop implements Idable<Long>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 店铺编号 （淘宝店铺ID）
	 */
	@Column(name = "sid")
	private Long sid; // 
	/**
	 * 
	 */
	@Column(name = "title")
	private String title;
	/**
	 * // 应用key （淘宝APP Key）
	 */
	@Column(name = "appkey")
	private String appkey;
	
	/**
	 * // 应用密码 （淘宝密钥）
	 */
	@Column(name = "app_secret")
	private String appSecret;
	
	/**
	 * // 沙箱key应用key （淘宝APP Key）
	 */
	@Column(name = "sessionkey")
	private String sessionKey;
	
	/**
	 * // 沙箱key应用key （淘宝APP Key）
	 */
	@Column(name = "sandbox_appkey")
	private String sandboxAppkey;
	
	/**
	 * 沙箱应用密码 （淘宝密钥）
	 */
	@Column(name = "sandbox_appsecret")
	private String sandboxAppSecret;
	/**
	 * // 沙箱 sessionkey
	 */
	@Column(name = "sandbox_sessionkey")
	private String sandboxSessionkey;
	
	/**
	 * / refresh_token
	 */
	@Column(name = "refresh_token")
	private String refreshToken;
	
	/**
	 * 账户 （淘宝主账号）
	 */
	@Column(name = "account")
	private String account;
	/**
	 * // 密码 （主账号密码）、、密文保存
	 */
	@Column(name = "password")
	private String password;
	/**
	 * // 0表示有用的淘宝旗舰店铺，1表示已过期或不是淘宝旗舰店
	 */
	@Column(name = "is_test")
	private Integer isTest;
	/**
	 * // 0表示有用的淘宝旗舰店铺，1表示已过期或不是淘宝旗舰店
	 */
	@Column(name = "source")
	private Integer source;
	
	@Column(name = "description")
	private String description;
	
	/**
	 * // 0表淘宝旗舰店，1表示分销，2表示京东，5表示其他
	 */
	@Column(name = "is_fenxiao")
	private Integer isFenxiao;
	/**
	 * // 品牌
	 */
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "shop_name")
	private String shopName;
	
	/**
	 * 是否与OCS 订单比较匹配 true：匹配
	 */
	@Column(name = "is_ocs_order_compare")
	private Boolean isOCSOrderCompare;
	
	/**
	 * 运动版自定义品牌ID，与表base_brand中id对应
	 */
	@Column(name = "brand_id")
	private Long brandId;
	
	/**
	 * OCS系统中店铺编号与OCS系统表sdb_ome_shop中shop_id对应
	 */
	@Column(name = "shop_id")
	private String shopId;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId()
	{
		
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
		
	}
	
	public Long getSid()
	{
		return sid;
	}
	
	public void setSid(Long sid)
	{
		this.sid = sid;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getAppkey()
	{
		return appkey;
	}
	
	public void setAppkey(String appkey)
	{
		this.appkey = appkey;
	}
	
	public String getAppSecret()
	{
		return appSecret;
	}
	
	public void setAppSecret(String appSecret)
	{
		this.appSecret = appSecret;
	}
	
	public String getSessionKey()
	{
		return sessionKey;
	}
	
	public void setSessionKey(String sessionKey)
	{
		this.sessionKey = sessionKey;
	}
	
	public String getSandboxAppkey()
	{
		return sandboxAppkey;
	}
	
	public void setSandboxAppkey(String sandboxAppkey)
	{
		this.sandboxAppkey = sandboxAppkey;
	}
	
	public String getSandboxAppSecret()
	{
		return sandboxAppSecret;
	}
	
	public void setSandboxAppSecret(String sandboxAppSecret)
	{
		this.sandboxAppSecret = sandboxAppSecret;
	}
	
	public String getSandboxSessionkey()
	{
		return sandboxSessionkey;
	}
	
	public void setSandboxSessionkey(String sandboxSessionkey)
	{
		this.sandboxSessionkey = sandboxSessionkey;
	}
	
	public String getRefreshToken()
	{
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public Integer getIsTest()
	{
		return isTest;
	}
	
	public void setIsTest(Integer isTest)
	{
		this.isTest = isTest;
	}
	
	public Integer getSource()
	{
		return source;
	}
	
	public void setSource(Integer source)
	{
		this.source = source;
	}
	
	public Integer getIsFenxiao()
	{
		return isFenxiao;
	}
	
	public void setIsFenxiao(Integer isFenxiao)
	{
		this.isFenxiao = isFenxiao;
	}
	
	public String getBrand()
	{
		return brand;
	}
	
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	
	public String getShopName()
	{
		return shopName;
	}
	
	public void setShopName(String shopName)
	{
		this.shopName = shopName;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public Boolean getIsOCSOrderCompare()
	{
		return isOCSOrderCompare;
	}
	
	public void setIsOCSOrderCompare(Boolean isOCSOrderCompare)
	{
		this.isOCSOrderCompare = isOCSOrderCompare;
	}
	
	public Long getBrandId()
	{
		return brandId;
	}
	
	public void setBrandId(Long brandId)
	{
		this.brandId = brandId;
	}
	
	public String getShopId()
	{
		return shopId;
	}
	
	public void setShopId(String shopId)
	{
		this.shopId = shopId;
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
