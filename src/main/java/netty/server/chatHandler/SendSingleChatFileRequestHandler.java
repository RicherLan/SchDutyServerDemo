package netty.server.chatHandler;

import db.ChatDBService;
import db.FileDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.SingleChatFile_PACKET;
import netty.protocol.request.otherRequest.SendSingleChatFileRequestPacket;
import netty.protocol.response.otherResponse.SendSingleChatFileResponsePacket;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	单人聊天 发送消息   语音 照片
 */
@ChannelHandler.Sharable
public class SendSingleChatFileRequestHandler extends SimpleChannelInboundHandler<SendSingleChatFileRequestPacket> {
	public static final SendSingleChatFileRequestHandler INSTANCE = new SendSingleChatFileRequestHandler();

	protected SendSingleChatFileRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SendSingleChatFileRequestPacket requestPacket) throws Exception {
		System.out.println("SendSingleChatFileRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				String senderid = requestPacket.getSenderid();
				String reciverid = requestPacket.getReciverid();
				String msgtype = requestPacket.getMsgtype();

				long msgtime = System.currentTimeMillis();
				String msgstate = "未读";
				double voicetime = requestPacket.getVoicetime();
				byte[] bs = requestPacket.getBytes();
				int fileid = FileDBService.addFileMsg(bs);

				if (fileid == -1) {
					//String json = "{\"rs\":\"error\",\"msgtime\":\"" + msgtime + "\"}";
					SendSingleChatFileResponsePacket responsePacket = new SendSingleChatFileResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					responsePacket.setMsgtime(msgtime);
					ctx.writeAndFlush(responsePacket);
					return;
				}

				// 返回消息在数据库中存储的id
				// 若返回-1 那么表示插入失败 消息发送失败
				//
				int id = ChatDBService.Addfriendtofriendmessage(senderid, reciverid, "", msgtype, msgtime, msgstate,
						voicetime, fileid);
				if (id != -1) {
					//String json = "{\"rs\":\"ok\",\"msgtime\":\"" + msgtime + "\"}";
					SendSingleChatFileResponsePacket responsePacket = new SendSingleChatFileResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setMsgtime(msgtime);
					ctx.writeAndFlush(responsePacket);
					
					// 对方在线 那么就把 消息发送出去 先不修改数据库中该消息的状态 发送时要把消息id也发送出去 已便确认接受者是否已经读了
					// 接收方接受该消息成功后 会回执消息 这才修改数据库该消息状态为已读
					// 这个在另一个函数写
					Channel channel = SessionUtil.getChannel(reciverid);
					if(SessionUtil.hasLogin(channel)) {
						SingleChatFile_PACKET packet = new SingleChatFile_PACKET();
						packet.setVersion(requestPacket.getVersion());
						packet.setMsgid(id);
						packet.setSenderid(senderid);
						packet.setMsgtype(msgtype);
						packet.setMsgtime(msgtime);
						packet.setVoicetime(voicetime);
						packet.setBs(bs);
						
						channel.writeAndFlush(packet);
					}
					
				
				} else {
					//String json = "{\"rs\":\"error\",\"msgtime\":\"" + msgtime + "\"}";
					SendSingleChatFileResponsePacket responsePacket = new SendSingleChatFileResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					responsePacket.setMsgtime(msgtime);
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});
		
	}
	
	
	
}
