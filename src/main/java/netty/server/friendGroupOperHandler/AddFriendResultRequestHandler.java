package netty.server.friendGroupOperHandler;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.AddFriendResultRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	添加好友时   对方同意或拒绝了你的请求   你已经读到了对方同意还是拒绝   给服务器回执
 */
@ChannelHandler.Sharable
public class AddFriendResultRequestHandler extends SimpleChannelInboundHandler<AddFriendResultRequestPacket>{

    public static final AddFriendResultRequestHandler INSTANCE = new AddFriendResultRequestHandler();

    protected AddFriendResultRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddFriendResultRequestPacket requestPacket) throws Exception {
		System.out.println("AddFriendResultRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int msgid = requestPacket.getMsgid();
				FriengOrGroupRequestDBService.changeRequestFriendOrGroupState(msgid, "已读");
				
			}
		});
		
	}
}
