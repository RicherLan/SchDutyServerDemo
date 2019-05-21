package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.GetCourseSchOfUID_REQUEST;


/*
    获取课表
 */
public class GetCourseSchByUIDRequestPacket extends Packet {

    int xnm;
    int xqm;
    String count;
    String password;
    public GetCourseSchByUIDRequestPacket() {

	}
    public GetCourseSchByUIDRequestPacket (int xnm,int xqm,String count,String password){

        this.xnm = xnm;
        this.xqm = xqm;
        this.count = count;
        this.password = password;

    }
    @Override
    public int getCommand() {

        return GetCourseSchOfUID_REQUEST;
    }

    public int getXnm() {
        return xnm;
    }

    public void setXnm(int xnm) {
        this.xnm = xnm;
    }

    public int getXqm() {
        return xqm;
    }

    public void setXqm(int xqm) {
        this.xqm = xqm;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
