package netty.server.corprationHandler;

import db.SchdutyDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.ReadDutyNotiRequestPacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 已经读取值班的通知   向服务器反馈
 */
@ChannelHandler.Sharable
public class ReadDutyNotiRequestHandler extends SimpleChannelInboundHandler<ReadDutyNotiRequestPacket> {
	public static final ReadDutyNotiRequestHandler INSTANCE = new ReadDutyNotiRequestHandler();

	protected ReadDutyNotiRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ReadDutyNotiRequestPacket requestPacket) throws Exception {
		System.out.println("ReadDutyNotiRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				
				int dutynotiid = requestPacket.getDnid();
				int groupid = requestPacket.getGid();
				SchdutyDBService.changeDutyNoticeState(ph, groupid, dutynotiid);
				
			}
		});

	}
}
