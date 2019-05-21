package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddFriendResult_RESPONSE;

/*
添加好友时   对方同意或拒绝了你的请求   你已经读到了对方同意还是拒绝   给服务器回执
服务器的响应包
 */
public class AddFriendResultResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return AddFriendResult_RESPONSE;
    }

}
