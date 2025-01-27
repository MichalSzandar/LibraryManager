package michal.projects.data_access;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import michal.projects.data.User;

public class UserDAO {
    private final Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public User findById(Long id) {
        return session.get(User.class, id);
    }

    public User findByEmail(String email) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root)
            .where(cb.equal(root.get("email"), email));

        return session.createQuery(query).uniqueResult();
    }

    public void save(User user) {
        session.persist(user);
    }

    public void delete(User user) {
        session.remove(user);
    }
}
