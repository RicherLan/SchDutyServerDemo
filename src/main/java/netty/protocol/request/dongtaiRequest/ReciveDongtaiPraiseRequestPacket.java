package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.ReciveDongtaiPraise_REQUEST;


/*
        收到了别人给自己的动态的点赞  回执服务器已读
 */
public class ReciveDongtaiPraiseRequestPacket extends Packet {
    int id;
    public ReciveDongtaiPraiseRequestPacket() {

   	}
    public ReciveDongtaiPraiseRequestPacket(int id){
        this.id = id;
    }
    @Override
    public int getCommand() {

        return ReciveDongtaiPraise_REQUEST;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
