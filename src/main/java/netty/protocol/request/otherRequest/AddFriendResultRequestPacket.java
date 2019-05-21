package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.AddFriendResult_REQUEST;

/*
添加好友时   对方同意或拒绝了你的请求   你已经读到了对方同意还是拒绝   给服务器回执
 */
public class AddFriendResultRequestPacket extends Packet {
    int msgid;
    public AddFriendResultRequestPacket() {
    	
    }
   
    public AddFriendResultRequestPacket(int msgid){
        this.msgid = msgid;
    }
    @Override
    public int getCommand() {

        return AddFriendResult_REQUEST;
    }
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

}
