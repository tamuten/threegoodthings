package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StrUtil {

	/**
	 * 引数に渡されたオブジェクトをJson形式の文字列に変換する
	 *
	 * @param obj
	 * @return
	 */
	public static String getJson(final Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		return json;
	}
}
