package netty.server.dongtaiHandler;

import java.util.Vector;

import db.DongtaiDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetUNDtIDsRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.dongtaiResponse.GetUNDtIDsResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.DongtaiPCTNum;
import threadUtil.FixedThreadPool;

/*
 * 进入用户资料界面  用户下拉刷新动态的页面  就是加载新的动态        返回6条动态的id    ph2是要查询的人的账号
 */
@ChannelHandler.Sharable
public class GetUNDtIDsRequestHandler extends SimpleChannelInboundHandler<GetUNDtIDsRequestPacket> {
	public static final GetUNDtIDsRequestHandler INSTANCE = new GetUNDtIDsRequestHandler();

	protected GetUNDtIDsRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetUNDtIDsRequestPacket requestPacket) throws Exception {
		System.out.println("GetUNDtIDsRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String ph1 = session.getUserId();
				String ph2 = requestPacket.getPh(); // 对方的账号
				// int id = jsonObject.getInt("id");
				// 收到后就像客户端回执 代表我已经收到

				Vector<Integer> dongtaiIDs = DongtaiDBService.getUserNewDongtaiIDs(ph2);
				Vector<DongtaiPCTNum> dongtaiPCTNums = new Vector<>();
				for (int i = 0; i < dongtaiIDs.size(); ++i) {
					int dongtaiid = dongtaiIDs.get(i);
					DongtaiPCTNum dongtaiPCTNum = DongtaiDBService.getDongtaiPNCNumByDTId(dongtaiid);
					dongtaiPCTNums.add(dongtaiPCTNum);
				}
				JSONArray jsonArray = JSONArray.fromObject(dongtaiPCTNums);

//				String json = "{\"ph\":\"" + ph2 + "\"}";
//				sendMsgToclient(ph1, "gUsNDtIDsRs " + json, jsonArray.toString());

				GetUNDtIDsResponsePacket responsePacket = new GetUNDtIDsResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setPh(ph2);
				responsePacket.setDongtaiPCTNums(dongtaiPCTNums);
				ctx.writeAndFlush(responsePacket);
			}
		});

	}
}
