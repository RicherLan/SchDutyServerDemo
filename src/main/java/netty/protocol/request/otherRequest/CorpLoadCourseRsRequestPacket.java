package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.CorpLoadCourseRs_REQUEST;

/*
    社团组织查看课表导入情况
 */
public class CorpLoadCourseRsRequestPacket extends Packet {
    int groupid;
    public CorpLoadCourseRsRequestPacket() {

	}
    public CorpLoadCourseRsRequestPacket(int groupid){
        this.groupid = groupid;
    }
    @Override
    public int getCommand() {

        return CorpLoadCourseRs_REQUEST;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
