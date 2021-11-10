package backend.core.repository;

import backend.core.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public Optional<Item> findById(Long id) {
        List<Item> items = em.createQuery(
                        "select i from Item i" +
                                " join fetch i.basket b" +
                                " where i.id = :id", Item.class)
                .setParameter("id", id)
                .getResultList();

        return items.stream().findAny();
    }

    public Optional<List<Item>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                        "select i from Item i" +
                                " join fetch i.basket b", Item.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }
}
