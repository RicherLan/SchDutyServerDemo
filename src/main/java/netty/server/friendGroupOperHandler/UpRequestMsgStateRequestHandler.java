package netty.server.friendGroupOperHandler;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpRequestMsgStateRequestPacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 		好友或群请求   收到消息后   更改消息的读取状态
 */
@ChannelHandler.Sharable
public class UpRequestMsgStateRequestHandler extends SimpleChannelInboundHandler<UpRequestMsgStateRequestPacket> {
	public static final UpRequestMsgStateRequestHandler INSTANCE = new UpRequestMsgStateRequestHandler();

    protected UpRequestMsgStateRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpRequestMsgStateRequestPacket requestPacket) throws Exception {
		System.out.println("UpRequestMsgStateRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int msgid = requestPacket.getMsgid();
				FriengOrGroupRequestDBService.changeRequestMsgState(msgid, "已读");

			}
		});
		
	}
}
