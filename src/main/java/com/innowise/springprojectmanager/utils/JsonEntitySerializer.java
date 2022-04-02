package com.innowise.springprojectmanager.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * JsonEntitySerializer class. Provides us methods for mapping JSON string from object and obtain
 * object from JSON string.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class JsonEntitySerializer {
  private final ObjectMapper objectMapper;

  /**
   * serializeObjectToJson method. Converts object of any class to JSON string and returns this one.
   *
   * @param object Object of class we need to convert to JSON string.
   * @param <T> Generic class of object.
   * @return String Obtained JSON String.
   */
  public <T> String serializeObjectToJson(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException exception) {
      log.error("JsonProcessingException caught!");
      return "";
    }
  }
}
