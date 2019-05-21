package netty.server.chatHandler;

import db.ChatDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.SingleChatText_PACKET;
import netty.protocol.request.otherRequest.SendSingleChatTextRequestPacket;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 		单人聊天 发送消息   字符串
 */
@ChannelHandler.Sharable
public class SendSingleChatTextRequestHandler extends SimpleChannelInboundHandler<SendSingleChatTextRequestPacket> {
	public static final SendSingleChatTextRequestHandler INSTANCE = new SendSingleChatTextRequestHandler();

	protected SendSingleChatTextRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SendSingleChatTextRequestPacket requestPacket)
			throws Exception {
		System.out.println("SendSingleChatTextRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String senderid = requestPacket.getSenderid();
				String reciverid = requestPacket.getReciverid();
				String message = requestPacket.getMessage();
				String msgtype = requestPacket.getMsgtype();
				long msgtime = System.currentTimeMillis();
				
				String msgstate = "未读";

				// 返回消息在数据库中存储的id
				// 若返回-1 那么表示插入失败 消息发送失败
				//
				int id = ChatDBService.Addfriendtofriendmessage(senderid, reciverid, message, msgtype, msgtime,
						msgstate, -1, -1);
				if (id != -1) {

					// 对方在线 那么就把 消息发送出去 先不修改数据库中该消息的状态 发送时要把消息id也发送出去 已便确认接受者是否已经读了
					// 接收方接受该消息成功后 会回执消息 这才修改数据库该消息状态为已读
					// 这个在另一个函数写
					Channel channel = SessionUtil.getChannel(reciverid);
					if(SessionUtil.hasLogin(channel)) {
						SingleChatText_PACKET packet = new SingleChatText_PACKET();
						packet.setVersion(requestPacket.getVersion());
						packet.setMsgid(id);
						packet.setSenderid(senderid);
						packet.setMessage(message);
						packet.setMsgtype(msgtype);
						packet.setMsgtime(msgtime);
						
						channel.writeAndFlush(packet);
					}

				}
				
			}
		});

	}
}
