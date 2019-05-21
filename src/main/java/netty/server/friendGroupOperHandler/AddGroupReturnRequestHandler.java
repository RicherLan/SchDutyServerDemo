package netty.server.friendGroupOperHandler;

import java.util.Vector;

import db.FriengOrGroupRequestDBService;
import db.GroupDBService;
import db.UserDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import netty.protocol.packet.SendAdmiAddGroupHandlered_PACKET;
import netty.protocol.packet.SendUserAddGroupResult_PACKET;
import netty.protocol.request.otherRequest.AddGroupReturnRequestPacket;
import netty.protocol.response.otherResponse.AddGroupReturnResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import object.GroupBasicInfo;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 	用户申请加群时   管理员同意或不同意   回执
 */

@ChannelHandler.Sharable
public class AddGroupReturnRequestHandler extends SimpleChannelInboundHandler<AddGroupReturnRequestPacket> {
	public static final AddGroupReturnRequestHandler INSTANCE = new AddGroupReturnRequestHandler();

	protected AddGroupReturnRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddGroupReturnRequestPacket requestPacket) throws Exception {
		System.out.println("AddGroupReturnRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				Session session = SessionUtil.getSession(ctx.channel());

				String admiphonenumber = session.getUserId();
				
				int groupid = requestPacket.getGroupid();
				int msgid = requestPacket.getMsgid();
				String rs = requestPacket.getRs();
				String otherphonenumber = requestPacket.getOtherphonenumber();
				
				User user1 = UserDBService.getUserInfoByPhonenumber(admiphonenumber);
				User user2 = UserDBService.getUserInfoByPhonenumber(otherphonenumber);
				
				//失败
				if(user1==null||user2==null){
					AddGroupReturnResponsePacket responsePacket = new AddGroupReturnResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setRs("error");
					responsePacket.setType("error");
					responsePacket.setGroupid(groupid);
					responsePacket.setOtherphonenumber(otherphonenumber);
					ctx.writeAndFlush(responsePacket);
					return;
				}

				//是否处理成功
				String hasHandled = "error";
				long jointime = System.currentTimeMillis();
				// 同意
				if (rs.equals("agree")) {
					
					
					String rsString = GroupDBService.Addusertogroup(otherphonenumber, groupid, jointime, user2.getNickname(),"普通");
					// 失败
					if (!rsString.equals("ok")) {
						// String jsonstr = "{\"type\":\"error\",\"groupid\":\"" + groupid +
						// "\",\"otherphonenumber\":\""
						// + otherphonenumber + "\"}";
						AddGroupReturnResponsePacket responsePacket = new AddGroupReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setRs("error");
						responsePacket.setType("error");
						responsePacket.setGroupid(groupid);
						responsePacket.setOtherphonenumber(otherphonenumber);
						ctx.writeAndFlush(responsePacket);
						return;
					}

					GroupBasicInfo group = GroupDBService.getGroupInfoByGroupId(groupid);
					
					//增加该社团到user表中自己所属的社团  
					if (group != null && group.getGrouptype().equals("社团群")) {

						// 群名称就是社团名称
						String corporationname = group.getGroupname();
						String position = "干事";
						FriengOrGroupRequestDBService.addCorporationGroup(otherphonenumber, corporationname, position);

					}
					
					hasHandled="agree";
					int id = FriengOrGroupRequestDBService.requestFriendOrGroup(admiphonenumber, "agreeAddGroup",
							otherphonenumber, System.currentTimeMillis(), "agree", "未读", groupid);
					
					//if (id != -1) 
					{

						// 已经处理完了 给自己回复ok
//						String jsonstr = "{\"msgid\":\"" + msgid + "\",\"type\":\"agree\",\"groupid\":\"" + groupid
//								+ "\",\"otherphonenumber\":\"" + otherphonenumber + "\",\"othernickname\":\""
//								+ othernickname + "\"}";
						AddGroupReturnResponsePacket responsePacket = new AddGroupReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setMsgid(msgid);
						responsePacket.setRs("ok");
						responsePacket.setType("agree");
						responsePacket.setGroupid(groupid);
						responsePacket.setOtherphonenumber(otherphonenumber);
						responsePacket.setOthernickname(user2.getNickname());
						ctx.writeAndFlush(responsePacket);
						
						//请求加群方如果在线  那么发送信息
						Channel channel = SessionUtil.getChannel(otherphonenumber);
						if (SessionUtil.hasLogin(channel)) {

							// String jsonstr2 = "{\"msgid\":\"" + id +
							// "\",\"type\":\"agree\",\"groupid\":\"" + groupid
							// + "\",\"handlerid\":\"" + admiphonenumber + "\",\"handlernickname\":\""
							// + adminickname + "\",\"icon\":\"" + icon + "\"}";
							SendUserAddGroupResult_PACKET packet = new SendUserAddGroupResult_PACKET();
							packet.setVersion(requestPacket.getVersion());
							packet.setMsgid(msgid);
							packet.setType("agree");
							packet.setGroupid(groupid);
							packet.setHandlerid(admiphonenumber);
							packet.setHandlernickname(user1.getNickname());
							packet.setTime(jointime);
							channel.writeAndFlush(packet);
						}

					}

					// 拒绝
				} else {
					
					int id = FriengOrGroupRequestDBService.requestFriendOrGroup(admiphonenumber, "disagreeAddGroup",
							otherphonenumber, System.currentTimeMillis(), "disagree", "未读", groupid);
					
					if (id != -1) {
						hasHandled = "disagree";
						// 已经处理完了 给自己回复ok
//						String jsonstr = "{\"msgid\":\"" + msgid + "\",\"type\":\"disagree\",\"groupid\":\"" + groupid
//								+ "\",\"otherphonenumber\":\"" + otherphonenumber + "\",\"othernickname\":\""
//								+ othernickname + "\"}";

						AddGroupReturnResponsePacket responsePacket = new AddGroupReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setMsgid(msgid);
						responsePacket.setRs("ok");
						responsePacket.setType("disagree");
						responsePacket.setGroupid(groupid);
						responsePacket.setOtherphonenumber(otherphonenumber);
						responsePacket.setOthernickname(user2.getNickname());
						ctx.writeAndFlush(responsePacket);
						
						Channel channel = SessionUtil.getChannel(otherphonenumber);
						if (SessionUtil.hasLogin(channel)) {
							// String jsonstr2 = "{\"msgid\":\"" + id +
							// "\",\"type\":\"agree\",\"groupid\":\"" + groupid
							// + "\",\"handlerid\":\"" + admiphonenumber + "\",\"handlernickname\":\""
							// + adminickname + "\",\"icon\":\"" + icon + "\"}";
							SendUserAddGroupResult_PACKET packet = new SendUserAddGroupResult_PACKET();
							packet.setVersion(requestPacket.getVersion());
							packet.setMsgid(msgid);
							packet.setType("disagree");
							packet.setGroupid(groupid);
							packet.setHandlerid(admiphonenumber);
							packet.setHandlernickname(user1.getNickname());
							packet.setTime(jointime);
							channel.writeAndFlush(packet);
						}

						// 失败
					} else {

//						String jsonstr = "{\"type\":\"error\",\"groupid\":\"" + groupid + "\",\"otherphonenumber\":\""
//								+ otherphonenumber + "\",\"othernickname\":\"" + othernickname + "\"}";
						AddGroupReturnResponsePacket responsePacket = new AddGroupReturnResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setRs("error");
						responsePacket.setType("error");
						responsePacket.setGroupid(groupid);
						responsePacket.setOtherphonenumber(otherphonenumber);
						responsePacket.setOthernickname(user2.getNickname());
						ctx.writeAndFlush(responsePacket);
						return;
					}

				}

				//自己处理失败
				if(hasHandled.equals("error")) {
					return;
				}
				
				String msg = hasHandled+" "+user1.getPhonenumber()+" "+user1.getNickname();
				// 处理表中该消息状态 修改的是对所有管理员的以及部长主席
				FriengOrGroupRequestDBService.changeAddGroupMsgState(groupid, otherphonenumber,admiphonenumber, "addgroup","someoneHasHandledAddGroup", "未读",msg);
				
				// 告知其他的在线管理员以及部长主席  我已经处理了
				Vector<String> phonenumbers = GroupDBService.getGroupAdministratorsAndBuzhang(groupid);
				ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
				if (phonenumbers != null && phonenumbers.size() != 0) {
					for (int i = 0; i < phonenumbers.size(); ++i) {
						String phonenumber = phonenumbers.get(i);
						if (phonenumber.equals(admiphonenumber)) {
							continue;
						}
						Channel channel = SessionUtil.getChannel(phonenumber);
						if (SessionUtil.hasLogin(channel)) {
							channelGroup.add(channel);
						}

					}
				}
				
				
				// String jsonstr2 = "{\"groupid\":\"" + groupid + "\",\"handlerid\":\"" +
				// admiphonenumber
				// + "\",\"handlernickname\":\"" + adminickname + "\",\"otherphonenumber\":\""
				// + otherphonenumber + "\",\"othernickname\":\"" + othernickname + "\"}";

				SendAdmiAddGroupHandlered_PACKET packet = new SendAdmiAddGroupHandlered_PACKET();
				
				packet.setVersion(requestPacket.getVersion());
				packet.setGroupid(groupid);
				packet.setHandlerid(admiphonenumber);
				packet.setHandlernickname(user1.getNickname());
				packet.setOtherphonenumber(otherphonenumber);
				packet.setOtherphonenumber(otherphonenumber);
				packet.setType(hasHandled);
				
				channelGroup.writeAndFlush(packet);

			}
		});

	}
}
