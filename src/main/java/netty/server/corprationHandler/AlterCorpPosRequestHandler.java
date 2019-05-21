package netty.server.corprationHandler;

import db.CorpDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.corpRequest.AlterCorpPosRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.corpResponse.AlterCorpPosResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 修改自己在社团的职位
 */
@ChannelHandler.Sharable
public class AlterCorpPosRequestHandler extends SimpleChannelInboundHandler<AlterCorpPosRequestPacket>{
	public static final AlterCorpPosRequestHandler INSTANCE = new AlterCorpPosRequestHandler();

    protected AlterCorpPosRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AlterCorpPosRequestPacket requestPacket) throws Exception {
		System.out.println("AlterCorpPosRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				int groupid = requestPacket.getGroupid();
				String newname = requestPacket.getNewname();
				String rString = CorpDBService.alterCorpPos(ph, groupid, newname);
				
				AlterCorpPosResponsePacket responsePacket = new AlterCorpPosResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				
				if (rString.equals("ok")) {

//					String json = "{\"rs\":\"ok\",\"gid\":\"" + groupid + "\",\"pos\":\"" + newname + "\"}";
//					sendMsgToclient(ph, "alterCorpPosRs", json.toString());
					responsePacket.setResult("ok");
					responsePacket.setGroupid(groupid);
					responsePacket.setPos(newname);
				} else {
//					String json = "{\"rs\":\"error\"}";
//					sendMsgToclient(ph, "alterCorpPosRs", json.toString());
					responsePacket.setResult("error");
				}
				
				ctx.writeAndFlush(responsePacket);
			}
		});
		
	}
}
