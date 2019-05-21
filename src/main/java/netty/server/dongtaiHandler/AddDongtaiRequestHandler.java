package netty.server.dongtaiHandler;

import db.DongtaiDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.AddDongtaiRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.AddDongtaiResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;

/*
 * 发表动态    普通用户的话  类型用user表示
 */
@ChannelHandler.Sharable
public class AddDongtaiRequestHandler extends SimpleChannelInboundHandler<AddDongtaiRequestPacket> {
	public static final AddDongtaiRequestHandler INSTANCE = new AddDongtaiRequestHandler();

	protected AddDongtaiRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddDongtaiRequestPacket requestPacket) throws Exception {
		System.out.println("AddDongtaiRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
			
				Session session = SessionUtil.getSession(ctx.channel());
				// String usertype = "user";
				String usertype = requestPacket.getUsertype();
				String userid = session.getUserId();
				String text = requestPacket.getText();
				int picturenum = requestPacket.getPicturenum();
				long time = requestPacket.getTime();

				int id = DongtaiDBService.addDongtai(usertype, userid, text, picturenum, time);
				AddDongtaiResponsePacket responsePacket = new AddDongtaiResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setId(id);
				if (id != -1) {

//					String json = "{\"type\":\"ok\",\"id\":\"" + id + "\"}";
//					sendMsgToclient(userid, "addDongtaiResult", json);
					responsePacket.setResult("ok");
				} else {
//					String json = "{\"type\":\"error\",\"id\":\"" + id + "\"}";
//					sendMsgToclient(userid, "addDongtaiResult", json);
					responsePacket.setResult("error");
				}
				ctx.writeAndFlush(responsePacket);
			}
		});
	}
}
