package michal.projects.menus;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import michal.projects.data.Author;
import michal.projects.data.Category;
import michal.projects.data.User;
import michal.projects.data_access.AuthorDAO;
import michal.projects.data_access.BookDAO;
import michal.projects.data_access.CategoryDAO;

public class AddBookMenu implements Menu{

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(user.getRole().getName().equals("reader")) {
            System.out.println("only admins and emplyees have access to this app");
            return;
        }
        
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();

        System.out.println("Enter isbn number: ");
        String isbn = scanner.nextLine();

        System.out.println("Enter category: ");
        String categoryName = scanner.nextLine();

        System.out.println("Enter Author first name: ");
        String firstName = scanner.nextLine();

        System.out.println("Enter author's last name: ");
        String lastName = scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            BookDAO bookDAO = new BookDAO(session); 
            AuthorDAO authorDAO = new AuthorDAO(session);
            Author author = authorDAO.findByFullName(firstName, lastName);
            CategoryDAO categoryDAO = new CategoryDAO(session);
            Category category = categoryDAO.findByName(categoryName);
            bookDAO.addBook(title, isbn, category, author);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

    }
    
}
