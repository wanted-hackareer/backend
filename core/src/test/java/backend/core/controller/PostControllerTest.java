package backend.core.controller;

import backend.core.controller.security.auth.TestAuthMemberService;
import backend.core.controller.security.auth.WithAuthMember;
import backend.core.member.domain.Member;
import backend.core.member.service.MemberService;
import backend.core.post.domain.PostStatus;
import backend.core.post.dto.PostCreateRequestDto;
import backend.core.post.dto.PostUpdateRequestDto;
import backend.core.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
public class PostControllerTest extends ApiDocumentationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TestAuthMemberService testAuthMemberService;

    @Test
    @DisplayName("[api] post 저장")
    @WithAuthMember(email = "test1@gmail.com")
    public void save() throws Exception {
        //given
        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()))
                .andDo(document("post_생성",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("게시글 설명글"),
                                fieldWithPath("maximum").type(JsonFieldType.NUMBER).description("게시글 모집 인원"),
                                fieldWithPath("dayOfTheWeek").type(JsonFieldType.STRING).description("가능한 요일 정보")
                        )));
    }

    @Test
    @DisplayName("[api] post id 조회")
    @WithAuthMember(email = "test@gmail.com", password = "12312311")
    public void findById() throws Exception {
        //given
        Member member = testAuthMemberService.getAuthenticationMember();

        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", 3, "월, 화, 수");
        Long postId = postService.save(dto, member.getId());

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/post/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.author").value(member.getNickName()))
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.dayOfTheWeek").value(dto.getDayOfTheWeek()))
                .andDo(document("post_id_조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[api] post 전체 조회")
    @WithAuthMember(email = "test@gmail.com", password = "12312311")
    public void findAll() throws Exception {
        //given
        Member member = testAuthMemberService.getAuthenticationMember();

        PostCreateRequestDto dto1 = new PostCreateRequestDto("테스트 제목1", "테스트 본문1", 2, "월, 화");
        PostCreateRequestDto dto2 = new PostCreateRequestDto("테스트 제목2", "테스트 본문2", 3, "월, 화, 수");
        postService.save(dto1, member.getId());
        postService.save(dto2, member.getId());

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andDo(document("post_전체_조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[api] post 수정")
    @WithAuthMember(email = "test@gmail.com", password = "12312311")
    public void updatePost() throws Exception {
        //given
        Member authMember = testAuthMemberService.getAuthenticationMember();

        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목1", "테스트 본문1", 2, "월, 화");
        Long postId = postService.save(dto, authMember.getId());

        //when
        PostUpdateRequestDto updateDto = new PostUpdateRequestDto(postId, "수정된 제목1", "수정된 본문1", 3, PostStatus.ACCESS, dto.getDayOfTheWeek());
        ResultActions result = mockMvc.perform(put("/api/v1/post/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(updateDto)));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value(updateDto.getTitle()))
                .andExpect(jsonPath("$.author").value(authMember.getNickName()))
                .andExpect(jsonPath("$.description").value(updateDto.getDescription()))
                .andExpect(jsonPath("$.dayOfTheWeek").value(updateDto.getDayOfTheWeek()))
                .andDo(document("post_수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("게시글 설명"),
                                fieldWithPath("maximum").type(JsonFieldType.NUMBER).description("게시글 참여 최대 인원"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("게시글 상태"),
                                fieldWithPath("dayOfTheWeek").type(JsonFieldType.STRING).description("장보기 가능 날짜")
                        )));
    }
}