package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetFriendIDOnline_REQUEST;

/*
 * 获得在线的好友有哪些    就是返回在线的账号就行
 */
public class GetFriendIDOnlineRequestPacket extends Packet {

	public GetFriendIDOnlineRequestPacket() {

	}
    @Override
    public int getCommand() {

        return GetFriendIDOnline_REQUEST;
    }
}
