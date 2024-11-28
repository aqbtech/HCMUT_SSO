package com.se.hcmut_sso.config;

import com.se.hcmut_sso.dto.AuthenticationRequest;
import com.se.hcmut_sso.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationProvider {
	private final AuthenticationService authenticationService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		var response = authenticationService
				.authenticate(AuthenticationRequest.builder()
						.username(username)
						.password(password)
						.build());

		return new UsernamePasswordAuthenticationToken(response.getToken(), null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
