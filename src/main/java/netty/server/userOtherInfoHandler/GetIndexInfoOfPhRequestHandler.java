package netty.server.userOtherInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetGroupsInfoOfUserRequestPacket;
import netty.protocol.request.personInfoRequest.GetIndexInfoOfPhRequestPacket;
import netty.protocol.response.personInfoResponse.GetIndexInfoOfPhResponsePacket;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 	进入某人的个人页面时  获得其基本信息
 */
@ChannelHandler.Sharable
public class GetIndexInfoOfPhRequestHandler extends SimpleChannelInboundHandler<GetIndexInfoOfPhRequestPacket> {
	public static final GetIndexInfoOfPhRequestHandler INSTANCE = new GetIndexInfoOfPhRequestHandler();

	protected GetIndexInfoOfPhRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetIndexInfoOfPhRequestPacket requestPacket) throws Exception {
		System.out.println("GetIndexInfoOfPhRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String ph = requestPacket.getPhonenumber();
				User user = UserDBService.getUserInfoByPhonenumber(ph);
//				JSONObject jsonObject2 = JSONObject.fromObject(user);
//				sendMsgToclient(ph1, "getIndexInfoByPhRs", jsonObject2.toString());
				GetIndexInfoOfPhResponsePacket responsePacket = new GetIndexInfoOfPhResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setUser(user);
				ctx.writeAndFlush(responsePacket);
				
				
			}
		});

	}
}
