package backend.core.post;

import backend.core.Staff.domain.StaffStatus;
import backend.core.Staff.service.StaffService;
import backend.core.global.response.ApiResponse;
import backend.core.member.exception.MemberNotAcceptableException;
import backend.core.post.domain.Post;
import backend.core.post.domain.PostStatus;
import backend.core.post.dto.PostCreateRequestDto;
import backend.core.post.dto.PostResponseDto;
import backend.core.post.dto.PostUpdateRequestDto;
import backend.core.post.repository.PostSearch;
import backend.core.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static backend.core.Staff.dto.StaffRequestDto.StaffCreateRequestDto;
import static backend.core.Staff.dto.StaffRequestDto.StaffUpdateRequestDto;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StaffService staffService;

    @PostMapping("/post")
    public PostResponseDto save(
            @AuthenticationPrincipal String memberId,
            @Valid @RequestBody PostCreateRequestDto dto) {
        Long id = Long.parseLong(memberId);
        Long postId = postService.save(dto, id);
        Post post = postService.findByIdOrThrow(postId);

        setAuthorToStaff(id, postId);
        return new PostResponseDto(post);
    }

    private void setAuthorToStaff(Long memberId, Long postId) {
        Long staffId = createStaff(memberId, postId);
        updateStaffStatusToAccept(staffId);
    }

    private Long createStaff(Long memberId, Long postId) {
        StaffCreateRequestDto createDto = new StaffCreateRequestDto(postId, memberId);
        Long staffId = staffService.save(createDto);
        return staffId;
    }

    private void updateStaffStatusToAccept(Long staffId) {
        StaffUpdateRequestDto updateDto = new StaffUpdateRequestDto(staffId, StaffStatus.ACCESS);
        staffService.updateOrThrow(updateDto);
    }

    @GetMapping("/posts")
    public ApiResponse postsV1(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Post> postList = postService.findAllOrThrow(offset, limit);

        List<PostResponseDto> result = postList.stream()
                .map(post -> new PostResponseDto(post))
                .collect(Collectors.toList());

        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto postV1(
            @PathVariable Long id) {
        Post post = postService.findByIdOrThrow(id);
        return new PostResponseDto(post);
    }

    @GetMapping("/post/search")
    public ApiResponse postSearchV1(
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "streetA", defaultValue = "") String streetA,
            @RequestParam(name = "streetB", defaultValue = "") String streetB) {
        log.info("title = {}, streetA = {}, districtB = {}", title, streetA, streetB);
        List<Post> postList = postService.findAllBySearchOrThrow(new PostSearch(title, streetA, streetB, PostStatus.ACCESS));

        List<PostResponseDto> result = postList.stream()
                .map(post -> new PostResponseDto(post))
                .collect(Collectors.toList());
        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @PutMapping("/post/{id}")
    public PostResponseDto postV1(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequestDto dto) {
        Post post = postService.findByIdOrThrow(id);
        validateMemberIsAuthor(userId, post);

        Long postId = postService.updateOrThrow(dto);
        return new PostResponseDto(post);
    }

    private void validateMemberIsAuthor(String userId, Post post) {
        if (!post.getMember().getId().equals(userId)) {
            throw new MemberNotAcceptableException();
        }
    }
}
