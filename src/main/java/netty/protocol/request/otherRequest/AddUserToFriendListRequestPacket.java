package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.AddUserToFriendList_REQUEST;
/*
    客户端添加好友成功后  将好友加入到自己的好友列表  因此需要好友的资料
 */
public class AddUserToFriendListRequestPacket extends Packet {
    String phonenumber;
    public AddUserToFriendListRequestPacket() {

	}
    public AddUserToFriendListRequestPacket(String phonenumber){
        this.phonenumber = phonenumber;
    }
    @Override
    public int getCommand() {

        return AddUserToFriendList_REQUEST;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
