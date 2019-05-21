package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddDongtai_RESPONSE;

/*
        发表动态    普通用户的话  类型用user表示
 */
public class AddDongtaiResponsePacket extends Packet {
	String result;
	int id;
    @Override
    public int getCommand() {

        return AddDongtai_RESPONSE;
    }
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
