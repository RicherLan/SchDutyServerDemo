package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import schedulearrangement.ClientArrangement;

import static netty.protocol.command.Command.GetSDOfGid_RESPONSE;

import java.util.Vector;

/*
    获得自己加入的某个组织的值班表
 */
public class GetSDOfGidResponsePacket extends Packet {
	
	int dnid;
	int groupid;
	Vector<ClientArrangement> clientArrangements;
	
    @Override
    public int getCommand() {

        return GetSDOfGid_RESPONSE;
    }

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public Vector<ClientArrangement> getClientArrangements() {
		return clientArrangements;
	}

	public void setClientArrangements(Vector<ClientArrangement> clientArrangements) {
		this.clientArrangements = clientArrangements;
	}

	public int getDnid() {
		return dnid;
	}

	public void setDnid(int dnid) {
		this.dnid = dnid;
	}

	

}
