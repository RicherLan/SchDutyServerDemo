package netty.server;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import db.CorpDBService;
import db.UserDBService;
import test.MoniDBService;
import threadUtil.FixedThreadPool;

public class StartServer {

	public static void main(String[] args) {
		
		//开启netty服务
		NettyServer.getInstance().start();
        //开启业务线程池
		FixedThreadPool.startThreadPool();
		
		
		/*
		 * 更新数据库   社团部门的当前周  +1
		 */
		FixedThreadPool.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {	

				
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_WEEK,0);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
		        calendar.set(Calendar.MINUTE, 59);
		        calendar.set(Calendar.SECOND, 50); 
		        Date time = calendar.getTime();
		        
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					
					@Override 
					public void run() {
						CorpDBService.updateCorpZhou();
//						UserDBService.updateUserZhou();		
					}
				},time,1000*7*60*60*24);
				
			
			}
		});
	
			
	}
	
	
}
