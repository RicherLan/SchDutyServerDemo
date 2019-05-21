package netty.server.userOtherInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.GetGroupsInfoOfUserRequestPacket;
import netty.protocol.request.personInfoRequest.GetIndexICOfPhRequestPacket;
import netty.protocol.response.personInfoResponse.GetIndexICOfPhResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	进入某人的个人页面时  获得其头像
 */
public class GetIndexICOfPhRequestHandler extends SimpleChannelInboundHandler<GetIndexICOfPhRequestPacket>{
	public static final GetIndexICOfPhRequestHandler INSTANCE = new GetIndexICOfPhRequestHandler();

	protected GetIndexICOfPhRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetIndexICOfPhRequestPacket requestPacket) throws Exception {
		System.out.println("GetIndexICOfPhRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				String ph = requestPacket.getPhonenumber();
				
				byte[] bs = UserDBService.getIconByPh(ph);
				GetIndexICOfPhResponsePacket responsePacket = new GetIndexICOfPhResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIcon(bs);
				
				ctx.writeAndFlush(responsePacket);
					
			}
		});
		
	}
}
