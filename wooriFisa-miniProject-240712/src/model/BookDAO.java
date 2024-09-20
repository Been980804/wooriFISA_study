package model;

import java.util.ArrayList;

import model.domain.BookDTO;

public class BookDAO {
	static ArrayList<BookDTO> bookArray = new ArrayList<>();

	static {
		bookArray.add(new BookDTO("어린왕자", "00001", "생택쥐페리", "길벗"));
		bookArray.add(new BookDTO("흥부전", "03232", "허균", "가톨릭 출판사"));
		bookArray.add(new BookDTO("반지의제왕", "38782", "J.R.R.톨킨", "자음과모음"));
		bookArray.add(new BookDTO("백종원의요리비책", "78732", "백종원", "교보"));
		bookArray.add(new BookDTO("자바 잘 읽는 법", "98755", "라우렌치우 스필커", "제이펍"));
		bookArray.add(new BookDTO("해리포터", "00120", "JK롤링", "블룸스버리"));
		bookArray.add(new BookDTO("Java 달려", "00628", "김자바", "한빛미디어"));
		bookArray.add(new BookDTO("코테 마스터하기", "00235", "김백준", "백준미디어"));
		bookArray.add(new BookDTO("개미", "10345", "베르나르베르베르", "인터파크"));
		bookArray.add(new BookDTO("아몬드", "20849", "손원평", "창비"));
	}

	public static ArrayList<BookDTO> getAllBook() {
		return bookArray;
	}

	public static void insertBook(BookDTO newBook) {
		bookArray.add(newBook);
	}

	public static int checkIsbn(String isbn) {
		for (int i = 0; i < bookArray.size(); i++) {
			if (bookArray.get(i).getIsbn().equals(isbn)) {
				return i;
			}
		}

		return -9999;
	}

	public static void updateBook(int index, String book_name, String author, String publisher) {
		BookDTO dto = bookArray.get(index);
		dto.setBookName(book_name);
		dto.setAuthor(author);
		dto.setPublisher(publisher);
		bookArray.remove(index);
		bookArray.add(dto);

	}

	public static void deleteBook(int index) {
		bookArray.remove(index);
	}
}
