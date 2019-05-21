package netty.server.chatHandler;

import java.util.Vector;

import db.ChatDBService;
import db.GroupDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import netty.protocol.packet.GroupChatText_PACKET;
import netty.protocol.request.otherRequest.SendGroupChatTextRequestPacket;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;
/*
 * 	 群聊 发送方  发送文字
 */
@ChannelHandler.Sharable
public class SendGroupChatTextRequestHandler extends SimpleChannelInboundHandler<SendGroupChatTextRequestPacket> {

	public static final SendGroupChatTextRequestHandler INSTANCE = new SendGroupChatTextRequestHandler();

	protected SendGroupChatTextRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SendGroupChatTextRequestPacket requestPacket) throws Exception {
		System.out.println("SendGroupChatTextRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
			
				int groupid = requestPacket.getGroupid();
				String senderid = requestPacket.getSenderid();
				String sendergroupname = requestPacket.getSendergroupname();
				String message = requestPacket.getMessage();
				String msgtype = requestPacket.getMsgtype();
				long msgtime = System.currentTimeMillis();

				// 把该消息插入到groupmsgcontent中
				int id = ChatDBService.Addgroupmsgcontent(groupid, senderid, sendergroupname, message, msgtype, msgtime,
						-1, -1);
				// System.out.println(id+" ? 222");
				// 插入成功
				if (id != -1) {

					// 拿到该群所有人的账号 不包括发送者 给每个人发送该信息
					// 并把消息插入到数据库groupmsgtouser
					Vector<String> groupusers = GroupDBService.getGroupUsersExceptPhonenumber(senderid, groupid);
					ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
					int id2=-1;
					for (int i = 0; i < groupusers.size(); ++i) {

						String userid = groupusers.get(i);
						if (senderid.equals(userid)) {
							continue;
						}
						// 插入到数据库
						 id2 = ChatDBService.Addgroupmsgtouser(groupid, id, userid, "未读");
						if (id2 != -1) {
							Channel channel = SessionUtil.getChannel(userid);
							if(SessionUtil.hasLogin(channel)) {
								channelGroup.add(channel);
							}
							
						}
						
					
					}
					if(id2!=-1) {
						GroupChatText_PACKET packet = new GroupChatText_PACKET();
						packet.setVersion(requestPacket.getVersion());
						
						packet.setMsgid(id2);
						packet.setSenderid(senderid);
						packet.setSendergroupname(sendergroupname);
						packet.setGroupid(groupid);
						packet.setMessage(message);
						packet.setMsgtype(msgtype);
						packet.setMsgtime(msgtime);
						channelGroup.writeAndFlush(packet);
					}
				
				}
				
			}
		});
		
	}
	
	
	
}
