package netty.protocol.request.corpRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddCorApPart_REQUEST;

/*
    添加社团组织的某一个部门
 */
public class AddCorApPartRequestPacket extends Packet {
    int groupid;
    String name;
    
    public AddCorApPartRequestPacket() {
    	
    }
    
    public AddCorApPartRequestPacket(int groupid,String name){
        this.groupid = groupid;
        this.name = name;
    }
    @Override
    public int getCommand() {

        return AddCorApPart_REQUEST;
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
