package netty.protocol.request.edu;

import netty.protocol.Packet;
import static netty.protocol.command.Command.SearchEmptyClassroom_REQUEST;

import java.util.Vector;

/*
 * 	查詢空教室
 */
public class SearchEmptyClassroomRequestPacket extends Packet{

	int xnm ;         //学年
	int xqm;          //学习
	String lh;		//建筑代号
	int zcd ;		//第几周
	int xqj;			//星期几
	Vector<Integer> jieshu;        //第几节课
	
	public SearchEmptyClassroomRequestPacket() {
		
	}
	public SearchEmptyClassroomRequestPacket(int xnm ,int xqm, String lh,int zcd ,int xqj,Vector<Integer> jieshu){
		this.xnm = xnm;
		this.xqm = xqm;
		this.lh = lh;
		this.zcd = zcd;
		this.xqj = xqj;
		this.jieshu = jieshu;
		
	}
	@Override
	public int getCommand() {
		
		return SearchEmptyClassroom_REQUEST;
	}
	public int getXnm() {
		return xnm;
	}
	public void setXnm(int xnm) {
		this.xnm = xnm;
	}
	public int getXqm() {
		return xqm;
	}
	public void setXqm(int xqm) {
		this.xqm = xqm;
	}
	public String getLh() {
		return lh;
	}
	public void setLh(String lh) {
		this.lh = lh;
	}
	public int getZcd() {
		return zcd;
	}
	public void setZcd(int zcd) {
		this.zcd = zcd;
	}
	public int getXqj() {
		return xqj;
	}
	public void setXqj(int xqj) {
		this.xqj = xqj;
	}
	public Vector<Integer> getJieshu() {
		return jieshu;
	}
	public void setJieshu(Vector<Integer> jieshu) {
		this.jieshu = jieshu;
	}

}
