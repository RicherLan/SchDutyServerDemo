package netty.protocol.request.otherRequest;

import netty.protocol.Packet;
import static netty.protocol.command.Command.REGISTERTEST_REQUEST;

/*
    用户注册  测试阶段
 */
public class RegisterTestRequestPacket extends Packet {

    String schoolname;
    String collegename;
    String majorname;
    int ruxueyear;
    String phonenumber;
    String password;
   
    @Override
    public int getCommand() {

        return REGISTERTEST_REQUEST;
    }

    public RegisterTestRequestPacket(){
    	
    }
    
    public RegisterTestRequestPacket(String schoolname, String collegename, String majorname, int ruxueyear, String phonenumber, String password){
        this.schoolname = schoolname;
        this.collegename = collegename;
        this.majorname = majorname;
        this.ruxueyear = ruxueyear;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getMajorname() {
        return majorname;
    }

    public void setMajorname(String majorname) {
        this.majorname = majorname;
    }

    public int getRuxueyear() {
        return ruxueyear;
    }

    public void setRuxueyear(int ruxueyear) {
        this.ruxueyear = ruxueyear;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
