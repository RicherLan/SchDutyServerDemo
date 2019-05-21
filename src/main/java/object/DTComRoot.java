package object;

import java.util.Vector;

/*
 * 动态的评论类1  首页的评论
 */
public class DTComRoot {

	private int dtid;
	private int rootcomid;
	
	private String rootuid;
	private String rootuname;
	private byte[] rootuic;
	
	private String msg;
	private long time;
	private int rootcomnum;
	private int praisenum = 0;
	
	private Vector<DTComCh> dtComChs = new Vector<DTComCh>();

	
	public int getPraisenum() {
		return praisenum;
	}

	public void setPraisenum(int praisenum) {
		this.praisenum = praisenum;
	}

	public int getDtid() {
		return dtid;
	}

	public void setDtid(int dtid) {
		this.dtid = dtid;
	}

	public int getRootcomid() {
		return rootcomid;
	}

	public void setRootcomid(int rootcomid) {
		this.rootcomid = rootcomid;
	}

	public String getRootuid() {
		return rootuid;
	}

	public void setRootuid(String rootuid) {
		this.rootuid = rootuid;
	}

	public String getRootuname() {
		return rootuname;
	}

	public void setRootuname(String rootuname) {
		this.rootuname = rootuname;
	}

	public byte[] getRootuic() {
		return rootuic;
	}

	public void setRootuic(byte[] rootuic) {
		this.rootuic = rootuic;
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

	public int getRootcomnum() {
		return rootcomnum;
	}

	public void setRootcomnum(int rootcomnum) {
		this.rootcomnum = rootcomnum;
	}

	public Vector<DTComCh> getDtComChs() {
		return dtComChs;
	}

	public void setDtComChs(Vector<DTComCh> dtComChs) {
		this.dtComChs = dtComChs;
	}

	
}
