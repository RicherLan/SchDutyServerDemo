package object;

/*
 * 根评论下的所有评论
 */
public class DTComRootCh {

	private int rootcomid;     //不需要此主人的信息  因为DTComRoot中已经保存
	private int comid;
	private int becomid;
	
	private String comuid;
	private String comuname;
	private byte[] comuic;
	
	private String becomuid;
	private String becomuname;
	private byte[] becomuic;
	
	private String msg;
	private long time;
	private int praisenum = 0;
	
	
	public int getPraisenum() {
		return praisenum;
	}
	public void setPraisenum(int praisenum) {
		this.praisenum = praisenum;
	}
	public int getRootcomid() {
		return rootcomid;
	}
	public void setRootcomid(int rootcomid) {
		this.rootcomid = rootcomid;
	}
	public int getComid() {
		return comid;
	}
	public void setComid(int comid) {
		this.comid = comid;
	}
	public int getBecomid() {
		return becomid;
	}
	public void setBecomid(int becomid) {
		this.becomid = becomid;
	}
	public String getComuid() {
		return comuid;
	}
	public void setComuid(String comuid) {
		this.comuid = comuid;
	}
	public String getComuname() {
		return comuname;
	}
	public void setComuname(String comuname) {
		this.comuname = comuname;
	}
	public byte[] getComuic() {
		return comuic;
	}
	public void setComuic(byte[] comuic) {
		this.comuic = comuic;
	}
	public String getBecomuid() {
		return becomuid;
	}
	public void setBecomuid(String becomuid) {
		this.becomuid = becomuid;
	}
	public String getBecomuname() {
		return becomuname;
	}
	public void setBecomuname(String becomuname) {
		this.becomuname = becomuname;
	}
	public byte[] getBecomuic() {
		return becomuic;
	}
	public void setBecomuic(byte[] becomuic) {
		this.becomuic = becomuic;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
	
	
}
