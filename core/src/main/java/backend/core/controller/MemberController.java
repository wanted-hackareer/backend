package backend.core.controller;

import backend.core.dto.response.MemberResponseDto;
import backend.core.domain.Member;
import backend.core.global.response.ApiResponse;
import backend.core.global.security.TokenProvider;
import backend.core.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.request.MemberRequestDto.MemberSignInRequestDto;
import static backend.core.dto.request.MemberRequestDto.MemberSignUpRequestDto;
import static backend.core.dto.response.MemberResponseDto.MemberSignInResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/sign-in")
    public MemberSignInResponseDto signInUser(@RequestBody MemberSignInRequestDto dto) {
        Member member = memberService.findByCredentialsOrThrow(
                dto.getEmail(),
                dto.getPassword(),
                passwordEncoder);

        String token = tokenProvider.createToken(member);
        log.debug("token = {}", token);
        return new MemberSignInResponseDto(member, token);
    }

    @PostMapping("/sign-up")
    public MemberResponseDto createMemberV1(
            @Valid @RequestBody MemberSignUpRequestDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        Long id = memberService.save(dto);
        Member member = memberService.findByIdOrThrow(id);

        log.info("dto.getPassword() = {}", dto.getPassword());
        log.info("member.getEmail() = {}", member.getEmail());
        log.info("member.getPassword() = {}", member.getPassword());
        return new MemberResponseDto(member);
    }

    @GetMapping("/member")
    public MemberResponseDto memberV1(@AuthenticationPrincipal String userId) {
        Member member = memberService.findByIdOrThrow(Long.parseLong(userId));

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
