package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetUICOfSearchAddUserRequestPacket;
import netty.protocol.request.friendGroupOperRequest.GetUIcOfAgreeYourFriendRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetUICOfSearchAddUserResponsePacket;
import netty.protocol.response.friendGroupOperResponse.GetUIcOfAgreeYourFriendResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	添加好友时    首先查询     获得对方的头像
 */
@ChannelHandler.Sharable
public class GetUICOfSearchAddUserRequestHandler extends SimpleChannelInboundHandler<GetUICOfSearchAddUserRequestPacket>{
	public static final GetUICOfSearchAddUserRequestHandler INSTANCE = new GetUICOfSearchAddUserRequestHandler();

	protected GetUICOfSearchAddUserRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUICOfSearchAddUserRequestPacket requestPacket) throws Exception {
		System.out.println("GetUICOfSearchAddUserRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String ph = requestPacket.getPh();
				byte[] ic = UserDBService.getIconByPh(ph);
				GetUICOfSearchAddUserResponsePacket responsePacket = new GetUICOfSearchAddUserResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIc(ic);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
