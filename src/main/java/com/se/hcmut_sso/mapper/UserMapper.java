package com.se.hcmut_sso.mapper;


import com.se.hcmut_sso.dto.UserCreationRequest;
import com.se.hcmut_sso.dto.UserDetail;
import com.se.hcmut_sso.dto.UserResponse;
import com.se.hcmut_sso.dto.UserUpdateRequest;
import com.se.hcmut_sso.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
