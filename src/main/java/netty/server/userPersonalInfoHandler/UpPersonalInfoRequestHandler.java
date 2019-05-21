package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpPeronalInfoRequestPacket;
import netty.protocol.response.otherResponse.UpPeronalInfoReponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 *    修改个人资料
 */
@ChannelHandler.Sharable
public class UpPersonalInfoRequestHandler extends SimpleChannelInboundHandler<UpPeronalInfoRequestPacket> {
	public static final UpPersonalInfoRequestHandler INSTANCE = new UpPersonalInfoRequestHandler();

	protected UpPersonalInfoRequestHandler() {
	   
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpPeronalInfoRequestPacket requestPacket) throws Exception {
		System.out.println("UpPersonalInfoRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				
				String phonenumber = session.getUserId();
				String ni = requestPacket.getNickname();
				String sex = requestPacket.getSex();
				String from = requestPacket.getFrom();
				String add = requestPacket.getAdd();
				String sch = requestPacket.getSch();
				String dep = requestPacket.getDep();
				String maj = requestPacket.getMaj();
				String bir = requestPacket.getBir();
				int rxy = requestPacket.getRxy();
				String info = requestPacket.getInfo();

				String rString = UserDBService.changePersonalInfo(phonenumber, ni, sex, from, add, sch, dep, maj, bir,
						rxy, info);
				
				
				UpPeronalInfoReponsePacket reponsePacket = new UpPeronalInfoReponsePacket();
				reponsePacket.setVersion(requestPacket.getVersion());
				reponsePacket.setResult(rString);
				
				ctx.writeAndFlush(reponsePacket);
				
			}
		});
		
	}

	
	
	
}
