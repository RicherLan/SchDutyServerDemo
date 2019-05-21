package schedulearrangement;

import java.util.Vector;

/*
 *   某一节课的值班
 */
public class Arrangement {
	
	private int corpaccount;     //社团或组织的账号
	private int groupid;
	
	private int week;                //该节课属于第几周
	private int way;                  //星期几
	private int section;              //第几节课
	
	private int buzhangnum;            //该次值班要求部长的数量
	private int ganshinum;             //该次值班要求干事的数量
	
	private Vector<String> phs = new Vector<String>();          //被安排值班的学生的账号
	private Vector<String> names = new Vector<String>();        //被安排值班的学生的名字
	private Vector<String> poss = new Vector<String>();         //被安排值班的学生的职位       部长或干事
	
	boolean state = false;                                 //是否已经完成  
	
	private int buzhangOKnum = 0;                          //在该节有空的部长的数量
	private int ganshiOknum = 0;                           //在该节有空的干事的数量
	
	private Vector<String> buzhangOKId= new Vector<String>();   //在该节有空的部长在部长Vector中的下标
	private Vector<String> ganshiOKId = new Vector<String>();    //在该节有空的干事在部长Vector中的下标
	 
	
	
	
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getCorpaccount() {
		return corpaccount;
	}
	public void setCorpaccount(int corpaccount) {
		this.corpaccount = corpaccount;
	}
	public Vector<String> getBuzhangOKId() {
		return buzhangOKId;
	}
	public void setBuzhangOKId(Vector<String> buzhangOKIndex) {
		this.buzhangOKId = buzhangOKIndex;
	}
	public Vector<String> getGanshiOKId() {
		return ganshiOKId;
	}
	public void setGanshiOKId(Vector<String> ganshiOKIndex) {
		this.ganshiOKId = ganshiOKIndex;
	}
	
	public int getBuzhangOKnum() {
		return buzhangOKnum;
	}
	public void setBuzhangOKnum(int buzhangOKnum) {
		this.buzhangOKnum = buzhangOKnum;
	}
	public int getGanshiOknum() {
		return ganshiOknum;
	}
	public void setGanshiOknum(int ganshiOknum) {
		this.ganshiOknum = ganshiOknum;
	}
	
	public int getBuzhangnum() {
		return buzhangnum;
	}
	public void setBuzhangnum(int buzhangnum) {
		this.buzhangnum = buzhangnum;
	}
	public int getGanshinum() {
		return ganshinum;
	}
	public void setGanshinum(int ganshinum) {
		this.ganshinum = ganshinum;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	
	public int getWay() {
		return way;
	}
	public void setWay(int way) {
		this.way = way;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public Vector<String> getPhs() {
		return phs;
	}
	public void setPhs(Vector<String> phs) {
		this.phs = phs;
	}
	public Vector<String> getNames() {
		return names;
	}
	public void setNames(Vector<String> names) {
		this.names = names;
	}
	public Vector<String> getPoss() {
		return poss;
	}
	public void setPoss(Vector<String> poss) {
		this.poss = poss;
	}
	
	
	
	
}
