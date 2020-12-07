package com.vapitea.datacollector.mapper;

import com.vapitea.datacollector.dto.UserDto;
import com.vapitea.datacollector.model.Admin;
import com.vapitea.datacollector.model.Operator;
import com.vapitea.datacollector.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDto userToUserDto(User user) {

    String dtype;
    if (user instanceof Admin) {
      dtype = "Admin";
    } else if (user instanceof Operator) {
      dtype = "Operator";
    } else {
      dtype = "User";
    }


    return UserDto.builder()
      .id(user.getId())
      .name(user.getName())
      .password(user.getPassword())
      .dType(dtype)
      .build();
  }

  public User userDtoToUser(UserDto userDto) {
    switch (userDto.getDType()) {
      case "User":
        return User.builder()
          .id(userDto.getId())
          .name(userDto.getName())
          .password(userDto.getPassword())
          .build();
      case "Operator":
        return Operator.builder()
          .id(userDto.getId())
          .name(userDto.getName())
          .password(userDto.getPassword())
          .build();
      case "Admin":
        return Admin.builder()
          .id(userDto.getId())
          .name(userDto.getName())
          .password(userDto.getPassword())
          .build();
    }
    return null;
  }
}
