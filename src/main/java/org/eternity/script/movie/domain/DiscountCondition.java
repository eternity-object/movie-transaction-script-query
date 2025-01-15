package org.eternity.script.movie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.eternity.script.generic.PlayTime;
import org.eternity.script.generic.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@SequenceGenerator(
        name = "discount_seq",
        sequenceName = "discount_seq",
        initialValue = 1, allocationSize = 50
)
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Getter @Setter
public class DiscountCondition {
    public enum ConditionType { PERIOD_CONDITION, SEQUENCE_CONDITION }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_sequence")
    private Long id;
    private Long policyId;
    private ConditionType conditionType;
    private PlayTime playTime;
    private Integer sequence;

    public DiscountCondition(Long policyId, ConditionType conditionType, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Integer sequence) {
        this.policyId = policyId;
        this.conditionType = conditionType;
        this.playTime = new PlayTime(dayOfWeek, new TimeInterval(startTime, endTime));
        this.sequence = sequence;
    }

    public DayOfWeek getDayOfWeek() {
        return playTime.getDayOfWeek();
    }

    public LocalTime getStartTime() {
        return playTime.getInterval().getStartTime();
    }

    public LocalTime getEndTime() {
        return playTime.getInterval().getEndTime();
    }


    public boolean isPeriodCondition() {
        return ConditionType.PERIOD_CONDITION.equals(conditionType);
    }

    public boolean isSequenceCondition() {
        return ConditionType.SEQUENCE_CONDITION.equals(conditionType);
    }
}
