package netty.server.dongtaiHandler;

import java.util.Vector;

import db.DongtaiDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetNewDongtaiIDsRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetNewDongtaiIDsResponsePacket;
import netty.server.handler.LoginRequestHandler;
import object.DongtaiPCTNum;
import threadUtil.FixedThreadPool;

/*
 * 用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id
 */
@ChannelHandler.Sharable
public class GetNewDongtaiIDsReqeustHandler extends SimpleChannelInboundHandler<GetNewDongtaiIDsRequestPacket>{
	public static final GetNewDongtaiIDsReqeustHandler INSTANCE = new GetNewDongtaiIDsReqeustHandler();

    protected GetNewDongtaiIDsReqeustHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetNewDongtaiIDsRequestPacket requestPacket) throws Exception {
		System.out.println("GetNewDongtaiIDsReqeustHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				// int id = jsonObject.getInt("id");
				// 收到后就像客户端回执 代表我已经收到

				Vector<Integer> dongtaiIDs = DongtaiDBService.getnewDongtaiIDs();
				Vector<DongtaiPCTNum> dongtaiPCTNums = new Vector<>();
				for (int i = 0; i < dongtaiIDs.size(); ++i) {
					int dongtaiid = dongtaiIDs.get(i);
					DongtaiPCTNum dongtaiPCTNum = DongtaiDBService.getDongtaiPNCNumByDTId(dongtaiid);
					dongtaiPCTNums.add(dongtaiPCTNum);
				}
//				JSONArray jsonArray = JSONArray.fromObject(dongtaiPCTNums);
//
//				sendMsgToclient(ph, "getnewDongtaiIDsResult", jsonArray.toString());
				GetNewDongtaiIDsResponsePacket responsePacket = new GetNewDongtaiIDsResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setDongtaiPCTNums(dongtaiPCTNums);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
