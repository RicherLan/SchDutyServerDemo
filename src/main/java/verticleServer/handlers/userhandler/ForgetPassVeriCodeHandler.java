package verticleServer.handlers.userhandler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import threadUtil.FixedThreadPool;
import util.AliService;
import verticleServer.entity.user.ForgetPass;
import verticleServer.entity.user.RegPhone;
import verticleServer.httpUtil.CacheService;
import verticleServer.httpUtil.HttpUtil;
import verticleServer.service.user.IUserService;

/*
 * 	忘记密码获得短信验证码
 */
public class ForgetPassVeriCodeHandler  implements Handler<RoutingContext>{
private IUserService service;
	
	public ForgetPassVeriCodeHandler(IUserService service) {
		this.service = service;
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		
		
		JsonObject bodyAsJson = ctx.getBodyAsJson();
		
		
		//System.out.println("忘记密码   短信验证码请求");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				String phone = bodyAsJson.getString("phone");
				if(!phone.startsWith("1")) {
					HttpUtil.resp(ctx.request(), new JsonObject().put("message", "error"));
					return;
				}
				
				
				String noteCode = AliService.getVerificationCode(4);
				
				if(!CacheService.phoneForgetPassMap.containsKey(phone)) {
					ForgetPass forgetPass = new ForgetPass();
					forgetPass.setPhone(phone);
					CacheService.phoneForgetPassMap.put(phone, forgetPass);
				}
				CacheService.phoneForgetPassMap.get(phone).setNodeCode(noteCode);
				CacheService.phoneForgetPassMap.get(phone).setLasttime(System.currentTimeMillis());
				
				//发送验证码
				String msgString = AliService.sendRegCode(phone,noteCode);
				
				HttpUtil.resp(ctx.request(), new JsonObject().put("message", msgString));
				
			}
		});
		
		
		
	}
}
