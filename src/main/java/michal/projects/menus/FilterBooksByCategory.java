package michal.projects.menus;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import michal.projects.data.Book;
import michal.projects.data.User;
import michal.projects.data_access.BookDAO;

public class FilterBooksByCategory implements Menu{

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(user.getRole().getName().equals("reader")) {
            System.out.println("only admins and emplyees have access to this app");
            return;
        }

        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();

        BookDAO bookDAO = new BookDAO(session);
        List<Book> books = bookDAO.findByCategory(categoryName);

        if (books.isEmpty()) {
            System.out.println("No books found in this category.");
        } else {
            books.forEach(book -> System.out.println("Book: " + book.getTitle() + " by " + book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName()));
        }
    }
    
}
