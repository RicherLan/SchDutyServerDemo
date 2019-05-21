package netty.protocol.request.corpRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AppointCorpPos_REQUEST;

/*
    社团组织任命职位
 */
public class AppointCorpPosRequestPacket extends Packet{
    int groupid;
    String ph;
    String postype;
    String oldph;
    public AppointCorpPosRequestPacket(){

    }

    public AppointCorpPosRequestPacket(int groupid,String ph,String postype,String oldph){
        this.groupid = groupid;
        this.ph=ph;
        this.postype = postype;
        this.oldph =oldph;
    }

    @Override
    public int getCommand() {
        return AppointCorpPos_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getPostype() {
        return postype;
    }

    public void setPostype(String postype) {
        this.postype = postype;
    }

    public String getOldph() {
        return oldph;
    }

    public void setOldph(String oldph) {
        this.oldph = oldph;
    }
}
