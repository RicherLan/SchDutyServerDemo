package netty.protocol.response.otherResponse;

import netty.protocol.Packet;
import object.GroupBasicInfo;

import static netty.protocol.command.Command.SearchAddGroup_RESPONSE;


/*
客户端添加群时   搜索账号 只需要看到其账号 网名 头像就行
 */
public class SearchAddGroupResponsePacket extends Packet{

	
	String result;
	GroupBasicInfo GroupBasicInfo;
	
    @Override
    public int getCommand() {

        return SearchAddGroup_RESPONSE;
    }

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public GroupBasicInfo getGroupBasicInfo() {
		return GroupBasicInfo;
	}

	public void setGroupBasicInfo(GroupBasicInfo groupBasicInfo) {
		GroupBasicInfo = groupBasicInfo;
	}

	

}
