package netty.protocol.response.corpResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AlterCorpPart_RESPONSE;

/*
    修改社团组织的某一个部门的名字
 */
public class AlterCorpPartResponsePacket extends Packet{

	String result;
	int grouid;
	String oldname;
	String newname;
	
    @Override
    public int getCommand() {

        return AlterCorpPart_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getGrouid() {
		return grouid;
	}

	public void setGrouid(int grouid) {
		this.grouid = grouid;
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
