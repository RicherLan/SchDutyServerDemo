package object;

/*
 * 社团组织中没有导入课表的成员的信息
 */

public class CorpUserNotLoadCourse {

	private String ph;
	private String groupnickname;
	private String part;           //所在部室
	private String pos;            //所在部室  自己的职位
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getGroupnickname() {
		return groupnickname;
	}
	public void setGroupnickname(String groupnickname) {
		this.groupnickname = groupnickname;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	
	
}
