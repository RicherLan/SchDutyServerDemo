package educationSystem;

public class UJN_URLManager {

	//public static String url_home = "http://jwgl6.ujn.edu.cn/jwglxt/xtgl/login_slogin.html?language=zh_CN&_t=";
   //登录和提交账号密码的网址     居然是一样的      济大可以啊   这快烦了好久
	public static String url_login = "http://jwgl6.ujn.edu.cn/jwglxt/xtgl/login_slogin.html";
    
    //课表查询 请求的网址     最后加上学号
    public static String url_get_kebiao = "http://jwgl6.ujn.edu.cn/jwglxt/kbcx/xskbcx_cxXskbcxIndex.html?gnmkdm=N2151&layout=default&su=";
    //课表的主界面
    public static String url_kebiao_home = "http://jwgl6.ujn.edu.cn/jwglxt/kbcx/xskbcx_cxXsKb.html?gnmkdm=N2151";
    
    
    //成绩查询 请求的网址   最后加上学号
    public static String url_get_score = "http://jwgl6.ujn.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?gnmkdm=N305005&layout=default&su=";
    //成绩主界面
    public static String url_score_home= "http://jwgl6.ujn.edu.cn/jwglxt/cjcx/cjcx_cxDgXscj.html?doType=query&gnmkdm=N305005";

    
    //空教室查询
    public static String url_searchEmptyClassroom_go = "http://jwgl6.ujn.edu.cn/jwglxt/cdjy/cdjy_cxKxcdlb.html?doType=query&gnmkdm=N2155";
    public static String url_searchEmptyClassroom_home = "http://jwgl6.ujn.edu.cn/jwglxt/cdjy/cdjy_cxKxcdlb.html?gnmkdm=N2155&layout=default&su=";

}
