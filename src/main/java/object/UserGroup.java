package object;

/*
 * 用户参与的  群信息
 */
public class UserGroup {


	public int groupid;
	public String groupname;             //群名称
	public String groupicon;          //群头像
	public String groupintro;         //群介绍
	public long createtime;           //建群时间
	public String creatorid;            //群主id
	public int authid;                 //官方账号
	public String grouptype;          //群类型    社团群还是普通群
	public int usernum;             //群人数
	
	public long jointime;                   //加群时间
	public String userauthority;             //群内权限
	public String part="";                    //部室
	public String corppos;                    //职称
	public String groupnickname;               //群内名片

	public String[] corppart;               //社团组织下的所有部门
	
	public int year;                   //社团组织课表的时间
	public int xueqi;
	public int zhou;

	
	
	public int getAuthid() {
		return authid;
	}

	public void setAuthid(int authid) {
		this.authid = authid;
	}

	public int getZhou() {
		return zhou;
	}

	public void setZhou(int zhou) {
		this.zhou = zhou;
	}

	public int getUsernum() {
		return usernum;
	}

	public void setUsernum(int usernum) {
		this.usernum = usernum;
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

	public String[] getCorppart() {
		return corppart;
	}

	public void setCorppart(String[] corppart) {
		this.corppart = corppart;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupicon() {
		return groupicon;
	}
	public void setGroupicon(String groupicon) {
		this.groupicon = groupicon;
	}
	public String getGroupintro() {
		return groupintro;
	}
	public void setGroupintro(String groupintro) {
		this.groupintro = groupintro;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public String getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}
	public String getGrouptype() {
		return grouptype;
	}
	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}
	public long getJointime() {
		return jointime;
	}
	public void setJointime(long jointime) {
		this.jointime = jointime;
	}
	public String getUserauthority() {
		return userauthority;
	}
	public void setUserauthority(String userauthority) {
		this.userauthority = userauthority;
	}
	public String getCorppos() {
		return corppos;
	}
	public void setCorppos(String corppos) {
		this.corppos = corppos;
	}
	public String getGroupnickname() {
		return groupnickname;
	}
	public void setGroupnickname(String groupnickname) {
		this.groupnickname = groupnickname;
	}
	
	
	
}
