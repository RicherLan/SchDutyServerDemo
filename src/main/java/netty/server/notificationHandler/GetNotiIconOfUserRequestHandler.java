package netty.server.notificationHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetUIcOfAgreeYourFriendRequestPacket;
import netty.protocol.request.notificationRequest.GetNotiIconOfUserRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetUIcOfAgreeYourFriendResponsePacket;
import netty.protocol.response.notificationResponse.GetNotiIconOfUserResponsePacket;
import netty.server.friendGroupOperHandler.GetUIcOfAgreeYourFriendRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	获得某用户头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
@ChannelHandler.Sharable
public class GetNotiIconOfUserRequestHandler extends SimpleChannelInboundHandler<GetNotiIconOfUserRequestPacket>{
	public static final GetNotiIconOfUserRequestHandler INSTANCE = new GetNotiIconOfUserRequestHandler();

	protected GetNotiIconOfUserRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetNotiIconOfUserRequestPacket requestPacket) throws Exception {
		System.out.println("GetNotiIconOfUserRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String ph = requestPacket.getPh();
				byte[] ic = UserDBService.getIconByPh(ph);
				GetNotiIconOfUserResponsePacket responsePacket = new GetNotiIconOfUserResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIcon(ic);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
