package io.vicp.goradical.surveypark.util;

import org.hibernate.id.UUIDHexGenerator;

import java.io.Serializable;

public class APP {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			UUIDHexGenerator uuid = new UUIDHexGenerator();
			Serializable generate = uuid.generate(null, null);
			System.out.println(generate);
		}
	}
}
