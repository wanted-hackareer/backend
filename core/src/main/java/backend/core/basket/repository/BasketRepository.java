package backend.core.basket.repository;

import backend.core.basket.domain.Basket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void save(Basket basket) {
        em.persist(basket);
    }

    public void deleteById(Long id) {
        Basket basket = em.createQuery(
                        "select b from Basket b" +
                                " where b.id = :id", Basket.class)
                .setParameter("id", id)
                .getSingleResult();
        em.remove(basket);
    }

    public Optional<Basket> findById(Long id) {
        List<Basket> baskets = em.createQuery(
                        "select b from Basket b" +
                                " where b.id = :id", Basket.class)
                .setParameter("id", id)
                .getResultList();
        return baskets.stream().findAny();
    }

    public Optional<List<Basket>> findAll(int offset, int limit) {
        return Optional.of(
                em.createQuery(
                                "select b from Basket b", Basket.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList());
    }
}
