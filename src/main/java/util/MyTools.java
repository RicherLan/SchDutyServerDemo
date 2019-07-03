package util;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

public class MyTools {

	// 判断字符串是不是整数
	public static boolean isInteger(String str) {

		if (null == str || str.trim().equals("")) {
			return false;
		}
		// 不允许负数
		if (str.contains("-")) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	// 判断字符串是不是浮点数
	public static boolean isDouble(String str) {

		if (null == str || str.trim().equals("")) {
			return false;
		}
		// 不允许负数
		if (str.contains("-")) {
			return false;
		}
		if (str.startsWith("."))
			return false;
		if (str.endsWith("."))
			return false;
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static Vector<Integer> getTime() {

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;// 获取当前月份
		int day = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
		int way = c.get(Calendar.DAY_OF_WEEK) - 1;// 获取当前日期的星期

		Vector<Integer> times = new Vector<Integer>();
		times.add(year);
		times.add(month);
		times.add(day);
		times.add(way);

		return times;

	}

	// 通用MD5基础加密方式 采用16进制 可以别的进制 但是不能不采用
	// 否则生成的密文都是乱码 乱码的坏处 我也和你说一下
	// 比如登陆时 可能会把密码通过json方式传输 有的特殊字符就会产生问题
	// 比如 字符.和+ 等等 .不能进行字符串的切割 +是正则表达式特殊字符
	// 进制的好处 还有一个就是 生成密文时 直接对字节的位进行移位操作 简单
	public static String getMD5(String password) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {

			// 这个是java自带的加密包
			// import java.security.MessageDigest;
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			// 对要加密的密码进行加密
			messageDigest.update(password.getBytes());
			// 加密的结果 保存在这个字节数组中
			byte[] md5 = messageDigest.digest();

			// 加密的结果 就是把上面的字节数组转化为16进制的字符串
			// 为什么要16进制呢 因为16进制包含数字和6个字母
			// 否则就会有其他特殊字符 类似��d��z[����NW�� 是乱码 看起来不舒服
			String md52string = "";

			for (int i = 0; i < md5.length; i++) {
				byte b = md5[i];
				// >>>表示无符号的右移 也可以hexDigits[(b& 0xf)>>4];
				// 为什么要>>>呢 因为右移会产生负数
				// 也可以先b& 0xf 然后再>>4 这样的话 因为先b& 0xf 所以绝对不会负数
				// 分为两部是因为 一个byte占了8位 分别对这个b的高4位和低4位转换
				md52string += hexDigits[b >>> 4 & 0xf];
				md52string += hexDigits[b & 0xf];
			}

			return md52string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	//生成随机数字和字母,
	public static String getRandomPassword(int length) {

		String val = "";
		Random random = new Random();
		// length为几位密码
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	
}
