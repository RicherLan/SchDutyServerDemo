package netty.server.dongtaiHandler;

import db.DongtaiDBService;
import db.FileDBService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetDTFirstImAndtextByIdRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetDTFirstImAndtextByIdResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.Dongtai;
import threadUtil.FixedThreadPool;

/*
 * 获得动态的第一张图片和内容
 */
public class GetDTFirstImAndtextByIdRequestHandler extends SimpleChannelInboundHandler<GetDTFirstImAndtextByIdRequestPacket>{
	public static final GetDTFirstImAndtextByIdRequestHandler INSTANCE = new GetDTFirstImAndtextByIdRequestHandler();

    protected GetDTFirstImAndtextByIdRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetDTFirstImAndtextByIdRequestPacket requestPacket)
			throws Exception {
		System.out.println("GetDTFirstImAndtextByIdRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				
				int id = requestPacket.getId();
				Dongtai dongtai = DongtaiDBService.getDongtaiByDTID(id, ph);
				int dtid = id;
				String userid = dongtai.getSdid();
				String username = dongtai.getSdname();
				String content = dongtai.getContent();
/*
				String json2 = "{\"type\":\"content\",\"dtid\":\"" + dtid + "\",\"userid\":\"" + userid
						+ "\",\"username\":\"" + username + "\",\"content\":\"" + content + "\"}";
				sendMsgToclient(ph, "getDTFirstImAndtextByIdResult" + " " + json2, "");

				// 发送动态的图片
				if (dongtai.getImph().size() != 0) {
					int fileid = Integer.parseInt(dongtai.getImph().get(0));
					byte[] bs3 = FileDBService.getFileMsgByFileid(fileid);
					String jString = "{\"type\":\"image\",\"dtid\":\"" + dongtai.getId() + "\"}";
					sendFileMsgToclient(ph, "getDTFirstImAndtextByIdResult" + " " + jString, bs3);

				}
				*/
				byte[] bs3 = null;
				if (dongtai.getImph().size() != 0) {
					int fileid = Integer.parseInt(dongtai.getImph().get(0));
					bs3 = FileDBService.getFileMsgByFileid(fileid);
//					String jString = "{\"type\":\"image\",\"dtid\":\"" + dongtai.getId() + "\"}";
//					sendFileMsgToclient(ph, "getDTFirstImAndtextByIdResult" + " " + jString, bs3);

				}
				GetDTFirstImAndtextByIdResponsePacket responsePacket = new GetDTFirstImAndtextByIdResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setDtid(dtid);
				responsePacket.setContent(content);
				responsePacket.setUserid(userid);
				responsePacket.setUsername(username);
				responsePacket.setFirstImage(bs3);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
	}
}
