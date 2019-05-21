package js;

/*
执行js脚本的接口

RSA加密  密码
 */
public interface JavaScriptInterface {

    public String getEncryptPassword(String modulus, String exponent, String password);

}
