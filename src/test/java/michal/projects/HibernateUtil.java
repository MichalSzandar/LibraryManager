package michal.projects;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import michal.projects.data.Author;
import michal.projects.data.Book;
import michal.projects.data.Category;
import michal.projects.data.Loan;
import michal.projects.data.Role;
import michal.projects.data.User;

import org.hibernate.Session;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Tworzymy SessionFactory na podstawie konfiguracji
            sessionFactory = new Configuration().configure("hibernate-test.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(Book.class)
                    .addAnnotatedClass(Loan.class)
                    .addAnnotatedClass(Role.class)
                    .addAnnotatedClass(Author.class)
                    .addAnnotatedClass(Category.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        Session session = sessionFactory.openSession(); // Zmieniamy z getCurrentSession() na openSession()
        return session;
    }
    

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();  // Zamykaj SessionFactory po zakończeniu operacji
        }
    }
}

