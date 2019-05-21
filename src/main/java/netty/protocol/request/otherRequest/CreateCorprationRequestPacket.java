package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;
import static  netty.protocol.command.Command.CreateCorpration_REQUEST;

/*
创建社团
 */
public class CreateCorprationRequestPacket extends Packet {

    String name;
    String type;
    String info;
    String corppart;
    int year;
    int xueqi;
    public CreateCorprationRequestPacket() {

   	}
    public CreateCorprationRequestPacket(String name,String type,String info ,String corppart,int year,int xueqi){
        this.name = name;
        this.type = type;
        this.info = info;
        this.corppart = corppart;
        this.year = year;
        this.xueqi = xueqi;
    }

    @Override
    public int getCommand() {

        return CreateCorpration_REQUEST;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCorppart() {
        return corppart;
    }

    public void setCorppart(String corppart) {
        this.corppart = corppart;
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
}
