package netty.server.friendGroupOperHandler;

import db.FriengOrGroupRequestDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.FDeleteMe_Packet;
import netty.protocol.request.otherRequest.DeleteFriendRequestPacket;
import netty.protocol.response.otherResponse.DeleteFriendResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	删除自己的某一个好友
 */
@ChannelHandler.Sharable
public class DeleteFriendRequestHandler extends SimpleChannelInboundHandler<DeleteFriendRequestPacket> {
	public static final DeleteFriendRequestHandler INSTANCE = new DeleteFriendRequestHandler();

	protected DeleteFriendRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DeleteFriendRequestPacket requestPacket) throws Exception {
		System.out.println("DeleteFriendRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				Session session = SessionUtil.getSession(ctx.channel());
				String myphonenumber = session.getUserId();

				String friendphonenumber = requestPacket.getFriendphonenumber();

				String rs = FriengOrGroupRequestDBService.deleteFriend(myphonenumber, friendphonenumber);
				if (rs.equals("ok")) {
					// String jsonstr = "{\"type\":\"ok\",\"ph\":\"" + friendphonenumber + "\"}";

					// 数据库操作成功后 把删除这个请求保存到数据库的requestfriendorgroup
					// 为什么要保存的理由就是 删除是个重要的事 要保证上方都要删除 被删除的一方要收到删除的消息 然后回执确认

					// String type = "deletefriend";
					long begintime = System.currentTimeMillis();
					String addmsg = "";

					int id = FriengOrGroupRequestDBService.requestFriendOrGroup(myphonenumber, "yourfrienddeleteyou",
							friendphonenumber, begintime, addmsg, "未读", -1);

					// 对方在线的话 立即发送过去

					if (id != -1) {

						DeleteFriendResponsePacket responsePacket = new DeleteFriendResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());

						responsePacket.setResult("ok");
						responsePacket.setPh(friendphonenumber);
						ctx.writeAndFlush(responsePacket);

						Channel channel = SessionUtil.getChannel(friendphonenumber);
						if (SessionUtil.hasLogin(channel)) {
							// String jsonstr2 = "{\"msgid\":\"" + id + "\",\"phonenumber\":\"" +
							// myphonenumber + "\"}";

							FDeleteMe_Packet packet = new FDeleteMe_Packet();
							packet.setVersion(requestPacket.getVersion());
							packet.setMsgid(id);
							packet.setPhonenumber(myphonenumber);
							channel.writeAndFlush(packet);
						}

					} else {
						DeleteFriendResponsePacket responsePacket = new DeleteFriendResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());

						responsePacket.setResult("error");
						responsePacket.setPh(friendphonenumber);
						ctx.writeAndFlush(responsePacket);
					}

				} else {
					DeleteFriendResponsePacket responsePacket = new DeleteFriendResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());

					responsePacket.setResult("error");
					responsePacket.setPh(friendphonenumber);
					ctx.writeAndFlush(responsePacket);
				}

			}
		});
	}

}
