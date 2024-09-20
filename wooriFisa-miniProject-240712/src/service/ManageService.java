package service;

import model.BookDAO;
import model.domain.BookDTO;

public class ManageService extends UserService{
	BookDAO bookDAO = new BookDAO();
	
	// 도서 등록하기
	public void insertBook(String book_name, String isbn, String author, String publisher) {
		BookDTO newBook = new BookDTO(book_name,isbn,author,publisher);
		
		bookDAO.insertBook(newBook);
	}
	// isbn 체크
	public int checkIsbn(String isbn) {		
		return bookDAO.checkIsbn(isbn);
	}
	// 도서 수정하기
	public void updateBook(int index, String book_name, String author, String publisher) {		
		bookDAO.updateBook(index, book_name, author, publisher);
	}
	// 도서 삭제하기	
	public void deleteBook(int index) {		
		bookDAO.deleteBook(index);
	}
}
