package netty.server.dongtaiHandler;

import java.util.Vector;

import db.DongtaiDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetOldDongtaiIDsRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetOldDongtaiIDsResponsePacket;
import netty.server.handler.LoginRequestHandler;
import object.DongtaiPCTNum;
import threadUtil.FixedThreadPool;

/*
 * 用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
@ChannelHandler.Sharable
public class GetOldDongtaiIDsRequestHandler extends SimpleChannelInboundHandler<GetOldDongtaiIDsRequestPacket>{
	public static final GetOldDongtaiIDsRequestHandler INSTANCE = new GetOldDongtaiIDsRequestHandler();

    protected GetOldDongtaiIDsRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetOldDongtaiIDsRequestPacket requestPacket) throws Exception {
		System.out.println("GetOldDongtaiIDsRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
		
			@Override
			public void run() {
				
				
				int id = requestPacket.getDongtaiid();

				// 收到后就像客户回执 代表我已经收到 否则客户端显示刷新失败

				Vector<Integer> dongtaiIDs = DongtaiDBService.getoldDongtaiIDs(id);
				dongtaiIDs.add(id);
				Vector<DongtaiPCTNum> dongtaiPCTNums = new Vector<>();
				for (int i = 0; i < dongtaiIDs.size(); ++i) {
					int dongtaiid = dongtaiIDs.get(i);
					DongtaiPCTNum dongtaiPCTNum = DongtaiDBService.getDongtaiPNCNumByDTId(dongtaiid);
					dongtaiPCTNums.add(dongtaiPCTNum);
				}
//				JSONArray jsonArray = JSONArray.fromObject(dongtaiPCTNums);
//				sendMsgToclient(ph, "getoldDongtaiIDsResult", jsonArray.toString());

				GetOldDongtaiIDsResponsePacket responsePacket = new GetOldDongtaiIDsResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setDongtaiPCTNums(dongtaiPCTNums);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
