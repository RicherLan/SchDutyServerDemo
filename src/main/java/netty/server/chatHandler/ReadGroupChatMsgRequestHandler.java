package netty.server.chatHandler;

import db.ChatDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.ReadGroupChatMsgRequestPacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	群聊   接收方接收消息后   回执该消息已读
 */
@ChannelHandler.Sharable
public class ReadGroupChatMsgRequestHandler  extends SimpleChannelInboundHandler<ReadGroupChatMsgRequestPacket>{
	public static final ReadGroupChatMsgRequestHandler INSTANCE = new ReadGroupChatMsgRequestHandler();

    protected ReadGroupChatMsgRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReadGroupChatMsgRequestPacket requestPacket) throws Exception {
		System.out.println("ReadGroupChatMsgRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
			
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				int msgid = requestPacket.getMsgid();
				int groupid = requestPacket.getGroupid();

				ChatDBService.changeGroupMsgToUserMessageState(phonenumber, msgid, groupid);

				
			}
		});
		
	}
    
    
}
