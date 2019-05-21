package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import object.DongtaiPCTNum;

import static netty.protocol.command.Command.GetOldDongtaiIDs_RESPONSE;

import java.util.Vector;

/*
    某用户主页 用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
public class GetOldDongtaiIDsResponsePacket extends Packet {
	Vector<DongtaiPCTNum> dongtaiPCTNums;
    @Override
    public int getCommand() {

        return GetOldDongtaiIDs_RESPONSE;
    }
	public Vector<DongtaiPCTNum> getDongtaiPCTNums() {
		return dongtaiPCTNums;
	}
	public void setDongtaiPCTNums(Vector<DongtaiPCTNum> dongtaiPCTNums) {
		this.dongtaiPCTNums = dongtaiPCTNums;
	}

}
