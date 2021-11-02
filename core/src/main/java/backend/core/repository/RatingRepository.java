package backend.core.repository;

import backend.core.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    //    private final EntityManager em;
//
//    public void save(Rating rating) {
//        em.persist(rating);
//    }
//
//    public Rating findOne(Long id) {
//        return em.find(Rating.class, id);
//    }
//
}
