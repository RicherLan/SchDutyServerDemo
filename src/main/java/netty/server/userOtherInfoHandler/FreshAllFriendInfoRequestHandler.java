package netty.server.userOtherInfoHandler;

import java.util.Vector;

import db.FriendDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.FreshAllFriendInfoRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.FreshAllFriendInfoResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.MyFriendEasy;
import threadUtil.FixedThreadPool;

/*
 * 	获得自己的所有好友信息    一般是客户端刷新好友列表时用到
 */
@ChannelHandler.Sharable
public class FreshAllFriendInfoRequestHandler extends SimpleChannelInboundHandler<FreshAllFriendInfoRequestPacket>{

    public static final FreshAllFriendInfoRequestHandler INSTANCE = new FreshAllFriendInfoRequestHandler();

    protected FreshAllFriendInfoRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FreshAllFriendInfoRequestPacket requestPacket) throws Exception {
		System.out.println("FreshAllFriendInfoRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				Vector<MyFriendEasy> users = FriendDBService.getAllFriendInfo(phonenumber);
				
				FreshAllFriendInfoResponsePacket responsePacket = new FreshAllFriendInfoResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setUsers(users);
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
	}
}
