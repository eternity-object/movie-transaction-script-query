package org.eternity.script.movie.jpa;

import jakarta.persistence.EntityManager;
import org.eternity.script.generic.Money;
import org.eternity.script.movie.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
public class JpqlTest {
    @Autowired
    private EntityManager em;

    @Test
    public void query_and_flush() {
        em.persist(new Movie(1L, "영화1", 120, Money.wons(10000L)));
        em.persist(new Movie(1L, "영화2", 120, Money.wons(10000L)));
        em.flush();
        em.clear();

        Movie movie = em.find(Movie.class, 1L);
        movie.setFee(Money.wons(15000));

        List<Movie> movies = em.createQuery("select m from Movie m where m.title like '%영화%'", Movie.class).getResultList();
        assertThat(movies).hasSize(2);
    }
}
