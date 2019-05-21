package netty.server.corprationHandler;

import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import db.SchdutyDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.request.otherRequest.SearchEmptyCourseRequestPacket;
import netty.protocol.response.otherResponse.SearchEmptyCourseResponsePacket;
import netty.server.handler.LoginRequestHandler;
import object.SearchEmptyCourse;
import object.UserCorp;
import threadUtil.FixedThreadPool;

/*
 * 	社团组织获得   获得某几节课都有空的人
 */
@ChannelHandler.Sharable
public class SearchEmptyCourseRequestHandler extends SimpleChannelInboundHandler<SearchEmptyCourseRequestPacket>{
	 public static final SearchEmptyCourseRequestHandler INSTANCE = new SearchEmptyCourseRequestHandler();

	    protected SearchEmptyCourseRequestHandler() {
	    }

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, SearchEmptyCourseRequestPacket requestPacket) throws Exception {
			System.out.println("SearchEmptyCourseRequestHandler");
			FixedThreadPool.threadPool.submit(new Runnable() {
				
				@Override
				public void run() {
					
//					
					int gid = requestPacket.getGroupid();
					int year = requestPacket.getYear();
					int xueqi = requestPacket.getXueqi();

					
					Vector<SearchEmptyCourse> SearchEmptyCourse = requestPacket.getSearchEmptyCourses();
							

					Vector<UserCorp> userCorps = SchdutyDBService.getStuByEptCou(gid, year, xueqi, SearchEmptyCourse);
						
					
//					JSONArray jsonArray = JSONArray.fromObject(userCorps);
//					sendMsgToclient(ph, "searchEmptyCourseRs " + gid, jsonArray.toString());
					SearchEmptyCourseResponsePacket responsePacket = new SearchEmptyCourseResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setGroupid(gid);
					responsePacket.setUserCorps(userCorps);
					
					ctx.writeAndFlush(responsePacket);
				}
			});
			
		}
}
