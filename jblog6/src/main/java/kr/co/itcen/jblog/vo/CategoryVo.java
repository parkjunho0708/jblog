package kr.co.itcen.jblog.vo;

public class CategoryVo {
	private int categoryNo;
	private String categoryName;
	private String categoryDesc;
	private String categoryRegdate;
	private String categoryStatus;
	private int postCount;
	private String userId;

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getCategoryRegdate() {
		return categoryRegdate;
	}

	public void setCategoryRegdate(String categoryRegdate) {
		this.categoryRegdate = categoryRegdate;
	}

	public String getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CategoryVo [categoryNo=" + categoryNo + ", categoryName=" + categoryName + ", categoryDesc="
				+ categoryDesc + ", categoryRegdate=" + categoryRegdate + ", categoryStatus=" + categoryStatus
				+ ", postCount=" + postCount + ", userId=" + userId + "]";
	}

}