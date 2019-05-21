package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetFriendListUIcByPhRequestPacket;
import netty.protocol.request.otherRequest.GetPerIconRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetFriendListUIcByPhResponsePacket;
import netty.protocol.response.otherResponse.GetPerIconResponsePacket;
import netty.server.userPersonalInfoHandler.GetPerIconRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	进入好友列表   如果本地没有好友的头像   获取好友的的头像
 */
@ChannelHandler.Sharable
public class GetFriendListUIcByPhRequestHandler  extends SimpleChannelInboundHandler<GetFriendListUIcByPhRequestPacket>{
	public static final GetFriendListUIcByPhRequestHandler INSTANCE = new GetFriendListUIcByPhRequestHandler();

    protected GetFriendListUIcByPhRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetFriendListUIcByPhRequestPacket requestPacket) throws Exception {
		System.out.println("GetFriendListUIcByPhRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String ph = requestPacket.getPh();
				
				byte[] bs = UserDBService.getIconByPh(ph);
				
				GetFriendListUIcByPhResponsePacket responsePacket = new GetFriendListUIcByPhResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIc(bs);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}

}
