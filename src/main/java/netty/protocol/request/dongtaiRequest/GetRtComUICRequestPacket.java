package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetRtComUIC_REQUEST;

/*
    进入某动态的所有评论界面    刚开始要刷新   返回根评论总共10条   大的评论下最多回执3条  获得头像
 */
public class GetRtComUICRequestPacket extends Packet {
    String userid;
    public GetRtComUICRequestPacket() {

   	}
    public GetRtComUICRequestPacket(String userid){
        this.userid = userid;
    }
    @Override
    public int getCommand() {

        return GetRtComUIC_REQUEST;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
