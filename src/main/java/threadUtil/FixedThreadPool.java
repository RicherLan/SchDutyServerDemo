package threadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

	static int threadnum = 2;
	public static ExecutorService threadPool;
	
	public static void startThreadPool() {
		threadPool = Executors.newFixedThreadPool(threadnum);
	}
	
	public static void shutdownThreadPool() {
		if(threadPool!=null) {
			threadPool.shutdown();
		}
	}
}
