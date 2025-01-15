package org.eternity.script.movie.persistence.repository;

import org.eternity.script.movie.domain.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
}
