package netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;


    /*
     * 	因为在通讯协议中会传输command，所以packet中的command就不需要额外传输了。
     */
    @JSONField(serialize = false)
    public abstract int getCommand();


	public Byte getVersion() {
		return version;
	}


	public void setVersion(Byte version) {
		this.version = version;
	}
    
  
    
}
