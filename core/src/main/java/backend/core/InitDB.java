package backend.core;

import backend.core.domain.Address;
import backend.core.domain.Member;
import backend.core.domain.Rating;
import backend.core.domain.RatingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {

            Address address = Address.builder().city("서울시").district("강남구").street("강남로 123-43").build();
            Member member = Member.builder().address(address).email("test@gmail.com").profile("asdsadsadsa").build();
            em.persist(member);

            Rating rating = Rating.builder().member(member).build();
            em.persist(rating);

            RatingInfo ratingInfo = RatingInfo.builder().rating(rating).isLike(true).opinion("테스트1").build();
            em.persist(ratingInfo);
        }
    }
}
