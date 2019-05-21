package netty.server.corprationHandler;

import java.util.Map;
import java.util.Vector;

import db.GroupDBService;
import db.SchdutyDBService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.CorpLoadCourseRsRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.CorpLoadCourseRsResponsePacket;
import netty.server.handler.LoginRequestHandler;
import object.CorpUserNotLoadCourse;
import threadUtil.FixedThreadPool;

/*
 * 	社团组织查看课表导入情况
 */
@ChannelHandler.Sharable
public class CorpLoadCourseRsRequestHandler extends SimpleChannelInboundHandler<CorpLoadCourseRsRequestPacket> {
	public static final CorpLoadCourseRsRequestHandler INSTANCE = new CorpLoadCourseRsRequestHandler();

	protected CorpLoadCourseRsRequestHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CorpLoadCourseRsRequestPacket requestPacket) throws Exception {
		System.out.println("CorpLoadCourseRsRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {

				
				
				int groupid = requestPacket.getGroupid();

				 Map<Integer, Vector<CorpUserNotLoadCourse>> map = SchdutyDBService.getCorpLoadCourseRs(groupid);
				 
				 if(map==null) {
					 return;
				 }
				 
				 //群人数
				 int groupusernum = -1;
				 for(Integer i:map.keySet()) {
					 groupusernum=i;
					 break;
				 }
				 if(groupusernum==-1) {
					 return;
				 }
				 Vector<CorpUserNotLoadCourse> corpUserNotLoadCourses = map.get(groupusernum);
//				JSONArray jsonArray = JSONArray.fromObject(corpUserNotLoadCourses);
//				sendMsgToclient(ph, "corpLoadCourseRs " + groupid, jsonArray.toString());
					
				CorpLoadCourseRsResponsePacket responsePacket = new CorpLoadCourseRsResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setGroupid(groupid);
				responsePacket.setCorpUserNotLoadCourses(corpUserNotLoadCourses);
				responsePacket.setGroupusernum(groupusernum);
				ctx.writeAndFlush(responsePacket);
				
				
				//更新下群人数
				GroupDBService.upGroupUsernum(groupid,groupusernum);
				
			}
		});

	}
}
