package netty.server.chatHandler;

import java.util.Vector;

import db.ChatDBService;
import db.FileDBService;
import db.GroupDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import netty.protocol.packet.GroupChatFile_PACKET;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.SendGroupChatFileRequestPacket;
import netty.protocol.response.otherResponse.SendGroupChatFileResponsePacket;
import netty.protocol.response.otherResponse.SendGroupChatTextResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

@ChannelHandler.Sharable
public class SendGroupChatFileRequestHandler extends SimpleChannelInboundHandler<SendGroupChatFileRequestPacket> {
	public static final SendGroupChatFileRequestHandler INSTANCE = new SendGroupChatFileRequestHandler();

	protected SendGroupChatFileRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SendGroupChatFileRequestPacket requestPacket) throws Exception {
		System.out.println("SendGroupChatFileRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {

				
				int groupid = requestPacket.getGroupid();
				String senderid = requestPacket.getSenderid();
				String sendergroupname = requestPacket.getSendergroupname();
				String message = requestPacket.getMessage();
				String msgtype = requestPacket.getMsgtype();
				long msgtime = System.currentTimeMillis();
				double voicetime = requestPacket.getVoicetime();
				byte[] bytes = requestPacket.getBytes();
				
				int fileid = FileDBService.addFileMsg(bytes);
				if (fileid == -1) {
				//	String json = "{\"rs\":\"error\",\"msgtime\":\"" + msgtime + "\"}";
					
					SendGroupChatFileResponsePacket responsePacket = new SendGroupChatFileResponsePacket();
					
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					responsePacket.setMsgtime(msgtime);
					ctx.writeAndFlush(responsePacket);
					return;
				}
				// 把该消息插入到groupmsgcontent中
				int id = ChatDBService.Addgroupmsgcontent(groupid, senderid, sendergroupname, "", msgtype, msgtime,
						voicetime, fileid);
				// 插入成功
				if (id != -1) {

					//String json = "{\"rs\":\"ok\",\"msgtime\":\"" + msgtime + "\"}";
					SendGroupChatFileResponsePacket responsePacket = new SendGroupChatFileResponsePacket();
					
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setMsgtime(msgtime);
					ctx.writeAndFlush(responsePacket);
					
					// 拿到该群所有人的账号 不包括发送者 给每个人发送该信息
					// 并把消息插入到数据库groupmsgtouser
					Vector<String> groupusers = GroupDBService.getGroupUsersExceptPhonenumber(senderid, groupid);
					ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
					int id2 = -1;
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
						GroupChatFile_PACKET packet = new GroupChatFile_PACKET();
						packet.setVersion(requestPacket.getVersion());
						
						packet.setMsgid(id2);
						packet.setSenderid(senderid);
						packet.setSendergroupname(sendergroupname);
						packet.setGroupid(groupid);
						packet.setMsgtype(msgtype);
						packet.setMsgtime(msgtime);
						packet.setVoicetime(voicetime);
						packet.setBs(bytes);
						
						channelGroup.writeAndFlush(packet);
					}
					

				} else {
					//String json = "{\"rs\":\"error\",\"msgtime\":\"" + msgtime + "\"}";
					SendGroupChatFileResponsePacket responsePacket = new SendGroupChatFileResponsePacket();
					
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					responsePacket.setMsgtime(msgtime);
					ctx.writeAndFlush(responsePacket);				}
				
			}
		});
		
	}
}
