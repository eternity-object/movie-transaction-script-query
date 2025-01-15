package org.eternity.script.movie.domain;

import jakarta.persistence.*;
import lombok.*;
import org.eternity.script.generic.Money;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Getter @Setter
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long policyId;
	private String title;
	private Integer runningTime;

	@Embedded
	@AttributeOverride(name = "fee", column = @Column(name="MOVIE_FEE"))
	private Money fee;

	public Movie(Long policyId, String title, Integer runningTime, Money fee) {
		this.policyId = policyId;
		this.title = title;
		this.runningTime = runningTime;
		this.fee = fee;
	}
}
