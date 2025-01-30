package michal.projects.menus;

import java.util.Scanner;

import org.hibernate.Session;

import michal.projects.data.User;

public class AddCategoryMenu implements Menu {

    @Override
    public void showMenu(Scanner scanner, Session session, User user) {
        if(user.getRole().getName().equals("reader")) {
            System.out.println("only admins and emplyees have access to this app");
            return;
        }

        
    }
    
}
