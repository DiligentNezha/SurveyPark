package io.vicp.goradical.surveypark.util;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class HibernateTest {
	public static void main(String[] args) throws SQLException {
		ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
		DataSource dataSource = (DataSource) act.getBean("dataSource");
		System.out.println(dataSource.getConnection());
		SessionFactory sessionFactory = (SessionFactory) act.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}
}
