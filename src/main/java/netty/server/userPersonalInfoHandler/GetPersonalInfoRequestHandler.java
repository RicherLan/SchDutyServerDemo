package netty.server.userPersonalInfoHandler;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetPersonalInfoRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetPersonalInfoResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.PersonalInfo;
import threadUtil.FixedThreadPool;

/*
 * 	获得个人信息
 */
@ChannelHandler.Sharable
public class GetPersonalInfoRequestHandler extends SimpleChannelInboundHandler<GetPersonalInfoRequestPacket> {

	public static final GetPersonalInfoRequestHandler INSTANCE = new GetPersonalInfoRequestHandler();

	protected GetPersonalInfoRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetPersonalInfoRequestPacket requestPacket)
			throws Exception {
		System.out.println("GetPersonalInfoRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
		
				String phonenumber = session.getUserId();
				PersonalInfo personalInfo = UserDBService.getPersonalInfo(phonenumber);
				
				GetPersonalInfoResponsePacket responsePacket = new GetPersonalInfoResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPersonalInfo(personalInfo);
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
		
	}

}
