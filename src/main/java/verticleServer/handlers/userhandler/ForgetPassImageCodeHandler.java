package verticleServer.handlers.userhandler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import threadUtil.FixedThreadPool;
import util.EncryptTool;
import util.MyTools;
import verticleServer.entity.user.ForgetPass;
import verticleServer.entity.user.RegPhone;
import verticleServer.httpUtil.CacheService;
import verticleServer.httpUtil.HttpUtil;
import verticleServer.service.user.IUserService;

public class ForgetPassImageCodeHandler implements Handler<RoutingContext>{
	
	private IUserService service;
	
	public ForgetPassImageCodeHandler(IUserService service) {
		this.service = service;
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		
		JsonObject bodyAsJson = ctx.getBodyAsJson();
		
		//System.out.println("忘记密码   图片验证码请求");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				String phone = bodyAsJson.getString("phone");
				
				boolean isPhoneExist = service.isPhoneReg(phone);
				if(!isPhoneExist) {
					JsonObject jsonObject = new JsonObject();
					jsonObject.put("message", "该手机号未被注册！");
					HttpUtil.resp(ctx.request(), jsonObject);
					return;
					
				}
				
				String imageCode = MyTools.getRandomPassword(4);
				
				String encrypt =(MyTools.getRandomPassword(10));
				
//				System.out.println(imageCode);
				
				if(!CacheService.phoneForgetPassMap.containsKey(phone)) {
					ForgetPass forgetPass = new ForgetPass();
					forgetPass.setPhone(phone);
					CacheService.phoneForgetPassMap.put(phone, forgetPass);
				}
				CacheService.phoneForgetPassMap.get(phone).setImageCode(imageCode);
				CacheService.phoneForgetPassMap.get(phone).setLasttime(System.currentTimeMillis());
				CacheService.phoneForgetPassMap.get(phone).setEncrypt(encrypt);
				
				
//				System.out.println(encrypt+"   5555");
				encrypt = EncryptTool.encryptStr(encrypt);
//				System.out.println(encrypt+"   5555");
				JsonObject jsonObject = new JsonObject();
				jsonObject.put("message", "ok");
				jsonObject.put("imageCode", imageCode);
				jsonObject.put("encrypt", encrypt);
				HttpUtil.resp(ctx.request(), jsonObject);
				
				
			}
		});
		
		
	}
}
