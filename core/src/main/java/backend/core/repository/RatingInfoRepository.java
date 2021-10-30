package backend.core.repository;

import backend.core.domain.Rating;
import backend.core.domain.RatingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RatingInfoRepository {

    private final EntityManager em;

    public void save(RatingInfo ratingInfo) {
        em.persist(ratingInfo);
    }

    public RatingInfo findOne(Long id) {
        return em.find(RatingInfo.class, id);
    }

    public List<RatingInfo> findAll(int offset, int limit) {
        return em.createQuery(
                        "select r from RatingInfo r", RatingInfo.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<RatingInfo> findById(Long id) {
        return em.createQuery(
                        "select r from RatingInfo r where r.id = :id", RatingInfo.class)
                .setParameter("id", id)
                .getResultList();
    }
}
