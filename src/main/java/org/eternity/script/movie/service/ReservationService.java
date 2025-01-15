package org.eternity.script.movie.service;

import jakarta.transaction.Transactional;
import org.eternity.script.generic.Money;
import org.eternity.script.movie.domain.*;
import org.eternity.script.movie.persistence.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ScreeningRepository screeningRepository;
    private MovieRepository movieRepository;
    private DiscountPolicyRepository discountPolicyRepository;
    private DiscountConditionRepository discountConditionRepository;
    private ReservationRepository reservationRepository;

    public ReservationService(ScreeningRepository screeningRepository,
                              MovieRepository movieRepository,
                              DiscountPolicyRepository discountPolicyRepository,
                              DiscountConditionRepository discountConditionRepository,
                              ReservationRepository reservationRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.discountPolicyRepository = discountPolicyRepository;
        this.discountConditionRepository = discountConditionRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation reserveScreening(Long customerId, Long screeningId, Integer audienceCount) {
        Screening screening = screeningRepository.findById(screeningId).get();
        Movie movie = movieRepository.findById(screening.getMovieId()).get();
        DiscountPolicy policy = discountPolicyRepository.findByMovieId(movie.getId());
        List<DiscountCondition> conditions = discountConditionRepository.findByPolicyId(policy.getId());

        DiscountCondition condition = findDiscountCondition(screening, conditions);

        Money fee;
        if (condition != null) {
            fee = movie.getFee().minus(calculateDiscount(policy, movie));
        } else {
            fee = movie.getFee();
        }

        Reservation reservation = makeReservation(customerId, screeningId, audienceCount, fee);
        reservationRepository.save(reservation);

        return reservation;
    }

    private DiscountCondition findDiscountCondition(Screening screening, List<DiscountCondition> conditions) {
        for(DiscountCondition condition : conditions) {
            if (condition.isPeriodCondition()) {
                if (screening.isPlayedIn(condition.getDayOfWeek(),
                                        condition.getStartTime(),
                                        condition.getEndTime())) {
                    return condition;
                }
            } else {
                if (condition.getSequence().equals(screening.getSequence())) {
                    return condition;
                }
            }
        }

        return null;
    }

    private Money calculateDiscount(DiscountPolicy policy, Movie movie) {
        if (policy.isAmountPolicy()) {
            return policy.getAmount();
        } else if (policy.isPercentPolicy()) {
            return movie.getFee().times(policy.getPercent());
        }

        return Money.ZERO;
    }

    private Reservation makeReservation(Long customerId, Long screeningId, Integer audienceCount, Money amount) {
        return new Reservation(customerId, screeningId, audienceCount, amount.times(audienceCount));
    }
}
