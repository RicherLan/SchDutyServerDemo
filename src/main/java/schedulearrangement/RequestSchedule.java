package schedulearrangement;

/*
 *   某节课值班的要求
 */
public class RequestSchedule {

	private  int week;                //该节课属于第几周
	private int way;                  //星期几
	private int section;              //第几节课

	private int buzhangnum;            //该次值班要求部长的数量
	private int ganshinum;             //该次值班要求干事的数量

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
}
