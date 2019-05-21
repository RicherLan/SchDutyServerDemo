package netty.server.friendGroupOperHandler;

import java.awt.event.MouseWheelEvent;
import java.util.Vector;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetGroupAllUserRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetGroupAllUserResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.UserInGroupInfo;
import threadUtil.FixedThreadPool;

/*
 * 	获得某群的所有成员   没有头像
 */
@ChannelHandler.Sharable
public class GetGroupAllUserRequestHandler extends SimpleChannelInboundHandler<GetGroupAllUserRequestPacket> {
	public static final GetGroupAllUserRequestHandler INSTANCE = new GetGroupAllUserRequestHandler();

	protected GetGroupAllUserRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupAllUserRequestPacket requestPacket) throws Exception {
		System.out.println("GetGroupAllUserRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int groupid = requestPacket.getGroupid();

				Vector<UserInGroupInfo> users = GroupDBService.getAllUsersByGroupid(groupid);
				
//				Vector<UserInGroupInfo> temp = new Vector<>();
//				boolean first = true;
//				int sum=0;
//				for(UserInGroupInfo userInGroupInfo : users) {
//					++sum;
//					temp.add(userInGroupInfo);
//					if(sum==30) {
//						
//					}
//				}
				
				GetGroupAllUserResponsePacket responsePacket = new GetGroupAllUserResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setUsers(users);
				responsePacket.setType(requestPacket.getType());
				ctx.writeAndFlush(responsePacket);
				
			}
		});

	}
}
