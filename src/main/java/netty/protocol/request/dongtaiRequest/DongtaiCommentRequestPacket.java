package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.DongtaiComment_REQUEST;

/*
        给动态评论
 */
public class DongtaiCommentRequestPacket extends Packet {
    int dongtaiid;
    String msg;
    public DongtaiCommentRequestPacket() {

	}
    public DongtaiCommentRequestPacket(int dongtaiid,String msg){
        this.dongtaiid = dongtaiid;
        this.msg = msg;
    }

    @Override
    public int getCommand() {

        return DongtaiComment_REQUEST;
    }

    public int getDongtaiid() {
        return dongtaiid;
    }

    public void setDongtaiid(int dongtaiid) {
        this.dongtaiid = dongtaiid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
