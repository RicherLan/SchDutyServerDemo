package netty.protocol.request.dongtaiRequest;

import static netty.protocol.command.Command.AddDongTaiImage_REQUEST;
import netty.protocol.Packet;

/*
        用户发表动态时   图片分开传送
 */
public class AddDongTaiImageRequestPacket extends Packet {
	int dongtaiid;
	long time;
	byte[] bytes;

	public AddDongTaiImageRequestPacket() {

	}

	public AddDongTaiImageRequestPacket(int dongtaiid, long time, byte[] bytes) {
		this.dongtaiid = dongtaiid;
		this.time = time;
		this.bytes = bytes;

	}

	@Override
	public int getCommand() {

		return AddDongTaiImage_REQUEST;
	}

	public int getDongtaiid() {
		return dongtaiid;
	}

	public void setDongtaiid(int dongtaiid) {
		this.dongtaiid = dongtaiid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
