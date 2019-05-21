package netty.server.dongtaiHandler;

import db.DongtaiDBService;
import db.FileDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetUDtByDTIDRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetUDtByDTIDResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.Dongtai;
import threadUtil.FixedThreadPool;

/*
 * 进入用户资料界面  请求某一条动态
 */
@ChannelHandler.Sharable
public class GetUDtByDTIDRequestHandler extends SimpleChannelInboundHandler<GetUDtByDTIDRequestPacket>{
    public static final GetUDtByDTIDRequestHandler INSTANCE = new GetUDtByDTIDRequestHandler();

    protected GetUDtByDTIDRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUDtByDTIDRequestPacket requestPacket) throws Exception {
		System.out.println("GetUDtByDTIDRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				int id = requestPacket.getId();

				Dongtai dongtai = DongtaiDBService.getDongtaiByDTID(id, ph);
				if (dongtai == null) {
					return;
				}
//				JSONObject jsonObject2 = JSONObject.fromObject(dongtai);
//				sendMsgToclient(ph, "gUsDtByDTIDRs", jsonObject2.toString());
				GetUDtByDTIDResponsePacket responsePacket = new GetUDtByDTIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setDongtai(dongtai);
				ctx.writeAndFlush(responsePacket);
				
				// //发送动态主人的头像
				// String jString2 = "{\"id\":\""+dongtai.getId()+"\"}";
				// byte[] bs4 = DBService.getIconByPh(dongtai.getSenderid());
				// sendFileMsgToclient(ph,"getDtByDTIDIcRs"+" "+jString2, bs4);

				
				
			}
		});
		
	}
}
