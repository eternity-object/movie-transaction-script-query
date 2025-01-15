package org.eternity.script.movie.jpa;

import jakarta.persistence.EntityManager;
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

    }

    @Test
    public void identity_transactional_write_behind() {

    }

    @Test
    public void first_level_cache() {

    }

    @Test
    public void remove() {

    }

    @Test
    public void automatic_dirty_checking() {

    }

    @Test
    public void detach_and_merge() {

    }

    @Test
    public void query_and_flush() {

    }
}
