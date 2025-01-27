package michal.projects;

import java.util.HashMap;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import michal.projects.data.Author;
import michal.projects.data.Book;
import michal.projects.data.Category;
import michal.projects.data.Loan;
import michal.projects.data.Role;
import michal.projects.data.User;
import michal.projects.data_access.UserDAO;
import michal.projects.menus.AddReaderMenu;
import michal.projects.menus.FilterBooksByCategory;
import michal.projects.menus.LoanBookToUserMenu;
import michal.projects.menus.Menu;
import michal.projects.menus.RemoveReaderMenu;
import michal.projects.menus.ReturnBookMenu;

public class App 
{
    private static SessionFactory sessionFactory;
    private static HashMap<Integer, Menu> menuMap;

    public static void main(String[] args) {
        // Configure Hibernate
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Loan.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(Author.class);

        sessionFactory = configuration.buildSessionFactory();

        menuMap = new HashMap<>();
        menuMap.put(1, new FilterBooksByCategory());
        menuMap.put(2, new LoanBookToUserMenu());
        menuMap.put(3, new ReturnBookMenu());
        menuMap.put(4, new AddReaderMenu());
        menuMap.put(5, new RemoveReaderMenu());


        try (Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n=== Library Management System ===");
                System.out.println("1. Log in");
                System.out.println("2. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    handleLogin(scanner);
                } else if (choice == 2) {
                    System.out.println("Exiting application. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        } finally {
            sessionFactory.close();
        }
    }

    private static void handleLogin(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            UserDAO userDAO = new UserDAO(session);
            User user = userDAO.findByEmail(email);

            if (user != null && user.isValidPassword(password)) { // Simplified password check
                System.out.println("Login successful! Welcome, " + user.getEmail());
                displayMenu(scanner, session, user);
            } else {
                System.out.println("Invalid email or password.");
            }
        }
    }

    private static void displayMenu(Scanner scanner, Session session, User user) {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Search books by category");
            System.out.println("2. Loan a book");
            System.out.println("3. Return a book");
            System.out.println("4. Add reader");
            System.out.println("5. Remove reader");
            System.out.println("6. Add book");
            System.out.println("7. Remove book");
            System.out.println("8. Add author");
            System.out.println("9. Add category");
            System.out.println("10. Log out");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            menuMap.get(choice).showMenu(scanner, session, user);
        }
    }
}
