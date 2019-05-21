package object;

/*
 * 群基本信息
 */

public class GroupBasicInfo {

	private int groupid;             //群号
	private String groupname;             //群名称
	private String groupicon;          //群头像
	private String groupintro;         //群介绍
	private long createtime;           //建群时间
	private String creatorid;            //群主id
	private String grouptype;          //群类型    社团群还是普通群
	private int usernum;               //群人数
	
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
	public int getUsernum() {
		return usernum;
	}
	public void setUsernum(int usernum) {
		this.usernum = usernum;
	}
	
	
	
	
}
