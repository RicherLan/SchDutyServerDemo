package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import object.DongtaiPCTNum;

import static netty.protocol.command.Command.GetNewDongtaiIDs_RESPONSE;

import java.util.Vector;

/*
        用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
 */
public class GetNewDongtaiIDsResponsePacket extends Packet {

	Vector<DongtaiPCTNum> dongtaiPCTNums;
    @Override
    public int getCommand() {

        return GetNewDongtaiIDs_RESPONSE;
    }
	public Vector<DongtaiPCTNum> getDongtaiPCTNums() {
		return dongtaiPCTNums;
	}
	public void setDongtaiPCTNums(Vector<DongtaiPCTNum> dongtaiPCTNums) {
		this.dongtaiPCTNums = dongtaiPCTNums;
	}
    
}
