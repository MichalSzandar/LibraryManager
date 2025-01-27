package michal.projects.menus;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import michal.projects.data.User;
import michal.projects.data_access.UserDAO;

public class RemoveReaderMenu implements Menu {

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(!user.getRole().getName().equals("admin")) {
            System.out.println("only admins can remove users");
            return;
        }

        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            UserDAO userDAO = new UserDAO(session);
            userDAO.delete(userDAO.findByEmail(email));
        } catch(Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
    
}
