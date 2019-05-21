package netty.protocol.request.otherRequest;


import static netty.protocol.command.Command.SchduleArrangement_REQUEST;

import java.util.Vector;

import netty.protocol.Packet;
import schedulearrangement.RequestSchedule;


/*
        安排值班表
 */
public class SchduleArrangementRequestPacket extends Packet {

    int account;
    int year;
    int xq;
    int bz;
    int xz;
    Vector<RequestSchedule> requestSchedules;
    String type;
    public SchduleArrangementRequestPacket() {
    	
    }
    public SchduleArrangementRequestPacket(int account, int year, int xq, int bz, int xz, Vector<RequestSchedule> requestSchedules, String type){
        this.account = account;
        this.year = year;
        this.xq = xq;
        this.bz = bz;
        this.xz = xz;
        this.requestSchedules = requestSchedules;
        this.type = type;

    }
    @Override
    public int getCommand() {

        return SchduleArrangement_REQUEST;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getXq() {
        return xq;
    }

    public void setXq(int xq) {
        this.xq = xq;
    }

    public int getBz() {
        return bz;
    }

    public void setBz(int bz) {
        this.bz = bz;
    }

    public int getXz() {
        return xz;
    }

    public void setXz(int xz) {
        this.xz = xz;
    }

    public Vector<RequestSchedule> getRequestSchedules() {
        return requestSchedules;
    }

    public void setRequestSchedules(Vector<RequestSchedule> requestSchedules) {
        this.requestSchedules = requestSchedules;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
