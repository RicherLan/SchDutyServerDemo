package util;

import java.util.Random;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import io.vertx.core.json.JsonObject;





/*
 * 	
 */
public class AliService {

	//用户注册    发送阿里短信验证码
	public static boolean sendRegCode(String phone,String code) {
		
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAITVqr9eUV8Fht", "i5PCnlCLGDFeXV2yRO1GeZfbxXltby");
        IAcsClient client = new DefaultAcsClient(profile);
        
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName","刚下课");
        request.putQueryParameter("TemplateCode","SMS_143717945");
        request.putQueryParameter("TemplateParam","{\"code\":\""+code+"\"}");
        
        
        try {
            CommonResponse response = client.getCommonResponse(request);
          //  {"Message":"OK","RequestId":"510647C4-2DA6-4AD9-BE79-0D987BE44A01","BizId":"781816662071107648^0","Code":"OK"}
            String body = response.getData();
            System.out.println(body);
            JsonObject jsonObject = new JsonObject(body);
            String message = jsonObject.getString("Message");
            if(message.equals("OK")) {
            	return true;
            }
            return false;
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
		
	}
	
	/*
	 * 	生成短信验证码  数字
	 */
	public static String getVerificationCode(int length) {
		String val = "";
		Random random = new Random();
		// length为几位
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}
	
	
	public static void main(String[] args) {
		sendRegCode("15254138809","shit");
	}
	
}
