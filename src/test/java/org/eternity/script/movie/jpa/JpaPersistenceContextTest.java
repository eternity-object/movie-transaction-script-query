package org.eternity.script.movie.jpa;

import jakarta.persistence.EntityManager;
import org.eternity.script.generic.Money;
import org.eternity.script.movie.domain.DiscountPolicy;
import org.eternity.script.movie.domain.DiscountPolicy.PolicyType;
import org.eternity.script.movie.domain.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
public class JpaPersistenceContextTest {
    @Autowired
    private EntityManager em;

    @Test
    public void sequence_transactional_write_behind() {
        DiscountPolicy policy1 = new DiscountPolicy(1L, PolicyType.AMOUNT_POLICY, Money.wons(1000), null);
        DiscountPolicy policy2 = new DiscountPolicy(1L, PolicyType.PERCENT_POLICY, Money.wons(1000), null);

        em.persist(policy1);
        em.persist(policy2);

        em.flush();
    }

    @Test
    public void identity_transactional_write_behind() {
        Movie movie1 =
                new Movie(1L, "영화1", 120, Money.wons(10000));
        Movie movie2 =
                new Movie(1L, "영화2", 120, Money.wons(10000));

        em.persist(movie1);
        em.persist(movie2);

        em.flush();
    }

    @Test
    public void first_level_cache() {
        DiscountPolicy policy = new DiscountPolicy(1L, PolicyType.AMOUNT_POLICY, Money.wons(1000), null);

        em.persist(policy);
        em.flush();

        DiscountPolicy loadedPolicy = em.find(DiscountPolicy.class, policy.getId());

        assertThat(policy).isSameAs(loadedPolicy);
    }

    @Test
    public void remove() {
        DiscountPolicy policy = new DiscountPolicy(1L, PolicyType.AMOUNT_POLICY, Money.wons(1000), null);

        em.persist(policy);
        em.flush();

        em.remove(policy);
        em.flush();
    }

    @Test
    public void automatic_dirty_checking() {
        DiscountPolicy policy = new DiscountPolicy(1L, PolicyType.AMOUNT_POLICY, Money.wons(1000), null);

        em.persist(policy);
        em.flush();

        policy.setAmount(Money.wons(2000));
        em.flush();
    }

    @Test
    public void detach_and_merge() {
        DiscountPolicy policy = new DiscountPolicy(1L, PolicyType.AMOUNT_POLICY, Money.wons(1000), null);

        em.persist(policy);
        em.flush();

        policy.setAmount(Money.wons(2000));
        em.detach(policy);
        em.flush();

        em.merge(policy);
        em.flush();
    }

    @Test
    public void query_and_flush() {
        Movie movie = new Movie(1L, "영화", 120, Money.wons(10000));
        em.persist(movie);
        em.flush();
        em.clear();

        Movie loaded1 = em.find(Movie.class, 1L);
        Movie loaded2 = em.find(Movie.class, 1L);

        assertThat(loaded1).isSameAs(loaded2);
    }
}
