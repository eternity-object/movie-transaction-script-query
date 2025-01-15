package org.eternity.script.movie.persistence.repository;

import jakarta.persistence.EntityManager;
import org.eternity.script.JpaConfig;
import org.eternity.script.movie.domain.DiscountCondition;
import org.eternity.script.movie.domain.DiscountCondition.ConditionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JpaConfig.class))
public class DiscountConditionRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private DiscountConditionRepository repository;

    @Test
    public void find_by_policyId() {
        Long policyId = 2L;

        repository.save(new DiscountCondition(policyId, policyId, ConditionType.SEQUENCE_CONDITION, null, 1));
        repository.save(new DiscountCondition(policyId, ConditionType.PERIOD_CONDITION,
                                DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(11, 30), null));
        em.flush();
        em.clear();

        List<DiscountCondition> conditions = repository.findByPolicyId(policyId);

        assertThat(conditions).hasSize(2).map(DiscountCondition::getPolicyId).containsOnly(policyId);
    }
}
