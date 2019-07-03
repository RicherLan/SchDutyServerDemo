package verticleServer.service.user;

public interface IUserService {

	void regVerificcationCode();             //发送短信验证码
	String regisUser(String schoolname, String collegename, String majorname, int ruxueyear,
			String phonenumber, String password); //注册用户
}
