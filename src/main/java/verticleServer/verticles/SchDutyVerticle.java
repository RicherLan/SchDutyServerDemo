package verticleServer.verticles;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;
import util.Config;
import verticleServer.handlers.userhandler.ForgetPassImageCodeHandler;
import verticleServer.handlers.userhandler.ForgetPassSubmitPassHandler;
import verticleServer.handlers.userhandler.ForgetPassVeriCodeHandler;
import verticleServer.handlers.userhandler.RegImageCodeHandler;
import verticleServer.handlers.userhandler.RegUserHandler;
import verticleServer.handlers.userhandler.RegVeriCodeHandler;
import verticleServer.httpUtil.CacheService;
import verticleServer.routePath.UserRoutePath;
import verticleServer.service.user.IUserService;
import verticleServer.service.user.UserServiceImpl;

public class SchDutyVerticle extends AbstractVerticle {


	@Override
	public void start(Future<Void> future) throws Exception {

		Router router = Router.router(vertx);
		// Enable HTTP Body parse.
		router.route().handler(BodyHandler.create());
		// Enable CORS.
		enableCorsSupport(router);
		
		//设置路由
		initRoute(router);
		
		vertx.createHttpServer().requestHandler(router)
		.listen(Config.verticlePort, res -> {
		     if (res.succeeded()) {
		    	 future.complete();
		    	 System.out.println("Vertx 端口["+Config.verticlePort+"]绑定成功!");
		     } else {
		    	 future.fail(res.cause());
		     }
		   });
		
	}

	protected void enableCorsSupport(Router router) {
		Set<String> allowHeaders = new HashSet<>();
		allowHeaders.add("x-requested-with");
		allowHeaders.add("Access-Control-Allow-Origin");
		allowHeaders.add("origin");
		allowHeaders.add("Content-Type");
		allowHeaders.add("accept");
		// CORS support
		router.route()
				.handler(CorsHandler.create("*").allowedHeaders(allowHeaders).allowedMethod(HttpMethod.GET)
						.allowedMethod(HttpMethod.POST).allowedMethod(HttpMethod.DELETE).allowedMethod(HttpMethod.PATCH)
						.allowedMethod(HttpMethod.PUT));
		
		
		
	}
	
	
	private void initRoute(Router router) {
		
		IUserService userService = new UserServiceImpl();

		router.route().path(UserRoutePath.API_REG_VERIFICATION_CODE).handler(new RegVeriCodeHandler(userService));
		router.route().path(UserRoutePath.API_REG_IMAGE_CODE).handler(new RegImageCodeHandler());
		router.route().path(UserRoutePath.API_REG_USERINFO).handler(new RegUserHandler(userService));
		router.route().path(UserRoutePath.API_FORGETPASS_IMAGE_CODE).handler(new ForgetPassImageCodeHandler(userService));
		router.route().path(UserRoutePath.API_FORGETPASS_VERIFICATION_CODE).handler(new ForgetPassVeriCodeHandler(userService));
		router.route().path(UserRoutePath.API_FORGETPASS_SUBMITPASS).handler(new ForgetPassSubmitPassHandler(userService));
		
		
	}
	
	

	  @Override
	  public void stop(Future<Void> stopFuture) throws Exception {
	    
		  //置空  gc回收
		  CacheService.phoneRegMap = null;

		  stopFuture.complete();
	  }

	
}
