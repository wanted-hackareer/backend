package backend.core.controller.security.auth;

import backend.core.global.domain.Address;
import backend.core.global.domain.Profile;
import backend.core.member.dto.MemberSignUpRequestDto;
import backend.core.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@Slf4j
public class WithAuthMemberSecurityContextFactory implements WithSecurityContextFactory<WithAuthMember> {

    private final MemberService memberService;

    public WithAuthMemberSecurityContextFactory(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public SecurityContext createSecurityContext(WithAuthMember annotation) {
        Long principal = memberService.save(new MemberSignUpRequestDto(
                annotation.email(),
                annotation.name(),
                annotation.password(),
                Address.builder().build(),
                Profile.builder().build()));

        AbstractAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                principal, null, AuthorityUtils.NO_AUTHORITIES);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
