package netty.server.handler;

import java.util.jar.Manifest;

import org.jsoup.Connection.Request;

import db.UserDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.packet.LoginByOther_PACKET;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.LoginResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    protected LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
    	System.out.println("LoginRequestHandler");
    	FixedThreadPool.threadPool.submit(new Runnable() {
			
    		// 登陆 成功 修改两个地方 在线列表和数据库
			@Override
			public void run() {
				
				LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
		        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
				
				String userId = loginRequestPacket.getUserName();
				
				
				
				String password = loginRequestPacket.getPassword();
				String  type = loginRequestPacket.getType();
				String result = UserDBService.login(userId, password);
				// 成功 那么把它加入在线列表 并修改数据库其在线状态
				if(result.startsWith("ok ")) {
				
					Channel channel = SessionUtil.getChannel(userId);
					//若该账户已经在线  那么  逼迫其下线
					if(SessionUtil.hasLogin(channel)) {
						SessionUtil.unBindSession(channel);
						LoginByOther_PACKET packet = new LoginByOther_PACKET();
						packet.setVersion(loginRequestPacket.getVersion());
						packet.setTime(System.currentTimeMillis());
						packet.setMsg("");
						
						channel.writeAndFlush(packet);
						
					}
					
					String[] strings = result.split(" ");
					String username = strings[1];
 					SessionUtil.bindSession(new Session(userId, username), ctx.channel());
 					result = strings[0];
 					
				}
				
				loginResponsePacket.setRs(result);
				loginResponsePacket.setType(type);
				// 登录响应
		        ctx.writeAndFlush(loginResponsePacket);
			
			}
		});
    }
    	
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    	System.out.println("断开Login");
        SessionUtil.unBindSession(ctx.channel());
    }
    
}
