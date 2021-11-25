package backend.core.controller;

import backend.core.domain.Address;
import backend.core.domain.Basket;
import backend.core.domain.Member;
import backend.core.domain.Profile;
import backend.core.dto.request.PostRequestDto;
import backend.core.dto.response.StaffResponseDto;
import backend.core.dto.response.TagResponseDto;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static backend.core.dto.request.PostRequestDto.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
public class PostControllerTest extends ApiDocumentationTest {

    @Autowired
    private PostController postController;

    @Test
    @DisplayName("[api] post 저장")
    public void save() throws Exception {
        //given
        Address address = Address.builder().city("서울시").district("강동구").street("미아로").build();
        Profile profile = Profile.builder().storeFileName("ASDAS-asDASDAS-aSDSA.jpg").uploadFileName("프로필 이미지").build();
        Basket basket = Basket.builder().build();
        Member member = Member.builder().email("test@gmail.com").password("DF#Q$FWAD").address(address).nickName("테스트2").basket(basket).profile(profile).build();
        em.persist(member);

        PostCreateRequestDto dto = new PostCreateRequestDto("테스트 제목", "테스트 본문", member.getId(), 3, "월, 화, 수");

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.author").value(member.getNickName()))
                .andDo(document("post-생성",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                            fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                            fieldWithPath("description").type(JsonFieldType.STRING).description("게시글 설명글"),
                            fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("작성 회원 id"),
                            fieldWithPath("maximum").type(JsonFieldType.NUMBER).description("게시글 모집 인원"),
                            fieldWithPath("dayOfTheWeek").type(JsonFieldType.STRING).description("가능한 요일 정보")
                        )));
    }

    //TODO 전체 조회, id 조회, 수정, 검색 테스트
}