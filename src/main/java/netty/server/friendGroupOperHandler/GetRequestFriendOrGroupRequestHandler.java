package netty.server.friendGroupOperHandler;

import java.util.Vector;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetRequestFriendOrGroupRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetRequestFriendOrGroupResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.ChatMsg;
import threadUtil.FixedThreadPool;

/*
 * 	拿到所有的好友、群请求信息  一般在刚登陆的时候
 */
@ChannelHandler.Sharable
public class GetRequestFriendOrGroupRequestHandler extends SimpleChannelInboundHandler<GetRequestFriendOrGroupRequestPacket>{
	public static final GetRequestFriendOrGroupRequestHandler INSTANCE = new GetRequestFriendOrGroupRequestHandler();

    protected GetRequestFriendOrGroupRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetRequestFriendOrGroupRequestPacket requestPacket)
			throws Exception {
		System.out.println("GetRequestFriendOrGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				Vector<ChatMsg> requestFriendOrGroups = FriengOrGroupRequestDBService
						.getRequestFriendOrGroup(phonenumber);
				
			
				GetRequestFriendOrGroupResponsePacket responsePacket = new GetRequestFriendOrGroupResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setRequestFriendOrGroups(requestFriendOrGroups);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
