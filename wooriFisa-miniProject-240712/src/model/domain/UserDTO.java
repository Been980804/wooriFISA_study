package model.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String id;
	private String userName;
	private int auth; // 관리자 권한 / 일반유저 : 0, 관리자 :1 or 9
	ArrayList<BookDTO> bookArray;
	public UserDTO(String id, String userName, int auth){
		this.id= id;
		this.userName= userName;
		this.auth = auth;
		bookArray = new ArrayList<>();
	}
	
}
