package model.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

@SequenceGenerator(name = "sub_seq",
 sequenceName = "sub_seq_id",
 allocationSize = 50,
 initialValue = 1)

@Entity
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_seq")
	@Column(name = "sub_id")
	private int subId;
	
	@NonNull
	@JoinColumn(name = "mem_id", nullable = false)
	@OneToOne
	private Member memId;
	@NonNull
	@OneToOne
	@JoinColumn(name = "prod_id", nullable = false)
	private Product prodId;
	@NonNull
	@Column(name = "rest_date", nullable = false)
	private int restDate;
	@NonNull
	@Column(name = "sub_period", nullable = false)
	private int period;
	
	private int startDate;
}
