package io.vicp.goradical.surveypark.struts2.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import java.lang.reflect.ParameterizedType;

/**
 * 抽象Action，专门用于继承
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>, Preparable{
	protected T model;

	public BaseAction() {
		try {
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class clazz = (Class) type.getActualTypeArguments()[0];
			model = (T) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getModel(){
		return model;
	};

	@Override
	public void prepare() throws Exception {

	}
}
