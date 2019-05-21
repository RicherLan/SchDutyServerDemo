package netty.server.friendGroupOperHandler;

import db.FriengOrGroupRequestDBService;
import db.UserDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.AgreeYourFriendRequest_PACKET;
import netty.protocol.packet.DisAgreeYourFriendRequest_PACKET;
import netty.protocol.request.otherRequest.AddMeFriendReturnRequestPacket;
import netty.protocol.response.otherResponse.AddMeFriendReturnResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 		用户添加自己为好友   自己给回复  同意还是不同意
 *  添加好友时 被添加的一方同意后 两人成为好友并且修改添加消息的状态为已读
         不同意的话 直接修改信息状态
 */
@ChannelHandler.Sharable
public class AddMeFriendReturnRequestHandler extends SimpleChannelInboundHandler<AddMeFriendReturnRequestPacket>{
	public static final AddMeFriendReturnRequestHandler INSTANCE = new AddMeFriendReturnRequestHandler();

    protected AddMeFriendReturnRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddMeFriendReturnRequestPacket requestPacket) throws Exception {
		System.out.println("AddMeFriendReturnRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String myphonenumber = session.getUserId(); // 被要求添加方
				String mynickname = session.getUserName();
				
				String otherphonenumber = requestPacket.getOtherphonenumber(); // 发送添加请求方
				String othernickname = requestPacket.getOthernickname();
				int msgid = requestPacket.getMsgid();
				String result = requestPacket.getResult();
				// 同意
				if (result.equals("agree")) {

					// 数据库Friends表添加数据 没有处理好友分组
					// 暂且0*******************************************************************
					String rString = FriengOrGroupRequestDBService.addfriend(myphonenumber, mynickname,
							otherphonenumber, othernickname, 0);
					if (rString.equals("ok")) {
						// 修改 该记录为已读
						FriengOrGroupRequestDBService.changeAddFriendMsgState(msgid, "已读");
						// 再次通知发送方 对方已经同意你的好友请求 一样分在线和不在线
						long begintime = System.currentTimeMillis();
						int id = FriengOrGroupRequestDBService.requestFriendOrGroup(myphonenumber, "agreeYourAddFriend",
								otherphonenumber, begintime, "agree", "未读", -1);
						if (id != -1) {
							
							//String jsonstr = "{\"type\":\"ok\",\"otherphonenumber\":\"" + otherphonenumber
//									+ "\",\"msgid\":\"" + msgid + "\",\"rs\":\"agree\"}";
							AddMeFriendReturnResponsePacket responsePacket = new AddMeFriendReturnResponsePacket();
							responsePacket.setVersion(requestPacket.getVersion());
							responsePacket.setType("agree");
							responsePacket.setOtherphonenumber(otherphonenumber);
							responsePacket.setMsgid(msgid);
							responsePacket.setRs("ok");
							ctx.writeAndFlush(responsePacket);
						
							
							// 对方在线的话 立即发送过去 收到的话 会回执消息 此时修改数据库该添加信息的状态
							// 回执在另一个函数写 发送的信息有 信息的id 添加者的账号 头像 性别 昵称 验证信息

							User user = UserDBService.getUserInfoByPhonenumber(myphonenumber);
						
							String sex = user.getSex();
							String nickname = user.getNickname();
							Channel channel = SessionUtil.getChannel(otherphonenumber);
							if(SessionUtil.hasLogin(channel)) {
								AgreeYourFriendRequest_PACKET packet = new AgreeYourFriendRequest_PACKET();
								packet.setVersion(requestPacket.getVersion());
								packet.setMsgid(id);
								packet.setNickname(user.getNickname());
								packet.setSex(user.getSex());
								packet.setPhonenumber(myphonenumber);
								packet.setTime(begintime);
								channel.writeAndFlush(packet);
							}
							
							
							// 同意失败
						} else {

//							String jsonstr = "{\"type\":\"error\",\"otherphonenumber\":\"" + otherphonenumber
//									+ "\",\"msgid\":\"" + msgid + "\"}";
							AddMeFriendReturnResponsePacket responsePacket = new AddMeFriendReturnResponsePacket();
							responsePacket.setVersion(requestPacket.getVersion());
							responsePacket.setType("error");
							responsePacket.setOtherphonenumber(otherphonenumber);
							responsePacket.setMsgid(msgid);
							responsePacket.setRs("agree");
							ctx.writeAndFlush(responsePacket);
						}
						// 同意失败
					} else {
						//String jsonstr = "{\"type\":\"error\",\"otherphonenumber\":\"" + otherphonenumber + "\"}";
						AddMeFriendReturnResponsePacket responsePacket = new AddMeFriendReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setType("error");
						responsePacket.setOtherphonenumber(otherphonenumber);
						responsePacket.setRs("agree");
						ctx.writeAndFlush(responsePacket);
					}

					// 不同意
				} else {

					// 修改 该记录为已读
					FriengOrGroupRequestDBService.changeExitGroupMsgState(msgid, "已读");
					// 再次通知发送方 对方不同意你的好友请求 一样分在线和不在线
					long begintime = System.currentTimeMillis();
					int id = FriengOrGroupRequestDBService.requestFriendOrGroup(otherphonenumber,
							"disagreeYourAddFriend", myphonenumber, begintime, "disagree", "未读", -1);
					if (id != -1) {
						
						//String jsonstr = "{\"type\":\"ok\",\"otherphonenumber\":\"" + otherphonenumber
//								+ "\",\"msgid\":\"" + msgid + "\",\"rs\":\"disagree\"}";
						AddMeFriendReturnResponsePacket responsePacket = new AddMeFriendReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setType("ok");
						responsePacket.setOtherphonenumber(otherphonenumber);
						responsePacket.setMsgid(msgid);
						responsePacket.setRs("disagree");
						ctx.writeAndFlush(responsePacket);
						
						// 对方在线的话 立即发送过去 收到的话 会回执消息 此时修改数据库该添加信息的状态
						// 回执在另一个函数写 发送的信息有 信息的id 添加者的账号 头像 性别 昵称 验证信息

						User user = UserDBService.getUserInfoByPhonenumber(myphonenumber);
						
						String sex = user.getSex();
						String nickname = user.getNickname();
						Channel channel = SessionUtil.getChannel(otherphonenumber);
						if(SessionUtil.hasLogin(channel)) {
							DisAgreeYourFriendRequest_PACKET packet = new DisAgreeYourFriendRequest_PACKET();
							packet.setVersion(requestPacket.getVersion());
							packet.setMsgid(id);
							packet.setNickname(user.getNickname());
							packet.setSex(user.getSex());
							packet.setPhonenumber(myphonenumber);
							packet.setTime(begintime);
							channel.writeAndFlush(packet);
						}
						
						
						
						
//						

					} else {

						//String jsonstr = "{\"type\":\"error\",\"otherphonenumber\":\"" + otherphonenumber + "\"}";
						AddMeFriendReturnResponsePacket responsePacket = new AddMeFriendReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setType("error");
						responsePacket.setOtherphonenumber(otherphonenumber);
						responsePacket.setRs("disagree");
						ctx.writeAndFlush(responsePacket);
					}
				}
				
			}
		});
	}
}
