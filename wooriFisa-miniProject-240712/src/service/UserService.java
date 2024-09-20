package service;

import java.util.ArrayList;

import model.BookDAO;
import model.domain.BookDTO;
import model.domain.UserDTO;

public class UserService {

	public static void borrowBook(String book, UserDTO user) {
		ArrayList<BookDTO> bookList = BookDAO.getAllBook();

		BookDTO temp = new BookDTO(null, null, null, null);
		// 해당 도서를 보유하고 있는지 확인
		for (int i = 0; i < bookList.size(); i++) {
			if (bookList.get(i).getBookName().equals(book)) {
				System.out.println(book + "을(를) 대여하겠습니다.\n");
				temp = bookList.get(i);
				bookList.remove(i);
				break;
			} else {
				if (i == bookList.size() - 1)
					System.out.println(book + "을(를) 대여할 수 없습니다.\n");
			}
		}
		// 회원의 대여 도서 목록에 추가
		user.getBookArray().add(temp);
	}

	public static void returnBook(String book, UserDTO user) {
		// 반납하려는 도서를 대여한 기록이 있는지 확인
		ArrayList<BookDTO> ownBook = user.getBookArray();

		BookDTO temp = new BookDTO(null, null, null, null);

		for (int i = 0; i < ownBook.size(); i++) {
			if (ownBook.get(i).getBookName().equals(book)) {
				System.out.println(book + "을(를) 반납하겠습니다.\n");
				temp = ownBook.get(i);
				ownBook.remove(i);
				break;
			} else {
				if (i == ownBook.size() - 1)
					System.out.println(book + "을(를) 대여한 기록이 없습니다.\n");
			}
		}

		// 반납된 도서를 다시 전체 도서 목록에 추가
		ArrayList<BookDTO> bookList = BookDAO.getAllBook();
		bookList.add(temp);
	}

	public static ArrayList<BookDTO> getAllBook() {
		ArrayList<BookDTO> bookList = BookDAO.getAllBook();
		return bookList;
	}

	public static void borrowBookList(UserDTO user) {
		ArrayList<BookDTO> bookList = user.getBookArray();
		if (bookList.size() == 0) {
			System.out.println("현재 대여중인 도서가 없습니다.\n");
		} else {
			System.out.println("------현재 대여중인 도서------");
			for (BookDTO b : bookList) {
				System.out.println(b.toString()); 
			}
			System.out.println("--------------------------");
		}
	}
}