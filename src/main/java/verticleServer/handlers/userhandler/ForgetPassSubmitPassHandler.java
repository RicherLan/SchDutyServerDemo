package verticleServer.handlers.userhandler;

import db.UserDBService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;
import threadUtil.FixedThreadPool;
import verticleServer.entity.user.ForgetPass;
import verticleServer.entity.user.RegPhone;
import verticleServer.httpUtil.CacheService;
import verticleServer.httpUtil.HttpUtil;
import verticleServer.service.user.IUserService;

public class ForgetPassSubmitPassHandler implements Handler<RoutingContext> {
	private IUserService service;

	public ForgetPassSubmitPassHandler(IUserService service) {
		this.service = service;
	}

	@Override
	public void handle(RoutingContext ctx) {

		JsonObject bodyAsJson = ctx.getBodyAsJson();

	//	System.out.println("忘记密码   提交新密码");
		FixedThreadPool.threadPool.submit(new Runnable() {

			@Override
			public void run() {

				String notecode = bodyAsJson.getString("noteCode");
				String imagecode = bodyAsJson.getString("imagecode");
				String phonenumber = bodyAsJson.getString("phonenumber"); // 要注册的手机号
				String encrypt = bodyAsJson.getString("encrypt");

				// System.out.println(encrypt+" 88888888888");
				// System.out.println("短信"+notecode+" 图片:"+imagecode);

				if (!CacheService.phoneForgetPassMap.containsKey(phonenumber)) {
					HttpUtil.resp(ctx.request(), new JsonObject().put("message", "error"));
					
					return;
				} else {
					ForgetPass forgetPass = CacheService.phoneForgetPassMap.get(phonenumber);

					if (!encrypt.equals(forgetPass.getEncrypt())) {
						
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "error"));
						return;
					}

					// System.out.println("短信"+regPhone.getNodeCode()+"
					// 图片:"+regPhone.getImageCode());

					if (forgetPass.getImageCode().equals("") || forgetPass.getNodeCode().equals("")) {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "error"));
						
						return;
					}
					if (!imagecode.toLowerCase().equals(forgetPass.getImageCode().toLowerCase())) {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "图片验证码不正确!"));
						return;
					} else if (!notecode.equals(forgetPass.getNodeCode())) {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "短信验证码不正确!"));
						return;
					}
				}

				String password = bodyAsJson.getString("password"); // 密码

				String rString = service.changePassword(phonenumber, password);

				if (rString.equals("ok")) {
					HttpUtil.resp(ctx.request(), new JsonObject().put("message", "ok"));
					
					CacheService.phoneForgetPassMap.get(phonenumber).setImageCode("");
					CacheService.phoneForgetPassMap.get(phonenumber).setNodeCode("");
					CacheService.phoneForgetPassMap.get(phonenumber).setEncrypt("");
					
				} else {
					if (rString.equals("errph")) {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "该号码未被注册!"));
					} else {
						HttpUtil.resp(ctx.request(), new JsonObject().put("message", "服务器繁忙,请稍后再试!"));
					}

				}

			}
		});

	}
}
