package object;

/*
用户再社团组织里的简单信息
 */
public class UserCorp {

    private String ph;
    private int groupid;
    private String groupname;
    private String sex;
    private String part;
    private String corppos;

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getCorppos() {
        return corppos;
    }

    public void setCorppos(String corppos) {
        this.corppos = corppos;
    }
}
