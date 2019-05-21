package netty.server.corprationHandler;

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
import netty.protocol.request.otherRequest.LoadCourseOfCorpRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.LoadCourseOfCorpResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.Course;
import threadUtil.FixedThreadPool;

/*
 * 	社团组织成员导入自己的课表
 */
@ChannelHandler.Sharable
public class LoadCourseOfCorpRequestHandler extends SimpleChannelInboundHandler<LoadCourseOfCorpRequestPacket>{
	public static final LoadCourseOfCorpRequestHandler INSTANCE = new LoadCourseOfCorpRequestHandler();

    protected LoadCourseOfCorpRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoadCourseOfCorpRequestPacket requestPacket) throws Exception {
		System.out.println("LoadCourseOfCorpRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());

				String phonenumber = session.getUserId();

				int year = requestPacket.getYear(); // 学年
				int xueqi = requestPacket.getXueqi(); // 学期
				String count = requestPacket.getCount(); // 教务账号
				String password = requestPacket.getPwd(); // 教务密码
				String schoolname = UserDBService.getSchoolNameOfph(phonenumber);

				javatest test = new javatest();
				test.username = count;
				test.password = password;
				String jsonstr = "";
				try {
					jsonstr = test.getkebiao(year, xueqi);
				} catch (Exception e) {
					LoadCourseOfCorpResponsePacket responsePacket = new LoadCourseOfCorpResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					
					ctx.writeAndFlush(responsePacket);
					//e.printStackTrace();
					return;
				}
				if (jsonstr.equals("errorUsernameOrPwd")) {
					//String json = "{\"rs\":\"errUNOrPwd\"}";
					
					LoadCourseOfCorpResponsePacket responsePacket = new LoadCourseOfCorpResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("errUNOrPwd");
					
					ctx.writeAndFlush(responsePacket);
					
					return;
				}

				Vector<Course> courses = HtmlPrase.convertKebiao(jsonstr);

				// 拿到课表后 存到数据库 并把它发给 客户端 为了保证速度 先发给客户端 然后存储数据库
				//JSONArray sJsonArray = JSONArray.fromObject(courses);

				// string = sJsonArray.toString();
				//String json = "{\"rs\":\"ok\",\"grade\":\"" + year + "\",\"xueqi\":\"" + xueqi + "\"}";
				//sendMsgToclient(phonenumber, "loadCourseRs " + json, string);
				LoadCourseOfCorpResponsePacket responsePacket = new LoadCourseOfCorpResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult("ok");
				responsePacket.setGrade(year);
				responsePacket.setXueqi(xueqi);
				responsePacket.setCourses(courses);
				ctx.writeAndFlush(responsePacket);


				String string2 = "";
				for (int i = 0; i < courses.size(); ++i) {
					Course course = courses.get(i);
					String string3 = "";
					string3 = course.getCT1() + "#" + course.getCT2() + "#" + course.getCT3();
					string2 = string2 + string3 + "=";
				}

				if (string2.endsWith("=")) {
					string2 = string2.substring(0, string2.length() - 1);
				}
				EDUDBService.saveCourse(phonenumber, string2, year, xueqi);
				EDUDBService.saveEdu(phonenumber, schoolname, count, password);

				
			}
		});
		
	}
}
