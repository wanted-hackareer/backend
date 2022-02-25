package backend.core.controller.security.auth;

import backend.core.member.domain.Member;
import backend.core.member.service.MemberService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TestAuthMemberService {
    private final MemberService memberService;

    public TestAuthMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Member getAuthenticationMember() {
        SecurityContext context = SecurityContextHolder.getContext();
        Long principal = (Long) context.getAuthentication().getPrincipal();
        return memberService.findByIdOrThrow(principal);
    }
}
