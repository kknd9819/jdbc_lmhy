package com.zz.model.basic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 6263104921304293805L;

	// 分页显示集合
	private List<T> content = Collections.emptyList();

	// 总行数
	private int total;
	
	// 每页显示多少个
	private int pageSize = 10;

	// 当前第几页
	private int currentPage;

	public Page() {
		this.content = new ArrayList<T>();
		this.total = 0;
		this.currentPage = 1;
		this.pageSize = 0;
	}

	public Page(List<T> content, int total, int currentPage, int pageSize) {
		this.content = content;
		this.total = total;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	/** 获取分页集合 */
	public List<T> getContent() {
		return content;
	}

	/** 设置分页集合 */
	public void setContent(List<T> content) {
		this.content = content;
	}

	/** 总行数 */
	public int getTotal() {
		return total;
	}

	/** 每页显示多少个 */
	public int getPageSize() {
		return pageSize == 0 ? 10 : pageSize;
	}

	/** 当前第几页 */
	public int getCurrentPage() {
		return currentPage == 0 ? 1 : currentPage;
	}

	/** 共多少页 */
	public int getTotalPage() {
		return pageSize == 0 ? 1 : (int) Math.ceil((double) total / (double) pageSize);
	}

	/** 分页开始处 */
	public int getStartRow() {
		return ((currentPage - 1) < 0) ? 0 : (currentPage - 1) * pageSize;
	}

	/** 分页结束处 */
	public int getEndRow() {
		return (currentPage * pageSize) < total ? (currentPage * pageSize) : total;
	}

	/** 是否有前一页 */
	public boolean isHasPrePage() {
		return currentPage - 1 >= 1;
	}

	/** 上一页 */
	public int getPrePage() {
		if (isHasPrePage()) {
			return currentPage - 1;
		} else {
			return currentPage;
		}
	}

	/** 是否第一页 */
	public boolean isFirstPage() {
		return currentPage <= 1;
	}

	/** 是否有下一页 */
	public boolean isHasNextPage() {
		return currentPage + 1 <= getTotalPage();
	}

	/** 下一页 */
	public int getNextPage() {
		if (isHasNextPage()) {
			return currentPage + 1;
		} else {
			return currentPage;
		}
	}

	/** 是否最后一页 */
	public boolean isLastPage() {
		return currentPage >= getTotalPage();
	}


	
}
