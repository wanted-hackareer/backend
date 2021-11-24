package backend.core.controller;

import backend.core.domain.Member;
import backend.core.domain.Post;
import backend.core.domain.Staff;
import backend.core.dto.response.PostResponseDto;
import backend.core.global.error.exception.CustomException;
import backend.core.global.response.ApiResponse;
import backend.core.repository.StaffRepository;
import backend.core.service.MemberService;
import backend.core.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.request.PostRequestDto.PostCreateRequestDto;
import static backend.core.dto.request.PostRequestDto.PostUpdateRequestDto;
import static backend.core.global.error.exception.ErrorCode.MEMBER_NOT_ACCEPTABLE;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final StaffRepository staffRepository;
    private final MemberService memberService;

    @PostMapping("/post")
    public PostResponseDto save(@Valid @RequestBody PostCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        dto.setMember(member);

        Long postId = postService.save(dto);
        Post post = postService.findByIdOrThrow(postId);

        Staff staff = Staff.builder().post(post).member(member).build();
        staffRepository.save(staff);

        return new PostResponseDto(post);
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

    @PutMapping("/post/{id}")
    public PostResponseDto postV1(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequestDto dto) {
        Post post = postService.findByIdOrThrow(id);
        validateMemberIsAuthor(userId, post);

        Long postId = postService.updateOrThrow(dto);
        Post updatedPost = postService.findByIdOrThrow(postId);
        return new PostResponseDto(updatedPost);
    }

    private void validateMemberIsAuthor(String userId, Post post) {
        if (!post.getMember().getId().equals(userId)) {
            throw new CustomException(MEMBER_NOT_ACCEPTABLE);
        }
    }
}
