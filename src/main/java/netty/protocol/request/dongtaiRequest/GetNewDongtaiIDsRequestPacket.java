package  netty.protocol.request.dongtaiRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetNewDongtaiIDs_REQUEST;

/*
     用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
 */
public class GetNewDongtaiIDsRequestPacket extends Packet {


	public GetNewDongtaiIDsRequestPacket() {

   	}
    @Override
    public int getCommand() {

        return GetNewDongtaiIDs_REQUEST;
    }

}
