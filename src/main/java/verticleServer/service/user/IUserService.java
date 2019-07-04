package verticleServer.service.user;

public interface IUserService {

	void regVerificcationCode();             //发送短信验证码
	String regisUser(String schoolname, String collegename, String majorname, int ruxueyear,
			String phonenumber, String password); //注册用户
	
	String changePassword(String phonenumber,String password);  //忘记密码时  修改密码
	boolean isPhoneReg(String phonenumber);  //某手机号是否被注册
}
