package netty.server.friendGroupOperHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.SearchAddUserRequestPacket;
import netty.protocol.response.otherResponse.SearchAddUserResponsePacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;
import util.User;

/*
 * 	客户端添加某人好友时   搜索账号 只需要看到其账号 网名 头像就行
 */
@ChannelHandler.Sharable
public class SearchAddUserRequestHandler extends SimpleChannelInboundHandler<SearchAddUserRequestPacket> {
	public static final SearchAddUserRequestHandler INSTANCE = new SearchAddUserRequestHandler();

	protected SearchAddUserRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SearchAddUserRequestPacket requestPacket) throws Exception {
		System.out.println("SearchAddUserRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				String phonenumber = requestPacket.getPhonenumber();

				User user = UserDBService.getUserInfoByPhonenumber(phonenumber);
				if (user != null) {
//					String string = "{\"rs\":\"ok\",\"ph\":\"" + phonenumber + "\",\"nickname\":\"" + user.getNickname()
//							+ "\",\"icon\":\"" + user.getIcon() + "\"}";
					
					SearchAddUserResponsePacket responsePacket = new SearchAddUserResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setPh(phonenumber);
					responsePacket.setNickname(user.getNickname());
					ctx.writeAndFlush(responsePacket);

				}else {
					//String string = "{\"rs\":\"notexist\",\"ph\":\"" + phonenumber + "\"}";
					SearchAddUserResponsePacket responsePacket = new SearchAddUserResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("notexist");
					responsePacket.setPh(phonenumber);
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});

	}
}
