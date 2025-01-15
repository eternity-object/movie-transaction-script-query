package org.eternity.script;

import org.eternity.script.generic.Money;
import org.eternity.script.movie.domain.DiscountCondition;
import org.eternity.script.movie.domain.DiscountPolicy;
import org.eternity.script.movie.domain.Movie;
import org.eternity.script.movie.domain.Screening;
import org.eternity.script.movie.persistence.repository.DiscountConditionRepository;
import org.eternity.script.movie.persistence.repository.DiscountPolicyRepository;
import org.eternity.script.movie.persistence.repository.MovieRepository;
import org.eternity.script.movie.persistence.repository.ScreeningRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class JpaConfig {
    @Bean
    public ApplicationRunner initializer(MovieRepository movieRepository,
                                         DiscountPolicyRepository discountPolicyRepository,
                                         DiscountConditionRepository discountConditionRepository,
                                         ScreeningRepository screeningRepository) {
        return args -> {
            Movie movie = new Movie(1L, "한산", 120, Money.wons(10000L));
            movieRepository.save(movie);

            Screening screening = new Screening(movie.getId(), 1,
                    LocalDateTime.of(2024, 12, 11, 18, 0));
            screeningRepository.save(screening);

            DiscountPolicy discountPolicy = new DiscountPolicy(movie.getId(),
                    DiscountPolicy.PolicyType.AMOUNT_POLICY, Money.wons(1000L), null);
            discountPolicyRepository.save(discountPolicy);

            discountConditionRepository.save(new DiscountCondition(discountPolicy.getId(),
                    DiscountCondition.ConditionType.SEQUENCE_CONDITION, null, null, null, 1));
            discountConditionRepository.save(new DiscountCondition(discountPolicy.getId(),
                    DiscountCondition.ConditionType.PERIOD_CONDITION, DayOfWeek.WEDNESDAY,
                    LocalTime.of(9, 0), LocalTime.of(11, 30), null));
        };
    }
}
