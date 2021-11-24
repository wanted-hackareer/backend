package backend.core.controller;

import backend.core.domain.Member;
import backend.core.domain.Post;
import backend.core.domain.Staff;
import backend.core.dto.request.StaffRequestDto;
import backend.core.dto.response.StaffResponseDto;
import backend.core.global.response.ApiResponse;
import backend.core.service.MemberService;
import backend.core.service.PostService;
import backend.core.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController @Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final MemberService memberService;
    private final PostService postService;

    @PostMapping("/staff")
    public StaffResponseDto save(
            @Valid @RequestBody StaffRequestDto.StaffCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        Post post = postService.findByIdOrThrow(dto.getPostId());
        dto.setMemberAndPost(member, post);

        Long staffId = staffService.save(dto);
        Staff staff = staffService.findByIdOrThrow(staffId);
        return new StaffResponseDto(staff);
    }

    @GetMapping("/staff/{id}")
    public StaffResponseDto findById(
            @PathVariable Long id) {
        Staff staff = staffService.findByIdOrThrow(id);
        return new StaffResponseDto(staff);
    }

    @GetMapping("/staffs")
    public ApiResponse findAll(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Staff> staffList = staffService.findAllOrThrow(offset, limit);

        List<StaffResponseDto> result = staffList.stream()
                .map(staff -> new StaffResponseDto(staff))
                .collect(Collectors.toList());

        return ApiResponse.builder().count(result.size()).data(result).build();
    }
}
