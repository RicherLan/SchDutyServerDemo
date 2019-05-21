package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpPasswordRequestPacket;
import netty.protocol.response.otherResponse.LoginResponsePacket;
import netty.protocol.response.otherResponse.UpPasswordResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	修改密码
 */
@ChannelHandler.Sharable
public class UpPasswordRequestHandler extends SimpleChannelInboundHandler<UpPasswordRequestPacket> {

	public static final UpPasswordRequestHandler INSTANCE = new UpPasswordRequestHandler();

	protected UpPasswordRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpPasswordRequestPacket upPasswordRequestPacket) {
		System.out.println("UpPasswordRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());

				String phonenumber = session.getUserId();
				String oldpassword = upPasswordRequestPacket.getOldpassword();
				String newpassword = upPasswordRequestPacket.getNewpassword();
				String rString = UserDBService.changePassword(phonenumber, oldpassword, newpassword);

				UpPasswordResponsePacket upPasswordResponsePacket = new UpPasswordResponsePacket();
				upPasswordResponsePacket.setVersion(upPasswordRequestPacket.getVersion());
				upPasswordResponsePacket.setResult(rString);
				ctx.writeAndFlush(upPasswordResponsePacket);

			}
		});
	}

}
