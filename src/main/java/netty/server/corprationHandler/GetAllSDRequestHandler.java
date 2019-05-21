package netty.server.corprationHandler;

import java.util.Vector;

import db.CorpDBService;
import db.SchdutyDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetAllSDRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetAllSDResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import schedulearrangement.ClientArrangement;
import threadUtil.FixedThreadPool;

/*	
 * 	获得自己加入的所有组织的值班表
 */
@ChannelHandler.Sharable
public class GetAllSDRequestHandler extends SimpleChannelInboundHandler<GetAllSDRequestPacket> {
	public static final GetAllSDRequestHandler INSTANCE = new GetAllSDRequestHandler();

	protected GetAllSDRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetAllSDRequestPacket requestPacket) throws Exception {
		System.out.println("GetAllSDRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				Vector<Integer> ids = CorpDBService.getAllCorpGroupid(ph);
				if (ids == null || ids.size() == 0) {
//					sendMsgToclient(ph, "getAllSDRs nocorp", "");
					GetAllSDResponsePacket responsePacket = new GetAllSDResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setGroupid(-1);
					responsePacket.setClientArrangements(null);
					
					ctx.writeAndFlush(responsePacket);
					
					return;
				}
				
				for (int i = 0; i < ids.size(); ++i) {
					int groupid = ids.get(i);
					Vector<ClientArrangement> clientArrangements = SchdutyDBService.getDutyTableBygroupid(groupid);
//
//					JSONArray jsonArray = JSONArray.fromObject(clientArrangements);
//					//
//					sendMsgToclient(ph, "getAllSDRs " + groupid, jsonArray.toString());

					GetAllSDResponsePacket responsePacket = new GetAllSDResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setGroupid(groupid);
					responsePacket.setClientArrangements(clientArrangements);
					
					ctx.writeAndFlush(responsePacket);
					
				}
				
			}
		});

	}
}
