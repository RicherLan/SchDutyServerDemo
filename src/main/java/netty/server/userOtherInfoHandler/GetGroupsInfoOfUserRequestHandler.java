package netty.server.userOtherInfoHandler;

import java.util.Vector;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetGroupsInfoOfUserRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetGroupsInfoOfUserResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.UserGroup;
import threadUtil.FixedThreadPool;

/*
 * 	获得某用户的群的基本信息
 */
@ChannelHandler.Sharable
public class GetGroupsInfoOfUserRequestHandler extends SimpleChannelInboundHandler<GetGroupsInfoOfUserRequestPacket> {
	public static final GetGroupsInfoOfUserRequestHandler INSTANCE = new GetGroupsInfoOfUserRequestHandler();

	protected GetGroupsInfoOfUserRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupsInfoOfUserRequestPacket requestPacket) throws Exception {
		System.out.println("GetGroupsInfoOfUserRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
			
				Vector<UserGroup> userGroups = GroupDBService.getGroupsInfoOfUser(phonenumber);
				
				GetGroupsInfoOfUserResponsePacket responsePacket = new GetGroupsInfoOfUserResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setUserGroups(userGroups);
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
	}

}
