package controller;

import java.util.ArrayList;

import model.UserDAO;
import model.domain.BookDTO;
import model.domain.UserDTO;

public class UserController {
	UserDAO userDAO = new UserDAO();

	// common
	public ArrayList<UserDTO> getAllUser() {
		return userDAO.getAllUser();
	}

	public void borrowBook(String book, UserDTO user) {
		userDAO.borrowBook(book, user);
	}

	public void returnBook(String book, UserDTO user) {
		userDAO.returnBook(book, user);
	}
	
	public ArrayList<BookDTO> getAllBook() {
		return userDAO.getAllBook();
	}

	public void borrowBookList(UserDTO user) {
		userDAO.borrowBookList(user);
	}
	
	public void signin(String id, String name) {
		userDAO.signin(id, name);
	}
}
