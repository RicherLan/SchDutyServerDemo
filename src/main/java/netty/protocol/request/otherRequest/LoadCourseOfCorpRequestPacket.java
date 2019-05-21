package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.LoadCourseOfCorp_REQUEST;


/*
    社团组织成员导入自己的课表
 */
public class LoadCourseOfCorpRequestPacket extends Packet {
    int year;
    int xueqi;
    String count;
    String pwd;
    public LoadCourseOfCorpRequestPacket() {

	}
    public LoadCourseOfCorpRequestPacket(int year,int xueqi,String count,String pwd){
        this.year = year;
        this.xueqi = xueqi;
        this.count = count;
        this.pwd = pwd;

    }
    @Override
    public int getCommand() {

        return LoadCourseOfCorp_REQUEST;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
