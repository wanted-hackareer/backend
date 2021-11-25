package backend.core.repository;

import backend.core.domain.Item;
import backend.core.domain.QItem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    public Optional<List<Item>> findAllBySearch(ItemSearch itemSearch) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QItem item = QItem.item;

        List<Item> itemList = query
                .select(item)
                .from(item)
                .where(nameLike(itemSearch.getName()))
                .offset(0)
                .limit(100)
                .fetch();
        return Optional.of(itemList);
    }

    private BooleanExpression nameLike(String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }
        return QItem.item.name.like(name);
    }
}
