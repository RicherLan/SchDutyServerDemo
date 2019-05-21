package object;

public class GroupChatMsg {

    int msgid;              //消息的id   服务器要处理消息的未读和已读情况
    String groupid;
    String senderid;
    String sendername;     //发送者的名字   群聊的话为其群里的名片   好友的话为备注
    String receiverid;
    String msgtype;          //消息类型  文字  图片  语音等
    String msgbody;           //文字的话  就是文字就行    文件的话  为手机中的路径
    long msgtime;             //消息的时间
    float voicetime = 0;
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getMsgbody() {
		return msgbody;
	}
	public void setMsgbody(String msgbody) {
		this.msgbody = msgbody;
	}
	public long getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(long msgtime) {
		this.msgtime = msgtime;
	}
	public float getVoicetime() {
		return voicetime;
	}
	public void setVoicetime(float voicetime) {
		this.voicetime = voicetime;
	}

    
   
}
