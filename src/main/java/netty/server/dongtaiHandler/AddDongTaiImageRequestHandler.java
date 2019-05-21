package netty.server.dongtaiHandler;

import db.DongtaiDBService;
import db.FileDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.AddDongTaiImageRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.AddDongTaiImageResponsePacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 用户发表动态时   图片分开传送
 */
@ChannelHandler.Sharable
public class AddDongTaiImageRequestHandler extends SimpleChannelInboundHandler<AddDongTaiImageRequestPacket> {
	public static final AddDongTaiImageRequestHandler INSTANCE = new AddDongTaiImageRequestHandler();

    protected AddDongTaiImageRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AddDongTaiImageRequestPacket requestPacket) throws Exception {
		System.out.println("AddDongTaiImageRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				int id = requestPacket.getDongtaiid(); // 动态的id
				// 当是最后一张照片时 修改数据该动态的时间 并改变状态为完成 其他照片传-1就行
				// 这样就标志该动态完成 一石二鸟
				long time = requestPacket.getTime();
				byte[] bs2 = requestPacket.getBytes();
				int fileid = FileDBService.addFileMsg(bs2);
				AddDongTaiImageResponsePacket responsePacket = new AddDongTaiImageResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				if (fileid != -1) {

					String rString = DongtaiDBService.addImageToDongtai(id, fileid, time);
					if (rString.equals("ok")) {
//						String json = "{\"type\":\"ok\",\"id\":\"" + id + "\"}";
//						sendMsgToclient(ph, "adddongtaiimageResult", json);
						responsePacket.setResult("ok");
						responsePacket.setId(id);
						
					} else {
//						String json = "{\"type\":\"error\",\"id\":\"" + id + "\"}";
//						sendMsgToclient(ph, "adddongtaiimageResult", json);
						responsePacket.setResult("error");
						responsePacket.setId(id);
					}
					ctx.writeAndFlush(responsePacket);

				} else {
//					String json = "{\"type\":\"error\",\"id\":\"" + id + "\"}";
//					sendMsgToclient(ph, "adddongtaiimageResult", json);
					responsePacket.setResult("error");
					responsePacket.setId(id);
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});
		
	}
}
