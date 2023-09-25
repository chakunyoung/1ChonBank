package com.woowahanbank.backend.domain.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.SignupDto;
import com.woowahanbank.backend.domain.user.service.UserService;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Api(tags ={"User API"})
public class UserController {

	private final UserService userService;

	@ApiOperation(value = "로그인")
	@ApiImplicitParam(name = "userId", value = "유저 이름", required = true, dataType = "string", paramType = "path")
	@ApiResponse(code = 200, message = "로그인 성공")
	@GetMapping("/{userId}")
	public ResponseEntity<?> searchItemList(@PathVariable String userId) {
		SignupDto loginUser = userService.signup(userId);
		return BaseResponse.okWithData(HttpStatus.OK, "Get Login User Successful", loginUser);
	}

	@ApiOperation(value = "닉네임 중복 확인")
	@ApiImplicitParam(name = "nickname", value = "닉네임", required = true, dataType = "string", paramType = "path")
	@ApiResponse(code = 200, message = "사용 가능한 닉네임 입니다.")
	@GetMapping("/duplication/{nickname}")
	public ResponseEntity<?> checkDuplication(@PathVariable String nickname) {
		if (userService.duplicationNickname(nickname)) {
			return BaseResponse.ok(HttpStatus.OK, "사용 가능한 닉네임입니다.");
		}

		return BaseResponse.fail("중복된 닉네임 입니다.", 500);
	}

	@ApiOperation(value = "회원 가입", notes = "회원 가입 정보를 저장합니다.")
	@PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(
		@ApiParam(value = "회원 가입", required = true)
		@RequestBody SignupDto signupDto) {
		userService.saveUser(signupDto);
		return BaseResponse.ok(HttpStatus.OK, "회원 가입 성공");
	}

	@ApiOperation(value = "전체 회원 리스트")
	@ApiImplicitParam(name = "keyword", value = "키워드", required = true, dataType = "string", paramType = "path")
	@ApiResponse(code = 200, message = "회원 리스트를 성공적으로 불러왔습니다.")
	@GetMapping("findFamily/{keyword}")
	public ResponseEntity<?> findFamilyName(@PathVariable String keyword) {
		List<String> users = userService.findFamily(keyword);
		return BaseResponse.okWithData(HttpStatus.OK, "회원 리스트를 성공적으로 불러왔습니다.", users);
	}

	@ApiOperation(value = "자식 선택")
	@ApiImplicitParam(name = "nickname", value = "닉네임", required = true, dataType = "string", paramType = "path")
	@ApiResponse(code = 200, message = "자식 선택 완료")
	@GetMapping("selectFamily/{nickname}")
	public ResponseEntity<?> selectFamily(@PathVariable String nickname) {
		User user = userService.findByNickname(nickname);
		return BaseResponse.okWithData(HttpStatus.OK, "자식 선택 완료", user);
	}
}
