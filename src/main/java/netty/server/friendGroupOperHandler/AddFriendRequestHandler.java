package netty.server.friendGroupOperHandler;

import db.FriendDBService;
import db.FriengOrGroupRequestDBService;
import db.UserDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.packet.AddMeAsFriend_PACKET;
import netty.protocol.request.otherRequest.AddFriendRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.AddFriendResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 添加好友
 */
@ChannelHandler.Sharable
public class AddFriendRequestHandler extends SimpleChannelInboundHandler<AddFriendRequestPacket>{
	public static final AddFriendRequestHandler INSTANCE = new AddFriendRequestHandler();

    protected AddFriendRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddFriendRequestPacket requestPacket) throws Exception {
		System.out.println("AddFriendRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String myphonenumber = session.getUserId();
				
				String otherid = requestPacket.getFriendid();

				// 是不是已经是好友
				boolean rs = FriendDBService.isFriend(myphonenumber, otherid);
				if (rs) {
					//String jsonstr = "{\"type\":\"hasFriend\",\"FriPh\":\"" + otherid + "\"}";
					
					AddFriendResponsePacket responsePacket = new AddFriendResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("hasFriend");
					responsePacket.setFriendPh(otherid);
					ctx.writeAndFlush(responsePacket);
					return;
				}

				String type = "addfriend";
				long begintime = System.currentTimeMillis();
				String addmsg = requestPacket.getAddmsg();

				int id = FriengOrGroupRequestDBService.requestFriendOrGroup(myphonenumber, type, otherid, begintime,
						addmsg, "未读", -1);
				if (id != -1) {
					//  给自己回复一个ok
					AddFriendResponsePacket responsePacket = new AddFriendResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setFriendPh(otherid);
					ctx.writeAndFlush(responsePacket);
					
					// 对方在线的话 立即发送过去 收到的话 会回执消息 此时修改数据库该添加信息的状态
					// 回执在另一个函数写 发送的信息有 信息的id 添加者的账号 头像 性别 昵称 验证信息

					Channel channel = SessionUtil.getChannel(otherid);;
					if(SessionUtil.hasLogin(channel)) {
						User user = UserDBService.getUserInfoByPhonenumber(myphonenumber);
						
						AddMeAsFriend_PACKET packet = new AddMeAsFriend_PACKET();
						packet.setVersion(requestPacket.getVersion());
						packet.setSex(user.getSex());
						packet.setNickname(user.getNickname());
						packet.setMsgid(id);
						packet.setPhonenumber(myphonenumber);
						packet.setAddmsg(addmsg);
						packet.setTime(begintime);
						
						channel.writeAndFlush(packet);
						
					}
					
					
					// 失败
				} else {

					AddFriendResponsePacket responsePacket = new AddFriendResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					responsePacket.setFriendPh(otherid);
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});
		
	}
}
