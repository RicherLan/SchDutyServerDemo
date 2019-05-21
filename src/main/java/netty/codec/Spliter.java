package netty.codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import netty.protocol.PacketCodec;
import netty.util.SessionUtil;

/*
 * 	不能单例化   因为这个是根据长度来拆包的     比如对面发来的两个数据包其实是一个数据包
 * 	如果单例化的话   就会有很多对面（多个客户端）    包就乱了   没法拆包念包
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 10;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    	
    	if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
    		SessionUtil.unBindSession(ctx.channel());
    		ctx.channel().close();
           
            return null;
        }
    	
        return super.decode(ctx, in);
    }
    
}
