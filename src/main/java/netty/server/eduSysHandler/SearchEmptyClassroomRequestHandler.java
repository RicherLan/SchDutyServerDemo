package netty.server.eduSysHandler;

import java.util.Vector;

import htmlnet.javatest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.edu.SearchEmptyClassroomRequestPacket;
import netty.protocol.request.otherRequest.GetScoreOfUIDRequestPacket;
import netty.protocol.response.edu.SearchEmptyClassroomResponsePacket;
import object.ClassRoom;
import threadUtil.FixedThreadPool;

/*
 * 	查询空教室
 */
@ChannelHandler.Sharable
public class SearchEmptyClassroomRequestHandler extends SimpleChannelInboundHandler<SearchEmptyClassroomRequestPacket>{
	public static final SearchEmptyClassroomRequestHandler INSTANCE = new SearchEmptyClassroomRequestHandler();

    protected SearchEmptyClassroomRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SearchEmptyClassroomRequestPacket requestPacket) throws Exception {
		
		System.out.println("SearchEmptyClassroomRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					int xnm = requestPacket.getXnm(); // 学年
					int xqm = requestPacket.getXqm(); // 学期
					
					String lh = requestPacket.getLh();
					int zcd = requestPacket.getZcd();
					int xqj = requestPacket.getXqj();
					Vector<Integer> jieshu = requestPacket.getJieshu();
					javatest test = new javatest();
					String loginstr = test.login();
					
					
					
					if(loginstr.equals("errorUsernameOrPwd")) {
						SearchEmptyClassroomResponsePacket responsePacket = new SearchEmptyClassroomResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setrString("error");
						ctx.writeAndFlush(responsePacket);
					}else {
						Vector<ClassRoom> classRooms = test.searchEmptyClassrooms(xnm, xqm, lh, zcd, xqj, jieshu);
						SearchEmptyClassroomResponsePacket responsePacket = new SearchEmptyClassroomResponsePacket();
						responsePacket.setVersion(requestPacket.getVersion());
						responsePacket.setrString("ok");
						responsePacket.setClassRoom(classRooms);
						ctx.writeAndFlush(responsePacket);
						
					}
				} catch (Exception e) {
					SearchEmptyClassroomResponsePacket responsePacket = new SearchEmptyClassroomResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setrString("error");
					ctx.writeAndFlush(responsePacket);
				}
				
				
			}
		});
	}
}
