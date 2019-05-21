package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GroupAdmiReciveExitGroup_RESPONSE;

/*
群成员退群时   群管理员收到退群消息时   回执         服务器回执
 */
public class GroupAdmiReciveExitGroupResponsePacket extends Packet {

    @Override
    public int getCommand() {

        return GroupAdmiReciveExitGroup_RESPONSE;
    }

}
