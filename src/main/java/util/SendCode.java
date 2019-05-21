package util;

import org.apache.commons.mail.HtmlEmail;

import java.util.HashMap;
import java.util.Map;

public class SendCode {

	public static Map<String, String> codemap = new HashMap<String, String>();      //验证码哈希表
	public static Map<String, Integer> codetimes = new HashMap<String, Integer>();   //某手机号今天一共申请了多少次  最多3次
		
	public static void sendEmail(String emailaddress, String code) {
		try {
			HtmlEmail htmlEmail = new HtmlEmail();
			// 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
			htmlEmail.setHostName("smtp.163.com");
			// 字符编码集的设置
			htmlEmail.setCharset("UTF-8");
			// 收件地址
			htmlEmail.addTo(emailaddress);
			// 发送人的邮箱
			htmlEmail.setFrom("m18265953928@163.com", "123456网盘验证码");
			// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			htmlEmail.setAuthentication("m18265953928@163.com", "zsq9898.");
			// 发送的邮件主题
			htmlEmail.setSubject("网盘验证码000");
			// 发送的邮件内容
			htmlEmail.setMsg("验证码是：" + code);
			htmlEmail.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
