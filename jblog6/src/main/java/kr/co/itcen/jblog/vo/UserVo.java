package kr.co.itcen.jblog.vo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class UserVo {

	@NotEmpty
	@Pattern(regexp = "\\w{8,16}")
	private String userId;

	@NotEmpty
	@Pattern(regexp = "^[가-힣]{2,6}$")
	private String userName;

	@NotEmpty
	@Size(min = 4, max = 12)
	private String userPassword;

	private String userJoindate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserJoindate() {
		return userJoindate;
	}

	public void setUserJoindate(String userJoindate) {
		this.userJoindate = userJoindate;
	}

	@Override
	public String toString() {
		return "UserVo [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword
				+ ", userJoindate=" + userJoindate + "]";
	}

}