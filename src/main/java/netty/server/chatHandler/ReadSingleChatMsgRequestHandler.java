package netty.server.chatHandler;

import db.ChatDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.ReadSingleChatMsgRequestPacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 		单人聊天    接收方接收消息后   回执该消息已读
 */
@ChannelHandler.Sharable
public class ReadSingleChatMsgRequestHandler extends SimpleChannelInboundHandler<ReadSingleChatMsgRequestPacket>{
	public static final ReadSingleChatMsgRequestHandler INSTANCE = new ReadSingleChatMsgRequestHandler();

    protected ReadSingleChatMsgRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReadSingleChatMsgRequestPacket requestPacket) throws Exception {
		System.out.println("ReadSingleChatMsgRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String senderid = requestPacket.getSenderid();
				String reciverid = session.getUserId();
				int msgid = requestPacket.getMsgid();

				ChatDBService.changeFriendToFriendMessageState(senderid, reciverid, msgid);

				
			}
		});
		
	}
    
    
    
}
