package htmlnet;

import js.ExecuteScript;
import net.sf.json.JSONObject;
import object.ClassRoom;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.mail.handlers.text_html;

import educationSystem.UJN_URLManager;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class javatest {

	public String username = "20161222159";
	public String password = "Wxr,.521";
	public String enPassword = "";
	public String csrftoken = null;
	private String exponent = null;
	private String modulus = null;
	public String cookieid = null;
	public Response rs = null;

	public static void main(String[] args) {

		javatest test = new javatest();
		try {
			
			// test.login2();
			// test.getscore(2017, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void login2() throws IOException {

		Connection con = Jsoup
				.connect("http://asset.ata-test.net/release/exam-sp/enterprise/dist/html/ycjjr/courseware1.html");// 获取连接
		// con.header("User-Agent" ,"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0)
		// Gecko/20100101 Firefox/61.0");

		rs = con.execute();// 获取响应
		Document document = Jsoup.parse(rs.body());// 转换为Dom树

		System.out.println(rs.body());

	}

	public String login() throws Exception {

		long time = new Date().getTime();
		Connection con = Jsoup.connect(UJN_URLManager.url_login);// 获取连接
		// con.header("User-Agent" ,"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0)
		// Gecko/20100101 Firefox/61.0");

		rs = con.execute();// 获取响应
		Document document = Jsoup.parse(rs.body());// 转换为Dom树
		// System.out.println(rs.cookies());
		String cookiestr = rs.cookies().toString();
		cookieid = cookiestr.substring(1, cookiestr.length() - 1);
		// System.out.println(cookieid);
		Elements elements = document.select("input[type=hidden]");
		Element element = elements.get(4);
		csrftoken = element.attr("value");

		// System.out.println(csrftoken);

		String getPublicKeySurl = "http://jwgl6.ujn.edu.cn/jwglxt/xtgl/login_getPublicKey.html?time=" + time;
		Connection con2 = Jsoup.connect(getPublicKeySurl);// 获取连接
		con2.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		rs = con2.ignoreContentType(true).method(Method.GET).cookies(rs.cookies()).execute();
		String string = rs.body().toString();

		JSONObject jsonObject = JSONObject.fromObject(string);
		exponent = jsonObject.getString("exponent");
		modulus = jsonObject.getString("modulus");
		ExecuteScript executeScript = new ExecuteScript();
		enPassword = executeScript.executeScript(modulus, exponent, password);

		// System.out.println(enPassword);

		Map<String, String> header = new HashMap<String, String>();

		header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		header.put("Accept-Encoding", "gzip,deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		header.put("Cache-Control", "max-age=0");
		header.put("Connection", "keep-alive");
		header.put("Host", "jwgl6.ujn.edu.cn");
		header.put("Upgrade-Insecure-Requests", "1");
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put("Origin", "http://jwgl6.ujn.edu.cn");
		header.put("Referer", UJN_URLManager.url_login);
		header.put("Cookie", cookieid);

		Map<String, String> datas = new HashMap<String, String>();

		datas.put("csrftoken", csrftoken);
		datas.put("yhm", username);
		datas.put("mm", enPassword);
		datas.put("mm", enPassword);

		Connection con3 = Jsoup.connect(UJN_URLManager.url_login);// 获取连接
		con3.headers(header);

		Response login = con3.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies()).execute();

		Document document2 = Jsoup.parse(login.body());
		Elements elements2 = document2.select("#tips");
		if (elements2.size() != 0) {
			// Element element2=elements2.get(0);
			// String tips =element2.text();

			// System.out.println(tips+" ]]]]]]]]]]]]]]]]]]]]]]]]");
			return "errorUsernameOrPwd";
		}
		return "ok";

		// 打印，登陆成功后的信息
		// System.out.println(login.body());

		// System.out.println("*********************************************");
		// getkebiao("2017", "12");
		// getscore("2017", "12");
		// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
		// Map<String, String> map = login.cookies();
		// for (String s : map.keySet()) {
		// System.out.println(s + " " + map.get(s));
		// }

	}

	// *************************************************************
	// 课表在济大教务处的查询方法 获得xnm和xqm后 调用js的searchResult1()获得结果
	// <button type="button" class="btn btn-primary btn-sm pull-right"
	// id="search_go1" onclick="searchResult1()"><span class="bigger-120 glyphicon
	// glyphicon-search"></span> 查询</button>

	/*
	 * xnm用开头的学年表示 比如2017-2018 那么 xnm等于2017 <option value="2024">2024-2025</option>
	 * <option value="2023">2023-2024</option> <option
	 * value="2022">2022-2023</option> <option value="2021">2021-2022</option>
	 * <option value="2020">2020-2021</option> <option
	 * value="2019">2019-2020</option> <option value="2018">2018-2019</option>
	 * <option value="2017" selected="selected">2017-2018</option> <option
	 * value="2016">2016-2017</option> <option value="2015">2015-2016</option>
	 * <option value="2014">2014-2015</option> <option
	 * value="2013">2013-2014</option> <option value="2012">2012-2013</option>
	 * 
	 * 
	 * 学期是这样的 xqm等于3代表第一学期 12代表第2学期 <option value="3">1</option> <option value="12"
	 * selected="selected">2</option> <option value="16">3</option>
	 * 
	 * 
	 */

	// xnm表示学年 xqm表示学期 因为在济大网站的js中就是这样起名的
	public String getkebiao(int xnm, int xqm) throws Exception {

		if (xqm == 1) {
			xqm = 3;
		} else if (xqm == 2) {
			xqm = 12;
		} else if (xqm == 3) {
			xqm = 16;
		}

		String rString = login();
		if (rString.equals("errorUsernameOrPwd")) {
			return "errorUsernameOrPwd";
		}

		Map<String, String> header = new HashMap<String, String>();

		header.put("Accept", "*/*");
		header.put("Accept-Encoding", "gzip,deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Connection", "keep-alive");
		header.put("Host", "jwgl6.ujn.edu.cn");
		// header.put( "Upgrade-Insecure-Requests" ,"1");
		header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		header.put("Origin", "http://jwgl6.ujn.edu.cn");
		header.put("Referer", UJN_URLManager.url_get_kebiao + username);
		header.put("Cookie", cookieid);

		Map<String, String> datas = new HashMap<String, String>();

		datas.put("xnm", xnm + "");
		datas.put("xqm", xqm + "");

		Connection con3 = Jsoup.connect(UJN_URLManager.url_kebiao_home);// 获取连接
		con3.headers(header);

		Response getkebiao = con3.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies())
				.execute();
		// 打印，登陆成功后的信息
		// System.out.println(getkebiao.body());
		return getkebiao.body();

	}

	/*
	 * <option value="2024">2024-2025</option> <option
	 * value="2023">2023-2024</option> <option value="2022">2022-2023</option>
	 * <option value="2021">2021-2022</option> <option
	 * value="2020">2020-2021</option> <option value="2019">2019-2020</option>
	 * <option value="2018">2018-2019</option> <option value="2017"
	 * selected="selected">2017-2018</option> <option
	 * value="2016">2016-2017</option> <option value="2015">2015-2016</option>
	 * <option value="2014">2014-2015</option> <option
	 * value="2013">2013-2014</option> <option value="2012">2012-2013</option>
	 * 
	 * 
	 * <option value="3">1</option> <option value="12"
	 * selected="selected">2</option> <option value="16">3</option>
	 * 
	 * 
	 */
	// 获得学生成绩
	public String getscore(int xnm, int xqm) throws Exception {

		if (xqm == 1) {
			xqm = 3;
		} else if (xqm == 2) {
			xqm = 12;
		} else if (xqm == 3) {
			xqm = 16;
		}

		String rString = login();
		if (rString.equals("errorUsernameOrPwd")) {
			return "errorUsernameOrPwd";
		}

		Map<String, String> header = new HashMap<String, String>();

		header.put("Accept", "application/json, text/javascript, */*; q=0.01");
		header.put("Accept-Encoding", "gzip,deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Connection", "keep-alive");
		header.put("Host", "jwgl6.ujn.edu.cn");
		// header.put( "Upgrade-Insecure-Requests" ,"1");
		header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		header.put("Origin", "http://jwgl6.ujn.edu.cn");
		header.put("Referer", UJN_URLManager.url_get_score + username);
		header.put("Cookie", cookieid);

		Map<String, String> datas = new HashMap<String, String>();
		long time = new Date().getTime();
		datas.put("_search", "false");
		datas.put("nd", time + "");
		datas.put("queryModel.currentPage", "1");
		datas.put("queryModel.showCount", "15");
		datas.put("queryModel.sortName", "");
		datas.put("queryModel.sortOrder", "asc");
		datas.put("time", "0");
		datas.put("xnm", xnm + "");
		datas.put("xqm", xqm + "");

		Connection con = Jsoup.connect(UJN_URLManager.url_score_home);// 获取连接
		con.headers(header);

		Response getkebiao = con.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies())
				.execute();
		// 打印，登陆成功后的信息
		// System.out.println(getkebiao.body());
		return getkebiao.body();

	}

	
	public Vector<ClassRoom> searchEmptyClassrooms(int xnm ,int xqm, String lh,int zcd ,int xqj,Vector<Integer> jieshu) throws Exception {
			
		if (xqm == 1) {
			xqm = 3;
		} else if (xqm == 2) {
			xqm = 12;
		} else if (xqm == 3) {
			xqm = 16;
		}

		int jcd = 0;
		for(Integer jie: jieshu) {
			
			jcd += Math.pow(2, jie-1);
		}
		
//		String rString = login();
//		if (rString.equals("errorUsernameOrPwd")) {
//			return "errorUsernameOrPwd";
//		}

		
		Map<String, String> header = new HashMap<String, String>();

		header.put("Accept", "application/json, text/javascript, */*; q=0.01");
		header.put("Accept-Encoding", "gzip,deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Connection", "keep-alive");
		header.put("Host", "jwgl6.ujn.edu.cn");
		// header.put( "Upgrade-Insecure-Requests" ,"1");
		header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		header.put("Origin", "http://jwgl6.ujn.edu.cn");
		header.put("Referer", UJN_URLManager.url_searchEmptyClassroom_home + username);
		header.put("Cookie", cookieid);

		Map<String, String> datas = new HashMap<String, String>();
	
		datas.put("fwzt", "cx");
		datas.put("xqh_id", "1");
		datas.put("xnm", xnm+"");
		datas.put("xqm", xqm+"");
		datas.put("cdlb_id", "");
		datas.put("cdejlb_id", "");
		datas.put("qszws", "");
		datas.put("jszws", "");
		datas.put("cdmc", "");
		datas.put("lh", lh+"");
		datas.put("qssd", "");
		datas.put("jssd", "");
		datas.put("qssj", "");
		datas.put("jssj", "");

		datas.put("jyfs", "0");
		datas.put("cdjylx", "");
		datas.put("zcd", zcd+"");
		datas.put("xqj",xqj+ "");
		datas.put("jcd", jcd+"");
		datas.put("_search", "false");
		
		long timetemp = System.currentTimeMillis();
		datas.put("nd", timetemp + "");
		datas.put("queryModel.showCount", "15");
		datas.put("queryModel.currentPage", "1");
		datas.put("queryModel.sortName", "cdbh");
		datas.put("queryModel.sortOrder", "asc");
		datas.put("time", "1");

		Connection con = Jsoup.connect(UJN_URLManager.url_searchEmptyClassroom_go);// 获取连接
		con.headers(header);

		Response getEmptyClassRooms = con.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies())
				.execute();

		String json = getEmptyClassRooms.body();
		
		Vector<ClassRoom> classRooms = new Vector<ClassRoom>();
		classRooms = HtmlPrase.covertClassRoom(json);
		
//		for(int k=0;k<classRooms.size();++k) {
//			System.out.println(classRooms.get(k).getRoomName());
//		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		int totalPage = jsonObject.getInt("totalPage");
		
		for(int i=2;i<=totalPage;++i) {
			String jsontemp=searchEmptyClassrooms2(i,xnm , xqm, lh , zcd  ,xqj ,jcd);
			
			Vector<ClassRoom> temp = HtmlPrase.covertClassRoom(jsontemp);
			if(classRooms==null) {
				classRooms = new Vector<ClassRoom>();
			}
			
			for(ClassRoom classRoom : temp) {
				classRooms.add(classRoom);
			}
		}
		
		return classRooms;
		

	}
	
	public String searchEmptyClassrooms2(int currentPage,int xnm ,int xqm, String lh,int zcd ,int xqj,int jcd) throws Exception {

		Map<String, String> header = new HashMap<String, String>();

		header.put("Accept", "application/json, text/javascript, */*; q=0.01");
		header.put("Accept-Encoding", "gzip,deflate");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0");
		header.put("X-Requested-With", "XMLHttpRequest");
		header.put("Connection", "keep-alive");
		header.put("Host", "jwgl6.ujn.edu.cn");
		// header.put( "Upgrade-Insecure-Requests" ,"1");
		header.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		header.put("Origin", "http://jwgl6.ujn.edu.cn");
		header.put("Referer", UJN_URLManager.url_searchEmptyClassroom_home + username);
		header.put("Cookie", cookieid);

		Map<String, String> datas = new HashMap<String, String>();
		long time = new Date().getTime();
		datas.put("fwzt", "cx");
		datas.put("xqh_id", "1");
		datas.put("xnm", xnm+"");
		datas.put("xqm", xqm+"");
		datas.put("cdlb_id", "");
		datas.put("cdejlb_id", "");
		datas.put("qszws", "");
		datas.put("jszws", "");
		datas.put("cdmc", "");
		datas.put("lh", lh+"");
		datas.put("qssd", "");
		datas.put("jssd", "");
		datas.put("qssj", "");
		datas.put("jssj", "");

		datas.put("jyfs", "0");
		datas.put("cdjylx", "");
		datas.put("zcd", zcd+"");
		datas.put("xqj",xqj+ "");
		datas.put("jcd", jcd+"");

		datas.put("_search", "false");
		long timetemp = System.currentTimeMillis();
		datas.put("nd", timetemp + "");
		datas.put("queryModel.showCount", "15");
		datas.put("queryModel.currentPage", currentPage+"");
		datas.put("queryModel.sortName", "cdbh");
		datas.put("queryModel.sortOrder", "asc");
		datas.put("time", "1");

		Connection con = Jsoup.connect(UJN_URLManager.url_searchEmptyClassroom_go);// 获取连接
		con.headers(header);

		Response getkebiao = con.ignoreContentType(true).method(Method.POST).data(datas).cookies(rs.cookies())
				.execute();

		String json = getkebiao.body();
		
		return json;
	}

}
