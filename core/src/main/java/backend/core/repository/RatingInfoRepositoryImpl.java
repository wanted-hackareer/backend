package backend.core.repository;

import backend.core.domain.RatingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public abstract class RatingInfoRepositoryImpl implements RatingRepository {

    private final EntityManager em;

    public void save(RatingInfo ratingInfo) {
        em.persist(ratingInfo);
    }

    public RatingInfo findOne(Long id) {
        return em.find(RatingInfo.class, id);
    }

    // Optional 사용 방법
    public Optional<RatingInfo> findOneOptional(Long id) {
        return Optional.of(em.find(RatingInfo.class, id));
    }

    public Optional<List<RatingInfo>> findByIdOptionalList(Long id) {
        return Optional.of(em.createQuery(
                        "select r from RatingInfo  r", RatingInfo.class)
                .getResultList());
    }

    public Iterable<RatingInfo> findAllWithOffsetLimit2(int offset, int limit) {
        return em.createQuery(
                        "select r from RatingInfo r", RatingInfo.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<RatingInfo> findAllWithOffsetLimit(int offset, int limit) {
        return em.createQuery(
                        "select r from RatingInfo r", RatingInfo.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<RatingInfo> customFindById(Long id) {
        return em.createQuery(
                        "select r from RatingInfo r where r.id = :id", RatingInfo.class)
                .setParameter("id", id)
                .getResultList();
    }
}
