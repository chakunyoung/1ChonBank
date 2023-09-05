package com.woowahanbank.backend.domain.user.repository;

import com.woowahanbank.backend.domain.family.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, Long> {
}
