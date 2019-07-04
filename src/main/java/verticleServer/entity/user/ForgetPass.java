package verticleServer.entity.user;

public class ForgetPass {

	String phone;
	String imageCode; // 图片验证码
	String nodeCode; // 短信验证码
	long lasttime; // 上次获得短信验证码的时间；
	
	String encrypt;   //传输图片验证码时  同时传播一个密文   防止图片被破解。

	public ForgetPass() {
		this.phone = "";
		this.imageCode = "";
		this.nodeCode = "";
		this.lasttime = -1;
		encrypt = "";
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public long getLasttime() {
		return lasttime;
	}

	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}

	
}
