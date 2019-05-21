package netty.server.friendGroupOperHandler;

import db.GroupDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetGroupICOfSearchAddGroupRequestPacket;
import netty.protocol.request.friendGroupOperRequest.GetUICOfSearchAddUserRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetGroupICOfSearchAddGroupResponsePacket;
import netty.protocol.response.friendGroupOperResponse.GetUICOfSearchAddUserResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	添加群时    首先查询     获得群的头像
 */
@ChannelHandler.Sharable
public class GetGroupICOfSearchAddGroupRequestHandler extends SimpleChannelInboundHandler<GetGroupICOfSearchAddGroupRequestPacket>{
	public static final GetGroupICOfSearchAddGroupRequestHandler INSTANCE = new GetGroupICOfSearchAddGroupRequestHandler();

	protected GetGroupICOfSearchAddGroupRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupICOfSearchAddGroupRequestPacket requestPacket)
			throws Exception {
		
		System.out.println("GetGroupICOfSearchAddGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int groupid = requestPacket.getGroupid();
				byte[] ic = GroupDBService.getGroupIcByGid(groupid);
				GetGroupICOfSearchAddGroupResponsePacket responsePacket = new GetGroupICOfSearchAddGroupResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setIc(ic);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
	}
}
