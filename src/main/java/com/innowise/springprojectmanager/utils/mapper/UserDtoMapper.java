package com.innowise.springprojectmanager.utils.mapper;

import com.innowise.springprojectmanager.model.dto.user.UserDto;
import com.innowise.springprojectmanager.model.entity.User;
import org.mapstruct.Mapper;

/**
 * DtoMapper interface, which contains methods to transform User and UserDto both ways.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

  /**
   * UserToUserDto method. Converts User object to UserDto.
   *
   * @param user User object we need to convert.
   * @return UserDto Obtained UserDto.
   */
  UserDto toDto(User user);

  /**
   * DtoUserToUser method. Converts UserDto to User object.
   *
   * @param userDto UserDto we need to convert.
   * @return User Obtained User object.
   */
  User toEntity(UserDto userDto);
}
