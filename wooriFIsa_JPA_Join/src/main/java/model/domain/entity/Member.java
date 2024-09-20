package model.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@SequenceGenerator(name = "mem_seq",
				   sequenceName = "mem_seq_id", 
				   allocationSize = 50, 
				   initialValue = 1)

@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mem_seq")
	@Column(name = "mem_id")
	private int memId;
	@NonNull
	@Column(name = "mem_name", nullable = false, length = 20)
	private String memName;
	@NonNull
	@Column(name = "mem_email", nullable = false, length = 30)
	private String memEmail;
	@NonNull
	@Column(name = "mem_birth")
	private String memBirth;
}
