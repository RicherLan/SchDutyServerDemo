package verticleServer.httpServerBoot;

import verticleServer.verticles.SchDutyVerticle;

public class VertXBootServer {

	public static void verticleServerStart() {
		Runner.runExample(SchDutyVerticle.class);
	}
	
}
