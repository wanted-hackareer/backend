package backend.core.repository;

import backend.core.domain.Basket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void save(Basket basket) {
        em.persist(basket);
    }

    public List<Basket> findById(Long id) {
        return em.createQuery(
                        "select b from Basket b" +
                                " join fetch b.member m" +
                                " where b.id = :id", Basket.class)
                .getResultList();
    }

    public List<Basket> findAll(int offset, int limit) {
        return em.createQuery(
                        "select b from Basket b" +
                                " join fetch b.member m", Basket.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
