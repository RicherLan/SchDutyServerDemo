package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UPPASSWORD_REQUEST;

public class UpPasswordRequestPacket extends Packet {

    String oldpassword;
    String newpassword;
    public UpPasswordRequestPacket() {

   	}
    public UpPasswordRequestPacket(String oldpassword,String newpassword){
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }

    @Override
    public int getCommand() {

        return UPPASSWORD_REQUEST;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
