package michal.projects.menus;

import java.util.Scanner;

import org.hibernate.Session;

import michal.projects.data.User;

public interface Menu {
    public void showMenu(Scanner scanner, Session session, User user);
}
