package com.woowahanbank.backend.global.auth.security;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		final Map<String, Object> body = new LinkedHashMap<>();

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		body.put("status", HttpServletResponse.SC_FORBIDDEN);
		body.put("error", "Forbidden");
		body.put("message", accessDeniedException.getMessage());
		body.put("detail", "접근 권한이 없습니다");
		body.put("path", request.getServletPath());

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), body);
	}
}
