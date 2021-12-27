package backend.core.item.repository;

import backend.core.item.domain.Item;
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

    public void delete(Item item) {
        em.remove(item);
    }

    public Optional<Item> findById(Long id) {
        List<Item> items = em.createQuery(
                        "select i from Item i" +
                                " where i.id = :id", Item.class)
                .setParameter("id", id)
                .getResultList();

        return items.stream().findAny();
    }

    public Optional<List<Item>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select i from Item i", Item.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }
}
