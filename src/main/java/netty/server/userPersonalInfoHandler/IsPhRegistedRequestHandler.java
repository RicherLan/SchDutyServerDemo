package netty.server.userPersonalInfoHandler;

import java.awt.event.MouseWheelEvent;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.IsPhonenumberRegistedRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.IsPhonenumberRegistedResponsePacket;
import netty.protocol.response.otherResponse.LoginResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 用户注册账号时 判断提交的手机号是否已经被注册
 */

@ChannelHandler.Sharable
public class IsPhRegistedRequestHandler extends SimpleChannelInboundHandler<IsPhonenumberRegistedRequestPacket> {
	public static final IsPhRegistedRequestHandler INSTANCE = new IsPhRegistedRequestHandler();

	protected IsPhRegistedRequestHandler() {

	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, IsPhonenumberRegistedRequestPacket requestPacket) {

		System.out.println("IsPhRegistedRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				
				String phonenumber = requestPacket.getPhonenumber();

				String rsString = UserDBService.isPhonenumberRegisted(phonenumber);
				String json = "{\"type\":\"" + rsString + "\"}";
				IsPhonenumberRegistedResponsePacket registedResponsePacket = new IsPhonenumberRegistedResponsePacket();
				registedResponsePacket.setVersion(requestPacket.getVersion());
				registedResponsePacket.setResult(rsString);

				ctx.writeAndFlush(registedResponsePacket);
			}
		});
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		System.out.println("断开");
		SessionUtil.unBindSession(ctx.channel());
	}

}
