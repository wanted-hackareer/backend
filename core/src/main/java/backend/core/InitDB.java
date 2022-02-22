package backend.core;

import backend.core.Staff.domain.Staff;
import backend.core.Staff.domain.StaffStatus;
import backend.core.basket.domain.Basket;
import backend.core.domain.Address;
import backend.core.domain.Profile;
import backend.core.item.domain.Item;
import backend.core.member.domain.Member;
import backend.core.post.domain.Post;
import backend.core.tag.domain.Tag;
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
            Address addressA = Address.builder().city("서울시").district("강동구").street("성내동").build();
            Profile profileA = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.png").uploadFileName("프로필 이미지.png").build();
            Basket basketA = Basket.builder().build();
            Member memberA = Member.builder().email("tester1@gmail.com").password("DF#Q$FWAD").basket(basketA).address(addressA).nickName("불꽃 양파").profile(profileA).build();
            em.persist(memberA);

            Address addressB = Address.builder().city("서울시").district("강동구").street("천호동").build();
            Profile profileB = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("kakao-image.jpg").build();
            Basket basketB = Basket.builder().build();
            Member memberB = Member.builder().email("tester2@gmail.com").password("SDFGDSGEW").basket(basketB).address(addressB).nickName("다섯 꼬리 뱀").profile(profileB).build();
            em.persist(memberB);

            Address addressC = Address.builder().city("서울시").district("강동구").street("둔촌동").build();
            Profile profileC = Profile.builder().storeFileName("akjshd-askdas-askjdass.png").uploadFileName("이미지.png").build();
            Basket basketC = Basket.builder().build();
            Member memberC = Member.builder().email("tester1@gmail.com").password("DF#Q$FWAD").basket(basketC).address(addressC).nickName("우주").profile(profileC).build();
            em.persist(memberC);

            Post postA = Post.builder().title("테스트1 제목").description("테스트1 본문").member(memberA).dayOfTheWeek("월, 수").maximum(3).build();
            em.persist(postA);

            Post postB = Post.builder().title("테스트2 제목").description("테스트2 본문").member(memberB).dayOfTheWeek("월, 화, 수").maximum(2).build();
            em.persist(postB);

            Tag tagA = Tag.builder().post(postA).name("태그 테스트 1").build();
            Tag tagB = Tag.builder().post(postA).name("태그 테스트 2").build();
            Tag tagC = Tag.builder().post(postB).name("태그 테스트 3").build();
            em.persist(tagA);
            em.persist(tagB);
            em.persist(tagC);

            Item itemA = Item.builder().basket(basketC).name("바나나").quantity(3).build();
            Item itemB = Item.builder().basket(basketC).name("휴지").quantity(8).build();
            Item itemC = Item.builder().basket(basketC).name("생수 1.5L").quantity(5).build();
            em.persist(itemA);
            em.persist(itemB);
            em.persist(itemC);

            Staff staffA = Staff.builder().post(postA).member(memberC).build();
            Staff staffB = Staff.builder().post(postB).member(memberC).build();
            staffA.update(StaffStatus.ACCESS);
            em.persist(staffA);
            em.persist(staffB);

        }
    }
}
