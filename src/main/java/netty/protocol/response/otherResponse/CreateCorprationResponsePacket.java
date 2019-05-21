package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.CreateCorpration_RESPONSE;

/*
    创建社团
 */
public class CreateCorprationResponsePacket extends Packet {

	
	String result;
	int count;
	String password;
	int groupid;
	
	
    @Override
    public int getCommand() {

        return CreateCorpration_RESPONSE;
    }


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getGroupid() {
		return groupid;
	}


	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

}
