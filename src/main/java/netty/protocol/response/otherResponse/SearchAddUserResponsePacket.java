package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SearchAddUser_RESPONSE;
/*
客户端添加某人好友时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddUserResponsePacket extends Packet {


	String result;
	String ph;
	String nickname;
	
    @Override
    public int getCommand() {

        return SearchAddUser_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
