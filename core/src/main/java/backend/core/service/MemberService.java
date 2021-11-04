package backend.core.service;

import backend.core.domain.Member;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.MemberRepository;
import backend.core.web.member.dto.MemberCreateRequestDto;
import backend.core.web.member.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static backend.core.global.error.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberCreateRequestDto dto) {
        Member member = dto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long update(MemberUpdateRequestDto dto) {
        Member member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        member.update(dto.getProfile(), dto.getNickName(), dto.getAddress());
        return member.getId();
    }

    public Member findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        return member;
    }

    public List<Member> findAll(int offset, int limit) {
        List<Member> members = memberRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        return members;
    }

    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        return member;
    }
}
