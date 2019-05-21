package netty.server.friendGroupOperHandler;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.AddGroupToGroupListRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.AddGroupToGroupListResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.UserGroup;
import threadUtil.FixedThreadPool;

/*
 * 加群  或  创建群成功后    把该群的信息加入到自己的群列表中
 */
@ChannelHandler.Sharable
public class AddGroupToGroupListRequestHandler extends SimpleChannelInboundHandler<AddGroupToGroupListRequestPacket> {
	public static final AddGroupToGroupListRequestHandler INSTANCE = new AddGroupToGroupListRequestHandler();

	protected AddGroupToGroupListRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddGroupToGroupListRequestPacket requestPacket) throws Exception {
		System.out.println("AddGroupToGroupListRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String phonenumber = session.getUserId();
				int groupid = requestPacket.getGroupid();
				UserGroup userGroup = GroupDBService.getUsergroupInfoByGroupid(phonenumber, groupid);
				
				AddGroupToGroupListResponsePacket responsePacket = new AddGroupToGroupListResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setUserGroup(userGroup);
				
				ctx.writeAndFlush(responsePacket);
			}
		});
	}
}
