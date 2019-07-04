package verticleServer.routePath;

public final class UserRoutePath {

	private UserRoutePath() {
	}
	
	public static final String API_REG_VERIFICATION_CODE = "/user/reg/verification_code";     //注册时短信验证码
	public static final String API_REG_IMAGE_CODE = "/user/reg/image_code";     //注册时短信验证码获得图片验证码
	public static final String API_REG_USERINFO = "/user/reg/userinfo";           //注册用户信息

	public static final String API_FORGETPASS_IMAGE_CODE = "/user/password/forget_image_code";     //忘记密码时获得图片验证码
	public static final String API_FORGETPASS_VERIFICATION_CODE = "/user/password/forget_verification_code"; //忘记密码时短信验证码
	public static final String API_FORGETPASS_SUBMITPASS = "/user/password/forget_submitpass";   //忘记密码提交新密码
	
}
