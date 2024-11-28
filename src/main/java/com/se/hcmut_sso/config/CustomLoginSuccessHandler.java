package com.se.hcmut_sso.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		String redirectUrl = request.getParameter("redirect");
		if (redirectUrl == null || redirectUrl.isEmpty()) {
			// Thiếu redirect, trả lỗi
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Missing required parameter: redirect");
			log.error("Missing required parameter: redirect");
			return;
		}
		String token = request.getSession().getAttribute("TOKEN").toString();

		// Thêm token vào session
//		request.getSession().setAttribute("TOKEN", token);

		// Hoặc thêm token vào cookie
		Cookie cookie = new Cookie("TOKEN", token);
		cookie.setHttpOnly(true); // Đảm bảo bảo mật
		cookie.setPath("/");      // Cookie có hiệu lực toàn bộ domain
		response.addCookie(cookie);

		// Lấy URL cần redirect từ request (nếu có)
		redirectUrl = request.getParameter("redirect");
		log.info("Redirecting to: {}", redirectUrl);
		// Redirect đến service hoặc URL
		response.sendRedirect(redirectUrl);
	}
}
