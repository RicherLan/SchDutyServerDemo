package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetUNDtIDs_REQUEST;

/*
    进入用户资料界面  用户下拉刷新动态的页面  就是加载新的动态     返回6条动态的id
 */
public class GetUNDtIDsRequestPacket extends Packet {

    String ph;
    public GetUNDtIDsRequestPacket() {

   	}
    public GetUNDtIDsRequestPacket(String ph){
        this.ph = ph;
    }
    @Override
    public int getCommand() {

        return GetUNDtIDs_REQUEST;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }
}
