package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import util.User;

import static netty.protocol.command.Command.GetFriendInfoByID_RESPONSE;

import db.UserDBService;
/*
 * 	获得某用户的user表中的基本信息
 */
public class GetFriendInfoByIDResponsePacket extends Packet {

	User user;     //用户信息
	byte[] bs;   //头像
    @Override
    public int getCommand() {

        return GetFriendInfoByID_RESPONSE;
    }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public byte[] getBs() {
		return bs;
	}
	public void setBs(byte[] bs) {
		this.bs = bs;
	}

}
