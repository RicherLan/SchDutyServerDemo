package netty.server.dongtaiHandler;

import db.DongtaiDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.dongtaiRequest.GetDTMsgByIdRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.server.handler.LoginRequestHandler;
import object.DongtaiMsg;
import threadUtil.FixedThreadPool;

/*
 * 	获得某动态消息的基本信息 不包括动态的图片 和 文字
 */
public class GetDTMsgByIdRequestHandler extends SimpleChannelInboundHandler<GetDTMsgByIdRequestPacket>{
	public static final GetDTMsgByIdRequestHandler INSTANCE = new GetDTMsgByIdRequestHandler();

    protected GetDTMsgByIdRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetDTMsgByIdRequestPacket requestPacket) throws Exception {
		System.out.println("GetDTMsgByIdRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				/*
				
				int id = requestPacket.getId();
				DongtaiMsg dongtaiMsg = DongtaiDBService.getDongtaiMsgByDTID(id);
				int DTMsgId = dongtaiMsg.msgid;
				int DTId = dongtaiMsg.dongtaiid;
				String userid = dongtaiMsg.userid;
				String username = dongtaiMsg.username;
				// String usericon = dongtaiMsg.usericon;
				String type = dongtaiMsg.type;
				String json = "{\"DTMsgId\":\"" + DTMsgId + "\",\"DTId\":\"" + DTId + "\",\"userid\":\"" + userid
						+ "\",\"username\":\"" + username + "\",\"type\":\"" + type + "\"}";
				;
				if (type.equals("praise")) {
					json = "{\"DTMsgId\":\"" + DTMsgId + "\",\"DTId\":\"" + DTId + "\",\"userid\":\"" + userid
							+ "\",\"username\":\"" + username + "\",\"type\":\"" + type + "\"}";
				} else if (type.equals("transmit")) {
					json = "{\"DTMsgId\":\"" + DTMsgId + "\",\"DTId\":\"" + DTId + "\",\"userid\":\"" + userid
							+ "\",\"username\":\"" + username + "\",\"type\":\"" + type + "\"}";
				} else if (type.equals("todongtai")) {
					String content = dongtaiMsg.msg;
					json = "{\"DTMsgId\":\"" + DTMsgId + "\",\"DTId\":\"" + DTId + "\",\"userid\":\"" + userid
							+ "\",\"username\":\"" + username + "\",\"type\":\"" + type + "\",\"content\":\"" + content
							+ "\"}";
				} else if (type.equals("tocomment")) {
					String content = dongtaiMsg.msg;
					int beComId = dongtaiMsg.commentid;
					String beComUSerid = dongtaiMsg.becommenteduserid;
					String beComUsername = dongtaiMsg.becommentedusername;
					json = "{\"DTMsgId\":\"" + DTMsgId + "\",\"DTId\":\"" + DTId + "\",\"userid\":\"" + userid
							+ "\",\"username\":\"" + username + "\",\"type\":\"" + type + "\",\"content\":\"" + content
							+ "\",\"beComId\":\"" + beComId + "\",\"beComUSerid\":\"" + beComUSerid
							+ "\",\"beComUsername\":\"" + beComUsername + "\"}";

				}

				sendMsgToclient(ph, "getDTMsgByIdResult", json);

				// 发送该动态消息的制造者的头像
				String json2 = "{\"DTMsgId\":\"" + DTMsgId + "\"}";
				byte[] bs3 = UserDBService.getIconByPh(userid);
				sendFileMsgToclient(ph, "getDTMsgUserIcRs " + json2, bs3);

				*/
			}
		});
		
	}
}
