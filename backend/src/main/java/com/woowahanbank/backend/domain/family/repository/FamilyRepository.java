package com.woowahanbank.backend.domain.family.repository;

import com.woowahanbank.backend.domain.family.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Optional<Family> findByFamilyName(String familyName);
}
