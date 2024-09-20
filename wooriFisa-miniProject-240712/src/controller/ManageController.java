package controller;

import model.ManageDAO;

public class ManageController {
	ManageDAO manageDAO = new ManageDAO();
	
	public void insertBook(String book_name, String isbn, String author, String publisher) {
		manageDAO.insertBook(book_name, isbn, author, publisher);
	}
	
	public int checkIsbn(String isbn) {
		return manageDAO.checkIsbn(isbn);
	}
	
	public void updateBook(int index, String book_name, String author, String publisher) {
		manageDAO.updateBook(index, book_name, author, publisher);
	}
	public void deleteBook(int index) {
		manageDAO.deleteBook(index);
	}
	
}
