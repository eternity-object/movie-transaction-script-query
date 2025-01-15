package org.eternity.script.movie.persistence.repository;

import org.eternity.script.movie.domain.DiscountPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {
    DiscountPolicy findByMovieId(Long movieId);
}
