package util;

public class EncryptTool {

	// 加密字符串
	public static String encryptStr(String str) {
		// 讲获取的字符串转成字符数组
		char[] c = str.toCharArray();
		// 使用for循环给字符数组加密
		for (int i = 0; i < c.length; i++) {
			c[i] = (char) (c[i] ^ 20000);
		}

		return new String(c);
	}

}
