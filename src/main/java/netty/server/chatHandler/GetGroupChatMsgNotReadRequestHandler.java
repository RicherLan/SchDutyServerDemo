package netty.server.chatHandler;

import java.util.Vector;

import org.jsoup.Connection.Request;

import db.ChatDBService;
import db.FileDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.packet.GetGroupChatTextNotRead_PACKET;
import netty.protocol.packet.GroupChatFile_PACKET;
import netty.protocol.request.otherRequest.GetGroupChatMsgNotReadRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.ChatMsg;
import threadUtil.FixedThreadPool;

/*
 * 	拿到群聊天  未读信息     一般是刚登陆的时候
 */
@ChannelHandler.Sharable
public class GetGroupChatMsgNotReadRequestHandler extends SimpleChannelInboundHandler<GetGroupChatMsgNotReadRequestPacket>{

    public static final GetGroupChatMsgNotReadRequestHandler INSTANCE = new GetGroupChatMsgNotReadRequestHandler();

    protected GetGroupChatMsgNotReadRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupChatMsgNotReadRequestPacket requestPacket) throws Exception {
		System.out.println("GetGroupChatMsgNotReadRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {

				Session session = SessionUtil.getSession(ctx.channel());
				String phonenumber = session.getUserId();
				Vector<ChatMsg> singleChatMsgs = ChatDBService.getGroupChatMsgNotRead(phonenumber);

				int msgssize = singleChatMsgs.size();
				//System.out.println(msgssize);
				for (int i = 0; i < msgssize;) {
					int sum = 0;
					Vector<ChatMsg> chatMsgs = new Vector<>();
					// 最多一下发送4条
					for (int j = 0; j < 4; ++j) {

						ChatMsg chatMsg = singleChatMsgs.get(i++);
						if (!chatMsg.getMsgtype().equals("text")) {
							if (chatMsgs.size() != 0) {
								
								GetGroupChatTextNotRead_PACKET packet = new GetGroupChatTextNotRead_PACKET();
								packet.setVersion(requestPacket.getVersion());
								packet.setChatMsgs(chatMsgs);
								
								ctx.writeAndFlush(packet);
							}

							int id = FileDBService.getFileidFromfriendtofriendmessageByMsgid(chatMsg.getMsgid());
							if (id != -1) {
//								String json2 = "{\"msgid\":\"" + chatMsg.getMsgid() + "\",\"senderid\":\""
//										+ chatMsg.getSenderid() + "\",\"sendergroupname\":\"" + chatMsg.getSendername()
//										+ "\",\"groupid\":\"" + chatMsg.getGroupid() + "\",\"msgtype\":\""
//										+ chatMsg.getMsgtype() + "\",\"msgtime\":\"" + chatMsg.getMsgtime()
//										+ "\",\"voicetime\":\"" + chatMsg.getVoicetime() + "\"}";

								byte[] bs3 = FileDBService.getFileMsgByFileid(id);
								GroupChatFile_PACKET packet = new GroupChatFile_PACKET();
								packet.setVersion(requestPacket.getVersion());
								packet.setMsgid(chatMsg.getMsgid());
								packet.setSenderid(chatMsg.getSenderid());
								packet.setSendergroupname(chatMsg.getSendername());
								packet.setGroupid(chatMsg.getGroupid());
								packet.setMsgtype(chatMsg.getMsgtype());
								packet.setMsgtime(chatMsg.getMsgtime());
								packet.setVoicetime(chatMsg.getVoicetime());
								
								ctx.writeAndFlush(packet);
								
								
							}
							break;
						} else {
							chatMsgs.add(chatMsg);
							if (chatMsgs.size() == 4 || i == singleChatMsgs.size()) {
								GetGroupChatTextNotRead_PACKET packet = new GetGroupChatTextNotRead_PACKET();
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
