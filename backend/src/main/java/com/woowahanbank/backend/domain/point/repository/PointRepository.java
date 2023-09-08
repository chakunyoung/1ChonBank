package com.woowahanbank.backend.domain.point.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woowahanbank.backend.domain.point.domain.Point;

public interface PointRepository extends JpaRepository<Point,Long> {
	List<Point> findAllBySender_IdOrReceiver_IdOrderByIdDesc(Long senderId, Long receiverId);
}
