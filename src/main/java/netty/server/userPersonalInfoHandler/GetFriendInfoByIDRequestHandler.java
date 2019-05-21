package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetFriendInfoByIDRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetFriendInfoByIDResponsePacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 	获得某用户的user表中的基本信息
 */
@ChannelHandler.Sharable
public class GetFriendInfoByIDRequestHandler extends SimpleChannelInboundHandler<GetFriendInfoByIDRequestPacket> {
	public static final GetFriendInfoByIDRequestHandler INSTANCE = new GetFriendInfoByIDRequestHandler();

	protected GetFriendInfoByIDRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetFriendInfoByIDRequestPacket requestPacket)
			throws Exception {
		System.out.println("GetFriendInfoByIDRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				String ph = requestPacket.getPhonenumber();
				User user = UserDBService.getUserInfoByPhonenumber(ph);
				byte[] bs = UserDBService.getIconByPh(ph);

				GetFriendInfoByIDResponsePacket responsePacket = new GetFriendInfoByIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setUser(user);
				responsePacket.setBs(bs);

				ctx.writeAndFlush(responsePacket);
			}
		});

	}
}
