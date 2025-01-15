package org.eternity.script.movie.persistence.repository;

import org.eternity.script.movie.domain.DiscountCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountConditionRepository extends JpaRepository<DiscountCondition, Long> {
    List<DiscountCondition> findByPolicyId(Long policyId);
}
