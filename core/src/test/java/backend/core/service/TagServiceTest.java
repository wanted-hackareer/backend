package backend.core.service;

import backend.core.basket.domain.Basket;
import backend.core.domain.Address;
import backend.core.domain.Profile;
import backend.core.global.error.exception.CustomException;
import backend.core.member.domain.Member;
import backend.core.post.domain.Post;
import backend.core.tag.domain.Tag;
import backend.core.tag.dto.TagCreateRequestDto;
import backend.core.tag.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Autowired
    private EntityManager em;

    private Post post;

    @BeforeEach
    public void init() {
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test1@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("테스트1").profile(profile).build();
        post = Post.builder().title("테스트 제목").description("테스트 본문").member(member).dayOfTheWeek("월, 금").maximum(2).build();
        em.persist(post);
    }

    @Test
    @DisplayName("Tag 생성")
    public void create() {
        //given
        TagCreateRequestDto dto = new TagCreateRequestDto(post.getId(), "아이언맨");

        //when
        Long tagId = tagService.save(dto);

        //then
        assertThat(tagService.findByIdOrThrow(tagId)).isInstanceOf(Tag.class);
        assertThat(tagService.findByIdOrThrow(tagId).getName()).isEqualTo("아이언맨");
        assertThat(tagService.findByIdOrThrow(tagId).getPost().getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("Tag 조회 - findAll")
    public void findAll() {
        //given
        Tag tagA = Tag.builder().post(post).name("아이언맨").build();
        Tag tagB = Tag.builder().post(post).name("캡틴 아메리카").build();

        //when
        em.persist(tagA);
        em.persist(tagB);

        //then
        assertThat(tagService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Tag 삭제 - delete")
    public void delete() {
        //given
        Tag tag = Tag.builder().post(post).name("아이언맨").build();
        em.persist(tag);

        //when
        Long id = tagService.delete(tag.getId());

        //then
        Assertions.assertThrows(CustomException.class, () -> {
            tagService.findByIdOrThrow(id);
        });
    }
}