package michal.projects;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import michal.projects.data.Book;
import michal.projects.data.Loan;
import michal.projects.data.User;
import michal.projects.data_access.BookDAO;
import michal.projects.data_access.LoanDAO;
import michal.projects.data_access.UserDAO;

import static org.junit.Assert.*;

public class LoanDAOTest {

    private Session session;
    private Transaction transaction;
    private UserDAO userDAO;
    private BookDAO bookDAO;
    private LoanDAO loanDAO;

    @Before
    public void setUp() {
        // Inicjalizujemy Hibernate session
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();

        // Inicjalizujemy DAO
        userDAO = new UserDAO(session);
        bookDAO = new BookDAO(session);
        loanDAO = new LoanDAO(session);

        // Tworzymy dane testowe
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("defaultpassword");
        session.persist(user);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("testisbn");
        book.setAvailable(Boolean.valueOf(true));
        session.persist(book);
    }

    @After
    public void tearDown() {
        // Kończymy transakcję i zamykamy sesję
        transaction.commit();
        HibernateUtil.close();
    }

    @Test
    public void testCreateLoan() {
        User user = userDAO.findByEmail("test@example.com");
        Book book = bookDAO.findByTitle("Test Book").get(0);

        // Tworzymy pożyczkę
        loanDAO.createLoan(user, book);

        // Sprawdzamy, czy pożyczka została dodana
        Loan loan = loanDAO.getLoan(user, book);
        assertNotNull(loan);
        assertNull(loan.getReturnDate());  // Pożyczka nie powinna być jeszcze zwrócona
    }

    @Test
    public void testReturnLoan() {
        // Pobieramy użytkownika i książkę
        User user = userDAO.findByEmail("test@example.com");
        Book book = bookDAO.findByTitle("Test Book").get(0);

        // Tworzymy pożyczkę
        //loanDAO.createLoan(user, book);

        // Pobieramy pożyczkę i ustawiamy datę zwrotu
        //Loan loan = loanDAO.getLoan(user, book);
        //loanDAO.returnLoan(loan.getId());

        // Sprawdzamy, czy pożyczka ma ustawioną datę zwrotu
        //loan = loanDAO.getLoan(user, book);
        //assertNotNull(loan.getReturnDate());
    }
}
