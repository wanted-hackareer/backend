package backend.core.tag.service;

import backend.core.domain.Post;
import backend.core.service.PostService;
import backend.core.tag.domain.Tag;
import backend.core.tag.dto.TagCreateRequestDto;
import backend.core.tag.exception.TagNotFoundException;
import backend.core.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final PostService postService;

    @Transactional
    public Long save(TagCreateRequestDto dto) {
        Post post = postService.findByIdOrThrow(dto.getPostId());
        Tag tag = dto.toEntity(post);
        tagRepository.save(tag);

        return tag.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Tag tag = findByIdOrThrow(id);
        tagRepository.delete(tag);

        return tag.getId();
    }

    public Tag findByIdOrThrow(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException());
        return tag;
    }

    public List<Tag> findAllOrThrow(int offset, int limit) {
        List<Tag> tags = tagRepository.findAll(offset, limit)
                .orElseThrow(() -> new TagNotFoundException());
        return tags;
    }
}
