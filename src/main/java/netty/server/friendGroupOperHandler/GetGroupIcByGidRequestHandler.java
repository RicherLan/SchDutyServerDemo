package netty.server.friendGroupOperHandler;

import db.GroupDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetGroupIcByGidRequestPacket;
import netty.protocol.request.otherRequest.AddFriendRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetGroupIcByGidResponsePacket;
import netty.protocol.response.otherResponse.GetPerIconResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	获得某群头像
 */
@ChannelHandler.Sharable
public class GetGroupIcByGidRequestHandler  extends SimpleChannelInboundHandler<GetGroupIcByGidRequestPacket>{
	public static final GetGroupIcByGidRequestHandler INSTANCE = new GetGroupIcByGidRequestHandler();

    protected GetGroupIcByGidRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupIcByGidRequestPacket requestPacket) throws Exception {
		System.out.println("GetGroupIcByGidRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				int groupid = requestPacket.getGroupid();

				byte[] bs = GroupDBService.getGroupIcByGid(groupid);
				if(bs!=null) {
					GetGroupIcByGidResponsePacket responsePacket = new GetGroupIcByGidResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setGroupid(groupid);
					responsePacket.setIcon(bs);
					
					ctx.writeAndFlush(responsePacket);
				}
			
				
			}
		});
		
	}
}
