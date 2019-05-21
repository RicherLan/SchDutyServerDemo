package netty.server.corprationHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import db.CorpDBService;
import db.GroupDBService;
import db.SchdutyDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.SendAdmiDutySche_PACKET;
import netty.protocol.packet.SendAllUsersDutyNotiNO_PACKET;
import netty.protocol.request.otherRequest.SchduleArrangementRequestPacket;
import netty.protocol.response.otherResponse.SchduleArrangementResponsePacket;
import netty.util.SessionUtil;
import schedulearrangement.Arrangement;
import schedulearrangement.ClientArrangement;
import schedulearrangement.RequestSchedule;
import schedulearrangement.ScheduleArrangement;
import schedulearrangement.UserKebiao;
import threadUtil.FixedThreadPool;
import util.MyTools;

/*
 * 	安排值班表
 */
@ChannelHandler.Sharable
public class SchduleArrangementRequestHandler extends SimpleChannelInboundHandler<SchduleArrangementRequestPacket>{
	public static final SchduleArrangementRequestHandler INSTANCE = new SchduleArrangementRequestHandler();

    protected SchduleArrangementRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SchduleArrangementRequestPacket requestPacket) throws Exception {
		System.out.println("SchduleArrangementRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				int account = requestPacket.getAccount();
				int groupid = CorpDBService.getGroupidByCorpAccount(account);
				int year = requestPacket.getYear();
				int xq = requestPacket.getXq();
				int benzhou = requestPacket.getBz(); // 没有的话传-1
				int xiazhou = requestPacket.getXz(); // 没有的话传-1

				// 安排下一周 next_zhou 安排本周 ben_zhou 安排今后7天 next_seven_days
				String type = requestPacket.getType(); // 排班的类型

				Vector<RequestSchedule> requestSchedules = requestPacket.getRequestSchedules();
						

				int maxArrangement = SchdutyDBService.getMaxArrangementByCorpAccount(account);
				if (maxArrangement == -1 || groupid == -1 || requestSchedules == null || requestSchedules.size() == 0) {
					//String json = "{\"rs\":\"error\"}";
					
					SchduleArrangementResponsePacket responsePacket = new SchduleArrangementResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					ctx.writeAndFlush(responsePacket);
					
					return;
				}

				Vector<UserKebiao> buzhangkebiaos = SchdutyDBService.getKebiaoByCorpAccount("部长", groupid, year, xq);
				Vector<UserKebiao> ganshikebiaos = SchdutyDBService.getKebiaoByCorpAccount("干事", groupid, year, xq);

				ScheduleArrangement scharr = new ScheduleArrangement(requestSchedules, buzhangkebiaos, ganshikebiaos,
						benzhou, xiazhou, maxArrangement, account, groupid);

				Vector<Arrangement> arrangements = scharr.scheduleArrangement();
				
				
				if (arrangements == null) {
				//	String json = "{\"rs\":\"error\"}";
					
					SchduleArrangementResponsePacket responsePacket = new SchduleArrangementResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					ctx.writeAndFlush(responsePacket);
					
					return;
				}

				Vector<ClientArrangement> clientArrangements = new Vector<>();

				Vector<Integer> times = MyTools.getTime();
				int timeyear = times.get(0);
				int month = times.get(1);
				int daytime = times.get(2);
				int way = times.get(3);

				

				Date d = new Date();
				SimpleDateFormat df1 = new SimpleDateFormat("dd");
				SimpleDateFormat df2 = new SimpleDateFormat("MM");
				SimpleDateFormat df3 = new SimpleDateFormat("yyyy");
				for (int i = 0; i < arrangements.size(); ++i) {

					int way2 = arrangements.get(i).getWay();

					int sub = 7 - way + 1;
					if (type.equals("next_zhou")) {
						sub = 7 - way + way2;
					} else {
						if (way2 >= way) {
							sub = way2 - way;
						} else {
							sub = 7 - way + way2;
						}
					}
					long dd = 24 * 60 * 60 * 1000;
					Long mmm = Long.parseLong(df2.format(new Date(d.getTime() + (sub) * dd)));
					Long ddd = Long.parseLong(df1.format(new Date(d.getTime() + (sub) * dd)));
					Long yyy = Long.parseLong(df3.format(new Date(d.getTime() + (sub) * dd)));

					int timeyear2 = Integer.parseInt(yyy + "");
					int month2 = Integer.parseInt(mmm + "");
					int daytime2 = Integer.parseInt(ddd + "");

					ClientArrangement clientArrangement = new ClientArrangement();
					clientArrangement.setYear(timeyear2);
					clientArrangement.setMonth(month2);
					clientArrangement.setDaytime(daytime2);
					clientArrangement.setWay(arrangements.get(i).getWay());
					clientArrangement.groupid = arrangements.get(i).getGroupid();
					clientArrangement.week = arrangements.get(i).getWeek();
					// clientArrangement.day = arrangements.get(i).getWay();
					clientArrangement.section = arrangements.get(i).getSection();
					clientArrangement.phs = arrangements.get(i).getPhs();

					Vector<String> names = GroupDBService.getGroupNamesByPhs(groupid, arrangements.get(i).getPhs());
					Vector<String> poss = CorpDBService.getCorppossByPhs(groupid, arrangements.get(i).getPhs());
					clientArrangement.names = names;
					clientArrangement.poss = poss;

					clientArrangement.setBuzhangnum(arrangements.get(i).getBuzhangnum());
					clientArrangement.setGanshinum(arrangements.get(i).getGanshinum());

					clientArrangements.add(clientArrangement);
				}

				int week = requestSchedules.get(0).getWeek();
				int section = requestSchedules.get(0).getSection();
//				SchdutyDBService.resetArrBeforeSaveArr(groupid, timeyear, month, daytime, week, way, section);

				String rsString2 = SchdutyDBService.saveSchDutyBeginTime(groupid, timeyear, month, daytime, way);
				if (rsString2.equals("ok")) {
					String rsString = SchdutyDBService.saveScheduleArrangement2(groupid, clientArrangements);
					if (!rsString.equals("ok")) {
						//String json = "{\"rs\":\"error\"}";
						
						SchduleArrangementResponsePacket responsePacket = new SchduleArrangementResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setResult("error");
						ctx.writeAndFlush(responsePacket);
						
						return;
					} else {
						//String json = "{\"rs\":\"ok\"}";
						SchduleArrangementResponsePacket responsePacket = new SchduleArrangementResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setResult("ok");
						ctx.writeAndFlush(responsePacket);
					}

				} else {
					//String json = "{\"rs\":\"error\"}";
					SchduleArrangementResponsePacket responsePacket = new SchduleArrangementResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					ctx.writeAndFlush(responsePacket);
					return;
				}

				

				//JSONArray jsonArray = JSONArray.fromObject(clientArrangements);
				
				//String json2 = "{\"schway\":\"" + type + "\",\"gid\":\"" + groupid + "\"}";
				//sendMsgToclient(account + "", "DutySche " + json2, jsonArray.toString());

				SendAdmiDutySche_PACKET packet = new SendAdmiDutySche_PACKET();
				packet.setVersion(requestPacket.getVersion());
				packet.setSchway(type);
				packet.setGroupid(groupid);
				packet.setClientArrangements(clientArrangements);
				
				ctx.writeAndFlush(packet);
				
				
				long time2 = System.currentTimeMillis();
				Vector<String> phStrings = GroupDBService.getAllUsersInGroupByGroupid(groupid);
				
				long time = System.currentTimeMillis();

				String corpname = CorpDBService.getCorpNameBygroupid(groupid);
				
				
				for (int i = 0; i < phStrings.size(); ++i) {
					
					String phString = phStrings.get(i);
		
					if (phString.equals(account + "")) {
						continue;
					}
					
					
					int id2 = SchdutyDBService.addDutyNotice(groupid, phString, "未读", time2);
					//System.out.println(phString+"  "+id2);
					if(id2==-1) {
						continue;
					}
					
					
					// 在线的话直接发过去
					Channel channel = SessionUtil.getChannel(phString);
					if(SessionUtil.hasLogin(channel)) {
						
						
						String myduty = "no"; // yes no 代表我是否被安排值班
						for (int j = 0; j < clientArrangements.size(); ++j) {
							boolean flag = false;
							if (clientArrangements.get(j).getPhs().contains(phString)) {
								myduty = "yes";
								flag = true;
								break;
							}
							if (flag) {
								break;
							}
						}

//						String json3 = "{\"gid\":\"" + groupid + "\",\"dnid\":\"" + id2 + "\",\"time\":\"" + time
//								+ "\",\"corpname\":\"" + corpname + "\",\"myduty\":\"" + myduty + "\"}";
//
//						sendMsgToclient(phString, "dutynoti", json3);
						
						SendAllUsersDutyNotiNO_PACKET packet2 = new  SendAllUsersDutyNotiNO_PACKET();
						packet2.setVersion(requestPacket.getVersion());
						packet2.setGroupid(groupid);
						packet2.setDnid(id2);
						packet2.setTime(time);
						packet2.setCorpname(corpname);
						packet2.setMyduty(myduty);
						
						channel.writeAndFlush(packet2);
						
					}

				}
				
			}
		});
		
	}
}
