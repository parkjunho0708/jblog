package kr.co.itcen.jblog.vo;

public class PostVo {
	private int postNo;
	private String postTitle;
	private String postContents;
	private String postCreatedate;
	private String postStatus;
	private int categoryNo;
	private String userId; // 포스트 삭제할 때, 사용

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContents() {
		return postContents;
	}

	public void setPostContents(String postContents) {
		this.postContents = postContents;
	}

	public String getPostCreatedate() {
		return postCreatedate;
	}

	public void setPostCreatedate(String postCreatedate) {
		this.postCreatedate = postCreatedate;
	}

	public String getPostStatus() {
		return postStatus;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PostVo [postNo=" + postNo + ", postTitle=" + postTitle + ", postContents=" + postContents
				+ ", postCreatedate=" + postCreatedate + ", postStatus=" + postStatus + ", categoryNo=" + categoryNo
				+ ", userId=" + userId + "]";
	}

}