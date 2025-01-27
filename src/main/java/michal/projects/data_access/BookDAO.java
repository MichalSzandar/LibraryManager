package michal.projects.data_access;

import java.util.List;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import michal.projects.data.Author;
import michal.projects.data.Book;
import michal.projects.data.Category;

public class BookDAO {
    private final Session session;

    public BookDAO(Session session) {
        this.session = session;
    }

    public Book findById(Long id) {
        return session.get(Book.class, id);
    }

    public List<Book> findByCategory(String categoryName) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
            .where(cb.equal(root.get("category").get("name"), categoryName));
        return session.createQuery(query).getResultList();
    }

    public List<Book> findByTitle(String title) { 
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
            .where(cb.equal(root.get("title"), title));

        return session.createQuery(query).getResultList();
    }

    public Book findByIsbn(String isbn, String title) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        query.select(root)
            .where(cb.and(
                cb.equal(root.get("isbn"), isbn),
                cb.equal(root.get("title"), title)
            ));
            
        return session.createQuery(query).uniqueResult();
    }

    public void save(Book book) {
        session.persist(book);
    }

    public void delete(Book book) {
        session.remove(book);
    }

    public void addBook(String title, String isbn, Category category, Author author) {
        Book book = new Book();
        book.setAuthor(author);
        book.setCategory(category);
        book.setIsbn(isbn);
        book.setTitle(title);
        save(book);
    }
}
