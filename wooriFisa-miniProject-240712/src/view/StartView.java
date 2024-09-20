package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.ManageController;
import controller.UserController;
import model.domain.BookDTO;
import model.domain.UserDTO;

public class StartView {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserDTO nowUser = null;
        UserController userController = new UserController();
        ManageController manageController = new ManageController();

        ArrayList<UserDTO> userArray = userController.getAllUser();

        while (true) {
            if (nowUser == null) {
                System.out.println("------우리FISA 도서관------");
                System.out.println("0. 회원가입");
                System.out.println("1. 로그인\n");
                int login_yn = -1;
                while (true) {
                    try {
                        System.out.print("번호 입력 : ");
                        login_yn = sc.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                        sc.next();
                    }
                }
                System.out.println();

                if (login_yn == 1) {
                    System.out.print("id를 입력해주세요 : ");
                    String login_id = sc.next();

                    boolean userFound = false;
                    for (UserDTO user : userArray) {
                        if (user.getId().equals(login_id)) {
                            System.out.println("\n환영합니다 " + user.getUserName() + "님\n");
                            nowUser = user;
                            userFound = true;
                            break;
                        }
                    }

                    if (!userFound) {
                        System.out.println("존재하지 않는 ID입니다. \n\n");
                    }
                } else if (login_yn == 0) {
                    System.out.println("------회원가입------");
                    System.out.print("아이디 : ");
                    String newUserId = sc.next();
                    System.out.print("이름 : ");
                    String newUserName = sc.next();
                    userController.signin(newUserId, newUserName);
                    userArray = userController.getAllUser();
                    System.out.println();
                } else {
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.\n");
                }
            } else {
                System.out.println("1. 모든 도서 보기");
                System.out.println("2. 도서 대여하기");
                System.out.println("3. 도서 반납하기");
                System.out.println("4. 대여중인 도서보기");
                if (nowUser.getAuth() == 1) {
                    System.out.println("5. 도서 등록하기");
                    System.out.println("6. 도서 수정하기");
                    System.out.println("7. 도서 삭제하기");
                }
                System.out.println("0. 로그아웃");

                int num = -1;
                while (true) {
                    try {
                        System.out.print("\n번호 입력 : ");
                        num = sc.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                        sc.next();
                    }
                }

                switch (num) {
                    case 0:
                        nowUser = null;
                        System.out.println("로그아웃 되었습니다.\n");
                        break;
                    case 1:
                        ArrayList<BookDTO> bookList = userController.getAllBook();
                        for (BookDTO b : bookList) {
                            System.out.println(b.toString());
                        }
                        System.out.println();
                        break;
                    case 2:
                        System.out.print("대여할 도서명을 입력해주세요 : ");
                        String bookname = sc.next();
                        userController.borrowBook(bookname, nowUser);
                        break;
                    case 3:
                        System.out.print("반납할 도서명을 입력해주세요 : ");
                        String bookname1 = sc.next();
                        userController.returnBook(bookname1, nowUser);
                        break;
                    case 4:
                        userController.borrowBookList(nowUser);
                        break;
                    case 5:
                        if (nowUser.getAuth() == 1) {
                            System.out.print("도서명 : ");
                            String book_name = sc.next();
                            System.out.print("isbn : ");
                            String isbn = sc.next();
                            System.out.print("작가 : ");
                            String author = sc.next();
                            System.out.print("출판사 : ");
                            String publisher = sc.next();
                            manageController.insertBook(book_name, isbn, author, publisher);
                        }
                        break;
                    case 6:
                        if (nowUser.getAuth() == 1) {
                            System.out.print("수정하려는 도서의 isbn을 입력하세요 : ");
                            String isbn_update = sc.next();
                            int bookArray_idx = manageController.checkIsbn(isbn_update);
                            if (bookArray_idx == -9999) {
                                System.out.println("존재하지 않는 isbn입니다.\n");
                                System.out.println();
                            } else {
                                System.out.print("도서명 : ");
                                String book_name_upd = sc.next();
                                System.out.print("작가 : ");
                                String author_upd = sc.next();
                                System.out.print("출판사 : ");
                                String publisher_upd = sc.next();
                                manageController.updateBook(bookArray_idx, book_name_upd, author_upd, publisher_upd);
                                System.out.println("수정되었습니다.\n");
                            }
                        }
                        break;
                    case 7:
                        if (nowUser.getAuth() == 1) {
                            System.out.print("삭제하려는 도서의 isbn을 입력하세요 : ");
                            String isbn_delete = sc.next();
                            int bookArray_idx2 = manageController.checkIsbn(isbn_delete);
                            if (bookArray_idx2 != -9999) {
                                manageController.deleteBook(bookArray_idx2);
                                System.out.println("삭제되었습니다.\n");
                            } else {
                                System.out.println("존재하지 않는 isbn 입니다.\n");
                            }
                        }
                        break;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 시도해주세요.\n");
                        break;
                }
            }
        }
    }
}
