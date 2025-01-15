package org.eternity.script.movie.persistence.repository;

import org.eternity.script.movie.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
