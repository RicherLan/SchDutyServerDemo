package netty.server.friendGroupOperHandler;

import java.util.Vector;

import db.FriengOrGroupRequestDBService;
import db.GroupDBService;
import db.UserDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.SendAdmiAddGroup_Packet;
import netty.protocol.request.otherRequest.AddGroupRequestPacket;
import netty.protocol.response.otherResponse.AddGroupResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 	申请加群
 */
@ChannelHandler.Sharable
public class AddGroupRequestHandler extends SimpleChannelInboundHandler<AddGroupRequestPacket> {

    public static final AddGroupRequestHandler INSTANCE = new AddGroupRequestHandler();

    protected AddGroupRequestHandler() {
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddGroupRequestPacket requestPacket) throws Exception {
		System.out.println("AddGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
		
			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());
				String myphonenumber = session.getUserId();
				int groupid = requestPacket.getGroupid();

				// 是不是已经在群中
				boolean rs = GroupDBService.isPhJoinGroup(myphonenumber, groupid);
				if (rs) {
					//String jsonstr = "{\"type\":\"hasjoin\"}";
					
					AddGroupResponsePacket responsePacket = new AddGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("hasjoin");
					responsePacket.setGroupid(groupid);
					ctx.writeAndFlush(responsePacket);
					return;
				}

				String type = "addgroup";
				long begintime = System.currentTimeMillis();
				String addmsg = requestPacket.getAddmsg();

				// Group group = DBService.getGroupInfoByGroupId(groupid);

				User user = UserDBService.getUserInfoByPhonenumber(myphonenumber);
				String icon = null;
				String sex = null;
				String nickname = null;
				if (user != null) {
					icon = user.getIcon();
					sex = user.getSex();
					nickname = user.getNickname();
				}

				//获得群的管理员和部长  主席 群主
				Vector<String> phonenumbers = GroupDBService.getGroupAdministratorsAndBuzhang(groupid);
				if (user == null || phonenumbers == null || phonenumbers.size() == 0) {
					//String jsonstr = "{\"type\":\"error\"}";
					AddGroupResponsePacket responsePacket = new AddGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setGroupid(groupid);
					responsePacket.setResult("error");
					ctx.writeAndFlush(responsePacket);
					return;
				}

				// 服务器已经收到该消息 向客户端回执
				//String jsonstr = "{\"type\":\"ok\",\"groupid\":\"" + groupid + "\"}";
				
				AddGroupResponsePacket responsePacket = new AddGroupResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult("ok");
				responsePacket.setGroupid(groupid);
				ctx.writeAndFlush(responsePacket);
				
				//发给管理员
				for (int i = 0; i < phonenumbers.size(); ++i) {
					String phonenumber = phonenumbers.get(i);
					
					int id = FriengOrGroupRequestDBService.requestFriendOrGroup(myphonenumber, type, phonenumber,
							begintime, addmsg, "未读", groupid);

					if (id != -1 ) {
						
						Channel channel = SessionUtil.getChannel(phonenumber);
						if(SessionUtil.hasLogin(channel)) {
//							String jsonstr2 = "{\"msgid\":\"" + id + "\",\"gid\":\"" + groupid + "\",\"ph\":\""
//									+ myphonenumber + "\",\"icon\":\"" + icon + "\",\"sex\":\"" + sex + "\",\"nickname\":\""
//									+ nickname + "\",\"time\":\"" + begintime + "\",\"msg\":\"" + addmsg + "\"}";
							SendAdmiAddGroup_Packet packet = new SendAdmiAddGroup_Packet();
							packet.setVersion(requestPacket.getVersion());
							packet.setMsgid(id);
							packet.setGroupid(groupid);
							packet.setPh(myphonenumber);
							packet.setSex(sex);
							packet.setNickname(nickname);
							packet.setTime(begintime);
							packet.setAddmsg(addmsg);
							
							channel.writeAndFlush(packet);
							
						}
						
					}
				}
				
			}
		});
		
	}
}
