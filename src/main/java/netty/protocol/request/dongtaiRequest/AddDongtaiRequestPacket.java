package netty.protocol.request.dongtaiRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.AddDongtai_REQUEST;

/*
        发表动态    普通用户的话  类型用user表示
 */
public class AddDongtaiRequestPacket extends Packet {
    String usertype;
    String text;
    int picturenum;
    long time;
    public AddDongtaiRequestPacket() {

	}
    public AddDongtaiRequestPacket(String usertype, String text, int picturenum, long time){
        this.usertype = usertype;
        this.text = text;
        this.picturenum = picturenum;
        this.time = time;

    }
    @Override
    public int getCommand() {

        return AddDongtai_REQUEST;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPicturenum() {
        return picturenum;
    }

    public void setPicturenum(int picturenum) {
        this.picturenum = picturenum;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
