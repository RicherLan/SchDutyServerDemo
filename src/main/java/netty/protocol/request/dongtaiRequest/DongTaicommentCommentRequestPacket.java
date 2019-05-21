package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.DongTaicommentCommnet_REQUEST;


/*
        给动态的评论评论
 */
public class DongTaicommentCommentRequestPacket extends Packet {
    int dongtaiid;
    int commentid;
    String msg;
    public DongTaicommentCommentRequestPacket() {

	}
    public DongTaicommentCommentRequestPacket(int dongtaiid,int commentid,String msg){
        this.dongtaiid =dongtaiid;
        this.commentid =commentid;
        this.msg =msg;

    }
    @Override
    public int getCommand() {

        return DongTaicommentCommnet_REQUEST;
    }

    public int getDongtaiid() {
        return dongtaiid;
    }

    public void setDongtaiid(int dongtaiid) {
        this.dongtaiid = dongtaiid;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
