package netty.server.userOtherInfoHandler;

import java.util.Vector;

import db.FriendDBService;
import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.friendGroupOperRequest.GetGroupInfoByGidRequestPacket;
import netty.protocol.request.otherRequest.GetFriendIDOnlineRequestPacket;
import netty.protocol.response.friendGroupOperResponse.GetGroupInfoByGidResponsePacket;
import netty.protocol.response.otherResponse.GetFriendIDOnlineResponsePacket;
import netty.protocol.response.otherResponse.GetGroupsInfoOfUserResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import object.UserGroup;
import threadUtil.FixedThreadPool;

/*
 * 	获得用户的某群的信息   一般用在更新某群信息时
 */
@ChannelHandler.Sharable
public class GetGroupInfoByGidRequestHandler extends SimpleChannelInboundHandler<GetGroupInfoByGidRequestPacket>{
	public static final GetGroupInfoByGidRequestHandler INSTANCE = new GetGroupInfoByGidRequestHandler();

	protected GetGroupInfoByGidRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupInfoByGidRequestPacket requestPacket) throws Exception {
		System.out.println("GetGroupInfoByGidRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				int groupid = requestPacket.getGroupid();
				UserGroup userGroup = GroupDBService.getGroupInfoOfGid(phonenumber,groupid);
				
				GetGroupInfoByGidResponsePacket responsePacket = new GetGroupInfoByGidResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setUserGroup(userGroup);
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
	}
}
