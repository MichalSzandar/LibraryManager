package michal.projects.data_access;

import org.hibernate.Session;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import michal.projects.data.Category;

public class CategoryDAO {
    private final Session session;

    public CategoryDAO(Session session) {
        this.session = session;
    }

    public Category findById(Long id) {
        return session.get(Category.class, id);
    }

    public Category findByName(String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        query.select(root)
            .where(cb.equal(root.get("name").get("name"), name));

        return session.createQuery(query).uniqueResult();
    }

    public void save(Category category) {
        session.persist(category);
    }

    public void delete(Category category) {
        session.remove(category);
    }

    public void addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        save(category);
    }
}
