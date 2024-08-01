package model.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(name = "prod_seq",
	 			   sequenceName = "prod_seq_id",
	 			   allocationSize = 50,
	 			   initialValue = 1)

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
	@Column(name = "prod_id")
	private int prodId;
	@NonNull
	@ManyToOne
	@JoinColumn(name = "mem_id", nullable = false)
	private Member memId; 
	@NonNull
	@Column(name = "prod_name", nullable = false)
	private String prodName;
	@NonNull
	@Column(name = "prod_price", nullable = false)
	private int prodPrice;
	
}
