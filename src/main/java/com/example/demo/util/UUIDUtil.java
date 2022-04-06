package com.example.demo.util;

import java.util.UUID;

public class UUIDUtil {
	private UUIDUtil() {
	}

	public static String generateUUID() {
		String uuid = UUID.randomUUID()
			.toString();

		return uuid;
	}
}
