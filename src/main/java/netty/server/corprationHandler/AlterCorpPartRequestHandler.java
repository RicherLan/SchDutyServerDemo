package netty.server.corprationHandler;

import java.util.Vector;

import db.CorpDBService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import net.sf.json.JSONObject;
import netty.protocol.packet.SendUserAlterCorpPart_PACKET;
import netty.protocol.packet.SendUserDeleteCorpPart_PACKET;
import netty.protocol.request.corpRequest.AlterCorpPartRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.corpResponse.AlterCorpPartResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	修改社团组织的某一个部门的名字
 */
@ChannelHandler.Sharable
public class AlterCorpPartRequestHandler extends SimpleChannelInboundHandler<AlterCorpPartRequestPacket>{
	public static final AlterCorpPartRequestHandler INSTANCE = new AlterCorpPartRequestHandler();

    protected AlterCorpPartRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AlterCorpPartRequestPacket requestPacket) throws Exception {
		System.out.println("AlterCorpPartRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				int grouid = requestPacket.getGroupid();
				String oldname = requestPacket.getOldname();
				String newname = requestPacket.getNewname();
				String rString = CorpDBService.alterCorpPart(grouid, oldname, newname);

//				String json = "{\"rs\":\"" + rString + "\",\"gid\":\"" + grouid + "\",\"oldname\":\"" + oldname
//						+ "\",\"newname\":\"" + newname + "\"}";
//				sendMsgToclient(ph, "alterCorpPartRs", json);

				AlterCorpPartResponsePacket responsePacket = new AlterCorpPartResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				
				responsePacket.setResult(rString);
				responsePacket.setGrouid(grouid);
				responsePacket.setOldname(oldname);
				responsePacket.setNewname(newname);
				ctx.writeAndFlush(responsePacket);
				
				
				if (rString.equals("ok")) {
					ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
					
					// 修改部门 还要通知该部室的成员 不进行通知
					Vector<String> phStrings = CorpDBService.getPhOfCorpPart(grouid, oldname, newname);

					if (phStrings != null) {

						for (String userph : phStrings) {
							Channel channel = SessionUtil.getChannel(userph);
							if(SessionUtil.hasLogin(channel)) {
								channelGroup.add(channel);
							}
						}
//						json = "{\"gid\":\"" + grouid + "\",\"oldname\":\"" + oldname + "\",\"newname\":\""
//								+ newname + "\"}";
//						sendMsgToclient(ph, "alterCorpPart", json);
					
						SendUserAlterCorpPart_PACKET packet = new SendUserAlterCorpPart_PACKET();
						packet.setVersion(requestPacket.getVersion());
						
						packet.setGrouid(grouid);
						packet.setOldname(oldname);
						packet.setNewname(newname);
						
						channelGroup.writeAndFlush(packet);
					}
				}
				
			}
		});
		
	}
}
