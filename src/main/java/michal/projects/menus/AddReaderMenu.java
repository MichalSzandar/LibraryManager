package michal.projects.menus;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import michal.projects.data.User;
import michal.projects.data_access.RoleDAO;
import michal.projects.data_access.UserDAO;

public class AddReaderMenu implements Menu {

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(user.getRole().getName().equals("reader")) {
            System.out.println("only admins and emplyees have access to this app");
            return;
        }

        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            User reader = new User();
            reader.setEmail(email);
            reader.setPassword(password);
            RoleDAO roleDAO = new RoleDAO(session);
            reader.setRole(roleDAO.findByName("reader"));
            UserDAO userDAO = new UserDAO(session);
            userDAO.save(reader);
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    
}
