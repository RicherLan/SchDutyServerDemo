package object;

import java.util.Vector;

public class Dongtai {
	private int id=-1;              //动态的id
	private String type;            //用户、官方、广告     user,auth,ad
	private String sdid;
	private String sdic;
	private String sdname;
	private String sdschname;
	
	private String content;
	private long time;
	private int imnum;            //照片数量
	private int transnum=0;
	private int pranum=0;         //点赞数
	private int comnum=0;        //评论数
	
	private Vector<String> imph = new Vector<String>();   //客户端保存动态图片的路径

	private boolean isPra = false;   //自己是否点赞过

	public int getTransnum() {
		return transnum;
	}

	public void setTransnum(int transnum) {
		this.transnum = transnum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSdid() {
		return sdid;
	}

	public void setSdid(String sdid) {
		this.sdid = sdid;
	}

	public String getSdic() {
		return sdic;
	}

	public void setSdic(String sdic) {
		this.sdic = sdic;
	}

	public String getSdname() {
		return sdname;
	}

	public void setSdname(String sdname) {
		this.sdname = sdname;
	}

	public String getSdschname() {
		return sdschname;
	}

	public void setSdschname(String sdschname) {
		this.sdschname = sdschname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getImnum() {
		return imnum;
	}

	public void setImnum(int imnum) {
		this.imnum = imnum;
	}

	public int getPranum() {
		return pranum;
	}

	public void setPranum(int pranum) {
		this.pranum = pranum;
	}

	public int getComnum() {
		return comnum;
	}

	public void setComnum(int comnum) {
		this.comnum = comnum;
	}

	public Vector<String> getImph() {
		return imph;
	}

	public void setImph(Vector<String> imph) {
		this.imph = imph;
	}

	public boolean isPra() {
		return isPra;
	}

	public void setPra(boolean pra) {
		isPra = pra;
	}
}
