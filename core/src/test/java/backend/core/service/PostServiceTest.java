package backend.core.service;

import backend.core.basket.domain.Basket;
import backend.core.domain.Address;
import backend.core.domain.Profile;
import backend.core.member.domain.Member;
import backend.core.post.domain.Post;
import backend.core.post.domain.PostStatus;
import backend.core.post.dto.PostCreateRequestDto;
import backend.core.post.dto.PostUpdateRequestDto;
import backend.core.post.service.PostService;
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
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private EntityManager em;

    private Member member;

    @BeforeEach
    public void setUp() {
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basket = Basket.builder().build();
        member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).basket(basket).nickName("테스트2").profile(profile).build();
        em.persist(member);
    }

    @Test
    @DisplayName("post 생성")
    public void create() {
        //given

        //when
        PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Long postId = postService.save(postCreateRequestDto, member.getId());

        //then
        assertThat(postService.findByIdOrThrow(postId).getTitle()).isEqualTo("테스트 제목");
        assertThat(postService.findByIdOrThrow(postId).getDescription()).isEqualTo("테스트 본문");
        assertThat(postService.findByIdOrThrow(postId).getDayOfTheWeek()).isEqualTo("월, 화, 수");
        assertThat(postService.findByIdOrThrow(postId)).isInstanceOf(Post.class);
    }

    @Test
    @DisplayName("post 전체 조회")
    public void findAll() {
        //given

        //when
        PostCreateRequestDto dto1 = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        PostCreateRequestDto dto2 = new PostCreateRequestDto("테스트 제목2", "테스트 본문2", 3, "월, 화, 수");

        postService.save(dto1, member.getId());
        postService.save(dto2, member.getId());

        //then
        assertThat(postService.findAllOrThrow(0, 100).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("post 상태 조회")
    public void findByStatus() {
        //given

        //when
        PostCreateRequestDto dto1 = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        PostCreateRequestDto dto2 = new PostCreateRequestDto("테스트 제목2", "테스트 본문2", 3, "월, 화, 수");

        postService.save(dto1, member.getId());
        postService.save(dto2, member.getId());

        //then
        assertThat(postService.findByStatusOrThrow(PostStatus.ACCESS).size()).isEqualTo(2);
        assertThat(postService.findByStatusOrThrow(PostStatus.DONE).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("post 수정 - title")
    public void updateTitle() {
        //given
        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Post post = postService.findByIdOrThrow(postService.save(dto, member.getId()));

        //when
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto(post.getId(), "수정된 제목", post.getDescription(), post.getMaximum(), post.getPostStatus(), post.getDayOfTheWeek());
        Long updateId = postService.updateOrThrow(updateDto);

        //then
        assertThat(postService.findByIdOrThrow(updateId).getTitle()).isEqualTo("수정된 제목");
    }

    @Test
    @DisplayName("post 수정 - description")
    public void updateDescription() {
        //given

        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Post post = postService.findByIdOrThrow(postService.save(dto, member.getId()));

        //when
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto(post.getId(), post.getTitle(), "수정된 본문", post.getMaximum(), post.getPostStatus(), post.getDayOfTheWeek());
        Long updateId = postService.updateOrThrow(updateDto);

        //then
        assertThat(postService.findByIdOrThrow(updateId).getDescription()).isEqualTo("수정된 본문");
    }

    @Test
    @DisplayName("post 수정 - Maximum")
    public void updateMaximum() {
        //given

        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Post post = postService.findByIdOrThrow(postService.save(dto, member.getId()));

        //when
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto(post.getId(), post.getTitle(), post.getDescription(), 2, post.getPostStatus(), post.getDayOfTheWeek());
        Long updateId = postService.updateOrThrow(updateDto);

        //then
        assertThat(postService.findByIdOrThrow(updateId).getMaximum()).isEqualTo(2);
    }

    @Test
    @DisplayName("post 수정 - status")
    public void updateStatus() {
        //given
        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Post post = postService.findByIdOrThrow(postService.save(dto, member.getId()));

        //when
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto(post.getId(), post.getTitle(), post.getDescription(), post.getMaximum(), PostStatus.DONE, post.getDayOfTheWeek());
        Long updateId = postService.updateOrThrow(updateDto);

        //then
        assertThat(postService.findByIdOrThrow(updateId).getPostStatus()).isEqualTo(PostStatus.DONE);
    }

    @Test
    @DisplayName("post 수정 - DayOfWeek")
    public void updateDayOfWeek() {
        //given
        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Post post = postService.findByIdOrThrow(postService.save(dto, member.getId()));

        //when
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto(post.getId(), post.getTitle(), post.getDescription(), post.getMaximum(), post.getPostStatus(), "월, 화");
        Long updateId = postService.updateOrThrow(updateDto);

        //then
        assertThat(postService.findByIdOrThrow(updateId).getDayOfTheWeek()).isEqualTo("월, 화");
    }
}