package backend.core.service;

import backend.core.domain.Member;
import backend.core.dto.request.MemberRequestDto;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.BasketRepository;
import backend.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.MemberRequestDto.MemberPasswordUpdateRequestDto;
import static backend.core.dto.request.MemberRequestDto.MemberSignUpRequestDto;
import static backend.core.global.error.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;

    @Transactional
    public Long save(MemberSignUpRequestDto dto) {
        if (!(isValidEmailOrThrow(dto.getEmail()) && isValidNicknameOrThrow(dto.getNickName()))) {
            // FIXME: 임시로 error 처리 따로 경로 만들어서 클라이언트에서 처리할 예정
        }
        Member member = dto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long updatePasswordOrThrow(MemberPasswordUpdateRequestDto dto, PasswordEncoder encoder) {
        Member member = findByIdOrThrow(dto.getId());
        member.updatePassword(encoder.encode(dto.getPassword()));

        return member.getId();
    }

    @Transactional
    public Long updateOrThrow(MemberRequestDto.MemberUpdateRequestDto dto) {
        Member member = findByIdOrThrow(dto.getId());
        member.update(dto.getProfile(), dto.getNickName(), dto.getAddress());
        return member.getId();
    }

    public Member findByCredentialsOrThrow(String email, String password, PasswordEncoder encoder) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        if (encoder.matches(password, member.getPassword())) {
            return member;
        }

        throw new CustomException(LOGIN_FAILED);
    }

    public Member findByIdOrThrow(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        return member;
    }

    public List<Member> findAllOrThrow(int offset, int limit) {
        List<Member> members = memberRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        return members;
    }

    public Boolean isValidEmailOrThrow(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(e -> {
                    throw new CustomException(EXIST_EMAIL);
                });
        return true;
    }

    public Boolean isValidNicknameOrThrow(String nickName) {
        memberRepository.findByNickName(nickName)
                .ifPresent(e -> {
                    throw new CustomException(EXIST_NICKNAME);
                });
        return true;
    }
}
