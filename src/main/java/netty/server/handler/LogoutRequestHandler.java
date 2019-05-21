package netty.server.handler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.LogoutRequestPacket;
import netty.protocol.response.otherResponse.LogoutResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/**
 * 登出请求
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
       System.out.println("LogoutRequestHandler");
    	FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());
				String userid = session.getUserId();
				UserDBService.loginout(userid);
				
				SessionUtil.unBindSession(ctx.channel());
		        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
		        logoutResponsePacket.setSuccess(true);
		        ctx.writeAndFlush(logoutResponsePacket);
				
			}
		});
    	
    	
    }
}
