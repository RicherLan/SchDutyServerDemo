package netty.server.eduSysHandler;

import java.util.Vector;

import db.EDUDBService;
import db.UserDBService;
import htmlnet.HtmlPrase;
import htmlnet.javatest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netty.protocol.request.otherRequest.GetScoreOfUIDRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetScoreOfUIDResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.ListViewScore;
import threadUtil.FixedThreadPool;

/*
 * 获取成绩
 */
@ChannelHandler.Sharable
public class GetScoreOfUIDRequestHandler extends SimpleChannelInboundHandler<GetScoreOfUIDRequestPacket>{
	public static final GetScoreOfUIDRequestHandler INSTANCE = new GetScoreOfUIDRequestHandler();

    protected GetScoreOfUIDRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetScoreOfUIDRequestPacket requestPacket) throws Exception {
		System.out.println("GetScoreOfUIDRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				Session session = SessionUtil.getSession(ctx.channel());
				String phonenumber = session.getUserId();
				
				int xnm = requestPacket.getXnm(); // 学年
				int xqm = requestPacket.getXqm(); // 学期
				String account = requestPacket.getCount(); // 教务账号
				String password = requestPacket.getPassword(); // 教务密码
				String schoolname = UserDBService.getSchoolNameOfph(phonenumber);

				javatest test = new javatest();
				test.username = account;
				test.password = password;
				String jsonstr = "";
				try {
					jsonstr = test.getscore(xnm, xqm);
				} catch (Exception e) {
					
					GetScoreOfUIDResponsePacket responsePacket = new GetScoreOfUIDResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					
					ctx.writeAndFlush(responsePacket);
					//e.printStackTrace();
					return;
				} // 得到了教务成绩
				if (jsonstr.equals("errorUsernameOrPwd")) {
					//String json = "{\"rs\":\"errUNOrPwd\"}";
					
					GetScoreOfUIDResponsePacket responsePacket = new GetScoreOfUIDResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("errUNOrPwd");
					
					ctx.writeAndFlush(responsePacket);
					return;
				}
				
				Vector<ListViewScore> scores = HtmlPrase.convertScore(jsonstr);

				
				// 拿到成绩后 存到数据库 并把它发给 客户端 为了保证速度 先发给客户端 然后存储数据库
				// 存储到数据库这块还没写 后续开发看情况决定

				//JSONArray jsonArray = JSONArray.fromObject(scores);
				

				//String json = "{\"rs\":\"ok\",\"grade\":\"" + xnm + "\",\"xueqi\":\"" + xqm + "\"}";
				GetScoreOfUIDResponsePacket responsePacket = new GetScoreOfUIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult("ok");
				responsePacket.setGrade(xnm);
				responsePacket.setXueqi(xqm);
				responsePacket.setScores(scores);
				
				ctx.writeAndFlush(responsePacket);
				
				EDUDBService.saveEdu(phonenumber, schoolname, account, password);
				// DBService.saveScore(scores);
				// ***************************************************************************************
				// 获得用户课表
				// 同时把它的教务账号密码存储一下

			}
		});
		
	}
}
