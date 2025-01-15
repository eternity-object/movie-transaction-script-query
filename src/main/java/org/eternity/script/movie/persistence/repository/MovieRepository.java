package org.eternity.script.movie.persistence.repository;


import org.eternity.script.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
