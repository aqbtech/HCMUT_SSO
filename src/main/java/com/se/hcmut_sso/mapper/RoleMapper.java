package com.se.hcmut_sso.mapper;


import com.se.hcmut_sso.dto.RoleRequest;
import com.se.hcmut_sso.dto.RoleResponse;
import com.se.hcmut_sso.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
	Role toRole(RoleRequest request);

	RoleResponse toRoleResponse(Role role);
}
