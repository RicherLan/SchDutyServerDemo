package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.AddFriendRequestPacket;
import netty.protocol.request.otherRequest.GetGroupUserIconByPhRequestPacket;
import netty.protocol.response.otherResponse.GetGroupUserIconByPhResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	获得群成员的头像
 */
@ChannelHandler.Sharable
public class GetGroupUserIconByPhRequestHandler extends SimpleChannelInboundHandler<GetGroupUserIconByPhRequestPacket>{
	public static final GetGroupUserIconByPhRequestHandler INSTANCE = new GetGroupUserIconByPhRequestHandler();

    protected GetGroupUserIconByPhRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupUserIconByPhRequestPacket requestPacket) throws Exception {
		//System.out.println("GetGroupUserIconByPhRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int groupid = requestPacket.getGroupid();
				String userph = requestPacket.getUserph();
				System.out.println("GetGroupUserIconByPhRequestHandler         "+userph);
				byte[] icon = UserDBService.getIconByPh(userph);
				
				GetGroupUserIconByPhResponsePacket responsePacket = new GetGroupUserIconByPhResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setPh(userph);
				responsePacket.setIcon(icon);
				responsePacket.setType(requestPacket.getType());
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
	}
}
