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
import netty.protocol.request.otherRequest.GetCourseSchByUIDRequestPacket;
import netty.protocol.request.otherRequest.LoginRequestPacket;
import netty.protocol.response.otherResponse.GetCourseSchByUIDResponsePacket;
import netty.server.handler.LoginRequestHandler;
import netty.session.Session;
import netty.util.SessionUtil;
import object.Course;
import threadUtil.FixedThreadPool;

/*
 * 	获取课表
 */
@ChannelHandler.Sharable
public class GetCourseSchOfUIDRequestHandler extends SimpleChannelInboundHandler<GetCourseSchByUIDRequestPacket>{

    public static final GetCourseSchOfUIDRequestHandler INSTANCE = new GetCourseSchOfUIDRequestHandler();

    protected GetCourseSchOfUIDRequestHandler() {
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, GetCourseSchByUIDRequestPacket requestPacket) throws Exception {
		System.out.println("GetCourseSchOfUIDRequestHandler");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				Session session = SessionUtil.getSession(ctx.channel());

				String phonenumber = session.getUserId();

				int xnm = requestPacket.getXnm(); // 学年
				int xqm = requestPacket.getXqm(); // 学期
				String count = requestPacket.getCount(); // 教务账号
				String password = requestPacket.getPassword(); // 教务密码
				String schoolname = UserDBService.getSchoolNameOfph(phonenumber);

				javatest test = new javatest();
				test.username = count;
				test.password = password;
				String jsonstr = "";
				try {
					jsonstr = test.getkebiao(xnm, xqm);
				} catch (Exception e) {
					GetCourseSchByUIDResponsePacket responsePacket = new GetCourseSchByUIDResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("error");
					
					ctx.writeAndFlush(responsePacket);
					//e.printStackTrace();
					return;
				}
				
				if (jsonstr.equals("errorUsernameOrPwd")) {
					//String json = "{\"rs\":\"errUNOrPwd\"}";
					GetCourseSchByUIDResponsePacket responsePacket = new GetCourseSchByUIDResponsePacket();
					responsePacket.setVersion(requestPacket.getVersion());
					responsePacket.setResult("errUNOrPwd");
					
					ctx.writeAndFlush(responsePacket);
					return;
				}

				Vector<Course> courses = HtmlPrase.convertKebiao(jsonstr);

				

				// 拿到课表后 存到数据库 并把它发给 客户端 为了保证速度 先发给客户端 然后存储数据库
				//JSONArray sJsonArray = JSONArray.fromObject(courses);

			//	String string = sJsonArray.toString();
				//String json = "{\"rs\":\"ok\",\"grade\":\"" + xnm + "\",\"xueqi\":\"" + xqm + "\"}";
				//sendMsgToclient(phonenumber, "getkBRs " + json, string);

				GetCourseSchByUIDResponsePacket responsePacket = new GetCourseSchByUIDResponsePacket();
				responsePacket.setVersion(requestPacket.getVersion());
				responsePacket.setResult("ok");
				responsePacket.setGrade(xnm);
				responsePacket.setXueqi(xqm);
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
				EDUDBService.saveCourse(phonenumber, string2, xnm, xqm);
				EDUDBService.saveEdu(phonenumber, schoolname, count, password);

				
			}
		});
		
	}
}
