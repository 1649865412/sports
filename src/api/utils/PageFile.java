package api.utils;

import java.util.List;

public class PageFile {

	/** 总记录数 */
	private int totalProperty;

	/** 分页结果 */
	private List root;

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}

	public int getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}


}
