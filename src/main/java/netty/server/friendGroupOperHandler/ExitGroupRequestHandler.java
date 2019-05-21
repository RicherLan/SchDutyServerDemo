package netty.server.friendGroupOperHandler;

import java.util.Vector;

import db.FriengOrGroupRequestDBService;
import db.GroupDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.SendExitGroupToAdmi_PACKET;
import netty.protocol.request.otherRequest.ExitGroupRequestPacket;
import netty.protocol.response.otherResponse.ExitGroupResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import object.GroupBasicInfo;
import threadUtil.FixedThreadPool;

/*
 * 退群
 * // 退群 同时若该群是社团的官方群的话正常处理 退官方群不是等于退社团 退社团牵扯较大 这个单独处理
// 给群的管理员通知 : 在requestfriendorgroup添加记录即可
// 普通群成员不用通知 没必要刷新群列表 因为我看qq也是这样的 点击群成员列表时 每次都请求服务器获得2
 */
@ChannelHandler.Sharable
public class ExitGroupRequestHandler extends SimpleChannelInboundHandler<ExitGroupRequestPacket> {
	public static final ExitGroupRequestHandler INSTANCE = new ExitGroupRequestHandler();

	protected ExitGroupRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ExitGroupRequestPacket requestPacket) throws Exception {
		System.out.println("ExitGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String myphonenumber = session.getUserId();
				int groupid = requestPacket.getGroupid();
				long begintime = System.currentTimeMillis();
				GroupBasicInfo groupBasicInfo = GroupDBService.getGroupInfoByGroupId(groupid);
				String groupnickname = GroupDBService.getGroupNameByph(myphonenumber, groupid);
				// 数据库中退群 修改usertogroup
				String rString = FriengOrGroupRequestDBService.exitGroup(myphonenumber, groupid);
				if (rString.equals("ok")&&groupBasicInfo!=null) {
					//String jsonstr = "{\"rs\":\"ok\",\"gid\":\"" + groupid + "\"}";
					
					ExitGroupResponsePacket responsePacket = new ExitGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setGroupid(groupid);
					
					ctx.writeAndFlush(responsePacket);
					
					// 获得该群的所有管理员包括群主
					Vector<String> phonenumbers = GroupDBService.getGroupAdministratorsAndBuzhang(groupid);

					for (int i = 0; i < phonenumbers.size(); ++i) {
						String phonenumber = phonenumbers.get(i);
						if (phonenumber.equals(myphonenumber)) {
							continue;
						}
						// 保存到数据库     msg属性填写群名称
						int id = FriengOrGroupRequestDBService.requestFriendOrGroup(myphonenumber+" "+groupnickname, "exitgroup",
								phonenumber, begintime, groupBasicInfo.getGroupname(), "未读", groupid);
						if (id != -1) {
							// 在线的话直接发送 收到的话要回执 回执在另一块处理
							Channel channel = SessionUtil.getChannel(phonenumber);
							if(SessionUtil.hasLogin(channel)) {
								SendExitGroupToAdmi_PACKET packet = new SendExitGroupToAdmi_PACKET();
								packet.setMsgid(id);
								packet.setGroupid(groupid);
								packet.setGroupnickname(groupnickname);
								packet.setPh(myphonenumber);
								packet.setTime(begintime);
								packet.setGroupname(groupBasicInfo.getGroupname());
							
								channel.writeAndFlush(packet);
							}
							
						}

					}

					// 失败
				} else {

					ExitGroupResponsePacket responsePacket = new ExitGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					responsePacket.setGroupid(groupid);
					
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});
		
	}
	
}
