package netty.server.corprationHandler;

import java.util.Vector;

import db.CorpDBService;
import db.SchdutyDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.SendAllUsersDutyNotiNO_PACKET;
import netty.protocol.request.otherRequest.GetAllDutyNotiNotReadRequestPacket;
import netty.session.Session;
import netty.util.SessionUtil;
import schedulearrangement.ClientArrangement;
import threadUtil.FixedThreadPool;

/*
 * 获得自己的未读的值班的通知
 */
@ChannelHandler.Sharable
public class GetAllDutyNotiNotReadRequestHandler
		extends SimpleChannelInboundHandler<GetAllDutyNotiNotReadRequestPacket> {
	public static final GetAllDutyNotiNotReadRequestHandler INSTANCE = new GetAllDutyNotiNotReadRequestHandler();

	protected GetAllDutyNotiNotReadRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetAllDutyNotiNotReadRequestPacket requestPacket) throws Exception {
		System.out.println("GetAllDutyNotiNotReadRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();

				Vector<String> strings = SchdutyDBService.getAllDutynotiNOtreadByph(ph);
				for (int i = 0; i < strings.size(); ++i) {
					String string = strings.get(i);
					String[] strings2 = string.split(" ");
					int dnid = Integer.parseInt(strings2[0]);
					int groupid = Integer.parseInt(strings2[1]);
					long time = Long.parseLong(strings2[2]);

					Vector<ClientArrangement> clientArrangements = SchdutyDBService.getDutyTableBygroupid(groupid);

					String corpname = CorpDBService.getCorpNameBygroupid(groupid);
					String myduty = "no"; // yes no 代表我是否被安排值班
					for (int j = 0; j < clientArrangements.size(); ++j) {
						boolean flag = false;
						if (clientArrangements.get(j).getPhs().contains(ph)) {
							myduty = "yes";
							flag = true;
							break;
						}
						if (flag) {
							break;
						}
					}

//					String json3 = "{\"gid\":\"" + groupid + "\",\"dnid\":\"" + dnid + "\",\"time\":\"" + time
//							+ "\",\"corpname\":\"" + corpname + "\",\"myduty\":\"" + myduty + "\"}";
//
//					sendMsgToclient(ph, "dutynoti", json3);
					
					SendAllUsersDutyNotiNO_PACKET packet2 = new  SendAllUsersDutyNotiNO_PACKET();
					packet2.setVersion(requestPacket.getVersion());
					packet2.setGroupid(groupid);
					packet2.setDnid(dnid);
					packet2.setTime(time);
					packet2.setCorpname(corpname);
					packet2.setMyduty(myduty);
					
					ctx.writeAndFlush(packet2);
				}
				
			}
		});

	}
}
