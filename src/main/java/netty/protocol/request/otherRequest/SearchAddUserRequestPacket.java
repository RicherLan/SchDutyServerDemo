package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SearchAddUser_REQUEST;

/*
客户端添加某人好友时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddUserRequestPacket extends Packet {
    String phonenumber;
    public SearchAddUserRequestPacket() {

	}
    public SearchAddUserRequestPacket(String phonenumber){
        this.phonenumber = phonenumber;
    }
    @Override
    public int getCommand() {

        return SearchAddUser_REQUEST;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
