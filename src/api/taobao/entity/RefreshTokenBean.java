package api.taobao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.base.entity.Idable;

/**
 *RefreshToken的bean
 */
@Entity
@Table(name = "t_refreshtoken")
public class RefreshTokenBean implements Idable<Long>
{
	/**
	 * 注意事项：每天每个sessionKey最多刷新60次。
	 */
	private static final long serialVersionUID = 1L;
	
	private String top_session;// 激活后的新sessionkey
	private long expires_in;// session过期时间（long型）
	private String refresh_token;// 激活后的新Refresh token
	private long re_expires_in;// Refresh token失效时间（long型）
	private String sign;// 签名
	private String error;// 错误码
	private String error_description;// 错误描述
	private long shop_id; // 店铺ID
	private Date updateTime;// 每次更新的时间记录
	private boolean success;// 是否更新成功
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public long getShop_id()
	{
		return shop_id;
	}
	
	public void setShop_id(long shopId)
	{
		shop_id = shopId;
	}
	
	public Date getUpdateTime()
	{
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}
	
	public String getTop_session()
	{
		return top_session;
	}
	
	public void setTop_session(String topSession)
	{
		top_session = topSession;
	}
	
	public long getExpires_in()
	{
		return expires_in;
	}
	
	public void setExpires_in(long expiresIn)
	{
		expires_in = expiresIn;
	}
	
	public String getRefresh_token()
	{
		return refresh_token;
	}
	
	public void setRefresh_token(String refreshToken)
	{
		refresh_token = refreshToken;
	}
	
	public long getRe_expires_in()
	{
		return re_expires_in;
	}
	
	public void setRe_expires_in(long reExpiresIn)
	{
		re_expires_in = reExpiresIn;
	}
	
	public String getSign()
	{
		return sign;
	}
	
	public void setSign(String sign)
	{
		this.sign = sign;
	}
	
	public String getError()
	{
		return error;
	}
	
	public void setError(String error)
	{
		this.error = error;
	}
	
	public String getError_description()
	{
		return error_description;
	}
	
	public void setError_description(String errorDescription)
	{
		error_description = errorDescription;
	}
	
	public Long getId()
	{
		
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
		
	}
	
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
