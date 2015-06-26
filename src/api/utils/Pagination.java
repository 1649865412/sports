package api.utils;

import java.util.List;

public class Pagination
{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public int results;
	
	public List<Object> rows;
	
	@SuppressWarnings("unchecked")
	public Pagination(List rows, int results)
	{
		super();
		this.results = results;
		this.rows = rows;
	}
	
	public int getResults()
	{
		return results;
	}
	
	public void setResults(int results)
	{
		this.results = results;
	}
	
	public List<Object> getRows()
	{
		return rows;
	}
	
	public void setRows(List<Object> rows)
	{
		this.rows = rows;
	}
	
}
