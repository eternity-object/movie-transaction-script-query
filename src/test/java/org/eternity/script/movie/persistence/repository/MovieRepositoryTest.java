package org.eternity.script.movie.persistence.repository;

import jakarta.persistence.EntityManager;
import org.eternity.script.JpaConfig;
import org.eternity.script.generic.Money;
import org.eternity.script.movie.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false,
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaConfig.class))
public class MovieRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private MovieRepository repository;

    @Test
    public void save_and_find() {
        Movie movie = repository.save(new Movie(1L, "영화1", 120, Money.wons(10000L)));
        em.flush();
        em.clear();

        Optional<Movie> found = repository.findById(movie.getId());
        assertThat(found).isPresent().map(Movie::getTitle).hasValue("영화1");
    }
}
