package com.innowise.springprojectmanager.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Custom API Errors class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDto {
  @JsonProperty(DtoJsonProperty.CODE)
  private int code;

  @JsonProperty(DtoJsonProperty.ERRORS)
  private Set<String> errors;

  public ApiErrorDto(int code, String error) {
    this.code = code;
    this.errors = Set.of(error);
  }
}
