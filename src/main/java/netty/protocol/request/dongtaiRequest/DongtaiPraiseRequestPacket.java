package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.DongtaiPraise_REQUEST;

/*
        用户给某一条动态点赞
 */
public class DongtaiPraiseRequestPacket extends Packet {

    int dongtaiid;
    public DongtaiPraiseRequestPacket() {

	}
    public DongtaiPraiseRequestPacket(int dongtaiid){
        this.dongtaiid = dongtaiid;
    }
    @Override
    public int getCommand() {

        return DongtaiPraise_REQUEST;
    }

    public int getDongtaiid() {
        return dongtaiid;
    }

    public void setDongtaiid(int dongtaiid) {
        this.dongtaiid = dongtaiid;
    }
}
