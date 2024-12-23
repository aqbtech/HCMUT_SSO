package com.se.hcmut_sso.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String ms;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleResponse> roles;
}
