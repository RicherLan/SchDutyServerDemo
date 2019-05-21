package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetUIcOfAgreeYourFriendRequestPacket;
import netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddMeAsFriendRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetUIcOfAgreeYourFriendResponsePacket;
import netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddMeAsFriendResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	对方同意了你的好友请求    获得对方的头像
 */
@ChannelHandler.Sharable
public class GetUIcOfAgreeYourFriendRequestHandler extends SimpleChannelInboundHandler<GetUIcOfAgreeYourFriendRequestPacket> {
	public static final GetUIcOfAgreeYourFriendRequestHandler INSTANCE = new GetUIcOfAgreeYourFriendRequestHandler();

	protected GetUIcOfAgreeYourFriendRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUIcOfAgreeYourFriendRequestPacket requestPacket) throws Exception {
		System.out.println("GetUIcOfAgreeYourFriendRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String ph = requestPacket.getPh();
				byte[] ic = UserDBService.getIconByPh(ph);
				GetUIcOfAgreeYourFriendResponsePacket responsePacket = new GetUIcOfAgreeYourFriendResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIcon(ic);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
