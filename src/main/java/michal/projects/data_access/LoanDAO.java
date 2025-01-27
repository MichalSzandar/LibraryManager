package michal.projects.data_access;

import java.time.Instant;
import java.util.Date;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import michal.projects.data.Book;
import michal.projects.data.Loan;
import michal.projects.data.User;

public class LoanDAO {
    private final Session session;
    public LoanDAO(Session session) {
        this.session = session;
    }

    public Loan getLoanById(Long id) {
        return session.get(Loan.class, id);
    }

    public Loan getLoan(User user, Book book) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Loan> query = cb.createQuery(Loan.class);
        Root<Loan> root = query.from(Loan.class);
        query.select(root)
            .where(cb.and(
                cb.equal(root.get("user"), user), 
                cb.equal(root.get("book"), book),
                cb.isNull(root.get("returnDate"))));

        return session.createQuery(query).uniqueResult();
    }

    public void returnLoan(Long id) {
        Loan loan = getLoanById(id);
        loan.setReturnDate(Date.from(Instant.now()));
    }

    public void delete(Loan loan) {
        session.remove(loan);
    }

    public void save(Loan loan) {
        session.persist(loan);
    }

    public void createLoan(User user, Book book) {
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setLoanDate(Date.from(Instant.now()));
        loan.setUser(user);
        save(loan);
    }
}
