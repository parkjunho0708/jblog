package kr.co.itcen.jblog.vo;

public class BlogVo {
	private String userId;
	private String blogTitle;
	private String blogLogo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogLogo() {
		return blogLogo;
	}

	public void setBlogLogo(String blogLogo) {
		this.blogLogo = blogLogo;
	}

	@Override
	public String toString() {
		return "BlogVo [userId=" + userId + ", blogTitle=" + blogTitle + ", blogLogo=" + blogLogo + "]";
	}

}