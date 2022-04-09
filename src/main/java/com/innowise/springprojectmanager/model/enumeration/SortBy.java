package com.innowise.springprojectmanager.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum, which contains all SortBy strings we need in app.
 *
 * @author Edvard Krainiy on 09/04/2022
 */
@RequiredArgsConstructor
@Getter
public enum SortBy {
  TITLE_ASC("titleAsc"),
  TITLE_DESC("titleDesc");

  private final String sortString;
}
