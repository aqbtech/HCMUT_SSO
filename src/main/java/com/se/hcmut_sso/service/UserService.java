package com.se.hcmut_sso.service;


import com.se.hcmut_sso.config.PredefinedRole;
import com.se.hcmut_sso.dto.UserCreationRequest;
import com.se.hcmut_sso.dto.UserDetail;
import com.se.hcmut_sso.dto.UserResponse;
import com.se.hcmut_sso.dto.UserUpdateRequest;
import com.se.hcmut_sso.entity.Role;
import com.se.hcmut_sso.entity.User;
import com.se.hcmut_sso.exception.ServerException;
import com.se.hcmut_sso.mapper.UserMapper;
import com.se.hcmut_sso.repo.RoleRepository;
import com.se.hcmut_sso.repo.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
	UserRepository userRepository;
	RoleRepository roleRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;

	public UserResponse createUser(UserCreationRequest request) {
		if (userRepository.existsByUsername(request.getUsername()))
			throw new ServerException("Username is already existed", HttpStatus.BAD_REQUEST);

		User user = userMapper.toUser(request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		HashSet<Role> roles = new HashSet<>();
		roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

		user.setRoles(roles);

		return userMapper.toUserResponse(userRepository.save(user));
	}

	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();

		User user = userRepository.findByUsername(name)
				.orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND));

		return userMapper.toUserResponse(user);
	}

	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse updateUser(String userId, UserUpdateRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND));

		userMapper.updateUser(user, request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		var roles = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));

		return userMapper.toUserResponse(userRepository.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getUsers() {
		log.info("In method get Users");
		return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse getUser(String id) {
		return userMapper.toUserResponse(userRepository.findById(id)
				.orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND)));
	}
	public UserDetail getUserDetail(String userId) {
		User user = userRepository.findByUsername(userId)
				.orElseThrow(() -> new ServerException("User not found", HttpStatus.NOT_FOUND));
		UserDetail userDetail = new UserDetail();
		userDetail.setUsername(user.getUsername());
		userDetail.setFullName(user.getFirstName() + " " + user.getLastName());
		String role;
		if (user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"))) {
			role = "ADMIN";
		} else {
			role = "USER";
		}
		userDetail.setRole(role);
		return userDetail;
	}
}
