package schedulearrangement;

import java.util.Vector;

/*
 * 传输给客户端的值班表
 */

public class ClientArrangement {

	private int year;                     //值班周第一天的年
	private int month;                  //值班周第一天的月
	private int daytime;                //值班周第一天的日
	private int way;                    //值班周第一天的周几

	public int groupid;
	public int week;                //该节课属于第几周
//	public int day;                  //星期几
	public int section;              //第几节课

	public Vector<String> phs = new Vector<String>();          //被安排值班的学生的账号
	public Vector<String> names = new Vector<String>();        //被安排值班的学生的名字
	public Vector<String> poss = new Vector<String>();         //被安排值班的学生的职位       部长或干事

	public int buzhangnum;            //该次值班要求部长的数量
	public int ganshinum;             //该次值班要求干事的数量


	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDaytime() {
		return daytime;
	}

	public void setDaytime(int daytime) {
		this.daytime = daytime;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}

	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
//	public int getDay() {
//		return day;
//	}
//	public void setDay(int day) {
//		this.day = day;
//	}
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
