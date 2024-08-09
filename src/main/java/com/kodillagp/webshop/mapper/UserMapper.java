package com.kodillagp.webshop.mapper;

import com.kodillagp.webshop.domain.User;
import com.kodillagp.webshop.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
