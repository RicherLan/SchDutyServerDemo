package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.UpPeronalInfo_REQUEST;

/*
修改个人资料
 */
public class UpPeronalInfoRequestPacket extends Packet {

    String nickname;
    String sex;
    String from;
    String add;
    String sch;
    String dep;
    String maj;
    String bir;
    int rxy;
    String info;
    public UpPeronalInfoRequestPacket() {

   	}
    public UpPeronalInfoRequestPacket(String nickname,String sex,String from,String add,String sch,String dep,String maj,String bir,int rxy,String info){
        this.nickname = nickname;
        this.sex = sex;
        this.from = from;
        this.add = add;
        this.sch = sch;
        this.dep = dep;
        this.maj = maj;
        this.bir = bir;
        this.rxy = rxy;
        this.info = info;
    }

    @Override
    public int getCommand() {

        return UpPeronalInfo_REQUEST;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getSch() {
        return sch;
    }

    public void setSch(String sch) {
        this.sch = sch;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getMaj() {
        return maj;
    }

    public void setMaj(String maj) {
        this.maj = maj;
    }

    public String getBir() {
        return bir;
    }

    public void setBir(String bir) {
        this.bir = bir;
    }

    public int getRxy() {
        return rxy;
    }

    public void setRxy(int rxy) {
        this.rxy = rxy;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
