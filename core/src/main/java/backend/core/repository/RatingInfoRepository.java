package backend.core.repository;

import backend.core.domain.RatingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingInfoRepository extends JpaRepository<RatingInfo, Long> {
}
