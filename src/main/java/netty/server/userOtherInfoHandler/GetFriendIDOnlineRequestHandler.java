package netty.server.userOtherInfoHandler;

import java.util.Vector;

import db.FriendDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetFriendIDOnlineRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetFriendIDOnlineResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	获得在线的好友有哪些    就是返回在线的账号就行
 */
@ChannelHandler.Sharable
public class GetFriendIDOnlineRequestHandler extends SimpleChannelInboundHandler<GetFriendIDOnlineRequestPacket> {
	public static final GetFriendIDOnlineRequestHandler INSTANCE = new GetFriendIDOnlineRequestHandler();

	protected GetFriendIDOnlineRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetFriendIDOnlineRequestPacket requestPacket) throws Exception {
		System.out.println("GetFriendIDOnlineRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				Vector<String> phonenumbers = FriendDBService.getFriendOnline(phonenumber);
				
				GetFriendIDOnlineResponsePacket responsePacket = new GetFriendIDOnlineResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				
				responsePacket.setPhonenumbers(phonenumbers);
				ctx.writeAndFlush(requestPacket);
			}
		});
	}
}
