package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.print.DocFlavor.STRING;

import com.mchange.v2.async.StrandedTaskReporting;

import db.DBService;
import htmlnet.HtmlPrase;
import htmlnet.javatest;
import net.sf.json.JSONArray;
import object.Course;
import schedulearrangement.UserKebiao;
import util.MyTools;

public class TestSaveCourseData {

	public static Map<String, Vector<TestStuInfo>> map = new HashMap<String, Vector<TestStuInfo>>();
	
	public static void main(String[] args) {
			
//		saveEduCourse();
		
//		MoniDBService.saveToUsers();
//		MoniDBService.saveToCourse();
//		getKongke();
		
	}
	
	public static void getKongke() {
		Vector<TestStudentCourse> bangongshi = MoniDBService.getStuCouByCorp("办公室");
		Vector<TestStudentCourse> fuwudui= MoniDBService.getStuCouByCorp("服务队");;
		Vector<TestStudentCourse> shijianbu= MoniDBService.getStuCouByCorp("实践部");;
		Vector<TestStudentCourse> wailianbu= MoniDBService.getStuCouByCorp("外联部");;
		Vector<TestStudentCourse> wangluobu= MoniDBService.getStuCouByCorp("网络部");;
		Vector<TestStudentCourse> xuanchuanbu= MoniDBService.getStuCouByCorp("宣传部");;
		
//		Vector<String> kongkes = new Vector<>();
		
		for(int day=1;day<=5;++day) {
			for(int section = 1;section<=7;section+=2) {
				System.out.println("星期"+day+"  第"+section+"节");
				printKongke(day,section,bangongshi,"办公室");
				printKongke(day,section,fuwudui,"服务队");
				printKongke(day,section,shijianbu,"实践部");
				printKongke(day,section,wailianbu,"外联部");
				printKongke(day,section,wangluobu,"网络部");
				printKongke(day,section,xuanchuanbu,"宣传部");
				
			}
		}
	}
	
	
	public static void printKongke(int day,int section,Vector<TestStudentCourse> bangongshi,String type) {
		
		Vector<String> kongkes = new Vector<String>();
		
		for(int i=0;i<bangongshi.size();++i) {
			TestStudentCourse testStudentCourse =  bangongshi.get(i);
//			if(!testStudentCourse.getName().equals("王玉尧")) {
//				continue;
//			}
			Vector<Integer> weeks = new Vector<Integer>();
			int aa = 1;
			if(testStudentCourse.getCount().startsWith("2018")) {
				aa = 4;
			}
			for(int week=aa;week<=18;++week) {
			
				if(isKongke(week, day, section,testStudentCourse.getCourse())) {
					weeks.add(week);
				}
				
			}
			if(weeks.size()!=0) {
//				if(testStudentCourse.getName().equals("刘超")&&day==2&&section==1) {
//					String string = "";
//					for(int j=0;j<weeks.size();++j) {
//						string+=weeks.get(j)+" ";
//					}
//					System.out.println(string+"   1111166666666");
//				}
				String string = trans(weeks);
				kongkes.add(testStudentCourse.getName()+string);
			}
		}
		
//		System.out.println("星期"+day+"  第"+section+"节");
//		String rString = "办公室：";
		String rString = type+"：";
		for(int i=0;i<kongkes.size();++i) {
			rString+=kongkes.get(i)+"、";
		}
		if(rString.endsWith("、")) {
			rString = rString.substring(0,rString.length()-1);
		}
		System.out.println(rString);
		kongkes = new Vector<String>();
		System.out.println();
	}
	
	public static void saveEduCourse() {
		
//		saveCourse("201831222134","周正浩","办公室","011452",2018,1);
//		saveCourse("201830421014","郭传平","办公室","102172",2018,1);
//		saveCourse("201832521123","李加乐","办公室","190023",2018,1);
//		saveCourse("201830801112","孙悦","办公室","281926",2018,1);
		
		Vector<TestStuInfo> testStuInfos = testdb.DBService.getTestStuInfos();
		
//		System.out.println(testStuInfos.size());
		
		for(int i=0;i<testStuInfos.size();++i) {
			TestStuInfo testStuInfo = testStuInfos.get(i);
			String rs = saveCourse(testStuInfo.getCount(),testStuInfo.getName(),testStuInfo.getCorp(),testStuInfo.getPwd(),2018,1);
			if(rs.equals("errorUsernameOrPwd")) {
				if(!map.containsKey(testStuInfo.getCorp())) {
					map.put(testStuInfo.getCorp(),new Vector<TestStuInfo>());
				}
				map.get(testStuInfo.getCorp()).addElement(testStuInfo);
				
			}
			if(i!=0&&i%5==0) {
				try {
					Thread.sleep(7500);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
		}
		
		for(String corp : map.keySet()) {
			System.out.println(corp+" : ");
			for(int i=0;i<map.get(corp).size();++i) {
				TestStuInfo testStuInfo = map.get(corp).get(i);
				System.out.println(testStuInfo.getName()+" "+testStuInfo.getCount()+"  账号或密码错误！");
			}
			System.out.println("----------------");
		}
	}
	
	public static String trans(Vector<Integer> weeks) {
		
		if(weeks.size()!=0){
			String string = "(";
			
			int index =0;
			int a = weeks.get(0);
			int b = weeks.get(0);

			
			//首先找出连续的周
			Vector<Integer> delete = new Vector<Integer>();
			delete.add(a);
			boolean flag = false;
			for(int i=1;i<weeks.size();++i) {
				if(weeks.get(i)==weeks.get(i-1)+1) {
					if(!delete.contains(a)) {
						delete.add(a);
					}
					flag = true;
					b=weeks.get(i);
					delete.add(weeks.get(i));
					
					if(i+1==weeks.size()) {
						string +=a+"-"+b+" ";
					}
				}else if(flag){
					flag = false;
					string +=a+"-"+b+" ";
					a = weeks.get(i);
					b = weeks.get(i);
				}else {
					a=weeks.get(i);
					b = weeks.get(i);
				}
			}
			
			if(delete.size()>=2&&delete.get(0)+1!=delete.get(1)) {
				delete.remove(0);
			}else if(delete.size()==1) {
				delete.clear();
			}
			
			for(int i=0;i<delete.size();++i) {
//				int index2 = delete.get(i);
				weeks.remove(delete.get(i));
			}
			
			if(weeks==null||weeks.size()==0) {
				if(string.endsWith(" ")) {
					string = string.substring(0,string.length()-1);
				}
				string+=")";
				if(string.equals("()")) {
					string="";
				}
				return string;
			}
			//在找不连续的周
			a = weeks.get(0);
			b = weeks.get(0);
			flag = true;
			delete = new Vector<Integer>();
			boolean flag2 = false;
			for(int i=1;i<weeks.size();++i) {
				flag2 = true;
				if(weeks.get(i)== weeks.get(i-1)+2) {
					b = weeks.get(i);
					if(i+1==weeks.size()) {
						if(a%2==0) {
							string +=a+"-"+b+"双"+" ";
						}else {
							string +=a+"-"+b+"单"+" ";
						}
					}
				}else {
					if(b-a!=0) {
						if(a%2==0) {
							string +=a+"-"+b+"双"+" ";
						}else {
							string +=a+"-"+b+"单"+" ";
						}
					}else {
						string +=a+" ";
					}
					
					a = weeks.get(i);
					b = weeks.get(i);
				}
			}
			if(!flag2) {
				string +=a;
			}
			if(string.endsWith(" ")) {
				string = string.substring(0,string.length()-1);
			}
			string+=")";
			if(string.equals("()")) {
				string="";
			}
			return string;
		}
		return null;
	}
	
	public static String saveCourse(String count,String name,String corp,String pwd,int year,int grade) {
		
		javatest test = new javatest();
		test.username = count;
		test.password = pwd;
		try {
			String jsonstr = test.getkebiao(year, grade);
			if(jsonstr.equals("errorUsernameOrPwd")) {
//				System.out.println(corp+"  "+name+"  "+count+"   账号或密码错误!");
				return "errorUsernameOrPwd";
			}
			Vector<Course> courses = HtmlPrase.convertKebiao(jsonstr);
			
			String string2 = "";	
			for(int i=0;i<courses.size();++i) {
				Course course = courses.get(i);
				String string3 = "";
				string3 = course.getCT1()+"#"+course.getCT2()+"#"+course.getCT3();
				string2 = string2+string3+"=";
			}
			
			if(string2.endsWith("=")) {
				string2 = string2.substring(0,string2.length()-1);
			}
			MoniDBService.saveCourse2(count,name,corp,string2, year, grade);
			return "ok";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
		
	}
	

	//判断某学生是否在某节课有空
	public static boolean isKongke(int week,int day,int section,String courseContent) {
	

		String[] courses = courseContent.split("=");
		for(int i=0;i<courses.length;++i) {
			String course = courses[i];
			String[] strings = course.split("#");
			if(strings.length==3) {
				//该节课      该学生有课
				if(isWeekOk(week, strings[0])&&isDayOk(day, strings[1])&&isSectionOk(section, strings[2])) {
					return false;
				}
			}
		}
		return true;
	}
	
	//周是不是匹配   周的形式有    5-13周      5-13周(双)   6周      3-6周,7-14周     5周,8-11周  甚至更多 
		public static boolean isWeekOk(int week,String string) {
		
			String[] weeks = string.split(",");
			for(int i=0;i<weeks.length;++i) {
				String coursecontent = weeks[i];
				if(coursecontent.contains("-")) {
					int num1 = 0;
					int num2 = 0;
					int index = coursecontent.indexOf("-");
					for(int j=index+1;j<coursecontent.length();++j) {
						if(!MyTools.isInteger(coursecontent.charAt(j)+"")) {
							break;
						}
						num1 = num1*10+Integer.parseInt(coursecontent.charAt(j)+"");
						
					}
					String aString = "";
					for(int j = index-1;j>=0;j--) {
						if(!MyTools.isInteger(coursecontent.charAt(j)+"")) {
							break;
						}
						aString += coursecontent.charAt(j);
					}
					for(int j=aString.length()-1;j>=0;--j) {
						num2 = num2*10+Integer.parseInt(aString.charAt(j)+"");
						
					}
					
					if(coursecontent.contains("双")) {
						if(week>=num1&&week<=num2||week>=num2&&week<=num1) {
							if(week%2==0) {
								return true;
							}
						}
						
					}else if(coursecontent.contains("单")){
						if(week>=num1&&week<=num2||week>=num2&&week<=num1) {
							if(week%2==1) {
								return true;
							}
						}
					}else {
						if(week>=num1&&week<=num2||week>=num2&&week<=num1) {
							return true;
						}
					}
					
					
				}else {
					
					int num = 0;
					boolean flag = false;
					for(int j=0;j<coursecontent.length();++j) {
						if(!MyTools.isInteger(coursecontent.charAt(j)+"")) {
							if(flag) {
								break;
							}
							continue;
						}
						flag = true;
						num = num*10+Integer.parseInt(coursecontent.charAt(j)+"");
						
					}
					if(num==week) {
						return true;
					}
				}
			}

			return false;
		}
		
		//星期几是不是匹配
		public static boolean isDayOk(int day,String string) {
			int a = 1;
			if(string.contains("一")||string.contains("1")) {
				a = 1;
			}else if(string.contains("二")||string.contains("2")) {
				a = 2;
			}else if(string.contains("三")||string.contains("3")) {
				a = 3;
			}else if(string.contains("四")||string.contains("4")) {
				a = 4;
			}else if(string.contains("五")||string.contains("5")) {
				a = 5;
			}else if(string.contains("六")||string.contains("6")) {
				a = 6;
			}else if(string.contains("日")||string.contains("7")||string.contains("七")) {
				a = 7;
			}
			if(a==day) {
				return true;
			}
			return false;
		}
		
		//第几节课是不是匹配
		public static boolean isSectionOk(int section,String string) {
			
			String sections[] = string.split(",");
			for(int i=0;i<sections.length;++i) {
				String sectioncontent = sections[i];
				if(sectioncontent.contains("-")) {
					
					int num1 = 0;
					int num2 = 0;
					int index = sectioncontent.indexOf("-");
					for(int j=index+1;j<sectioncontent.length();++j) {
						if(!MyTools.isInteger(sectioncontent.charAt(j)+"")) {
							break;
						}
						num1 = num1*10+Integer.parseInt(sectioncontent.charAt(j)+"");
						
					}
					String aString = "";
					for(int j = index-1;j>=0;j--) {
						if(!MyTools.isInteger(sectioncontent.charAt(j)+"")) {
							break;
						}
						aString += sectioncontent.charAt(j);
					}
					for(int j=aString.length()-1;j>=0;--j) {
						num2 = num2*10+Integer.parseInt(aString.charAt(j)+"");
						
					}
					
					if(section>=num1&&section<=num2||section>=num2&&section<=num1) {
						return true;
					}
					
				}else {
					int num = 0;
					boolean flag = false;
					for(int j=0;j<sectioncontent.length();++j) {
						if(!MyTools.isInteger(sectioncontent.charAt(j)+"")) {
							if(flag) {
								break;
							}
							continue;
						}
						flag = true;
						num = num*10+Integer.parseInt(sectioncontent.charAt(j)+"");
						
					}
					if(num==section) {
						return true;
					}
				}
			}
			
			return false;
			
		}
	
}
