package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetUDtByDTID_REQUEST;

/*
    进入用户资料界面  请求某一条动态
 */
public class GetUDtByDTIDRequestPacket extends Packet {
    String ph;
    int id;
    public GetUDtByDTIDRequestPacket() {

   	}
    public GetUDtByDTIDRequestPacket(String ph,int id){
        this.ph = ph;
        this.id = id;
    }
    @Override
    public int getCommand() {

        return GetUDtByDTID_REQUEST;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
