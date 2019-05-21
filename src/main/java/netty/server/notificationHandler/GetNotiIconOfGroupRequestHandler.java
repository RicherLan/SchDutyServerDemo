package netty.server.notificationHandler;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetGroupIcByGidRequestPacket;
import netty.protocol.request.notificationRequest.GetNotiIconOfGroupRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetGroupIcByGidResponsePacket;
import netty.protocol.response.notificationResponse.GetNotiIconOfGroupResponsePacket;
import netty.server.friendGroupOperHandler.GetGroupIcByGidRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	获得某群头像    客户端加载消息通知时  若本机没有头像  那么向服务器请求
 */
@ChannelHandler.Sharable
public class GetNotiIconOfGroupRequestHandler extends SimpleChannelInboundHandler<GetNotiIconOfGroupRequestPacket>{
	public static final GetNotiIconOfGroupRequestHandler INSTANCE = new GetNotiIconOfGroupRequestHandler();

    protected GetNotiIconOfGroupRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetNotiIconOfGroupRequestPacket requestPacket) throws Exception {
		System.out.println("GetNotiIconOfGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				int groupid = requestPacket.getGroupid();

				byte[] bs = GroupDBService.getGroupIcByGid(groupid);
				if(bs!=null) {
					GetNotiIconOfGroupResponsePacket responsePacket = new GetNotiIconOfGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setGroupid(groupid);
					responsePacket.setIcon(bs);
					
					ctx.writeAndFlush(responsePacket);
				}
			
				
			}
		});
		
	}
}
