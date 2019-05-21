package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddMeAsFriendRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddMeAsFriendResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	添加好友  被添加方要获得对方的头像
 */
@ChannelHandler.Sharable
public class GetUserIcOfAddMeAsFriendRequestHandler
		extends SimpleChannelInboundHandler<GetUserIcOfAddMeAsFriendRequestPacket> {
	public static final GetUserIcOfAddMeAsFriendRequestHandler INSTANCE = new GetUserIcOfAddMeAsFriendRequestHandler();

	protected GetUserIcOfAddMeAsFriendRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUserIcOfAddMeAsFriendRequestPacket requestPacket) throws Exception {
		
		System.out.println("GetUserIcOfAddMeAsFriendRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String ph = requestPacket.getPh();
				byte[] ic = UserDBService.getIconByPh(ph);
				GetUserIcOfAddMeAsFriendResponsePacket responsePacket = new GetUserIcOfAddMeAsFriendResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIcon(ic);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
	}
}
