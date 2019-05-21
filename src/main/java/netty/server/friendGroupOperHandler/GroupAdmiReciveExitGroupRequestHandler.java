package netty.server.friendGroupOperHandler;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GroupAdmiReciveExitGroupRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 群成员退群时   群管理员收到退群消息时   回执
 */
@ChannelHandler.Sharable
public class GroupAdmiReciveExitGroupRequestHandler extends SimpleChannelInboundHandler<GroupAdmiReciveExitGroupRequestPacket>{
	public static final GroupAdmiReciveExitGroupRequestHandler INSTANCE = new GroupAdmiReciveExitGroupRequestHandler();

    protected GroupAdmiReciveExitGroupRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GroupAdmiReciveExitGroupRequestPacket requestPacket)
			throws Exception {
		
		System.out.println("GroupAdmiReciveExitGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				int msgid = requestPacket.getMsgid();
				// 修改 该记录为已读
				FriengOrGroupRequestDBService.changeExitGroupMsgState(msgid, "已读");
				
			}
		});
		
	}
}
