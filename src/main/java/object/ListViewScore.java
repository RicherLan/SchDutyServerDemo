package object;

/*
 * 客户端  对成绩只要求   课程名字  课程分数   课程学分
 *
 */
public class ListViewScore {

	 public int year;
	 public int grade;
	 public String courseName;
	 public double courseScore;
	 public double courseCredit;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public double getCourseScore() {
		return courseScore;
	}
	public void setCourseScore(double courseScore) {
		this.courseScore = courseScore;
	}
	public double getCourseCredit() {
		return courseCredit;
	}
	public void setCourseCredit(double courseCredit) {
		this.courseCredit = courseCredit;
	}
	 
	 
	 

}
