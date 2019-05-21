package netty.protocol.response.corpResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AppointCorpPos_RESPONSE;

/*
    社团组织任命职位
 */
public class AppointCorpPosResponsePacket extends Packet{
    int groupid;
    String ph;
    String oldph;
    String rs;
    String postype;

    @Override
    public int getCommand() {
        return AppointCorpPos_RESPONSE;
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

	public String getOldph() {
		return oldph;
	}

	public void setOldph(String oldph) {
		this.oldph = oldph;
	}

	public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

	public String getPostype() {
		return postype;
	}

	public void setPostype(String postype) {
		this.postype = postype;
	}
    
}
