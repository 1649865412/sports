package com.innshine.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.entity.main.Organization;
import com.base.entity.main.User;
import com.base.shiro.ShiroUser;
import com.innshine.common.brand.BrandConfigEntity;
import com.innshine.common.brand.BrandConfigResource;
import com.utils.Exceptions;
import com.utils.SecurityUtils;
import com.utils.SystemConst;

public class LogInfoOrganizationFilter implements Filter
{
	private static final String ORGANIZATION_IDS_FIELD_NAME = "organizationIds";
	
	private static final String COMMA_SYMBOL = ",";
	
	/**
	 * 添加、修改时使用的字段名
	 */
	public static final String SAVE_FIELD_NAME_BRAND_ID = "brandId";
	
	/**
	 * 查询时使用的
	 */
	public static final String QUERY_FIELD_NAME_BRAND_ID = "search_EQ_brandId";
	
	/**
	 * 需要拦截的请求链表，默认按"，"分割
	 */
	private String interceptorActions;
	
	/**
	 * 不需要过滤的路径
	 */
	private String needToFilterActions;
	
	/**
	 * 所属部门编号列表
	 */
	private String organizationIds;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LogInfoOrganizationInterceptor.class);
	
	@Override
	public void destroy()
	{
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		// LOGGER.info("Need to intercept the request list ！ Values = [ " +
		// interceptorActions + "]");
		if (checkCurrRequest((HttpServletRequest) request))
		{
			request = setBrandId((HttpServletRequest) request);
		}
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.interceptorActions = filterConfig.getInitParameter("interceptorActions");
		this.organizationIds = getOrganizationIds(filterConfig);
		this.needToFilterActions = filterConfig.getInitParameter("needToFilterActions");
	}
	
	/**
	 * 获取品牌列表
	 * <p>
	 * 
	 * @param filterConfig
	 * @return
	 */
	private String getOrganizationIds(FilterConfig filterConfig)
	{
		String tmpString = "";
		
		try
		{
			List<BrandConfigEntity> configAttrEntities = BrandConfigResource.getInstance().getConfig();
			
			StringBuffer buffer = new StringBuffer();
			if (null != configAttrEntities)
			{
				for (int i = 0; i < configAttrEntities.size(); i++)
				{
					BrandConfigEntity brandConfigEntity = configAttrEntities.get(i);
					if (brandConfigEntity != null && StringUtils.isNotBlank(brandConfigEntity.getOrganizationId()))
					{
						buffer.append(brandConfigEntity.getOrganizationId()).append(COMMA_SYMBOL);
					}
				}
				
				if (StringUtils.isNotBlank(buffer.toString()))
				{
					tmpString = buffer.lastIndexOf(COMMA_SYMBOL) != -1 ? buffer.substring(0,
							buffer.lastIndexOf(COMMA_SYMBOL)) : buffer.toString();
				}
				else
				{
					tmpString = filterConfig.getInitParameter(ORGANIZATION_IDS_FIELD_NAME);
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
			tmpString = filterConfig.getInitParameter(ORGANIZATION_IDS_FIELD_NAME);
		}
		
		return tmpString;
	}
	
	/**
	 * 检测当前请求是否需要设置组织ID
	 * <p>
	 * 
	 * @param request
	 *            请求对象
	 * @return true ： 需要 flase ： 不需要
	 */
	private boolean checkCurrRequest(HttpServletRequest request)
	{
		String requestUri = request.getRequestURI();
		// String contextPath = request.getContextPath();
		// LOGGER.info("Request URI =  " + requestUri + ", contextPath = " +
		// contextPath);
		
		if (StringUtils.isNotBlank(requestUri))
		{
			String[] actions = StringUtils.isNotBlank(interceptorActions) ? interceptorActions.split(COMMA_SYMBOL)
					: null;
			
			if (ArrayUtils.isNotEmpty(actions))
			{
				return checkURL(requestUri, actions);
				
			}
			else
			{
				// 不需要添加所属部门编号的公用地址
				String[] tmpNeedToFilterActions = StringUtils.isNotBlank(needToFilterActions) ? needToFilterActions
						.split(COMMA_SYMBOL) : null;
				if (ArrayUtils.isNotEmpty(tmpNeedToFilterActions))
				{
					return checkURL(requestUri, tmpNeedToFilterActions) ? false : true;
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 检测URL是否需要过滤，不需要过滤的则直接返回false ，故则相反
	 * 
	 * @param requestUri
	 *            请求路径
	 * @param actions
	 *            过滤URL列表
	 * @param isFlag
	 *            true 找到需要过滤返回true false:找到不需要过滤false
	 * @return
	 */
	public boolean checkURL(String requestUri, String[] actions)
	{
		for (String action : actions)
		{
			if (StringUtils.isNotBlank(action))
			{
				if (action.toUpperCase().indexOf(requestUri.toUpperCase()) != -1
						|| requestUri.toUpperCase().indexOf(action.toUpperCase()) != -1)
				{
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 设置所属组织参数
	 * 
	 * @param request
	 *            请求对象
	 * @param isQuery
	 *            true ： 查询请求 false : 添加、删除请求
	 */
	private HttpServletRequest setBrandId(HttpServletRequest request)
	{
		try
		{
			ShiroUser shiroUser = SecurityUtils.getShiroUser();
			if(shiroUser == null){
				return request;
			}
			User u = shiroUser.getUser(); 
	/*		if (u.isAdmin())
			{
				return request;
			} */
			
			if(StringUtils.isNotBlank(u.getSwitchBrandId())){
				request = setParamter(request, u.getSwitchBrandId());
			}
			
		}
		catch (Exception e)
		{
			LOGGER.error(Exceptions.getStackTraceAsString(e));
		}
		
		return request;
	}
	
	@SuppressWarnings("unchecked")
	public HttpServletRequest setParamter(HttpServletRequest request, String brandId)
	{
		Map<String, String[]> map = new HashMap<String, String[]>(request.getParameterMap());
		
		map.putAll(request.getParameterMap());
		
		request = new ParameterRequestWrapper(request, map);
		
		if (null != map)
		{
			map.put(SAVE_FIELD_NAME_BRAND_ID, new String[] { brandId }); 
			 
		    map.put(QUERY_FIELD_NAME_BRAND_ID,new String[] { brandId });
			 
		}
		return request;
	}
}
