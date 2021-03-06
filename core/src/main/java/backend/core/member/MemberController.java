package backend.core.member;

import backend.core.global.response.ApiResponse;
import backend.core.global.security.TokenProvider;
import backend.core.member.domain.Member;
import backend.core.member.dto.MemberResponseDto;
import backend.core.member.dto.MemberSignInRequestDto;
import backend.core.member.dto.MemberSignInResponseDto;
import backend.core.member.dto.MemberSignUpRequestDto;
import backend.core.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/sign-in")
    public MemberSignInResponseDto signIn(
            @Valid @RequestBody MemberSignInRequestDto dto) {
        Member member = memberService.findByCredentialsOrThrow(
                dto.getEmail(),
                dto.getPassword(),
                passwordEncoder);

        String token = tokenProvider.createToken(member);
        log.debug("signIn token = {}", token);
        return new MemberSignInResponseDto(member, token);
    }

    @PostMapping("/sign-up")
    public MemberResponseDto createMemberV1(
            @Valid @RequestBody MemberSignUpRequestDto dto) {
        Long id = memberService.save(dto);
        Member member = memberService.findByIdOrThrow(id);

        return new MemberResponseDto(member);
    }

    @GetMapping("/member")
    public MemberResponseDto memberV1(
            @AuthenticationPrincipal Long userId) {
        Member member = memberService.findByIdOrThrow(userId);

        log.info("Authenticated userId = {}", userId);
        return new MemberResponseDto(member);
    }

    @GetMapping("/members")
    public ApiResponse membersV1(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Member> members = memberService.findAllOrThrow(offset, limit);
        List<MemberResponseDto> result = members.stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
        return ApiResponse.builder().count(result.size()).data(result).build();
    }
}
