package backend.core.controller;

import backend.core.dto.response.PostResponseDto;
import backend.core.domain.Member;
import backend.core.domain.Post;
import backend.core.global.error.exception.CustomException;
import backend.core.service.MemberService;
import backend.core.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static backend.core.dto.request.PostRequestDto.*;
import static backend.core.global.error.exception.ErrorCode.MEMBER_NOT_ACCEPTABLE;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    @PostMapping("/post")
    public PostResponseDto save(@RequestBody PostCreateRequestDto dto) {
        Member member = memberService.findByIdOrThrow(dto.getMemberId());
        dto.setMember(member);

        Long postId = postService.save(dto);
        Post post = postService.findById(postId);

        return new PostResponseDto(post);
    }

    @GetMapping("/post/{id}")
    public PostResponseDto postV1(
            @PathVariable Long id) {
        Post post = postService.findById(id);
        return new PostResponseDto(post);
    }

    @PutMapping("/post/{id}")
    public PostResponseDto postV1(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id,
            @RequestBody PostUpdateRequestDto dto) {
        Post post = postService.findById(id);
        validateMemberIsAuthor(userId, post);

        Long postId = postService.update(dto);
        Post updatedPost = postService.findById(postId);
        return new PostResponseDto(updatedPost);
    }

    private void validateMemberIsAuthor(String userId, Post post) {
        if (!post.getMember().getId().equals(userId)) {
            throw new CustomException(MEMBER_NOT_ACCEPTABLE);
        }
    }
}
