package object;

/*
 * 请求信息    添加删除等
 */
public class RequestFriendOrGroupMsg {

	private int msgid;
	private String myphonenumber;          //发送者的账号
	private String type;                  //类型 比如退群等
	private String otherid;               //对方的账号  也就是接收者的账号
	private long begintime;
	private String msg;                     //添加时的验证信息之类的
	private String groupid;                  //加群或退群时的群号
	 
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getMyphonenumber() {
		return myphonenumber;
	}
	public void setMyphonenumber(String myphonenumber) {
		this.myphonenumber = myphonenumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOtherid() {
		return otherid;
	}
	public void setOtherid(String otherid) {
		this.otherid = otherid;
	}
	public long getBegintime() {
		return begintime;
	}
	public void setBegintime(long begintime) {
		this.begintime = begintime;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	
	
}
