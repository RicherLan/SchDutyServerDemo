package netty.protocol.request.corpRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AlterCorpPart_REQUEST;

/*
    修改社团组织的某一个部门的名字
 */
public class AlterCorpPartRequestPacket extends Packet {
    int groupid;
    String oldname;
    String newname;

    public AlterCorpPartRequestPacket() {
    	
    }
    
    public AlterCorpPartRequestPacket(int groupid,String oldname,String newname){
        this.groupid = groupid;
        this.oldname = oldname;
        this.newname = newname;
    }
    @Override
    public int getCommand() {

        return AlterCorpPart_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }
}
