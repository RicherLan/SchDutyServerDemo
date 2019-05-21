package object;

/*
 * 用户  理论课程
 */
public class Course {


	private String CN;            //课程名字
	private String CC;			//上课所在校区
	private String CP;				//上课具体地点
	private String CT1;				//上课时间 ，比如  2-14周(双)
	private String CT2;				//这个课是周几
	private String CT3;				//上课时间，比如 1-2节
	private String CTN;		//教师名字

	public String getCN() {
		return CN;
	}

	public void setCN(String CN) {
		this.CN = CN;
	}

	public String getCC() {
		return CC;
	}

	public void setCC(String CC) {
		this.CC = CC;
	}

	public String getCP() {
		return CP;
	}

	public void setCP(String CP) {
		this.CP = CP;
	}

	public String getCT1() {
		return CT1;
	}

	public void setCT1(String CT1) {
		this.CT1 = CT1;
	}

	public String getCT2() {
		return CT2;
	}

	public void setCT2(String CT2) {
		this.CT2 = CT2;
	}

	public String getCT3() {
		return CT3;
	}

	public void setCT3(String CT3) {
		this.CT3 = CT3;
	}

	public String getCTN() {
		return CTN;
	}

	public void setCTN(String CTN) {
		this.CTN = CTN;
	}
}
