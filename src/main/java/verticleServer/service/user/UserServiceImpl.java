package verticleServer.service.user;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import db.DBManager;

public class UserServiceImpl implements IUserService {

	// 发送验证码
	@Override
	public void regVerificcationCode() {
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAITVqr9eUV8Fht",
				"i5PCnlCLGDFeXV2yRO1GeZfbxXltby");
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");

		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}

	}

	// 注册用户
	public String regisUser(String schoolname, String collegename, String majorname, int ruxueyear,
			String phonenumber, String password) {

		Connection conn = null;
		try {
			conn = DBManager.getConnection();
			String string = "select phonenumber from users where phonenumber='" + phonenumber + "'";
			ResultSet resultSet = conn.createStatement().executeQuery(string);
			if (resultSet.next()) {
				return "hasregisted";
			}

			String sql = "INSERT INTO users(phonenumber,password,schoolname,departmentname,majorname,ruxueyear) VALUES(?,?,?,?,?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, phonenumber);
			pst.setString(2, password);
			pst.setString(3, schoolname);
			pst.setString(4, collegename);
			pst.setString(5, majorname);
			pst.setInt(6, ruxueyear);

			int row = pst.executeUpdate();
			if (row <= 0) {
				return "errdb";
			}

			return "ok";

		} catch (Exception a) {
			a.printStackTrace();
			return "errorother";

		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
