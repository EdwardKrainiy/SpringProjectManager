package com.innowise.springprojectmanager.utils.mapper.user;

import com.innowise.springprojectmanager.model.dto.user.UserSignUpDto;
import com.innowise.springprojectmanager.model.entity.User;
import org.mapstruct.Mapper;

/**
 * DtoMapper interface, which contains methods to transform User and UserDtoSignUp both ways.
 *
 * @author Edvard Krainiy on 03/04/2022
 */
@Mapper(componentModel = "spring")
public interface UserSignUpDtoMapper {

  /**
   * UserToUserDto method. Converts User object to UserDto.
   *
   * @param user User object we need to convert.
   * @return UserDto Obtained UserDto.
   */
  UserSignUpDto toDto(User user);

  /**
   * DtoUserToUser method. Converts UserDto to User object.
   *
   * @param userSignUpDto UserDto we need to convert.
   * @return User Obtained User object.
   */
  User toEntity(UserSignUpDto userSignUpDto);
}
