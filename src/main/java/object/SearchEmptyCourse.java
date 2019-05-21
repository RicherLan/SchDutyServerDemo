package object;

/*
 *  查询空课
 */

public class SearchEmptyCourse {
    private int week;            //第几周
    private int way;             //星期几
    private int section;          //第几节课

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
}
