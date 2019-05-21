package netty.protocol.request.corpRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.DeleteCorpPart_REQUEST;

/*
    删除社团组织的某一个部门
 */
public class DeleteCorpPartRequestPacket extends Packet {
    int groupid;
    String name;
    public DeleteCorpPartRequestPacket() {
    	
    }
    public DeleteCorpPartRequestPacket(int groupid,String name){
        this.groupid = groupid;
        this.name = name;
    }
    @Override
    public int getCommand() {

        return DeleteCorpPart_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
