package netty.server.friendGroupOperHandler;

import db.GroupDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.UpGroupIconRequestPacket;
import netty.protocol.request.otherRequest.AddFriendRequestPacket;
import netty.protocol.response.friendGroupOperResponse.UpGroupIconResponsePacket;
import netty.protocol.response.otherResponse.UpPersonalIconResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	更改群头像
 */
@ChannelHandler.Sharable
public class UpGroupIconRequestHandler extends SimpleChannelInboundHandler<UpGroupIconRequestPacket>{
	public static final UpGroupIconRequestHandler INSTANCE = new UpGroupIconRequestHandler();

    protected UpGroupIconRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpGroupIconRequestPacket requestPacket) throws Exception {
		System.out.println("UpGroupIconRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int groupid = requestPacket.getGroupid();
				byte[] bs = requestPacket.getIc();
				
				String rs = GroupDBService.changeGroupIconBygid(groupid,bs);
				
				UpGroupIconResponsePacket responsePacket = new UpGroupIconResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setRs(rs);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
