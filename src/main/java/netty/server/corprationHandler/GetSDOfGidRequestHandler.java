package netty.server.corprationHandler;

import java.util.Vector;

import db.SchdutyDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetSDOfGidRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetSDOfGidResponsePacket;
import netty.server.handler.LoginRequestHandler;
import schedulearrangement.ClientArrangement;
import threadUtil.FixedThreadPool;

/*
 * 	获得自己加入的某个组织的值班表
 */
@ChannelHandler.Sharable
public class GetSDOfGidRequestHandler extends SimpleChannelInboundHandler<GetSDOfGidRequestPacket> {
	public static final GetSDOfGidRequestHandler INSTANCE = new GetSDOfGidRequestHandler();

	protected GetSDOfGidRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetSDOfGidRequestPacket requestPacket) throws Exception {
		System.out.println("GetSDOfGidRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int groupid = requestPacket.getGroupid();
				Vector<ClientArrangement> clientArrangements = SchdutyDBService.getDutyTableBygroupid(groupid);

//				JSONArray jsonArray = JSONArray.fromObject(clientArrangements);
//				String json = "{\"gid\":\"" + groupid + "\"}";
//				
//				sendMsgToclient(ph, "DutySche " + json, jsonArray.toString());
					
				GetSDOfGidResponsePacket responsePacket = new GetSDOfGidResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setDnid(requestPacket.getDnid());
				responsePacket.setGroupid(groupid);
				responsePacket.setClientArrangements(clientArrangements);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});

	}
}
