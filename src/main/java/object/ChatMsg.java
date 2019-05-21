package object;

public class ChatMsg {

    int id=-1;

    int msgid=-1;              //消息的id   服务器要处理消息的未读和已读情况
    String type;             //群消息还是单人聊天消息  group single
                            // 请求  addgroup   addfriend     agreeYourAddFriend  disagreeYourAddFriend  agreeAddGroup  disagreeAddGroup
                            //yourfrienddeleteyou
                            //值班表到来通知  schDuty
    int groupid;
    String senderid;
    
    String sendername;     //发送者的名字   群聊的话为其群里的名片   好友的话为备注
    String sendersex;
    String sendericon;
    String receiverid;
    String receivername;     //发送者的名字   群聊的话为其群里的名片   好友的话为备注
    String msgtype;          //消息类型  文字  图片  语音等  text  image   voice
    String msgbody;           //文字的话  就是文字就行    文件的话  为手机中的路径
                            //当type是schDuty时   msgbody的值为  yes  no  代表本次值班我是否被安排进来
    long msgtime;             //消息的时间
    double voicetime;

    boolean sendrs = true;       //消息发送是否成功   现阶段只判断语音和图片


  String handleRs = "nothandle";        //nothandle     agree   disagree  hadread
                                        //当type是schDuty时 new old  hasread代表是不是新的值班表 hasread代表已读


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
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

    public String getSendersex() {
        return sendersex;
    }

    public void setSendersex(String sendersex) {
        this.sendersex = sendersex;
    }

    public String getSendericon() {
        return sendericon;
    }

    public void setSendericon(String sendericon) {
        this.sendericon = sendericon;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
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

    public double getVoicetime() {
        return voicetime;
    }

    public void setVoicetime(double voicetime) {
        this.voicetime = voicetime;
    }

    public boolean isSendrs() {
        return sendrs;
    }

    public void setSendrs(boolean sendrs) {
        this.sendrs = sendrs;
    }

    public String getHandleRs() {
        return handleRs;
    }

    public void setHandleRs(String handleRs) {
        this.handleRs = handleRs;
    }
}
