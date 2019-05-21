package object;

/*
 * 用户自己的好友的对于自己的信息
 */
public class MyFriendEasy {

	private  String phonenumber;
	private String nickname;
	private  String icon;
	private  String sex;
	
	private String remark;                  //备注
	private int friendgroup;             //该好友在自己的哪个分组

	
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getFriendgroup() {
		return friendgroup;
	}

	public void setFriendgroup(int friendgroup) {
		this.friendgroup = friendgroup;
	}
}
