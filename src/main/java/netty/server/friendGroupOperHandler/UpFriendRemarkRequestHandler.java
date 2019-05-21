package netty.server.friendGroupOperHandler;

import db.FriendDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpFriendRemarkRequestPacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	修改好友的备注
 * 	不需要回执
 */
@ChannelHandler.Sharable
public class UpFriendRemarkRequestHandler extends SimpleChannelInboundHandler<UpFriendRemarkRequestPacket> {
	public static final UpFriendRemarkRequestHandler INSTANCE = new UpFriendRemarkRequestHandler();

	protected UpFriendRemarkRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpFriendRemarkRequestPacket requestPacket) throws Exception {
		System.out.println("UpFriendRemarkRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String myph = session.getUserId();
				String friendph = requestPacket.getFriendph();
				String remark = requestPacket.getRemark();
				
				String rString = FriendDBService.changeFriendRemark(myph, friendph, remark);
				
			}
		});

	}

}
