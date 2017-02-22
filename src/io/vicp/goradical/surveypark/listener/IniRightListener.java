package io.vicp.goradical.surveypark.listener;

import io.vicp.goradical.surveypark.model.security.Right;
import io.vicp.goradical.surveypark.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化权限监听器，在spring初始化完成后立即调用
 */
@Component
public class IniRightListener implements ApplicationListener, ServletContextAware {

	@Autowired
	private RightService rightService;
	//接收ServletContext
	private ServletContext servletContext;

	@Override
	public void onApplicationEvent(ApplicationEvent ae) {
		//上下文刷新事件
		if (ae instanceof ContextRefreshedEvent) {
			//查出所有权限
			List<Right> rights = rightService.findAllEntities();
			Map<String, Right> map = new HashMap<>();
			for (Right right : rights) {
				map.put(right.getRightUrl(), right);
			}
			if (servletContext != null) {
				servletContext.setAttribute("all_rights_map", map);
			}
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
