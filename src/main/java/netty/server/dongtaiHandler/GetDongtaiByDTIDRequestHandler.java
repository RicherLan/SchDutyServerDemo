package netty.server.dongtaiHandler;

import db.DongtaiDBService;
import db.FileDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetDongtaiByDTIDRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetDongtaiByDTIDResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.Dongtai;
import threadUtil.FixedThreadPool;

/*
 * 请求某一条动态
 */
@ChannelHandler.Sharable
public class GetDongtaiByDTIDRequestHandler extends SimpleChannelInboundHandler<GetDongtaiByDTIDRequestPacket>{
	public static final GetDongtaiByDTIDRequestHandler INSTANCE = new GetDongtaiByDTIDRequestHandler();

    protected GetDongtaiByDTIDRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetDongtaiByDTIDRequestPacket requestPacket) throws Exception {
		//System.out.println("GetDongtaiByDTIDRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph = session.getUserId();
				int id = requestPacket.getId();
				System.out.println("GetDongtaiByDTIDRequestHandler          "+id);
				Dongtai dongtai = DongtaiDBService.getDongtaiByDTID(id, ph);
				if (dongtai == null) {
					return;
					
				}
//				JSONObject jsonObject2 = JSONObject.fromObject(dongtai);
//				sendMsgToclient(ph, "getDtByDTIDRs", jsonObject2.toString());
				GetDongtaiByDTIDResponsePacket responsePacket = new GetDongtaiByDTIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setDongtai(dongtai);
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
