package com.base.tag.dwz;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.base.util.dwz.Page;

/** 
 * @author 	Vigor
 * @since   2013年10月15日 上午11:27:43 
 */
public class PaginationFormTag extends SimpleTagSupport {

	private Page page;
	private String action;
	private String onsubmit;
	private String name;
	private boolean reverseOrderDirection;
	

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		StringBuilder builder = new StringBuilder();
		if (onsubmit != null) {
			builder.append("<form id=\"pagerForm\" method=\"post\" action=\"" + action + "\"");
			builder.append(StringUtils.isNotEmpty(name) ? " name=\"" +name +"\"" : "");
			builder.append("onsubmit=\"" + onsubmit + "\">\n");
		} else {
			builder.append("<form id=\"pagerForm\"" );
			builder.append(StringUtils.isNotEmpty(name) ? " name=\"" +name +"\"" : "");
			builder.append(	"method=\"post\" action=\"" + action + "\">\n");
	
		}
		
		builder.append("<input type=\"hidden\" name=\"pageNum\" value=\"" + page.getPageNum() + "\"/>\n");
		builder.append("<input type=\"hidden\" name=\"numPerPage\" value=\"" + page.getNumPerPage() + "\"/>\n");
		builder.append("<input type=\"hidden\" name=\"orderField\" value=\"" + page.getOrderField() + "\"/>\n");
		builder.append("<input type=\"hidden\" name=\"orderDirection\" value=\"" + getOrderDirection(page) + "\"/>\n");
		builder.append("<input type=\"hidden\" name=\"totalCount\" value=\"" + page.getTotalCount() + "\"/>\n");

		getJspContext().getOut().write(builder.toString());
		
		getJspBody().invoke(null);
		
		getJspContext().getOut().write("</form>\n");
	}

	private String getOrderDirection(Page page)
	{	
		String orderDirection = page.getOrderDirection();
		if(isReverseOrderDirection())
		{
			 if(StringUtils.isNotEmpty(orderDirection))
			 {
				 if("ASC".equalsIgnoreCase(orderDirection))
				 {
					 orderDirection = "desc";
				 }
				 else
				 {
					 orderDirection = "asc";
				 }
			 }
			 
			 page.setOrderDirection(orderDirection);
		}
		
		return orderDirection;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param onsubmit the onsubmit to set
	 */
	public void setOnsubmit(String onsubmit) {
		this.onsubmit = onsubmit;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isReverseOrderDirection()
	{
		return reverseOrderDirection;
	}

	public void setReverseOrderDirection(boolean reverseOrderDirection)
	{
		this.reverseOrderDirection = reverseOrderDirection;
	}

}
