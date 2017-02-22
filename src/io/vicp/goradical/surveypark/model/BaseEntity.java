package io.vicp.goradical.surveypark.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 抽象的实体超类，专门用于继承
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 4443192900681947539L;

	public abstract Integer getId();

	public abstract void setId(Integer id);

	@Override
	public String toString() {
		try {
			StringBuffer buffer = new StringBuffer();
			Class clazz = getClass();
			String simpleName = clazz.getSimpleName();
			buffer.append(simpleName);
			buffer.append("{");
			Field[] fields = clazz.getDeclaredFields();
			Class ftype;
			String fname;
			Object fvalue;
			for (Field field : fields) {
				ftype = field.getType();
				fname = field.getName();
				field.setAccessible(true);
				fvalue = field.get(this);
				if ((ftype.isPrimitive()
						|| ftype == Integer.class
						|| ftype == Long.class
						|| ftype == Short.class
						|| ftype == Boolean.class
						|| ftype == Character.class
						|| ftype == Float.class
						|| ftype == Double.class
						|| ftype == String.class) && !Modifier.isStatic(field.getModifiers())) {
					//是否是基本数据类型
					buffer.append(fname);
					buffer.append(":");
					buffer.append(fvalue);
					buffer.append(",");
				}
			}
			buffer.append("}");
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
