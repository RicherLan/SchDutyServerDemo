package netty.protocol.response.otherResponse;
import netty.protocol.Packet;

import static netty.protocol.command.Command.UpGroupRemarkt_RESPONSE;

/*
修改自己的群名片
 */
public class UpGroupRemarkResponsePacket extends Packet {

	String result;
	int groupid;
	String remark;
	
    @Override
    public int getCommand() {

        return UpGroupRemarkt_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
