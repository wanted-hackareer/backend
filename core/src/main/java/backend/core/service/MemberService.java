package backend.core.service;

import backend.core.controller.member.dto.MemberRequestDto;
import backend.core.domain.Member;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.controller.member.dto.MemberRequestDto.*;
import static backend.core.global.error.exception.ErrorCode.*;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSignUpRequestDto dto) {
        if (!(isValidEmail(dto.getEmail()) && isValidNickname(dto.getNickName()))) {
            // 임시로 error 처리 따로 경로 만들어서 클라이언트에서 처리할 예정
        }
        Member member = dto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updatePassword(MemberPasswordUpdateRequestDto dto, PasswordEncoder encoder) {
        Member member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        member.updatePassword(encoder.encode(dto.getPassword()));

        return member.getId();
    }

    @Transactional
    public Long update(MemberRequestDto.MemberUpdateRequestDto dto) {
        Member member = memberRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        member.update(dto.getProfile(), dto.getNickName(), dto.getAddress());
        return member.getId();
    }

    public Member findByCredentials(String email, String password, PasswordEncoder encoder) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        if (encoder.matches(password, member.getPassword())) {
            return member;
        }

        throw new CustomException(LOGIN_FAILED);
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

    public Boolean isValidEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(e -> {
                    throw new CustomException(EXIST_EMAIL);
                });
        return true;
    }

    public Boolean isValidNickname(String nickName) {
        memberRepository.findByNickName(nickName)
                .ifPresent(e -> {
                    throw new CustomException(EXIST_NICKNAME);
                });
        return true;
    }
}
