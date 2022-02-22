package backend.core.Staff.service;

import backend.core.Staff.domain.Staff;
import backend.core.Staff.domain.StaffStatus;
import backend.core.Staff.exception.StaffNotFoundException;
import backend.core.Staff.repository.StaffRepository;
import backend.core.member.domain.Member;
import backend.core.member.service.MemberService;
import backend.core.post.domain.Post;
import backend.core.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.Staff.dto.StaffRequestDto.StaffCreateRequestDto;
import static backend.core.Staff.dto.StaffRequestDto.StaffUpdateRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StaffService {

    private final StaffRepository staffRepository;
    private final MemberService memberService;
    private final PostService postService;

    @Transactional
    public Long save(StaffCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        Post post = postService.findByIdOrThrow(dto.getPostId());

        Staff staff = dto.toEntity(member, post);
        staffRepository.save(staff);

        return staff.getId();
    }

    @Transactional
    public Long updateOrThrow(StaffUpdateRequestDto dto) {
        Staff staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new StaffNotFoundException());

        staff.update(dto.getStatus());
        return staff.getId();
    }

    public Staff findByIdOrThrow(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException());

        return staff;
    }

    public List<Staff> findAllOrThrow(int offset, int limit) {
        List<Staff> staffs = staffRepository.findAll(offset, limit)
                .orElseThrow(() -> new StaffNotFoundException());

        return staffs;
    }

    public List<Staff> findByStatusOrThrow(StaffStatus status) {
        List<Staff> staffs = staffRepository.findByStatus(status)
                .orElseThrow(() -> new StaffNotFoundException());

        return staffs;
    }
}
