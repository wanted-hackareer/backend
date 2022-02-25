package backend.core.controller;

import backend.core.controller.security.auth.TestAuthMemberService;
import backend.core.controller.security.auth.WithAuthMember;
import backend.core.global.domain.Address;
import backend.core.global.domain.Profile;
import backend.core.member.domain.Member;
import backend.core.member.dto.MemberSignInRequestDto;
import backend.core.member.dto.MemberSignUpRequestDto;
import backend.core.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
public class MemberControllerTest extends ApiDocumentationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TestAuthMemberService testAuthMemberService;

    @Test
    @DisplayName("[api] member 회원가입")
    public void signUp() throws Exception {
        //given
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "test@gmail.com",
                "test",
                "treweqw123",
                Address.builder().city("city1").street("street1").district("district1").build(),
                Profile.builder().uploadFileName("upload").storeFileName("store").build()
        );

        //when
        ResultActions result = mockMvc.perform(post("/api/v1/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(dto))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.nickName").value(dto.getNickName()))
                .andExpect(jsonPath("$.address.city").value(dto.getAddress().getCity()))
                .andExpect(jsonPath("$.address.district").value(dto.getAddress().getDistrict()))
                .andExpect(jsonPath("$.address.street").value(dto.getAddress().getStreet()))
                .andExpect(jsonPath("$.profile.uploadFileName").value(dto.getProfile().getUploadFileName()))
                .andExpect(jsonPath("$.profile.storeFileName").value(dto.getProfile().getStoreFileName()))
                .andDo(document("member_회원가입",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("nickName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                fieldWithPath("address.city").type(JsonFieldType.STRING).description("시 주소"),
                                fieldWithPath("address.district").type(JsonFieldType.STRING).description("구 주소"),
                                fieldWithPath("address.street").type(JsonFieldType.STRING).description("동 주소"),
                                fieldWithPath("profile.uploadFileName").type(JsonFieldType.STRING).description("프로필 업로드 이름"),
                                fieldWithPath("profile.storeFileName").type(JsonFieldType.STRING).description("서버 저장 이름")
                        )));
    }

    @Test
    @DisplayName("[api] member 로그인")
    public void signIn() throws Exception {
        //given
        MemberSignUpRequestDto dto = new MemberSignUpRequestDto(
                "test@gmail.com", "test", "afadas123", Address.builder().build(), Profile.builder().build());
        Member member = memberService.findByIdOrThrow(memberService.save(dto));

        //when
        MemberSignInRequestDto requestDto = new MemberSignInRequestDto(member.getEmail(), dto.getPassword());
        ResultActions result = mockMvc.perform(post("/api/v1/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(requestDto))
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(requestDto.getEmail()))
                .andDo(document("member_로그인",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인 비밀번호")
                        )
                ));
    }

    @Test
    @DisplayName("[api] member 본인 정보 조회")
    @WithAuthMember(email = "test123@naver.com", password = "1231444")
    public void findById() throws Exception {
        //given
        Member member = testAuthMemberService.getAuthenticationMember();

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(member.getEmail()))
                .andExpect(jsonPath("$.nickName").value(member.getNickName()))
                .andDo(document("member_본인_정보_조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @DisplayName("[api] member 전체 조회")
    public void findAll() throws Exception {
        //given
        MemberSignUpRequestDto dto1 = new MemberSignUpRequestDto(
                "test1@gmail.com", "test1", "afadas123", Address.builder().build(), Profile.builder().build());
        memberService.findByIdOrThrow(memberService.save(dto1));

        MemberSignUpRequestDto dto2 = new MemberSignUpRequestDto(
                "test2@gmail.com", "test2", "afad12as123", Address.builder().build(), Profile.builder().build());
        memberService.findByIdOrThrow(memberService.save(dto2));

        //when
        ResultActions result = mockMvc.perform(get("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andDo(document("member_전체_조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
