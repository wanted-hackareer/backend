package backend.core.repository;

import backend.core.domain.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public abstract class RatingRepositoryImpl implements RatingRepository {

    private final EntityManager em;

    @Override
    public Optional<Rating> findById(Long id) {
        return Optional.of(em.createQuery(
                        "select r from Rating r" +
                                " where r.id = :id", Rating.class)
                .setParameter("id", id)
                .getSingleResult());
    }
}
