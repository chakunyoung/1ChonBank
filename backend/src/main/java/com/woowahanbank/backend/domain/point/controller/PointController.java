package com.woowahanbank.backend.domain.point.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.domain.point.dto.PointDto;
import com.woowahanbank.backend.domain.point.service.PointServiceImpl;
import com.woowahanbank.backend.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
public class PointController {
	private final PointServiceImpl pointService;
	@GetMapping("/{userId}")
	public ResponseEntity<?> getAllPointList(@PathVariable Long userId) {
		List<PointDto> pointDtoList = pointService.getMyPointList(userId);
		return BaseResponse.okWithData(HttpStatus.OK, "나의 모든 거래 내역", pointDtoList);
	}
}
