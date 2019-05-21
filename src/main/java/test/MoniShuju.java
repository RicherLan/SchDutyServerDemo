package test;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

/*
 * 本地模拟生成数据
 */
public class MoniShuju {

	
	public static void main(String[] args) {
		
		Vector<String> phStrings = moniUser();
		int year = 2018;
		int xueqi = 1;
		for(int i=0;i<phStrings.size();++i)
		{
			String ph = phStrings.get(i);
			MoniStuCourse(ph,year,xueqi);
		}
		
	}
	
	
	public static Vector<String> moniUser(){
		Vector<String> phStrings = new Vector<String>();
		long id = 1000000000;
		for(int i=1;i<=400;++i) {
			String ph = (id+i)+"";
			String nickname = "";
			for(int k=0;k<3;++k) {
				nickname+=getRandomChar();
			}
			String password = "110";
			String schoolname = "济南大学";
			String departmentname = "信息科学与工程学院";
			String majorname = "计算机科学与技术";
			MoniDBService.adduser(ph, nickname, password, schoolname, departmentname, majorname);
			long jointime = System.currentTimeMillis()/1000;
			String corppos = "";
			Random random = new Random();
			int a = random.nextInt(100);
			if(a<5) {
				corppos = "部长";
			}else {
				corppos = "干事";
			}
			MoniDBService.addCorpGroup(ph,600003,jointime,nickname,"普通",corppos);
			phStrings.add(ph);
		}
		
		return phStrings;
	}
	


	 public static char getRandomChar() {
	        String str = "";
	        int hightPos;
	        int lowPos;

	        Random random = new Random();

	        hightPos = (176 + Math.abs(random.nextInt(39)));
	        lowPos = (161 + Math.abs(random.nextInt(93)));

	        byte[] b = new byte[2];
	        b[0] = (Integer.valueOf(hightPos)).byteValue();
	        b[1] = (Integer.valueOf(lowPos)).byteValue();

	        try {
	            str = new String(b, "GB2312");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }

	        return str.charAt(0);
	    }


	 //模拟用户课表
	 //    9-16周$星期五$5-6节=1-8周$星期五$7-8节
	 public static void MoniStuCourse(String ph,int year,int xueqi) {
		 Random random = new Random();
		 String coursestring = "";
		 for(int i=1;i<=7;++i) {          //星期几
			 for(int j=1;j<=9;j+=2) {    //第几节课
				 int num = random.nextInt(10);
				 if(num%2==0) {           //该课上  那么就产生周
					 int beginzhou = random.nextInt(17)+1;
					 int endzhou = random.nextInt(17)+1;
					 if(beginzhou>endzhou) {
						 int a = beginzhou;
						 beginzhou = endzhou;	
						 endzhou = beginzhou;
					 }
					 
					 coursestring += beginzhou+"-"+endzhou+"周$";
					 String string = "星期一";
					 if(i==1) {
						 string = "星期一";
					 }else if(i==2) {
						 string = "星期二";
					 }else if(i==3) {
						 string = "星期三";
					 }else if(i==4) {
						 string = "星期四";
					 }else if(i==5) {
						 string = "星期五";
					 }else if(i==6) {
						 string = "星期六";
					 }else if(i==7) {
						 string = "星期日";
					 }
					 coursestring+=string+"$";
					 coursestring+=j+"-"+(j+1)+"节=";
				 }
			 }
		 }
		 if(coursestring!="") {
			 if(coursestring.endsWith("=")) {
				 coursestring = coursestring.substring(0,coursestring.length()-1);
				 //加入数据库
				 MoniDBService.saveCourse(ph, coursestring, year, xueqi);
			 }
		 }
		 
	 }
	
}
