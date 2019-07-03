package verticleServer.handlers.userhandler;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import threadUtil.FixedThreadPool;
import util.AliService;
import util.MyTools;
import verticleServer.entity.user.RegPhone;
import verticleServer.httpUtil.CacheService;
import verticleServer.httpUtil.HttpUtil;
import verticleServer.service.user.IUserService;


/*
 * 	短信验证码处理器
 */
public class RegVeriCodeHandler implements Handler<RoutingContext>{

	private IUserService service;
	
	public RegVeriCodeHandler(IUserService service) {
		this.service = service;
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		
		
		JsonObject bodyAsJson = ctx.getBodyAsJson();
		
		
		System.out.println("注册用户   短信验证码请求");
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				String phone = bodyAsJson.getString("phone");
				
				String noteCode = AliService.getVerificationCode(4);
				
				if(!CacheService.phoneRegMap.containsKey(phone)) {
					RegPhone regPhone = new RegPhone();
					regPhone.setPhone(phone);
					CacheService.phoneRegMap.put(phone, regPhone);
				}
				CacheService.phoneRegMap.get(phone).setNodeCode(noteCode);
				CacheService.phoneRegMap.get(phone).setLasttime(System.currentTimeMillis());
				
				
				HttpUtil.resp(ctx.request(), new JsonObject().put("message", "ok"));
				
			}
		});
		
		
		
	}

	
}
