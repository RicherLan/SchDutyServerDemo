package netty.server.dongtaiHandler;

import db.FileDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.dongtaiRequest.GetDongtaiICByDTIDRequestPacket;
import netty.protocol.response.dongtaiResponse.GetDongtaiICByDTID_ResponsePacket;
import threadUtil.FixedThreadPool;

/*
 * 	请求某一条动态  获得动态的图片
 */
@ChannelHandler.Sharable
public class GetDongtaiICByDTID_RequestHandler extends SimpleChannelInboundHandler<GetDongtaiICByDTIDRequestPacket> {
	public static final GetDongtaiICByDTID_RequestHandler INSTANCE = new GetDongtaiICByDTID_RequestHandler();

	protected GetDongtaiICByDTID_RequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetDongtaiICByDTIDRequestPacket requestPacket)
			throws Exception {
		System.out.println("GetDongtaiICByDTID_RequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				int fileid = requestPacket.getFileid();
				int dtid = requestPacket.getDtid();
				byte[] bs3 = FileDBService.getFileMsgByFileid(fileid);
				// String jString = "{\"id\":\"" + dongtai.getId() + "\"}";
				// sendFileMsgToclient(ph, "getDtByDTIDImRs" + " " + jString, bs3);
				GetDongtaiICByDTID_ResponsePacket responsePacket = new GetDongtaiICByDTID_ResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());

				responsePacket.setDtid(dtid);
				responsePacket.setIc(bs3);
				responsePacket.setFileid(fileid);
				ctx.writeAndFlush(responsePacket);
			}
		});

	}
}
