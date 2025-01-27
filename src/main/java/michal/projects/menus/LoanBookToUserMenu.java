package michal.projects.menus;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import michal.projects.data.Book;
import michal.projects.data.User;
import michal.projects.data_access.BookDAO;
import michal.projects.data_access.LoanDAO;
import michal.projects.data_access.UserDAO;

public class LoanBookToUserMenu implements Menu{

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(user.getRole().getName().equals("reader")) {
            System.out.println("only admins and emplyees have access to this app");
            return;
        }

        System.out.println("Enter email of reader who wants to borrow: ");
        String email = scanner.nextLine();

        UserDAO userDAO = new UserDAO(session);
        User reader = userDAO.findByEmail(email);

        if(reader == null) {
            System.out.println("User does not exist!");
            return;
        }

        System.out.print("Enter book title to loan: ");
        String title = scanner.nextLine();

        System.out.println("Enter isbn number: ");
        String isbn = scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            BookDAO bookDAO = new BookDAO(session);
            Book book = bookDAO.findByIsbn(isbn, title);

            if (book != null && book.getAvailable().booleanValue()) {
                LoanDAO loanDAO = new LoanDAO(session);
                loanDAO.createLoan(user, book);
                book.setAvailable(Boolean.valueOf(false));
                transaction.commit();
                System.out.println("Book successfully loaned to user.");
            } else {
                System.out.println("Book not found.");
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    
}
