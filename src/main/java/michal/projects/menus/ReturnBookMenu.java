package michal.projects.menus;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import michal.projects.data.Book;
import michal.projects.data.Loan;
import michal.projects.data.User;
import michal.projects.data_access.BookDAO;
import michal.projects.data_access.LoanDAO;
import michal.projects.data_access.UserDAO;

public class ReturnBookMenu implements Menu {

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(user.getRole().getName().equals("reader")) {
            System.out.println("only admins and emplyees have access to this app");
            return;
        }

        System.out.print("Enter mail of reader to return book: ");
        String mail = scanner.nextLine();
        System.out.println("Enter book title to return: ");
        String title = scanner.nextLine();
        System.out.println("Enter isbn number: ");
        String isbn = scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            LoanDAO loanDAO = new LoanDAO(session);
            UserDAO userDAO = new UserDAO(session);
            BookDAO bookDAO = new BookDAO(session);

            User reader = userDAO.findByEmail(mail);
            Book book = bookDAO.findByIsbn(isbn, title);
            Loan loan = loanDAO.getLoan(reader, book);

            if(reader != null && book != null ) {
                loan.setReturnDate(Date.from(Instant.now()));
                book.setAvailable(Boolean.valueOf(true));
                transaction.commit();
                System.out.println("Book successfully returned.");
            } else {
                System.out.println("Unable to return book.");
                transaction.rollback();
            }
            
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    
}
