package com.se.hcmut_sso.api;

import com.se.hcmut_sso.dto.AuthenticationRequest;
import com.se.hcmut_sso.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class Login {
	private final AuthenticationService authenticationService;
	@GetMapping("/home")
	public String index() {
		return "login/Rare_fault";
	}
	@GetMapping("/login")
	public String login() {
		return "login/login_form";
	}
	@PostMapping("/login")
	public String login(@ModelAttribute("form") AuthenticationRequest request, HttpSession httpSession, HttpServletResponse httpServletResponse) {
		var var1 = authenticationService.authenticate(request);
		httpSession.setAttribute("TOKEN", var1);
		Cookie cookie = new Cookie("TOKEN", var1.getToken());
		cookie.setPath("/");
//		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 5);
		httpServletResponse.addCookie(cookie);
		return "redirect:http://localhost:3000";
	}
}
