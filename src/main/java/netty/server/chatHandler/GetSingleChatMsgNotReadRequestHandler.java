package netty.server.chatHandler;

import java.util.Vector;

import db.ChatDBService;
import db.FileDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.packet.GetSingleChatTextNotRead_PACKET;
import netty.protocol.packet.SingleChatFile_PACKET;
import netty.protocol.request.otherRequest.GetSingleChatMsgNotReadRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.ChatMsg;
import threadUtil.FixedThreadPool;

/*
 * 	拿到单人聊天  未读信息     一般是刚登陆的时候
 */
@ChannelHandler.Sharable
public class GetSingleChatMsgNotReadRequestHandler
		extends SimpleChannelInboundHandler<GetSingleChatMsgNotReadRequestPacket> {
	public static final GetSingleChatMsgNotReadRequestHandler INSTANCE = new GetSingleChatMsgNotReadRequestHandler();

	protected GetSingleChatMsgNotReadRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetSingleChatMsgNotReadRequestPacket requestPacket)
			throws Exception {
		System.out.println("GetSingleChatMsgNotReadRequestHandler");
		
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				Vector<ChatMsg> singleChatMsgs = ChatDBService.getSingleChatMsgNotRead(phonenumber);
				for (int i = 0; i < singleChatMsgs.size();) {
					int sum = 0;
					Vector<ChatMsg> chatMsgs = new Vector<>();
					// 最多一下发送4条
					for (int j = 0; j < 4; ++j) {

						ChatMsg chatMsg = singleChatMsgs.get(i++);
						if (!chatMsg.getMsgtype().equals("text")) {
							if (chatMsgs.size() != 0) {
								
								GetSingleChatTextNotRead_PACKET packet = new GetSingleChatTextNotRead_PACKET();
								packet.setVersion(requestPacket.getVersion());
								packet.setChatMsgs(chatMsgs);
								ctx.writeAndFlush(packet);
							}

							int id = FileDBService.getFileidFromfriendtofriendmessageByMsgid(chatMsg.getMsgid());
							if (id != -1) {
//								String json2 = "{\"msgid\":\"" + chatMsg.getMsgid() + "\",\"senderid\":\""
//										+ chatMsg.getSenderid() + "\",\"msgtype\":\"" + chatMsg.getMsgtype()
//										+ "\",\"msgtime\":\"" + chatMsg.getMsgtime() + "\",\"voicetime\":\""
//										+ chatMsg.getVoicetime() + "\"}";

								byte[] bs = FileDBService.getFileMsgByFileid(id);
								
								SingleChatFile_PACKET packet = new SingleChatFile_PACKET();
								packet.setVersion(requestPacket.getVersion());
								
								packet.setMsgid(chatMsg.getMsgid());
								packet.setSenderid(chatMsg.getSenderid());
								packet.setMsgtype(chatMsg.getMsgtype());
								packet.setMsgtime(chatMsg.getMsgtime());
								packet.setVoicetime(chatMsg.getVoicetime());
								packet.setBs(bs);
								ctx.writeAndFlush(packet);
							}
							break;
						} else {
							chatMsgs.add(chatMsg);
							if (chatMsgs.size() == 4 || i == singleChatMsgs.size()) {

								GetSingleChatTextNotRead_PACKET packet = new GetSingleChatTextNotRead_PACKET();
								packet.setVersion(requestPacket.getVersion());
								packet.setChatMsgs(chatMsgs);
								ctx.writeAndFlush(packet);
							}
						}

						if (i == singleChatMsgs.size()) {
							break;
						}
					}

				}

			}
		});
	}
}
