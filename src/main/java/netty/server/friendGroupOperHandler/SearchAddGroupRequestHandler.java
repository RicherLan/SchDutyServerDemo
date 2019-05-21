package netty.server.friendGroupOperHandler;

import db.GroupDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.SearchAddGroupRequestPacket;
import netty.protocol.response.otherResponse.SearchAddGroupResponsePacket;
import netty.server.handler.LoginRequestHandler;
import object.GroupBasicInfo;
import threadUtil.FixedThreadPool;

/*
 * 	客户端添加群时   搜索账号 只需要看到其账号 网名 头像就行
 */
@ChannelHandler.Sharable
public class SearchAddGroupRequestHandler extends SimpleChannelInboundHandler<SearchAddGroupRequestPacket> {
	public static final SearchAddGroupRequestHandler INSTANCE = new SearchAddGroupRequestHandler();

	protected SearchAddGroupRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SearchAddGroupRequestPacket requestPacket) throws Exception {
		System.out.println("SearchAddGroupRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				
				int groupid = requestPacket.getGroupid();
			
				GroupBasicInfo group = GroupDBService.getGroupInfoByGroupId(groupid);
				if (group != null) {
//					String string = "{\"rs\":\"ok\",\"gid\":\"" + groupid + "\",\"gname\":\"" + group.getGroupname()
//							+ "\",\"icon\":\"" + group.getGroupicon() + "\"}";

					SearchAddGroupResponsePacket responsePacket = new SearchAddGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("ok");
					responsePacket.setGroupBasicInfo(group);

					ctx.writeAndFlush(responsePacket);
					

				}else {
					//String string = "{\"rs\":\"notexist\",\"gid\":\"" + groupid + "\"}";
					SearchAddGroupResponsePacket responsePacket = new SearchAddGroupResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("notexist");
					
					ctx.writeAndFlush(responsePacket);
				}
				
			}
		});

	}
}
