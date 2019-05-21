package netty.protocol.packet;

import static netty.protocol.command.Command.GetGroupChatTextNotRead_PACKET;

import java.util.Vector;

import netty.protocol.Packet;
import object.ChatMsg;

/*
 * 	拿到群聊天  未读信息     一般是刚登陆的时候
 * 发送多条未读的文字消息
 */
public class GetGroupChatTextNotRead_PACKET extends Packet{
	
	Vector<ChatMsg> chatMsgs;
	@Override
	public int getCommand() {
		
		return GetGroupChatTextNotRead_PACKET;
	}
	public Vector<ChatMsg> getChatMsgs() {
		return chatMsgs;
	}
	public void setChatMsgs(Vector<ChatMsg> chatMsgs) {
		this.chatMsgs = chatMsgs;
	}
	

}
