package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.AddUserToFriendListRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.AddUserToFriendListResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 	客户端添加好友成功后  将好友加入到自己的好友列表  因此需要好友的资料
 */
@ChannelHandler.Sharable
public class AddUserToFriendListRequestHandler extends SimpleChannelInboundHandler<AddUserToFriendListRequestPacket>{
	public static final AddUserToFriendListRequestHandler INSTANCE = new AddUserToFriendListRequestHandler();

    protected AddUserToFriendListRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddUserToFriendListRequestPacket requestPacket) throws Exception {
		System.out.println("AddUserToFriendListRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String myph = session.getUserId();
				String ph = requestPacket.getPhonenumber();
				
				User user = UserDBService.getUserInfoByPhonenumber(ph);
				if (user != null) {
					
					AddUserToFriendListResponsePacket responsePacket = new AddUserToFriendListResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setUser(user);
					
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});
	}
}
