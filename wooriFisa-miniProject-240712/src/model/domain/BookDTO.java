package model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO { // 대략 10권정도

	private String bookName;
	private String isbn;
	private String author;
	private String publisher;

	@Override
	public String toString() {
		return "도서명 : " + bookName + ", isbn : " + isbn + ", 저자 : " + author + ", 출판사 : " + publisher;
	}

}
