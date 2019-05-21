package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddGroupReturn_RESPONSE;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/*
用户申请加群时   管理员同意或不同意   回执
服务器响应
 */
public class AddGroupReturnResponsePacket extends Packet {

	String rs;
	int msgid;
	String type;
	int groupid;
	String otherphonenumber;
	String othernickname;
	
    @Override
    public int getCommand() {

        return AddGroupReturn_RESPONSE;
    }

    
	public String getRs() {
		return rs;
	}


	public void setRs(String rs) {
		this.rs = rs;
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

	public String getOtherphonenumber() {
		return otherphonenumber;
	}

	public void setOtherphonenumber(String otherphonenumber) {
		this.otherphonenumber = otherphonenumber;
	}

	public String getOthernickname() {
		return othernickname;
	}

	public void setOthernickname(String othernickname) {
		this.othernickname = othernickname;
	}

}
