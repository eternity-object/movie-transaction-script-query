package org.eternity.script.movie.service;

import org.eternity.script.generic.Money;
import org.eternity.script.movie.domain.*;
import org.eternity.script.movie.persistence.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.time.DayOfWeek.MONDAY;
import static org.eternity.script.movie.domain.DiscountCondition.ConditionType.PERIOD_CONDITION;
import static org.eternity.script.movie.domain.DiscountCondition.ConditionType.SEQUENCE_CONDITION;
import static org.eternity.script.movie.domain.DiscountPolicy.PolicyType.AMOUNT_POLICY;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @InjectMocks  private ReservationService reservationService;

    @Mock private ScreeningRepository screeningRepository;
    @Mock private MovieRepository movieRepository;
    @Mock private DiscountPolicyRepository discountPolicyRepository;
    @Mock private DiscountConditionRepository discountConditionRepository;
    @Mock private ReservationRepository reservationRepository;

    @Test
    public void 금액할인정책_계산() {
        // given
        Long customerId = 1L;
        Long screeningId = 1L;
        Long movieId = 1L;
        Long policyId = 1L;

        Mockito.when(screeningRepository.findById(screeningId))
                .thenReturn(Optional.of(new Screening(screeningId, movieId, 1, LocalDateTime.of(2024, 12, 11, 18, 0))));

        Mockito.when(movieRepository.findById(movieId))
                .thenReturn(Optional.of(new Movie(movieId, policyId, "한신", 120, Money.wons(10000L))));

        Mockito.when(discountPolicyRepository.findByMovieId(movieId))
                .thenReturn(new DiscountPolicy(policyId, movieId, AMOUNT_POLICY, Money.wons(1000L), null));

        Mockito.when(discountConditionRepository.findByPolicyId(policyId))
                .thenReturn(List.of(
                        new DiscountCondition(policyId, SEQUENCE_CONDITION, null, null, null, 1),
                        new DiscountCondition(policyId, PERIOD_CONDITION, MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 30), null)));

        // when
        Reservation reservation = reservationService.reserveScreening(customerId, screeningId, 2);

        // then
        Assertions.assertEquals(reservation.getFee(), Money.wons(18000L));
    }
}
