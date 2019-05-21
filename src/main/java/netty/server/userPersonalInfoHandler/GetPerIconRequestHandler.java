package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.GetPerIconRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetPerIconResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	获取自己的的头像
 */
@ChannelHandler.Sharable
public class GetPerIconRequestHandler extends SimpleChannelInboundHandler<GetPerIconRequestPacket>{
	public static final GetPerIconRequestHandler INSTANCE = new GetPerIconRequestHandler();

    protected GetPerIconRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetPerIconRequestPacket requestPacket) throws Exception {
		System.out.println("GetPerIconRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				String icph = requestPacket.getPhonenumber();
				
				byte[] bs = UserDBService.getIconByPh(icph);
				GetPerIconResponsePacket responsePacket = new GetPerIconResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setIcph(icph);
				responsePacket.setBs(bs);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}

}
