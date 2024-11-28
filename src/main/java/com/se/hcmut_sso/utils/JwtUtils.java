package com.se.hcmut_sso.utils;

import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public class JwtUtils {

	public static String extractSubject(String authHeader) {
		try {
			String jwtToken = authHeader.substring(7);
			// Parse token (cả header, payload và signature)
			SignedJWT signedJWT = SignedJWT.parse(jwtToken);

			// Lấy claim "sub" (subject) từ payload
			return signedJWT.getJWTClaimsSet().getSubject();
		} catch (ParseException e) {
			throw new RuntimeException("Invalid JWT token", e);
		}
	}
}

