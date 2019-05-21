package schedulearrangement;

import db.DBService;
import db.SchdutyDBService;
import object.SearchEmptyCourse;
import util.MyTools;

import java.io.BufferedWriter;
import java.util.*;

/*
       值班逻辑
   数据库中学生课表的存储方式为   9-16周#星期五#5-6节=1-8周#星期五#7-8节
     不同课之间用=间隔   一门课 ：周#星期几#节
 */

public class ScheduleArrangement {

	private int account;                             //社团或组织的账号
	private int groupid;
	
	private Vector<RequestSchedule> requestSchedules;   //某节课值班的要求
	
	private Vector<UserKebiao> buzhangKebiaos;         //部长的课表
	private Vector<UserKebiao> ganshiKebiaos;          //干事的课表
	
	private Vector<String> buzhangArrangementIsZero = new Vector<String>(); //从未安排拍过值班的部长的账号
	private Vector<String> ganshiArrangementIsZero = new Vector<String>();  //从未安排拍过值班的干事的账号
	
	private int maxArrangement;
	
	private int benzhou;
	private int xiazhou;
	private Map<String, Integer> benzhouKongkeNum = new HashMap<String, Integer>(); //学生本周的空课数   只看白天就行  因为晚上一般不值班
	private Map<String, Integer> xiazhouKongkeNum = new HashMap<String, Integer>(); //学生下周的空课数   只看白天就行  因为晚上一般不值班
	
	private Map<String, Integer> haschangedArrangement = new HashMap<String, Integer>(); //更改过arrangement的学生  要记录   因为最后要写入数据库
	
	private Vector<Arrangement> arrangements = new Vector<Arrangement>();   //最终的值班表
	
	public ScheduleArrangement(Vector<RequestSchedule> requestSchedules,Vector<UserKebiao> buzhangKebiaos,Vector<UserKebiao> ganshiKebiaos,int benzhou,int xiazhou,int maxArrangement,int account,int groupid) {
		this.requestSchedules = requestSchedules;
		this.buzhangKebiaos = buzhangKebiaos;
		this.ganshiKebiaos = ganshiKebiaos;
		
		this.benzhou = benzhou;
		this.xiazhou = xiazhou;
		this.maxArrangement = maxArrangement;
		this.account = account;
		this.groupid = groupid;
	}
	
	
	public  Vector<Arrangement> scheduleArrangement(){
	
	
		init();
		getKongkeNum();
		select();
		scheduleBuzhang();
		scheduleGanshi();
		
		
		//值班表是不是为空
		boolean flag = false;
		for(int i=0;i<this.arrangements.size();++i) {
			if(this.arrangements.get(i).getPhs().size()>0) {
				flag = true;
				break;
			}
		}
		
		if(flag) {
			boolean rs = changeDB();
			if(!rs) {
				return null;
			}
		}
		
		return this.arrangements;
	}
	
	
	//根据 Vector<RequestSchedule> 初始化Vector<Arrangement> arrangements
	public void init() {
		
		for(int i=0;i<requestSchedules.size();++i) {
			Arrangement arrangement = new Arrangement();
			RequestSchedule requestSchedule = requestSchedules.get(i);
			arrangement.setWeek(requestSchedule.getWeek());
			arrangement.setWay(requestSchedule.getWay());
			arrangement.setSection(requestSchedule.getSection());
			arrangement.setBuzhangnum(requestSchedule.getBuzhangnum());
			arrangement.setGanshinum(requestSchedule.getGanshinum());
			arrangement.setCorpaccount(account);
			arrangement.setGroupid(groupid);
			this.arrangements.add(arrangement);
		}
		
		for(int i=0;i<buzhangKebiaos.size();++i) {
			if(buzhangKebiaos.get(i).getArrangement()==0) {
				this.buzhangArrangementIsZero.add(buzhangKebiaos.get(i).getPh());
			}
		}
		
		for(int i=0;i<ganshiKebiaos.size();++i) {
			if(ganshiKebiaos.get(i).getArrangement()==0) {
				this.ganshiArrangementIsZero.add(ganshiKebiaos.get(i).getPh());
			}
		}
		
	}
	
	//计算出学生的在本周和下周的空课数量
	private void getKongkeNum() {
		
		if(benzhou!=-1) {
			for(int i=1;i<=7;++i) {              //周一到周日
				for(int j=1;j<=7;j+=2) {          //第一小节到第八小节
					
					for(int k=0;k<this.buzhangKebiaos.size();++k) {
						String ph = buzhangKebiaos.get(k).getPh();
						if(!this.benzhouKongkeNum.containsKey(ph)) {
							this.benzhouKongkeNum.put(ph, 0);
						}
						if(isKongke(benzhou, i, j, buzhangKebiaos.get(k))) {
							this.benzhouKongkeNum.put(ph, this.benzhouKongkeNum.get(ph)+1);
						}
					}
					
					for(int k=0;k<this.ganshiKebiaos.size();++k) {
						String ph = ganshiKebiaos.get(k).getPh();
						if(!this.benzhouKongkeNum.containsKey(ph)) {
							this.benzhouKongkeNum.put(ph, 0);
						}
						if(isKongke(benzhou, i, j, ganshiKebiaos.get(k))) {
						
							this.benzhouKongkeNum.put(ph, this.benzhouKongkeNum.get(ph)+1);
							
						}
					}
				}
			}
			
		}
		
		if(xiazhou!=-1) {
			for(int i=1;i<=7;++i) {              //周一到周日
				for(int j=1;j<=7;j+=2) {          //第一小节到第八小节
					for(int k=0;k<this.buzhangKebiaos.size();++k) {
						String ph = buzhangKebiaos.get(k).getPh();
						if(!this.xiazhouKongkeNum.containsKey(ph)) {
							this.xiazhouKongkeNum.put(ph, 0);
						}
						if(isKongke(xiazhou, i, j, buzhangKebiaos.get(k))) {
						
							this.xiazhouKongkeNum.put(ph, this.xiazhouKongkeNum.get(ph)+1);
							
						}
					}
					
					for(int k=0;k<this.ganshiKebiaos.size();++k) {
						String ph = ganshiKebiaos.get(k).getPh();
						if(!this.xiazhouKongkeNum.containsKey(ph)) {
							this.xiazhouKongkeNum.put(ph, 0);
						}
						if(isKongke(xiazhou, i, j, ganshiKebiaos.get(k))) {
						
							this.xiazhouKongkeNum.put(ph, this.xiazhouKongkeNum.get(ph)+1);
							
						}
					}
				}
			}
			
		}
		
	}
	
	//计算出  要排班的那节课   有空的学生人数   并把学生的学号加入
	public void select() {
		for(int i=0;i<arrangements.size();++i) {
			
			for(int j=0;j<buzhangKebiaos.size();++j) {
				
				if(scheduleOK(arrangements.get(i),buzhangKebiaos.get(j))) {
					arrangements.get(i).setBuzhangOKnum(arrangements.get(i).getBuzhangOKnum()+1);
					arrangements.get(i).getBuzhangOKId().add(buzhangKebiaos.get(j).getPh());
					
				}
			}
			
			for(int j=0;j<ganshiKebiaos.size();++j) {
				if(scheduleOK(arrangements.get(i),ganshiKebiaos.get(j))) {
					arrangements.get(i).setGanshiOknum(arrangements.get(i).getGanshiOknum()+1);
					arrangements.get(i).getGanshiOKId().add(ganshiKebiaos.get(j).getPh());
				
				}
				
			}
		}
	
	}
	
	//安排部长值班 
	//先按照 buzhangOKnum排序  然后
	public void scheduleBuzhang() {
		
		Vector<Arrangement> arrangements2 = (Vector<Arrangement>)arrangements.clone();
		Vector<Arrangement> delete = new Vector<Arrangement>();     //找出不参与的
		for(int i=0;i<arrangements2.size();++i) {
			if(arrangements2.get(i).getBuzhangnum()==0||arrangements2.get(i).getBuzhangOKnum()==0) {
				delete.add(arrangements2.get(i));
			}
		}
		
		for(int i=0;i<delete.size();++i){
			arrangements2.remove(delete.get(i));
		}
		
		//排序     按照buzhangOknum升序排列
		 Collections.sort(arrangements2, new Comparator<Arrangement>() {

	            public int compare(Arrangement l, Arrangement r) {
	                       
	                if(l.getBuzhangOKnum()>r.getBuzhangOKnum()) {
	                	return 1;
	                }else if(l.getBuzhangOKnum()<r.getBuzhangOKnum()) {
	                	return -1;
	                }else {
	                	return 0;
	                }
	            }
	        });
		
		//剩下的参与的  一定能安排满   或者排满oknum
		 for(int i=0;i<arrangements2.size();++i) {
			 //首先排从未安排过值班的        需要课先增序   然后找满足的学生中该周课最少的学生
			 Arrangement arrangement = arrangements2.get(i);
			 int hasArrGanshiNum = arrangement.getPhs().size();   //该节课已经安排的干事的数量
			 boolean flag = false;      //是否已经排完该节课
			 for(int j=0;j<arrangement.getBuzhangnum();++j) {
			
				 if(arrangement.getPhs().size()-hasArrGanshiNum>=arrangement.getBuzhangnum()||0==arrangement.getBuzhangOKnum()) {
					 flag = true;
					 break;
				 }
				 int minnum = 999999;    //选出满足的学生中  该周空课最少的学生
				 String okid = "";
				 for(int k=0;k<arrangement.getBuzhangOKId().size();++k) {
					 String id = arrangement.getBuzhangOKId().get(k);
					 if(!this.buzhangArrangementIsZero.contains(id)) {
						 continue;
					 }
					 if(arrangement.getWeek()==this.benzhou&&minnum>this.benzhouKongkeNum.get(id)) {
						 minnum = this.benzhouKongkeNum.get(id);
						 okid = id;
					 }else if(arrangement.getWeek()==this.xiazhou&&minnum>this.xiazhouKongkeNum.get(id)) {
						 minnum = this.xiazhouKongkeNum.get(id);
						 okid = id;
					 }
				 }
				 
				 if(minnum!=999999) {
					 arrangements2.get(i).getPhs().add(okid);
					 int index = -1;
					 //该成员已经安排进去  那么就要从中删除
					 for(int k=0;k<arrangements2.get(i).getBuzhangOKId().size();++k) {
						 if(okid.equals(arrangements2.get(i).getBuzhangOKId().get(k))) {
							 index = k;
							 break;
						 }
					 }
					 arrangements2.get(i).getBuzhangOKId().remove(index);
					 arrangements2.get(i).setBuzhangOKnum(arrangements2.get(i).getBuzhangOKnum()-1);
					 
					 index = -1;
					 //修改该成员的 arrangement值     并arrangement值增序排序
					 for(int k=0;k<buzhangKebiaos.size();++k) {
						 if(buzhangKebiaos.get(k).getPh().equals(okid)) {
							 index = k;
							 break;
						 }
					 }
					 this.maxArrangement++;
					 buzhangKebiaos.get(index).setArrangement(this.maxArrangement);
					 //调整顺序  到最后
					 UserKebiao userKebiao =  buzhangKebiaos.remove(index);
					 buzhangKebiaos.add(userKebiao);
					 
					 this.buzhangArrangementIsZero.remove(okid);
					 this.haschangedArrangement.put(okid, this.maxArrangement);
				 }
				 
			 }
			 if(flag) {
				 for(int k=0;k<this.arrangements.size();++k) {
					 if(arrangements.get(k).getWeek()==arrangement.getWeek()&&arrangements.get(k).getWay()==arrangement.getWay()&&arrangements.get(k).getSection()==arrangement.getSection()) {
						 arrangements.get(k).setPhs(arrangement.getPhs());
					 }
				 }
				 continue;
			 }
			
		
			 
			//已经安排过值班的    按顺序替换就行
			 int sum = 0;
			 while(true) {
				 if(sum>=2000) {
					 break;
				 }
				 ++sum;
				 
				 if( arrangements2.get(i).getPhs().size()-hasArrGanshiNum>= arrangements2.get(i).getBuzhangnum()|| 0== arrangements2.get(i).getBuzhangOKnum()) {
					 break;
				 }
				 
				 Vector<Integer> okids = new Vector<Integer>();
				 for(int j=0;j<buzhangKebiaos.size();++j){
					 if( arrangements2.get(i).getPhs().size()-hasArrGanshiNum== arrangements2.get(i).getBuzhangnum()|| 0== arrangements2.get(i).getBuzhangOKnum()) {
						 break;
					 }
					 String okid = buzhangKebiaos.get(j).getPh();
					 //满足
					 if(arrangements2.get(i).getBuzhangOKId().contains(okid)) {
						 
						 arrangements2.get(i).getPhs().add(okid);
//						 int index = -1;
						 //该成员已经安排进去  那么就要从中删除
//						 for(int z=0;z<arrangements2.get(i).getBuzhangOKId().size();++z) {
//							 if(okid.equals(arrangements2.get(i).getBuzhangOKId().get(z))) {
////								 index = z;
//								 break;
//							 }
//						 }
						
						arrangements2.get(i).getBuzhangOKId().remove(okid);
						arrangements2.get(i).setBuzhangOKnum(arrangements2.get(i).getBuzhangOKnum()-1);
//						arrangements2.get(i).getPhs().addElement(okid);
						 
						
						 UserKebiao userKebiao = null;
						
						 //修改该成员的 arrangement值     并arrangement值增序排序
						 for(int k=0;k<buzhangKebiaos.size();++k) {
							 if(buzhangKebiaos.get(k).getPh().equals(okid)) {
								
								 userKebiao = buzhangKebiaos.get(k);
								 break;
							 }
						 }
						 this.maxArrangement++;
//						 buzhangKebiaos.get(index).setArrangement(this.maxArrangement);
						 //调整顺序  到最后
//						 UserKebiao userKebiao =  buzhangKebiaos.remove(index);
						 buzhangKebiaos.remove(userKebiao);
						 userKebiao.setArrangement(this.maxArrangement);
						 buzhangKebiaos.add(userKebiao);
						 j--;
						 if( this.buzhangArrangementIsZero.contains(okid)) {
							 this.buzhangArrangementIsZero.remove(okid);
						 }
						 
						 this.haschangedArrangement.put(okid, this.maxArrangement);
					 }
				 }
			 }
			 
			 //安排完本节课  进行结果的保存
			 for(int k=0;k<this.arrangements.size();++k) {
				 if(arrangements.get(k).getWeek()==arrangement.getWeek()&&arrangements.get(k).getWay()==arrangement.getWay()&&arrangements.get(k).getSection()==arrangement.getSection()) {
					 arrangements.get(k).setPhs(arrangement.getPhs());
				 }
			 }
		
		 }
		
	}
		
	
	
	//安排干事值班
	//先按照 ganshiOKnum排序  然后
	public void scheduleGanshi() {
		Vector<Arrangement> arrangements2 = (Vector<Arrangement>) this.arrangements.clone();
		
//		Vector<Integer> delete = new Vector<>();     //找出不参与的
		Vector<Arrangement> delete = new Vector<Arrangement>();
		for(int i=0;i<arrangements2.size();++i) {
			if(arrangements2.get(i).getGanshinum()==0||arrangements2.get(i).getGanshiOknum()==0) {
				delete.add(arrangements2.get(i));
			}
		}
		
		for(int i=0;i<delete.size();++i) {
			arrangements2.remove(delete.get(i));
		}
		
		//排序     按照buzhangOknum升序排列
		 Collections.sort(arrangements2, new Comparator<Arrangement>() {

	            public int compare(Arrangement l, Arrangement r) {
	                       
	                if(l.getGanshiOknum()>r.getGanshiOknum()) {
	                	return 1;
	                }else if(l.getGanshiOknum()<r.getGanshiOknum()) {
	                	return -1;
	                }else {
	                	return 0;
	                }
	            }
	        });
		
		//剩下的参与的  一定能安排满   或者排满oknum
		 for(int i=0;i<arrangements2.size();++i) {
			 //首先排从未安排过值班的        需要课先增序   然后找满足的学生中该周课最少的学生
			 Arrangement arrangement = arrangements2.get(i);
			 int hasArrBuzhangNum = arrangement.getPhs().size();   //该节课已经安排的部长的数量
			 boolean flag = false;      //是否已经排完该节课
			 for(int j=0;j<arrangement.getGanshinum();++j) {
			
				 if(arrangement.getPhs().size()-hasArrBuzhangNum>=arrangement.getGanshinum()||0==arrangement.getGanshiOknum()) {
					 flag = true;
					 break;
				 }
				 int minnum = 999999;    //选出满足的学生中  该周空课最少的学生
				 String okid = "";
				 for(int k=0;k<arrangement.getGanshiOKId().size();++k) {
					 String id = arrangement.getGanshiOKId().get(k);
					 if(!this.ganshiArrangementIsZero.contains(id)) {
						 continue;
					 }
					 if(arrangement.getWeek()==this.benzhou&&minnum>this.benzhouKongkeNum.get(id)) {
						 minnum = this.benzhouKongkeNum.get(id);
						 okid = id;
					 }else if(arrangement.getWeek()==this.xiazhou&&minnum>this.xiazhouKongkeNum.get(id)) {
						 minnum = this.xiazhouKongkeNum.get(id);
						 okid = id;
					 }
				 }
				 
				 if(minnum!=999999) {
					 arrangements2.get(i).getPhs().add(okid);
//					 int index = -1;
//					 //该成员已经安排进去  那么就要从中删除
//					 for(int k=0;k<arrangements2.get(i).getGanshiOKId().size();++k) {
//						 if(okid.equals(arrangements2.get(i).getGanshiOKId().get(k))) {
//							 index = k;
//							 break;
//						 }
//					 }
					 arrangements2.get(i).getGanshiOKId().remove(okid);
					 arrangements2.get(i).setGanshiOknum(arrangements2.get(i).getGanshiOknum()-1);
					UserKebiao userKebiao = null;
					 //修改该成员的 arrangement值     并arrangement值增序排序
					 for(int k=0;k<ganshiKebiaos.size();++k) {
						 if(ganshiKebiaos.get(k).getPh().equals(okid)) {
							 userKebiao = ganshiKebiaos.get(k);
							 break;
						 }
					 }
					 this.maxArrangement++;
					 
//					 ganshiKebiaos.get(index).setArrangement(this.maxArrangement);
					 //调整顺序  到最后
//					 UserKebiao userKebiao =  ganshiKebiaos.remove(index);
					 ganshiKebiaos.remove(userKebiao);
					 userKebiao.setArrangement(this.maxArrangement);
					 ganshiKebiaos.add(userKebiao);
					 
					 this.ganshiArrangementIsZero.remove(okid);
					 this.haschangedArrangement.put(okid, this.maxArrangement);
				 }
				 
			 }
			 if(flag) {
//				 for(int k=0;k<this.arrangements.size();++k) {
//					 if(arrangements.get(k).getWeek()==arrangement.getWeek()&&arrangements.get(k).getWay()==arrangement.getWay()&&arrangements.get(k).getSection()==arrangement.getSection()) {
//						 arrangements.get(k).setPhs(arrangement.getPhs());
//					 }
//				 }
				 continue;
			 }
			
			
			 int sum = 0;
			//已经安排过值班的    按顺序替换就行
			 while(true) {
				 ++sum;
				 if(sum>2000) {
					 break;
				 }
				 if( arrangements2.get(i).getPhs().size()-hasArrBuzhangNum>= arrangements2.get(i).getGanshinum()|| 0== arrangements2.get(i).getGanshiOknum()) {
					 break;
				 }
				 
				 Vector<Integer> okids = new Vector<Integer>();
				 for(int j=0;j<ganshiKebiaos.size();++j){
//					 if( arrangements2.get(i).getPhs().size()== arrangements2.get(i).getGanshinum()|| 0== arrangements2.get(i).getGanshiOknum()) {
//						 break;
//					 }
					 String okid = ganshiKebiaos.get(j).getPh();
					 //满足
					 if(arrangements2.get(i).getGanshiOKId().contains(okid)) {
						 
						 arrangements2.get(i).getPhs().add(okid);
						 
//						 int index = -1;
//						 //该成员已经安排进去  那么就要从中删除
//						 for(int z=0;z<arrangements2.get(i).getGanshiOKId().size();++z) {
//							 if(okid.equals(arrangements2.get(i).getGanshiOKId().get(z))) {
//								 index = z;
//								 break;
//							 }
//						 }
						 arrangements2.get(i).getGanshiOKId().remove(okid);
						 arrangements2.get(i).setGanshiOknum(arrangements2.get(i).getGanshiOknum()-1);
//						 arrangements2.get(i).getPhs().addElement(okid);
						 
//						 int index = -1;
						 UserKebiao userKebiao = null;
						 //修改该成员的 arrangement值     并arrangement值增序排序
						 for(int k=0;k<ganshiKebiaos.size();++k) {
							 if(ganshiKebiaos.get(k).getPh().equals(okid)) {
//								 index = k;
								 userKebiao = ganshiKebiaos.get(k);
								 break;
							 }
						 }
						 this.maxArrangement++;
//						 ganshiKebiaos.get(index).setArrangement(this.maxArrangement);
						 //调整顺序  到最后
//						 UserKebiao userKebiao =  ganshiKebiaos.remove(index);
						 ganshiKebiaos.remove(userKebiao);
						 userKebiao.setArrangement(this.maxArrangement);
						 ganshiKebiaos.add(userKebiao);
						 j--;
						 if( this.ganshiArrangementIsZero.contains(okid)) {
							 this.ganshiArrangementIsZero.remove(okid);
						 }
						 
						 this.haschangedArrangement.put(okid, this.maxArrangement);
						 
						 if( arrangements2.get(i).getPhs().size()>= arrangements2.get(i).getGanshinum()|| 0== arrangements2.get(i).getGanshiOknum()) {
							 break;
						 }
					 }
				 }
			 }
			 
			 //安排完本节课  进行结果的保存
			 for(int k=0;k<this.arrangements.size();++k) {
				 if(arrangements.get(k).getWeek()==arrangement.getWeek()&&arrangements.get(k).getWay()==arrangement.getWay()&&arrangements.get(k).getSection()==arrangement.getSection()) {
					 arrangements.get(k).setPhs(arrangement.getPhs());
				 }
			 }
		
		 }
		
	}
	
	//判断某学生是否在某节课有空
	public boolean isKongke(int week,int day,int section,UserKebiao buzhangKebiao) {
	
		String courseContent = buzhangKebiao.getCourse();
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
	
	
	public boolean scheduleOK(Arrangement arrangement,UserKebiao kebiao) {
		
		int week = arrangement.getWeek();
		int way = arrangement.getWay();
		int section = arrangement.getSection();
		
		
		String courseContent = kebiao.getCourse();
		String[] courses = courseContent.split("=");
		for(int i=0;i<courses.length;++i) {
			String course = courses[i];
			String[] strings = course.split("#");
			if(strings.length==3) {
				//该节课      该学生有课
				if(isWeekOk(week, strings[0])&&isDayOk(way, strings[1])&&isSectionOk(section, strings[2])) {
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
	
	private boolean changeDB() {
		
		boolean rs = SchdutyDBService.changeUserCourseArrangement(this.haschangedArrangement, groupid);
		return rs;
	}
	
	
	//某学生是否在给定的几节课都没有课
		public static boolean isOkEmpty2(String courseContent,Vector<SearchEmptyCourse> searchEmptyCourses) {
			
			for(int j=0;j<searchEmptyCourses.size();j++) {
				int week = searchEmptyCourses.get(j).getWeek();
				int way = searchEmptyCourses.get(j).getWay();
				int section = searchEmptyCourses.get(j).getSection();
				
				String[] courses = courseContent.split("=");
				for(int i=0;i<courses.length;++i) {
					String course = courses[i];
					String[] strings = course.split("#");
					if(strings.length==3) {
						//该节课      该学生有课
						if(isWeekOk(week, strings[0])&&isDayOk(way, strings[1])&&isSectionOk(section, strings[2])) {
							return false;
						}
						
					}
				}
				
			}
			
			return true;
			
		}
	
	//某学生是否在给定的几节课都没有课
	public static boolean isOkEmpty(String courseContent,Vector<Integer> integers) {
		
		int week = integers.get(4);
		
		String[] courses = courseContent.split("=");
		for(int i=0;i<courses.length;++i) {
			String course = courses[i];
			String[] strings = course.split("#");
			if(strings.length==3) {
				for(int j=4;j<integers.size();j+=2) {
					int day = integers.get(j);
					int section = integers.get(j+1);
					//该节课      该学生有课
					if(isWeekOk(week, strings[0])&&isDayOk(day, strings[1])&&isSectionOk(section, strings[2])) {
						return false;
					}
				}
				
			}
		}
		return true;
		
	}
	
}
