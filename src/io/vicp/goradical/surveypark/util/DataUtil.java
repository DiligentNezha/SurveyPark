package io.vicp.goradical.surveypark.util;

import java.io.*;
import java.security.MessageDigest;

/**
 * 数据工具类
 */
public class DataUtil {
	public static void main(String[] args) {
		System.out.println(md5("root"));
	}
	/**
	 * 使用MD5算法进行加密
	 * @param src
	 * @return
	 */
	public static String md5(String src) {
		try {
			StringBuffer sb = new StringBuffer();
			char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
			byte[] bytes = src.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(bytes);
			for (byte b : digest) {
				sb.append(chars[(b >> 4) & 0x0F]);
				sb.append(chars[b & 0x0F]);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 深度复制，复制的整个对象图
	 * @param src
	 * @return
	 */
	public static Serializable deeplyCopy(Serializable src) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.close();
			baos.close();

			byte[] bytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bais.close();
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
