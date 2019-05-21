package  netty.protocol.request.otherRequest;

import  netty.protocol.Packet;

import static  netty.protocol.command.Command.LOGIN_REQUEST;


public class LoginRequestPacket extends Packet {
    private String userName;
    private String password;
    private String type;
    public LoginRequestPacket() {

	}
    @Override
    public int getCommand() {

        return LOGIN_REQUEST;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
