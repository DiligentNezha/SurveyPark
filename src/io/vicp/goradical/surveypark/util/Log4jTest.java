package io.vicp.goradical.surveypark.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jTest {
	private static final Logger LOG = LogManager.getLogger(Log4jTest.class);

	public static void main(String[] args) {
		LOG.error("This is log4j test!");
	}
}
