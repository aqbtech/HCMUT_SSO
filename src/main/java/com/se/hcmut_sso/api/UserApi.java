package com.se.hcmut_sso.api;

import com.se.hcmut_sso.dto.IntrospectRequest;
import com.se.hcmut_sso.dto.UserCreationRequest;
import com.se.hcmut_sso.dto.UserUpdateRequest;
import com.se.hcmut_sso.service.UserService;
import com.se.hcmut_sso.utils.JwtUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserApi {
	UserService userService;

	@PostMapping("/users")
	ResponseEntity<?> createUser(@RequestBody UserCreationRequest request) {
		return ResponseEntity.ok(userService.createUser(request));
	}

	@GetMapping("/users")
	ResponseEntity<?> getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}

	@GetMapping("/users/{userId}")
	ResponseEntity<?> getUser(@PathVariable("userId") String userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@GetMapping("/users/my-info")
	ResponseEntity<?> getMyInfo() {
		return ResponseEntity.ok(userService.getMyInfo());
	}

	@GetMapping("/users/my-info/{username}")
	ResponseEntity<?> getMyInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getMyInfo(username));
	}

	@DeleteMapping("/users/{userId}")
	ResponseEntity<?> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/users/{userId}")
	ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
		return ResponseEntity.ok(userService.updateUser(userId, request));
	}
	@PostMapping("/user-detail")
	ResponseEntity<?> getUserDetail(@RequestBody IntrospectRequest token) {
		String username = JwtUtils.extractSubject(token.getToken());
		log.info("Get user detail: {}", username);
		return ResponseEntity.ok(userService.getUserDetail(username));
	}
}
