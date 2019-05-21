package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddDongTaiImage_RESPONSE;

/*
        用户发表动态时   图片分开传送
 */
public class AddDongTaiImageResponsePacket extends Packet {

	String result;
	int id;
	
    @Override
    public int getCommand() {

        return AddDongTaiImage_RESPONSE;
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
