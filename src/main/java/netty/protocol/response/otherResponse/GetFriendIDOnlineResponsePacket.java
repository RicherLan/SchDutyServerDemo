package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetFriendIDOnline_RESPONSE;

import java.util.Vector;

/*
 * 获得在线的好友有哪些    就是返回在线的账号就行
 */
public class GetFriendIDOnlineResponsePacket extends Packet {
	Vector<String> phonenumbers;
    @Override
    public int getCommand() {

        return GetFriendIDOnline_RESPONSE;
    }
	public Vector<String> getPhonenumbers() {
		return phonenumbers;
	}
	public void setPhonenumbers(Vector<String> phonenumbers) {
		this.phonenumbers = phonenumbers;
	}

}
