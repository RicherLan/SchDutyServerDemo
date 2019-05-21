package netty.server.corprationHandler;

import db.CorpDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.CreateCorprationRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.CreateCorprationResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import threadUtil.FixedThreadPool;
import util.MyTools;

/*
 * 	创建社团
 */
@ChannelHandler.Sharable
public class CreateCorprationRequestHandler extends SimpleChannelInboundHandler<CreateCorprationRequestPacket> {
	public static final CreateCorprationRequestHandler INSTANCE = new CreateCorprationRequestHandler();

	protected CreateCorprationRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CreateCorprationRequestPacket requestPacket)
			throws Exception {
		System.out.println("CreateCorprationRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				Session session = SessionUtil.getSession(ctx.channel());

				String phonenumber = session.getUserId();

				String corporationname = requestPacket.getName();
				String corporationtype = requestPacket.getType();
				String corporationinfo = requestPacket.getInfo();

				int year = requestPacket.getYear();
				int xueqi = requestPacket.getXueqi();

				// 社团的所有部门
				String corppart = requestPacket.getCorppart();
				String password = MyTools.getRandomPassword(6);
				String rsString = CorpDBService.createCorporation(password,phonenumber, corporationname, corporationtype,
						corporationinfo, corppart, year, xueqi);

				if (rsString.equals("errdb")) {
					int a = -1;
					// String json = "{\"rs\":\"errdb\",\"count\":\"" + a + "\",\"groupid\":\"" + a
					// + "\"}";

					CreateCorprationResponsePacket responsePacket = new CreateCorprationResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("errdb");
					responsePacket.setCount(a);
					responsePacket.setGroupid(a);

					ctx.writeAndFlush(responsePacket);

				} else {
					String[] strings = rsString.split(" ");
					String count = strings[0];
					String groupid = strings[1];
					// String json = "{\"rs\":\"ok\",\"count\":\"" + Integer.parseInt(count) +
					// "\",\"groupid\":\""
					// + Integer.parseInt(groupid) + "\"}";

					CreateCorprationResponsePacket responsePacket = new CreateCorprationResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setCount(Integer.parseInt(count));
					responsePacket.setPassword(password);
					responsePacket.setGroupid(Integer.parseInt(groupid));

					ctx.writeAndFlush(responsePacket);

				}

			}
		});

	}
}
