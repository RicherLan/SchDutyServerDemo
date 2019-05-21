package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.AlterCorpYearTerm_REQUEST;

/*
    社团组织群管理修改学年学期
 */
public class AlterCorpYearTermRequestPacket extends Packet {
    int groupid;
    int year;
    int xueqi;
    int zhou;
    public AlterCorpYearTermRequestPacket() {

	}
    public AlterCorpYearTermRequestPacket(int groupid,int year,int xueqi,int zhou){
        this.groupid = groupid;
        this.year = year;
        this.xueqi = xueqi;
        this.zhou = zhou;

    }
    @Override
    public int getCommand() {

        return AlterCorpYearTerm_REQUEST;
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
