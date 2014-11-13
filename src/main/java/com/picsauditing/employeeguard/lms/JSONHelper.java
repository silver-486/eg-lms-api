package com.picsauditing.employeeguard.lms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;

public class JSONHelper {


	public static String toJSON(Object message) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return mapper.writeValueAsString(message);
	}

	public static String toJSONGson(Object obj) {
		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(obj);
	}

	public static List<Integer> toIdList(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, List.class);
	}


}
