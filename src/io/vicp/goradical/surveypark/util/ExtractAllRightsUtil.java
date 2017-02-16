package io.vicp.goradical.surveypark.util;

import io.vicp.goradical.surveypark.service.RightService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

/**
 * 提取所有权限工具类
 */
public class ExtractAllRightsUtil {
	public static void main(String[] args) throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		RightService rightService = (RightService) ac.getBean("rightService");

		ClassLoader loader = ExtractAllRightsUtil.class.getClassLoader();
		URL url = loader.getResource("io/vicp/goradical/surveypark/struts2/action");
		File dir = new File(url.toURI());
		File[] files = dir.listFiles();
		String fileName;
		for (File file : files) {
			fileName = file.getName();
			if (fileName.endsWith(".class") && !fileName.equals("BaseAction.class")) {
				processAction(fileName, rightService);
			}
		}
	}

	/**
	 * 处理Action类,捕获所有URL地址，形成权限
	 * @param fileName
	 */
	private static void processAction(String fileName, RightService rightService) {
		try {
			String pkgName = "io.vicp.goradical.surveypark.struts2.action";
			String simpleClassName = fileName.substring(0, fileName.indexOf(".class"));
			String className = pkgName + "." + simpleClassName;
			//得到具体类
			Class clazz = Class.forName(className);
			Method[] methods = clazz.getDeclaredMethods();
			Class retType;
			String mName;
			Class[] paramType;
			String url;
			for (Method m : methods) {
				retType = m.getReturnType();
				mName = m.getName();
				paramType = m.getParameterTypes();
				if (retType == String.class && !ValidateUtil.isValid(paramType) && Modifier.isPublic(m.getModifiers())) {
					if ("execute".equals(mName)) {
						url = "/" + simpleClassName;
					} else {
						url = "/" + simpleClassName + "_" + mName;
					}
					rightService.appendRightByURL(url);
					System.out.println(url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
