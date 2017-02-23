package test.io.vicp.goradical.surveypark.service.impl;

import io.vicp.goradical.surveypark.model.User;
import io.vicp.goradical.surveypark.service.LogService;
import io.vicp.goradical.surveypark.service.StatisticsService;
import io.vicp.goradical.surveypark.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImplTest {
	private UserService userService;
	private StatisticsService statisticsService;

	@Before
	public void setUp() throws Exception {
		ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) act.getBean("userService");
		statisticsService = (StatisticsService) act.getBean("statisticsService");
		LogService logService = (LogService) act.getBean("logService");
	}

	@Test
	public void testStatistics() throws Exception {
		statisticsService.statistics(16);
	}

	@Test
	public void saveEntity() throws Exception {
		User user = new User();
		user.setEmail("123456@qq.com");
		user.setName("helloworld");
		user.setNickName("你好！世界");
		user.setPassword("12346");
		userService.saveEntity(user);
	}

	@Test
	public void saveOrUpdateEntity() throws Exception {

	}

	@Test
	public void updateEntity() throws Exception {

	}

	@Test
	public void deleteEntity() throws Exception {

	}

	@Test
	public void batchEntityByHQL() throws Exception {

	}

	@Test
	public void loadEntity() throws Exception {

	}

	@Test
	public void getEntity() throws Exception {

	}

	@Test
	public void findEntityByHQL() throws Exception {

	}

}