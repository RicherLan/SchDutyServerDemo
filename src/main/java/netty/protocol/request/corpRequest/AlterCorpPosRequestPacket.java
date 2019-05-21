package netty.protocol.request.corpRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AlterCorpPos_REQUEST;

/*
    修改自己在社团的职位
 */
public class AlterCorpPosRequestPacket extends Packet {
    int groupid;
    String newname;
    public AlterCorpPosRequestPacket() {
    	
    }
    public AlterCorpPosRequestPacket(int groupid,String newname){
        this.groupid = groupid;
        this.newname = newname;
    }
    @Override
    public int getCommand() {

        return AlterCorpPos_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }
}
