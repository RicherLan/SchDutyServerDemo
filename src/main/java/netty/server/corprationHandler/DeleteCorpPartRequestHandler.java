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
import netty.protocol.packet.SendUserDeleteCorpPart_PACKET;
import netty.protocol.request.corpRequest.DeleteCorpPartRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.corpResponse.DeleteCorpPartResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	删除社团组织的某一个部门
 */
@ChannelHandler.Sharable
public class DeleteCorpPartRequestHandler extends SimpleChannelInboundHandler<DeleteCorpPartRequestPacket>{
	public static final DeleteCorpPartRequestHandler INSTANCE = new DeleteCorpPartRequestHandler();

    protected DeleteCorpPartRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DeleteCorpPartRequestPacket requestPacket) throws Exception {
		System.out.println("DeleteCorpPartRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
			
				int groupid = requestPacket.getGroupid();
				String name = requestPacket.getName();
				String rString = CorpDBService.deleteCorpPart(groupid, name);

				//String json = "{\"rs\":\"" + rString + "\",\"gid\":\"" + grouid + "\",\"name\":\"" + name + "\"}";
				//sendMsgToclient(ph, "deleteCorpPartRs", json);
				DeleteCorpPartResponsePacket responsePacket = new DeleteCorpPartResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult(rString);
				responsePacket.setGroupid(groupid);
				responsePacket.setName(name);
				ctx.writeAndFlush(responsePacket);
				
				
				if (rString.equals("ok")) {
					ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
					
					// 删除部门 还要通知该部室的成员 暂时只把成员的部室设为空 不进行通知
					Vector<String> phStrings = CorpDBService.getPhOfCorpPart(groupid, name, "");

					if (phStrings != null) {

						for (String userph : phStrings) {
							Channel channel = SessionUtil.getChannel(userph);
							if(SessionUtil.hasLogin(channel)) {
								channelGroup.add(channel);
							}
						}
//						json = "{\"gid\":\"" + groupid + "\",\"name\":\"" + name + "\"}";
//						sendMsgToclient(ph, "deleteCorpPart", json);
						SendUserDeleteCorpPart_PACKET packet = new SendUserDeleteCorpPart_PACKET();
						packet.setVersion(requestPacket.getVersion());
						
						packet.setGroupid(groupid);
						packet.setName(name);
						
						channelGroup.writeAndFlush(packet);
					}

					
					
				}
				
			}
		});
		
	}
}
