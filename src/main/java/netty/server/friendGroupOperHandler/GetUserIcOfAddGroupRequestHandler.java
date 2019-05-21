package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddGroupRequestPacket;
import netty.protocol.request.friendGroupOperRequest.GetUserIcOfAddMeAsFriendRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddGroupResponsePacket;
import netty.protocol.response.friendGroupOperResponse.GetUserIcOfAddMeAsFriendResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	添加群  群管理员要获得对方的头像
 */
@ChannelHandler.Sharable
public class GetUserIcOfAddGroupRequestHandler extends SimpleChannelInboundHandler<GetUserIcOfAddGroupRequestPacket>{
	public static final GetUserIcOfAddGroupRequestHandler INSTANCE = new GetUserIcOfAddGroupRequestHandler();

	protected GetUserIcOfAddGroupRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUserIcOfAddGroupRequestPacket requestPacket) throws Exception {

		System.out.println("GetUserIcOfAddGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				int groupid = requestPacket.getGroupid();	
				String ph = requestPacket.getPh();
				byte[] ic = UserDBService.getIconByPh(ph);
				GetUserIcOfAddGroupResponsePacket responsePacket = new GetUserIcOfAddGroupResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph);
				responsePacket.setIcon(ic);
				responsePacket.setGroupid(groupid);
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
