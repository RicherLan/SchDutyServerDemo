package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import object.DongtaiPCTNum;

import static netty.protocol.command.Command.GetUNDtIDs_RESPONSE;

import java.util.Vector;

/*
    进入用户资料界面  用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id    ph2是要查询的人的账号
 */
public class GetUNDtIDsResponsePacket extends Packet {

	String ph;
	Vector<DongtaiPCTNum> dongtaiPCTNums;
	
    @Override
    public int getCommand() {

        return GetUNDtIDs_RESPONSE;
    }

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public Vector<DongtaiPCTNum> getDongtaiPCTNums() {
		return dongtaiPCTNums;
	}

	public void setDongtaiPCTNums(Vector<DongtaiPCTNum> dongtaiPCTNums) {
		this.dongtaiPCTNums = dongtaiPCTNums;
	}

}
