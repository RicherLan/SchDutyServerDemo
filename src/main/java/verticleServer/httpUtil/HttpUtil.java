package verticleServer.httpUtil;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.http.HttpServerRequest;

public class HttpUtil {

	public static void resp(HttpServerRequest request, JsonObject ret) {
		request.response().putHeader("content-type", "application/json;charset=utf-8")
				.putHeader("Access-Control-Allow-Origin", "*").putHeader("Access-Control-Allow-Credentials", "true")
				.putHeader("Content-Disposition", "attachment").end(Json.encodePrettily(ret));
	}

	public static void respError(HttpServerRequest request, int code, String error) {
		request.response().putHeader("content-type", "application/json;charset=utf-8")
				.putHeader("Access-Control-Allow-Origin", "*").putHeader("Access-Control-Allow-Credentials", "true")
				.putHeader("Content-Disposition", "attachment").setStatusCode(code)
				.end(Json.encodePrettily(new JsonObject().put("error", error)));
	}

}
