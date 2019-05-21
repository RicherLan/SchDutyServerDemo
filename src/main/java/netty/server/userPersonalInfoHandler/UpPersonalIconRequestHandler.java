package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpPersonalIconRequestPacket;
import netty.protocol.response.otherResponse.UpPersonalIconResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	更改自己的头像
 */
@ChannelHandler.Sharable
public class UpPersonalIconRequestHandler extends SimpleChannelInboundHandler<UpPersonalIconRequestPacket> {

	public static final UpPersonalIconRequestHandler INSTANCE = new UpPersonalIconRequestHandler();

	protected UpPersonalIconRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpPersonalIconRequestPacket requestPacket) throws Exception {
		System.out.println("UpPersonalIconRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				byte[] bs = requestPacket.getBytes();
				String rs = UserDBService.changeIconByPh(ph, bs);
				//String json = "{\"rs\":\"" + rs + "\"}";
				
				UpPersonalIconResponsePacket responsePacket = new UpPersonalIconResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult(rs);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});

	}

}
