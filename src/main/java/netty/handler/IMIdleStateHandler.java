package netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import netty.util.SessionUtil;

import java.util.concurrent.TimeUnit;

public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 20;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        SessionUtil.unBindSession(ctx.channel());
        ctx.channel().close();
        
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    	System.out.println("断开Idle");
        SessionUtil.unBindSession(ctx.channel());
    }
    
}
