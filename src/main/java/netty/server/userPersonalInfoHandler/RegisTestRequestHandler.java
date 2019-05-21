package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.RegisterTestRequestPacket;
import netty.protocol.response.otherResponse.RegisterTestResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	// 测试阶段的用户注册
 */

@ChannelHandler.Sharable
public class RegisTestRequestHandler extends SimpleChannelInboundHandler<RegisterTestRequestPacket> {
	public static final RegisTestRequestHandler INSTANCE = new RegisTestRequestHandler();

	protected RegisTestRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RegisterTestRequestPacket requestPacket) throws Exception {
		System.out.println("RegisTestRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				String schoolname = requestPacket.getSchoolname(); // 学校名字
				String departmentname = requestPacket.getCollegename(); // 学院名字
				String majorname = requestPacket.getMajorname(); // 所修专业

				String phonenumber = requestPacket.getPhonenumber(); // 要注册的手机号
				String password = requestPacket.getPassword(); // 密码
				int ruxueyear = requestPacket.getRuxueyear();

				String rString = UserDBService.regisUsertest(schoolname, departmentname, majorname, ruxueyear,
						phonenumber, password);

				RegisterTestResponsePacket responsePacket = new RegisterTestResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult(rString);

				ctx.writeAndFlush(responsePacket);

			}
		});
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		System.out.println("断开Register");
		SessionUtil.unBindSession(ctx.channel());
	}

}
