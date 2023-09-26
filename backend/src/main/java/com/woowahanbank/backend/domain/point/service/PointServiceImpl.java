package com.woowahanbank.backend.domain.point.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woowahanbank.backend.domain.point.domain.Point;
import com.woowahanbank.backend.domain.point.dto.PointDto;
import com.woowahanbank.backend.domain.point.repository.PointRepository;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PointServiceImpl {
	private final UserRepository userRepository;
	private final PointRepository pointRepository;

	public List<PointDto> getMyPointList(Long userId) {
		List<Point> entityList = pointRepository.findAllBySender_IdOrReceiver_IdOrderByIdDesc(userId, userId);
		List<PointDto> resList = new ArrayList<>();
		for (int i = 0; i < entityList.size(); i++) {
			resList.add(changeToDto(entityList.get(i)));
		}
		return resList;
	}

	public void makePoint(User sender, User receiver, String memo, int money) {
		Point point = Point.builder()
			.sender(sender).receiver(receiver).amount(money).memo(memo).build();
		pointRepository.save(point);
	}

	private PointDto changeToDto(Point point) {
		User sender = userRepository.findById(point.getSender().getId()).get();
		User receiver = userRepository.findById(point.getReceiver().getId()).get();
		return PointDto.builder()
			.id(point.getId())
			.senderNickname(sender.getNickname())
			.receiverNickname(receiver.getNickname())
			.amount(point.getAmount())
			.memo(point.getMemo())
			.time(point.getTime())
			.build();
	}
}
