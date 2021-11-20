package backend.core.service;

import backend.core.domain.Post;
import backend.core.domain.PostStatus;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.PostRequestDto.*;
import static backend.core.global.error.exception.ErrorCode.POST_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostCreateRequestDto dto) {
        Post post = dto.toEntity();
        postRepository.save(post);
        return post.getId();
    }

    @Transactional
    public Long updateOrThrow(PostUpdateRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        post.update(dto.getTitle(), dto.getDescription(), dto.getMaximum(), dto.getStatus(), dto.getDayOfTheWeek());
        return post.getId();
    }

    public Post findByIdOrThrow(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return post;
    }

    public List<Post> findByStatusOrThrow(PostStatus status) {
        List<Post> posts = postRepository.findByStatus(status)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return posts;
    }

    public List<Post> findAllOrThrow(int offset, int limit) {
        List<Post> posts = postRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        return posts;
    }
}
