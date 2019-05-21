package object;

/*
 * 动态的  点赞  评论 转发 消息
 */
public class DongtaiMsg {

	public int msgid;
	public String type;    //praise       todongtai  tocomment    transmit
	public int dongtaiid;
	public String userid;          
	public String username;
	public String usericon;
	public String sex;
	public String schoolname;
	//private String departmentname;
	public String msg;          //评论的话  或者转发时说的话
	public int commentid;     //给动态的某一条评论 评论
	public String becommenteduserid;   //当type是tocomment时  要有被评论的人的账号   因为动态主人要看到信息
	public String becommentedusername; //当type是tocomment时  要有被评论的人的名字
	public long time;
	
	
	
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDongtaiid() {
		return dongtaiid;
	}
	public void setDongtaiid(int dongtaiid) {
		this.dongtaiid = dongtaiid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsericon() {
		return usericon;
	}
	public void setUsericon(String usericon) {
		this.usericon = usericon;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public String getBecommenteduserid() {
		return becommenteduserid;
	}
	public void setBecommenteduserid(String becommenteduserid) {
		this.becommenteduserid = becommenteduserid;
	}
	public String getBecommentedusername() {
		return becommentedusername;
	}
	public void setBecommentedusername(String becommentedusername) {
		this.becommentedusername = becommentedusername;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

	
		
}
