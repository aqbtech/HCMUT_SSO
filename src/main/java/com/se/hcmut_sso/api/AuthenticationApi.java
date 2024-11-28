package com.se.hcmut_sso.api;

import com.se.hcmut_sso.dto.*;
import com.se.hcmut_sso.dto.AuthenticationResponse;
import com.se.hcmut_sso.dto.IntrospectResponse;
import com.se.hcmut_sso.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationApi {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/refresh")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseEntity.ok().build();
    }
}
