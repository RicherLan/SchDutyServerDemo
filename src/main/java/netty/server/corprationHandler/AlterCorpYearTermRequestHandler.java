package netty.server.corprationHandler;

import db.CorpDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.AlterCorpYearTermRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.AlterCorpYearTermResponsePacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	社团组织群管理修改学年学期
 */
@ChannelHandler.Sharable
public class AlterCorpYearTermRequestHandler extends SimpleChannelInboundHandler<AlterCorpYearTermRequestPacket> {
	public static final AlterCorpYearTermRequestHandler INSTANCE = new AlterCorpYearTermRequestHandler();

	protected AlterCorpYearTermRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AlterCorpYearTermRequestPacket requestPacket) throws Exception {
		System.out.println("AlterCorpYearTermRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int groupid = requestPacket.getGroupid();
				int year = requestPacket.getYear();
				int xueqi = requestPacket.getXueqi();
				int zhou = requestPacket.getZhou();

				String rString = CorpDBService.alterCorpTerm(groupid, year, xueqi, zhou);
				
				AlterCorpYearTermResponsePacket responsePacket = new AlterCorpYearTermResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				
				if (rString.equals("ok")) {
//					String json = "{\"rs\":\"ok\",\"gid\":\"" + groupid + "\",\"year\":\"" + year + "\",\"xueqi\":\""
//							+ xueqi + "\",\"zhou\":\"" + zhou + "\"}";
//					sendMsgToclient(ph, "alterCorpTermRs", json);
					responsePacket.setResult("ok");
					responsePacket.setGroupid(groupid);
					responsePacket.setYear(year);
					responsePacket.setXueqi(xueqi);
					responsePacket.setZhou(zhou);
					
				} else {
//					String json = "{\"rs\":\"error\",\"gid\":\"" + groupid + "\"}";
//					sendMsgToclient(ph, "alterCorpTermRs", json);
					responsePacket.setResult("error");
					responsePacket.setGroupid(groupid);
				}
				
				ctx.writeAndFlush(responsePacket);
			}
		});

	}
}
