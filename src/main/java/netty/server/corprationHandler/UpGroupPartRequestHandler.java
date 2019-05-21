package netty.server.corprationHandler;

import db.CorpDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpGroupPartRequestPacket;
import netty.protocol.response.otherResponse.UpGroupPartResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	修改自己的部室
 */
@ChannelHandler.Sharable
public class UpGroupPartRequestHandler extends SimpleChannelInboundHandler<UpGroupPartRequestPacket> {
	public static final UpGroupPartRequestHandler INSTANCE = new UpGroupPartRequestHandler();

	protected UpGroupPartRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpGroupPartRequestPacket requestPacket) throws Exception {
		System.out.println("UpGroupPartRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				int groupid = requestPacket.getGroupid();
				String part = requestPacket.getPart();

				String rString = CorpDBService.changeGroupPart(ph, groupid, part);
				if (rString.equals("ok")) {

					//String json = "{\"rs\":\"ok\",\"gid\":\"" + groupid + "\",\"part\":\"" + part + "\"}";
					UpGroupPartResponsePacket responsePacket = new UpGroupPartResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setGroupid(groupid);
					responsePacket.setPart(part);
					
					ctx.writeAndFlush(responsePacket);

				} else {
					//String json = "{\"rs\":\"error\"}";
					UpGroupPartResponsePacket responsePacket = new UpGroupPartResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});

	}
}
