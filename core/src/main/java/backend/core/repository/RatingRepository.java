package backend.core.repository;

import backend.core.domain.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RatingRepository {

    private final EntityManager em;

    public void save(Rating rating) {
        em.persist(rating);
    }

    public Rating findOne(Long id) {
        return em.find(Rating.class, id);
    }

    public List<Rating> findAll() {
        return em.createQuery(
                        "select r from Rating r", Rating.class)
                .getResultList();
    }
}
