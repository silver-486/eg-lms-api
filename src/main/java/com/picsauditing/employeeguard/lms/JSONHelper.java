package com.picsauditing.employeeguard.lms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class JSONHelper {


  public static String toJSONJackson(Object message) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    return mapper.writeValueAsString(message);
  }

  public static String toJSON(Object obj) {
    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(obj);
  }


}
