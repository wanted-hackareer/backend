package backend.core.post.service;

import backend.core.member.domain.Member;
import backend.core.member.service.MemberService;
import backend.core.post.domain.Post;
import backend.core.post.domain.PostStatus;
import backend.core.post.dto.PostCreateRequestDto;
import backend.core.post.dto.PostUpdateRequestDto;
import backend.core.post.exception.PostNotFoundException;
import backend.core.post.repository.PostRepository;
import backend.core.post.repository.PostSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Transactional
    public Long save(PostCreateRequestDto dto, Long memberId) {
        Member member = memberService.findByIdOrThrow(memberId);
        Post post = dto.toEntity(member);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long updateOrThrow(PostUpdateRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostNotFoundException());

        post.update(dto.getTitle(), dto.getDescription(), dto.getMaximum(), dto.getStatus(), dto.getDayOfTheWeek());
        return post.getId();
    }

    public Post findByIdOrThrow(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());
        return post;
    }

    public List<Post> findByStatusOrThrow(PostStatus status) {
        List<Post> postList = postRepository.findByStatus(status)
                .orElseThrow(() -> new PostNotFoundException());
        return postList;
    }

    public List<Post> findAllOrThrow(int offset, int limit) {
        List<Post> postList = postRepository.findAll(offset, limit)
                .orElseThrow(() -> new PostNotFoundException());
        return postList;
    }

    public List<Post> findAllBySearchOrThrow(PostSearch postSearch) {
        List<Post> postList = postRepository.findAllBySearch(postSearch)
                .orElseThrow(() -> new PostNotFoundException());
        return postList;
    }
}
