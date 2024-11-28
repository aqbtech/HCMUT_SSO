package com.se.hcmut_sso.api;


import com.se.hcmut_sso.dto.RoleRequest;
import com.se.hcmut_sso.dto.RoleResponse;
import com.se.hcmut_sso.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorizationApi {
    RoleService roleService;

    @PostMapping
    ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.create(request));
    }

    @GetMapping
    ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @DeleteMapping("/{role}")
    ResponseEntity<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ResponseEntity.ok().build();
    }
}
