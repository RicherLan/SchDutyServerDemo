package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetPerIcon_REQUEST;

/*
获取自己的的头像
 */
public class GetPerIconRequestPacket extends Packet {
   
    String phonenumber;
    public GetPerIconRequestPacket() {

	}
    public GetPerIconRequestPacket(String phonenumber){
       
        this.phonenumber = phonenumber;
    }

    @Override
    public int getCommand() {

        return GetPerIcon_REQUEST;
    }

 

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
