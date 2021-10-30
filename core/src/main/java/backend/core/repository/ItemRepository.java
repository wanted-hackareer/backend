package backend.core.repository;

import backend.core.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public List<Item> findById(Long id) {
        return em.createQuery(
                        "select i from Item i" +
                                " join fetch i.basket b" +
                                " where i.id = :id", Item.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Item> findAll(int offset, int limit) {
        return em.createQuery(
                        "select i from Item i" +
                                " join fetch i.basket b", Item.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
