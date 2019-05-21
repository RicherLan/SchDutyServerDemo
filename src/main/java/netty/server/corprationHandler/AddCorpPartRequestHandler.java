package netty.server.corprationHandler;

import db.CorpDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.corpRequest.AddCorApPartRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.corpResponse.AddCorApPartResponsePacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	添加社团组织的某一个部门
 */
@ChannelHandler.Sharable
public class AddCorpPartRequestHandler extends SimpleChannelInboundHandler<AddCorApPartRequestPacket>{
	public static final AddCorpPartRequestHandler INSTANCE = new AddCorpPartRequestHandler();

    protected AddCorpPartRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddCorApPartRequestPacket requestPacket) throws Exception {
		System.out.println("AddCorApPartRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				int grouid = requestPacket.getGroupid();
				String name = requestPacket.getName();
				String rString = CorpDBService.addCorpPart(grouid, name);

				//String json = "{\"rs\":\"" + rString + "\",\"gid\":\"" + grouid + "\",\"name\":\"" + name + "\"}";
				//sendMsgToclient(ph, "addCorpPartRs", json);
				
				AddCorApPartResponsePacket responsePacket = new AddCorApPartResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult(rString);
				responsePacket.setGrouid(grouid);
				responsePacket.setName(name);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
