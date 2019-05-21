package netty.server.friendGroupOperHandler;

import java.awt.event.MouseWheelEvent;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.UpGroupRemarkRequestPacket;
import netty.protocol.response.otherResponse.UpGroupRemarkResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 	修改自己的群名片
 */
@ChannelHandler.Sharable
public class UpGroupRemarkRequestHandler extends SimpleChannelInboundHandler<UpGroupRemarkRequestPacket>{
	public static final UpGroupRemarkRequestHandler INSTANCE = new UpGroupRemarkRequestHandler();

    protected UpGroupRemarkRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, UpGroupRemarkRequestPacket requestPacket) throws Exception {
		System.out.println("UpGroupRemarkRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				int groupid = requestPacket.getGroupid();
				String remark = requestPacket.getRemark();
				String rString = GroupDBService.changeGroupRemark(ph, groupid, remark);
				if (rString.equals("ok")) {

					//String json = "{\"rs\":\"ok\",\"gid\":\"" + groupid + "\",\"remark\":\"" + remark + "\"}";
					
					UpGroupRemarkResponsePacket responsePacket = new UpGroupRemarkResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setGroupid(groupid);
					responsePacket.setRemark(remark);
					ctx.writeAndFlush(responsePacket);
					
				} else {
					//String json = "{\"rs\":\"error\"}";
					UpGroupRemarkResponsePacket responsePacket = new UpGroupRemarkResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
			
					ctx.writeAndFlush(responsePacket);

				}
				
			}
		});
		
	}
}
