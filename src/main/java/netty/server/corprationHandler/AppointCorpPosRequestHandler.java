package netty.server.corprationHandler;

import javax.print.DocFlavor.STRING;

import db.CorpDBService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.corpRequest.AppointCorpPosRequestPacket;
import netty.protocol.request.otherRequest.CorpLoadCourseRsRequestPacket;
import netty.protocol.response.corpResponse.AppointCorpPosResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	社团组织任命职位
 */
public class AppointCorpPosRequestHandler  extends SimpleChannelInboundHandler<AppointCorpPosRequestPacket>{
	public static final AppointCorpPosRequestHandler INSTANCE = new AppointCorpPosRequestHandler();

	protected AppointCorpPosRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AppointCorpPosRequestPacket requestPacket) throws Exception {
		
		System.out.println("AppointCorpPosRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				int groupid = requestPacket.getGroupid();
				String ph = requestPacket.getPh();
				String oldph = requestPacket.getOldph();
				String postype = requestPacket.getPostype();
				
				String rString = CorpDBService.appointCorpPos(groupid,ph,postype,oldph);
				
				AppointCorpPosResponsePacket responsePacket = new AppointCorpPosResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setRs(rString);
				responsePacket.setPostype(postype);
				responsePacket.setPh(ph);
				responsePacket.setOldph(oldph);
				ctx.writeAndFlush(responsePacket);
			}
		});
	}
}
