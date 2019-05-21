package netty.server.dongtaiHandler;

import java.awt.image.RescaleOp;

import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.dongtaiRequest.GetDongtaiUICByDTID_RequestPacket;
import netty.protocol.response.dongtaiResponse.GetDongtaiUICByDTIDResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	请求某一条动态  获得动态主人头像
 */
@ChannelHandler.Sharable
public class GetDongtaiUICByDTID_RequestHandler extends SimpleChannelInboundHandler<GetDongtaiUICByDTID_RequestPacket>{
	public static final GetDongtaiUICByDTID_RequestHandler INSTANCE = new GetDongtaiUICByDTID_RequestHandler();

	protected GetDongtaiUICByDTID_RequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetDongtaiUICByDTID_RequestPacket requestPacket) throws Exception {
		System.out.println("GetDongtaiUICByDTID_RequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				// 发送动态主人的头像
				//String jString2 = "{\"id\":\"" + dongtai.getId() + "\"}";
				String ph = requestPacket.getPh();
				int dtid = requestPacket.getDtid();
				byte[] ic = UserDBService.getIconByPh(ph);

				//sendFileMsgToclient(ph, "getDtByDTIDIcRs" + " " + jString2, bs4);
				GetDongtaiUICByDTIDResponsePacket responsePacket = new GetDongtaiUICByDTIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				
				responsePacket.setDtid(dtid);
				responsePacket.setUic(ic);
				 
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
