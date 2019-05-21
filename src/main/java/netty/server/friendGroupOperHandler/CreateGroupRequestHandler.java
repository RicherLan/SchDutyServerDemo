package netty.server.friendGroupOperHandler;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.CreateGroupRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.CreateGroupResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	创建群
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket>{
	public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    protected CreateGroupRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) throws Exception {
		System.out.println("CreateGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String phonenumber = session.getUserId();
				String groupname = requestPacket.getGroupname();
				int groupid = GroupDBService.createGroup(phonenumber, groupname);
				if (groupid != -1) {
					//String json = "{\"result\":\"ok\",\"groupid\":\"" + groupid + "\"}";
					
					CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setGroupid(groupid);
					
					ctx.writeAndFlush(responsePacket);
					
				} else {
					//String json = "{\"result\":\"error\"}";
					CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					
					ctx.writeAndFlush(responsePacket);

				}
				
			}
		});
		
	}
    
    
}
