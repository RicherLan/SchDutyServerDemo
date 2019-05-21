package netty.server.friendGroupOperHandler;

import java.awt.event.MouseWheelEvent;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.ReciveFDeleteMeRequestPAcket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	好友删除自己时   自己收到被删除的消息   要回执
 */
@ChannelHandler.Sharable
public class ReciveFDeleteMeRequestHandler extends SimpleChannelInboundHandler<ReciveFDeleteMeRequestPAcket> {
	public static final ReciveFDeleteMeRequestHandler INSTANCE = new ReciveFDeleteMeRequestHandler();

	protected ReciveFDeleteMeRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReciveFDeleteMeRequestPAcket requestPAcket) throws Exception {
		System.out.println("ReciveFDeleteMeRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int msgid = requestPAcket.getMsgid();
				FriengOrGroupRequestDBService.changeDeleteFriendMsgState(msgid, "已读");
				
			}
		});
		
	}
}
