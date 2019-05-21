package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.ReadDutyNoti_REQUEST;

/*
    已经读取值班的通知   向服务器反馈
 */
public class ReadDutyNotiRequestPacket extends Packet {
    int dnid;
    int gid;
    public ReadDutyNotiRequestPacket() {

   	}
    public ReadDutyNotiRequestPacket(int dnid,int gid){
        this.dnid = dnid;
        this.gid = gid;
    }
    @Override
    public int getCommand() {

        return ReadDutyNoti_REQUEST;
    }

    public int getDnid() {
        return dnid;
    }

    public void setDnid(int dnid) {
        this.dnid = dnid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
