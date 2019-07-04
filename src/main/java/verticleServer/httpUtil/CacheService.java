package verticleServer.httpUtil;

import java.util.HashMap;
import java.util.Map;

import verticleServer.entity.user.ForgetPass;
import verticleServer.entity.user.RegPhone;

public class CacheService {

	//用户注册手机号信息   防止刷短信
	public static Map<String, RegPhone> phoneRegMap = new HashMap<String, RegPhone>();
	
	public static Map<String, ForgetPass> phoneForgetPassMap = new HashMap<String, ForgetPass>();
	
}
