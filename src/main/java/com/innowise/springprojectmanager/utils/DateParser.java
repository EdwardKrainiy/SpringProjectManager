package com.innowise.springprojectmanager.utils;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * DateParser class. Provides us methods to parse String to LocalDateTime using ISO 8601.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Component
@Log4j2
@RequiredArgsConstructor
public class DateParser {
  public LocalDateTime stringToLocalDateTime(String dateString){
    return LocalDateTime.parse(dateString);
  }
}
