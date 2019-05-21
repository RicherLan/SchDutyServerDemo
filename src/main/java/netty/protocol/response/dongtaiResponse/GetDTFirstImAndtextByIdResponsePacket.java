package netty.protocol.response.dongtaiResponse;

import netty.protocol.Packet;
import static netty.protocol.command.Command.GetDTFirstImAndtextById_RESPONSE;

/*
        获得动态的第一张图片和内容
 */
public class GetDTFirstImAndtextByIdResponsePacket extends Packet {

	int dtid;
	String userid;
	String username;
	String content;
	
	byte[] firstImage;
	
    @Override
    public int getCommand() {

        return GetDTFirstImAndtextById_RESPONSE;
    }

	public int getDtid() {
		return dtid;
	}

	public void setDtid(int dtid) {
		this.dtid = dtid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getFirstImage() {
		return firstImage;
	}

	public void setFirstImage(byte[] firstImage) {
		this.firstImage = firstImage;
	}

}
