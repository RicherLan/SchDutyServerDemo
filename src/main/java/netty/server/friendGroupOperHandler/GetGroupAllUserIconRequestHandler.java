package netty.server.friendGroupOperHandler;

import java.util.Vector;

import db.GroupDBService;
import db.UserDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetGroupUserIconByPhRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetGroupAllUserIconResponsePacket;
import netty.server.handler.LoginRequestHandler;
import threadUtil.FixedThreadPool;

/*
 * 	获得某群的所有成员后  向服务器回执     来让服务器发头像
 */
@ChannelHandler.Sharable
public class GetGroupAllUserIconRequestHandler extends SimpleChannelInboundHandler<GetGroupUserIconByPhRequestPacket>{
	public static final GetGroupAllUserIconRequestHandler INSTANCE = new GetGroupAllUserIconRequestHandler();

    protected GetGroupAllUserIconRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetGroupUserIconByPhRequestPacket requestPacket) throws Exception {
		System.out.println("GetGroupAllUserIconRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				/*
				int groupid = requestPacket.getGroupid();
				Vector<String> phStrings = GroupDBService.getAllUsersPhByGroupid(groupid);
				
				if(phStrings!=null) {
					for(String ph : phStrings) {
						
						byte[] bs = UserDBService.getIconByPh(ph);
						if(bs==null||bs.length==0) {
							continue;
						}
//						
						//sendFileMsgToclient(phonenumber, "groupUserIc " +ph+" "+groupid, bs3);
						
						GetGroupAllUserIconResponsePacket responsePacket = new GetGroupAllUserIconResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setGroupid(groupid);
						responsePacket.setPh(ph);
						responsePacket.setIcon(bs);
						
					}
				}*/
				
			}
		});
		
	}
}
