package backend.core.controller;

import backend.core.global.domain.Address;
import backend.core.global.domain.Profile;
import backend.core.member.dto.MemberSignUpRequestDto;
import backend.core.member.service.MemberService;
import backend.core.post.domain.Post;
import backend.core.post.dto.PostCreateRequestDto;
import backend.core.post.service.PostService;
import backend.core.tag.domain.Tag;
import backend.core.tag.dto.TagCreateRequestDto;
import backend.core.tag.service.TagService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class TagControllerTest extends ApiDocumentationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TagService tagService;

    private Post post;

    @Before
    public void init() {
        MemberSignUpRequestDto memberDto = new MemberSignUpRequestDto(
                "test@gmail.com", "test", "12312311", Address.builder().build(), Profile.builder().build()
        );
        Long memberId = memberService.save(memberDto);

        PostCreateRequestDto postDto = new PostCreateRequestDto(
                "title1", "description1", 3, "월, 화, 수");
        Long postId = postService.save(postDto, memberId);
        post = postService.findByIdOrThrow(postId);
    }

    @Test
    @DisplayName("[api] tag 전체 조회")
    public void findAll() throws Exception {
        //given
        TagCreateRequestDto dto1 = new TagCreateRequestDto(
                post.getId(), "사이다");
        TagCreateRequestDto dto2 = new TagCreateRequestDto(
                post.getId(), "사이다");
        tagService.save(dto1);
        tagService.save(dto2);

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/tags")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andDo(document("tag-전체-조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[api] tag id 조회")
    public void findById() throws Exception {
        //given
        TagCreateRequestDto dto = new TagCreateRequestDto(post.getId(), "코카 콜라");
        Tag tag = tagService.findByIdOrThrow(tagService.save(dto));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/tag/" + tag.getId())
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tag.getId()))
                .andExpect(jsonPath("$.name").value(tag.getName()))
                .andDo(document("tag-id-조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("[api] tag 저장")
    public void save() throws Exception {
        //given
        TagCreateRequestDto dto = new TagCreateRequestDto(
                post.getId(), "칠성 사이다"
        );

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/tag")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .characterEncoding("UTf-8"));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andDo(document("tag-저장",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름")
                        )));
    }

    @Test
    @DisplayName("[api] tag 다수 등록")
    public void saves() throws Exception {
        //given
        List<TagCreateRequestDto> dtoList = Arrays.asList(
                new TagCreateRequestDto(post.getId(), "생수 1.5L"),
                new TagCreateRequestDto(post.getId(), "초코파이"));

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/tags")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoList))
                .characterEncoding("UTF-8"));

        //then
        result.andExpect(status().isOk())
                .andDo(document("tag_다수_등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("[].name").type(JsonFieldType.STRING).description("태그 이름")
                        )));
    }
}