package model;

import service.ManageService;

public class ManageDAO {
	ManageService manageService = new ManageService();
	
	public void insertBook(String book_name, String isbn, String author, String publisher) {
		manageService.insertBook(book_name, isbn, author, publisher);
	}
	
	public int checkIsbn(String isbn) {
		return manageService.checkIsbn(isbn);
	}
	
	public void updateBook(int index, String book_name, String author, String publisher) {
		manageService.updateBook(index, book_name, author, publisher);
	}
	
	public void deleteBook(int index) {
		manageService.deleteBook(index);
	}
}
