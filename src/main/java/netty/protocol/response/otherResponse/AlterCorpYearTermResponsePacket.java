package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AlterCorpYearTerm_RESPONSE;

/*
    社团组织群管理修改学年学期
 */
public class AlterCorpYearTermResponsePacket extends Packet {

	
	String result;
	int groupid;
	int year;
	int xueqi;
	int zhou;
	
    @Override
    public int getCommand() {

        return AlterCorpYearTerm_RESPONSE;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getXueqi() {
		return xueqi;
	}

	public void setXueqi(int xueqi) {
		this.xueqi = xueqi;
	}

	public int getZhou() {
		return zhou;
	}

	public void setZhou(int zhou) {
		this.zhou = zhou;
	}

}
