package netty.server.friendGroupOperHandler;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.ReadaddGroupResultRequestPacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	申请加群的用户已经收到管理员的同意还是不同意 现在回执
 */
@ChannelHandler.Sharable
public class ReadAddGroupResultRequestHandler extends SimpleChannelInboundHandler<ReadaddGroupResultRequestPacket> {
	public static final ReadAddGroupResultRequestHandler INSTANCE = new ReadAddGroupResultRequestHandler();

	protected ReadAddGroupResultRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReadaddGroupResultRequestPacket requestPacket)
			throws Exception {
		System.out.println("ReadAddGroupResultRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				int msgid = requestPacket.getMsgid();
				FriengOrGroupRequestDBService.changeRequestFriendOrGroupState(msgid, "已读");

			}
		});

	}
}
