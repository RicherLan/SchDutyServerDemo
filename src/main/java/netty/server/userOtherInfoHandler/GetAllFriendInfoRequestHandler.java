package netty.server.userOtherInfoHandler;

import java.util.Vector;

import db.FriendDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetAllFriendInfoRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetAllFriendInfoResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.MyFriendEasy;
import threadUtil.FixedThreadPool;

/*
 * 	获得自己的所有好友信息    一般是刚登陆
 */
@ChannelHandler.Sharable
public class GetAllFriendInfoRequestHandler extends SimpleChannelInboundHandler<GetAllFriendInfoRequestPacket>{
	public static final GetAllFriendInfoRequestHandler INSTANCE = new GetAllFriendInfoRequestHandler();

    protected GetAllFriendInfoRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetAllFriendInfoRequestPacket requestPacket) throws Exception {
		System.out.println("GetAllFriendInfoRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				
				Vector<MyFriendEasy> users = FriendDBService.getAllFriendInfo(phonenumber);
				
				GetAllFriendInfoResponsePacket responsePacket = new GetAllFriendInfoResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setUsers(users);
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
	}
    
}
