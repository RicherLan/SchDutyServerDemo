package netty.server.dongtaiHandler;

import db.FileDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.dongtaiRequest.GetUDtICByDTICIDRequestPacket;
import netty.protocol.response.dongtaiResponse.GetUDtICByDTICIDResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	进入用户资料界面  请求某一条动态    获得图片
 */
@ChannelHandler.Sharable
public class GetUDtICByDTICID_RequestHandler extends SimpleChannelInboundHandler<GetUDtICByDTICIDRequestPacket> {
	public static final GetUDtICByDTICID_RequestHandler INSTANCE = new GetUDtICByDTICID_RequestHandler();

	protected GetUDtICByDTICID_RequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUDtICByDTICIDRequestPacket requestPacket) throws Exception {
		System.out.println("GetUDtICByDTICID_RequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				int fileid = requestPacket.getFileid();
				int dtid = requestPacket.getDtid();
				String ph = requestPacket.getPh();
				byte[] bs3 = FileDBService.getFileMsgByFileid(fileid);
//				String jString = "{\"id\":\"" + dongtai.getId() + "\",\"ph\":\"" + dongtai.getSdid() + "\"}";
//				sendFileMsgToclient(ph, "gUsDtByDTIDImRs" + " " + jString, bs3);
				GetUDtICByDTICIDResponsePacket responsePacket = new GetUDtICByDTICIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				
				responsePacket.setFileid(fileid);
				responsePacket.setDtid(dtid);
				responsePacket.setPh(ph);
				responsePacket.setIc(bs3);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
