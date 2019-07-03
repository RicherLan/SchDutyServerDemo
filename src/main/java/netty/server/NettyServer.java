package netty.server;

import java.util.Date;

import com.fasterxml.jackson.databind.cfg.ConfigFeature;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.codec.PacketCodecHandler;
import netty.codec.Spliter;
import netty.handler.IMIdleStateHandler;
import netty.server.handler.AuthHandler;
import netty.server.handler.HeartBeatRequestHandler;
import netty.server.handler.IMHandler;
import netty.server.handler.LoginRequestHandler;
import netty.server.userPersonalInfoHandler.IsPhRegistedRequestHandler;
import netty.server.userPersonalInfoHandler.RegisTestRequestHandler;
import util.Config;


public class NettyServer {

	private static NettyServer instance = null;


    public static NettyServer getInstance() {
        if (instance == null) {
            synchronized (NettyServer.class) {
                if (instance == null) {
                    instance = new NettyServer();
                }
            }
        }
        return instance;
    }
    
    private NettyServer() {
    }
    
    public void start() {
    	 NioEventLoopGroup bossGroup = new NioEventLoopGroup();
         NioEventLoopGroup workerGroup = new NioEventLoopGroup();

         final ServerBootstrap serverBootstrap = new ServerBootstrap();
         serverBootstrap
                 .group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .option(ChannelOption.SO_BACKLOG, 1024)
                 .childOption(ChannelOption.SO_KEEPALIVE, true)
                 .childOption(ChannelOption.TCP_NODELAY, true)
                 .childHandler(new ChannelInitializer<NioSocketChannel>() {
                     protected void initChannel(NioSocketChannel ch) {
                    	 System.out.println("连接到来");
                    	 // 空闲检测
                         ch.pipeline().addLast(new IMIdleStateHandler());
                         ch.pipeline().addLast(new Spliter());
                         ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        
                         ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        // ch.pipeline().addLast(IsPhRegistedRequestHandler.INSTANCE);
                         ch.pipeline().addLast(RegisTestRequestHandler.INSTANCE);
                         
                         ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                         ch.pipeline().addLast(AuthHandler.INSTANCE);
                         ch.pipeline().addLast(IMHandler.INSTANCE);
                     }
                 });


         bind(serverBootstrap, Config.nettyPort);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
