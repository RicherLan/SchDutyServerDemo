package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.ReciveDongtaiComment_REQUEST;

/*
    收到了别人给自己的动态的评论  回执服务器已读
 */
public class ReciveDongtaiCommentRequestPacket extends Packet {
    int id;
    public ReciveDongtaiCommentRequestPacket() {

   	}
    public  ReciveDongtaiCommentRequestPacket(int id){
        this.id = id;
    }
    @Override
    public int getCommand() {

        return ReciveDongtaiComment_REQUEST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
