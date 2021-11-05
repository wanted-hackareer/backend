package backend.core.controller.member;

import backend.core.domain.Member;
import backend.core.global.response.ApiResponse;
import backend.core.security.TokenProvider;
import backend.core.service.MemberService;
import backend.core.controller.member.dto.MemberCreateRequestDto;
import backend.core.controller.member.dto.MemberResponseDto;
import backend.core.controller.member.dto.MemberSignInRequestDto;
import backend.core.controller.member.dto.MemberSignInResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class MemberApiController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign-in")
    public MemberSignInResponseDto authenticate(@RequestBody MemberSignInRequestDto dto) {
        Member member = memberService.findByEmail(dto.getEmail());
        String token = tokenProvider.createToken(member);

        log.debug("token = {}", token);
        return new MemberSignInResponseDto(member, token);
    }

    @PostMapping("/member")
    public MemberResponseDto createMemberV1(
            @Valid @RequestBody MemberCreateRequestDto dto) {
        Long id = memberService.save(dto);
        Member member = memberService.findById(id);
        log.info("member = {}", member.getEmail());
        return new MemberResponseDto(member);
    }

    @GetMapping("/member/{id}")
    public MemberResponseDto memberV1(
            @PathVariable Long id, @AuthenticationPrincipal String userId) {
        log.info("Authenticated userId = {}", userId);
        Member member = memberService.findById(id);
        return new MemberResponseDto(member);
    }

    @GetMapping("/members")
    public ApiResponse membersV1(
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "limit") int limit) {
        List<Member> members = memberService.findAll(offset, limit);
        List<MemberResponseDto> result = members.stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
        return ApiResponse.builder().count(result.size()).data(result).build();
    }
}
