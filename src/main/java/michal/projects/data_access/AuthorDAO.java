package michal.projects.data_access;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import michal.projects.data.Author;

public class AuthorDAO {
    private final Session session;

    public AuthorDAO(Session session) {
        this.session = session;
    }

    public Author findById(Long id) {
        return session.get(Author.class, id);
    }

    public Author findByFullName(String firstName, String lastName) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);
        query.select(root)
            .where(cb.and(
                cb.equal(root.get("lastname"), lastName), 
                cb.equal(root.get("firstname"), firstName)));

        return session.createQuery(query).uniqueResult();
    }

    public void save(Author author) {
        session.persist(author);
    }

    public void delete(Author author) {
        session.remove(author);
    }

    public void addAuthor(String firstname, String lastname) {
        Author author = new Author();
        author.setFirstName(firstname);
        author.setLastName(lastname);
        save(author);
    }
}
