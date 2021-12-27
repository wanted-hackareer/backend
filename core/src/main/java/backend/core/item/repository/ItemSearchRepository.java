package backend.core.item.repository;

import backend.core.item.domain.Item;
import backend.core.item.domain.QItem;
import backend.core.repository.ItemSearch;
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
public class ItemSearchRepository {

    private final EntityManager em;

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
        return QItem.item.name.contains(name);
    }
}
