package netty.server.dongtaiHandler;

import java.util.Vector;

import db.DongtaiDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetUsODtIDsRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetUsODtIDsResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.DongtaiPCTNum;
import threadUtil.FixedThreadPool;

/*
 * 进入用户资料界面   用户上拉刷新动态的页面  就是加载旧的动态        返回6条动态的id
    //从当前的dongtaiid开始往前找6条以前的
 */
@ChannelHandler.Sharable
public class GetUsODtIDsRequestHandler extends SimpleChannelInboundHandler<GetUsODtIDsRequestPacket>{
	public static final GetUsODtIDsRequestHandler INSTANCE = new GetUsODtIDsRequestHandler();

    protected GetUsODtIDsRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUsODtIDsRequestPacket requestPacket) throws Exception {
		System.out.println("GetUsODtIDsRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph1 = session.getUserId();
				int id = requestPacket.getDongtaiid();
				String ph2 = requestPacket.getPh();

				// 收到后就像客户回执 代表我已经收到 否则客户端显示刷新失败

				Vector<Integer> dongtaiIDs = DongtaiDBService.getUseroldDongtaiIDs(id, ph2);
				dongtaiIDs.add(id);
				Vector<DongtaiPCTNum> dongtaiPCTNums = new Vector<>();
				for (int i = 0; i < dongtaiIDs.size(); ++i) {
					int dongtaiid = dongtaiIDs.get(i);
					DongtaiPCTNum dongtaiPCTNum = DongtaiDBService.getDongtaiPNCNumByDTId(dongtaiid);
					dongtaiPCTNums.add(dongtaiPCTNum);
				}
				JSONArray jsonArray = JSONArray.fromObject(dongtaiPCTNums);

//				String json = "{\"ph\":\"" + ph2 + "\"}";
//				sendMsgToclient(ph1, "gUsODtIDsRs " + json, jsonArray.toString());
				GetUsODtIDsResponsePacket responsePacket = new GetUsODtIDsResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph2);
				responsePacket.setDongtaiPCTNums(dongtaiPCTNums);
				
				ctx.writeAndFlush(responsePacket);
				
			}
		});
		
	}
}
