package model;

import java.util.ArrayList;

import model.domain.BookDTO;
import model.domain.UserDTO;
import service.UserService;

public class UserDAO {

	UserService userService = new UserService();

	static ArrayList<UserDTO> userArray = new ArrayList<>();

	static {
		userArray.add(new UserDTO("id1", "조성현", 1));
		userArray.add(new UserDTO("id2", "이주원", 1));
		userArray.add(new UserDTO("id3", "최나영", 0));
		userArray.add(new UserDTO("id4", "이현빈", 0));
	}

	public ArrayList<UserDTO> getAllUser() {
		return userArray;
	}

	public void borrowBook(String book, UserDTO user) {
		UserService.borrowBook(book, user);
	}

	public void returnBook(String book, UserDTO user) {
		UserService.returnBook(book, user);
	}
	
	public ArrayList<BookDTO> getAllBook() {
		return UserService.getAllBook();
	}
	
	public void borrowBookList(UserDTO user) {
		UserService.borrowBookList(user);
	}

	public void signin(String id, String name) {
		userArray.add(new UserDTO(id, name, 0));
	}
}