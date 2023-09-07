package com.woowahanbank.backend.global.auth.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.service.UserServiceImpl;
import com.woowahanbank.backend.global.auth.security.CustomMemberDetails;
import com.woowahanbank.backend.global.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final UserServiceImpl userService;
	private final RedisTemplate<String, String> template;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserServiceImpl userService,
		RedisTemplate<String, String> template) {
		// super(authenticationManager);
		this.userService = userService;
		this.template = template;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		// Header의 Key값을 통해 PREFIX를 받기
		log.info("jwt filter on");
		String header = request.getHeader(JwtTokenUtil.HEADER_STRING);

		// Header의 PREFIX가 일치하지 않는다면 다른 필터를 타도록
		if (header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			// 다른 로그인 방식이 없기 때문에 Exception 발생
			// If header is present, try grab user principal from database and perform authorization
			Authentication authentication = getAuthentication(request);
			// jwt 토큰으로 부터 획득한 인증 정보(authentication) 설정.
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (TokenExpiredException ex) {
			request.setAttribute("expired", ex);

		} catch (Exception ex) {
			request.setAttribute("exception", ex.getMessage());
		}

		filterChain.doFilter(request, response);
	}

	@Transactional(readOnly = true)
	public Authentication getAuthentication(HttpServletRequest request) throws Exception {
		String token = request.getHeader(JwtTokenUtil.HEADER_STRING);
		// 요청 헤더에 Authorization 키값에 jwt 토큰이 포함된 경우에만, 토큰 검증 및 인증 처리 로직 실행.
		if (token != null) {
			// parse the token and validate it (decode)
			JWTVerifier verifier = JwtTokenUtil.getVerifier();
			JwtTokenUtil.handleError(token);
			DecodedJWT decodedJWT = verifier.verify(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""));
			String userId = decodedJWT.getSubject();

			// Search in the DB if we find the user by token subject (username)
			// If so, then grab user details and create spring auth token using username, pass, authorities/roles
			if (userId != null) {
				// jwt 토큰에 포함된 계정 정보(userId) 통해 실제 디비에 해당 정보의 계정이 있는지 조회.
				Optional<User> optionalMember = userService.findByUserId(userId);

				if (optionalMember.isPresent()) {
					// 식별된 정상 유저인 경우, 요청 context 내에서 참조 가능한 인증 정보(jwtAuthentication) 생성.
					CustomMemberDetails userDetails = new CustomMemberDetails(optionalMember.get());
					UsernamePasswordAuthenticationToken jwtAuthentication = new UsernamePasswordAuthenticationToken(
						userDetails,
						null, userDetails.getAuthorities());

					jwtAuthentication.setDetails(userDetails);
					log.info("JWT Auth OK!");
					return jwtAuthentication;
				}
			}
			return null;
		}
		return null;
	}
}
