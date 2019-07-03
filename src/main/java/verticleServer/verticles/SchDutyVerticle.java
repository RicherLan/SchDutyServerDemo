package verticleServer.verticles;

import java.util.HashSet;
import java.util.Set;

import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;
import util.Config;
import verticleServer.handlers.userhandler.RegImageCodeHandler;
import verticleServer.handlers.userhandler.RegUserHandler;
import verticleServer.handlers.userhandler.RegVeriCodeHandler;
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
		
		HttpServer server = vertx.createHttpServer().requestHandler(router);
		server.listen(Config.verticlePort, res -> {
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
		
	}
}
