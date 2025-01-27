package michal.projects.data_access;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import michal.projects.data.Role;

public class RoleDAO {
    private final Session session;

    public RoleDAO(Session session) {
        this.session = session;
    }

    public Role findById(Long id) {
        return session.get(Role.class, id);
    }

    public Role findByName(String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Role> query = cb.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        query.select(root)
            .where(cb.equal(root.get("name"), name));

        return session.createQuery(query).uniqueResult();
    }

    public void save(Role role) {
        session.persist(role);
    }

    public void delete(Role role) {
        session.remove(role);
    }
}
