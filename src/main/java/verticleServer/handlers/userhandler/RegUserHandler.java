package verticleServer.handlers.userhandler;

import db.UserDBService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import netty.protocol.response.otherResponse.RegisterTestResponsePacket;
import threadUtil.FixedThreadPool;
import util.AliService;
import verticleServer.entity.user.RegPhone;
import verticleServer.httpUtil.CacheService;
import verticleServer.httpUtil.HttpUtil;
import verticleServer.service.user.IUserService;

/*
 * 	用户注册
 */
public class RegUserHandler implements Handler<RoutingContext>{

	private IUserService service;
	
	public RegUserHandler(IUserService service) {
		this.service = service;
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		
		
		JsonObject bodyAsJson = ctx.getBodyAsJson();
		
		System.out.println("注册用户");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				String notecode = bodyAsJson.getString("noteCode");
				String imagecode = bodyAsJson.getString("imagecode");
				String phonenumber = bodyAsJson.getString("phonenumber"); // 要注册的手机号
				if(!CacheService.phoneRegMap.containsKey(phonenumber)) {
					HttpUtil.resp(ctx.request(), new JsonObject().put("message", "error"));
					return;
				}else {
					RegPhone regPhone = CacheService.phoneRegMap.get(phonenumber);
					if(regPhone.getImageCode().equals("")||regPhone.getNodeCode().equals("")) {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "error"));
						return;
					}
					if(!imagecode.equals(regPhone.getImageCode())) {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "图片验证码不正确!"));
						return;
					}else if(!notecode.equals(regPhone.getNodeCode())){
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "短信验证码不正确!"));
						return;
					}
				}
				
				String schoolname = bodyAsJson.getString("schoolname"); // 学校名字
				String departmentname = bodyAsJson.getString("collegename"); // 学院名字
				String majorname = bodyAsJson.getString("majorname"); // 所修专业
				
				String password = bodyAsJson.getString("password"); // 密码
				int ruxueyear = bodyAsJson.getInteger("ruxueyear");
				

				String rString = service.regisUser(schoolname, departmentname, majorname, ruxueyear,
						phonenumber, password);
				
				if(rString.equals("ok")) {
					HttpUtil.resp(ctx.request(), new JsonObject().put("message", "ok"));
				}else {
					HttpUtil.resp(ctx.request(), new JsonObject().put("message", "服务器繁忙,请稍后再试!"));
				}
				

			}
		});
		
		
		
		
		
	}
	
}
