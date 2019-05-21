package netty.server.userOtherInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.FreshAllFriendInfoRequestPacket;
import netty.protocol.request.userOtherInfoRequest.GetUserIcOfPhRequestPacket;
import netty.protocol.response.otherResponse.GetPerIconResponsePacket;
import netty.protocol.response.userOtherInfoResponse.GetUserIcOfPhResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	 获得某用户头像
 */
@ChannelHandler.Sharable
public class GetUserIcOfPhResquestHandler extends SimpleChannelInboundHandler<GetUserIcOfPhRequestPacket>{

    public static final GetUserIcOfPhResquestHandler INSTANCE = new GetUserIcOfPhResquestHandler();

    protected GetUserIcOfPhResquestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUserIcOfPhRequestPacket requestPacket) throws Exception {
		System.out.println("GetUserIcOfPhResquestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				String ph = requestPacket.getPh();
				
				byte[] bs = UserDBService.getIconByPh(ph);
				GetUserIcOfPhResponsePacket responsePacket = new GetUserIcOfPhResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIc(bs);
			
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
