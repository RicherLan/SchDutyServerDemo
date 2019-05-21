package js;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/*

执行js脚本
 */
public class ExecuteScript {

	public static void main(String[] args) {
		String password = "Wxr,.521";
		String modulus = "AIudkhA5fm2bH5uS4CseKogd+Svo7h3YDAWM3lwNG1rviIz9/5IlNICOsEFGeJWnfzOGXeY41cxshoYsS0DFY3RCfGbItq0GQjsJJwSaGCftGdot3i69+gfzFADal3cWNEctYVxjg/q6KqrsMVkDWs2O872lxBJfgUv5YOSbIaLh";
		String exponent = "AQAB";
		ExecuteScript executeScript = new ExecuteScript();
		executeScript.executeScript(modulus, exponent, password);
	}

	private static InputStream is1;
	private static BufferedReader br1;
	private static InputStream is2;
	private static BufferedReader br2;
	private static InputStream is3;
	private static BufferedReader br3;
	private static InputStream is4;
	private static BufferedReader br4;
	private static InputStream is5;
	private static BufferedReader br5;
	private static InputStream is6;
	private static BufferedReader br6;

	public static boolean isInit = false;

	public void initJS() {

		/*
		 * jar包下
		 */
		/*
		 * String path1 = "resources/base64.js";
		 * is1=ExecuteScript.class.getClassLoader().getResourceAsStream(path1); br1=new
		 * BufferedReader(new InputStreamReader(is1));
		 * 
		 * String path2 = "resources/jsbn.js";
		 * is2=ExecuteScript.class.getClassLoader().getResourceAsStream(path2); br2=new
		 * BufferedReader(new InputStreamReader(is2));
		 * 
		 * String path3 = "resources/passwordencrypt.js";
		 * is3=ExecuteScript.class.getClassLoader().getResourceAsStream(path3); br3=new
		 * BufferedReader(new InputStreamReader(is3));
		 * 
		 * String path4 = "resources/prng4.js";
		 * is4=ExecuteScript.class.getClassLoader().getResourceAsStream(path4); br4=new
		 * BufferedReader(new InputStreamReader(is4));
		 * 
		 * String path5 = "resources/rng.js";
		 * is5=ExecuteScript.class.getClassLoader().getResourceAsStream(path5); br5=new
		 * BufferedReader(new InputStreamReader(is5));
		 * 
		 * String path6 = "resources/rsa.js";
		 * is6=ExecuteScript.class.getClassLoader().getResourceAsStream(path6); br6=new
		 * BufferedReader(new InputStreamReader(is6));
		 * 
		 */
		/*
		 * eclipse 下
		 */

		String path1 = "base64.js";
		is1 = ExecuteScript.class.getClassLoader().getResourceAsStream(path1);
		br1 = new BufferedReader(new InputStreamReader(is1));

		String path2 = "jsbn.js";
		is2 = ExecuteScript.class.getClassLoader().getResourceAsStream(path2);
		br2 = new BufferedReader(new InputStreamReader(is2));

		String path3 = "passwordencrypt.js";
		is3 = ExecuteScript.class.getClassLoader().getResourceAsStream(path3);
		br3 = new BufferedReader(new InputStreamReader(is3));

		String path4 = "prng4.js";
		is4 = ExecuteScript.class.getClassLoader().getResourceAsStream(path4);
		br4 = new BufferedReader(new InputStreamReader(is4));

		String path5 = "rng.js";
		is5 = ExecuteScript.class.getClassLoader().getResourceAsStream(path5);
		br5 = new BufferedReader(new InputStreamReader(is5));

		String path6 = "rsa.js";
		is6 = ExecuteScript.class.getClassLoader().getResourceAsStream(path6);
		br6 = new BufferedReader(new InputStreamReader(is6));

	}

	public String executeScript(String modulus, String exponent, String password) {

		String enpassword = null;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {

			// initJS();

			/*
			 * jar包下
			 */

			String path1 = "resources/base64.js";
			is1 = ExecuteScript.class.getClassLoader().getResourceAsStream(path1);
			br1 = new BufferedReader(new InputStreamReader(is1));

			String path2 = "resources/jsbn.js";
			is2 = ExecuteScript.class.getClassLoader().getResourceAsStream(path2);
			br2 = new BufferedReader(new InputStreamReader(is2));

			String path3 = "resources/passwordencrypt.js";
			is3 = ExecuteScript.class.getClassLoader().getResourceAsStream(path3);
			br3 = new BufferedReader(new InputStreamReader(is3));

			String path4 = "resources/prng4.js";
			is4 = ExecuteScript.class.getClassLoader().getResourceAsStream(path4);
			br4 = new BufferedReader(new InputStreamReader(is4));

			String path5 = "resources/rng.js";
			is5 = ExecuteScript.class.getClassLoader().getResourceAsStream(path5);
			br5 = new BufferedReader(new InputStreamReader(is5));

			String path6 = "resources/rsa.js";
			is6 = ExecuteScript.class.getClassLoader().getResourceAsStream(path6);
			br6 = new BufferedReader(new InputStreamReader(is6));

			/*
			 * eclipse 下
			 */
			/*
			 * String path1 = "base64.js";
			 * is1=ExecuteScript.class.getClassLoader().getResourceAsStream(path1); br1=new
			 * BufferedReader(new InputStreamReader(is1));
			 * 
			 * String path2 = "jsbn.js";
			 * is2=ExecuteScript.class.getClassLoader().getResourceAsStream(path2); br2=new
			 * BufferedReader(new InputStreamReader(is2));
			 * 
			 * String path3 = "passwordencrypt.js";
			 * is3=ExecuteScript.class.getClassLoader().getResourceAsStream(path3); br3=new
			 * BufferedReader(new InputStreamReader(is3));
			 * 
			 * String path4 = "prng4.js";
			 * is4=ExecuteScript.class.getClassLoader().getResourceAsStream(path4); br4=new
			 * BufferedReader(new InputStreamReader(is4));
			 * 
			 * String path5 = "rng.js";
			 * is5=ExecuteScript.class.getClassLoader().getResourceAsStream(path5); br5=new
			 * BufferedReader(new InputStreamReader(is5));
			 * 
			 * String path6 = "rsa.js";
			 * is6=ExecuteScript.class.getClassLoader().getResourceAsStream(path6); br6=new
			 * BufferedReader(new InputStreamReader(is6));
			 */

			engine.eval(br1);
			engine.eval(br2);
			engine.eval(br3);
			engine.eval(br4);
			engine.eval(br5);
			engine.eval(br6);

			if (engine instanceof Invocable) {
				Invocable invocable = (Invocable) engine;
				JavaScriptInterface executeMethod = invocable.getInterface(JavaScriptInterface.class);
				enpassword = executeMethod.getEncryptPassword(modulus, exponent, password);

			}
			return enpassword;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} finally {
			try {
				br1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				br2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is2.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				br3.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is3.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				br4.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is4.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				br5.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is5.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				br6.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			try {
				is6.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

		}

	}

}
