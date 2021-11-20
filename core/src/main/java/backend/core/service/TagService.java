package backend.core.service;

import backend.core.domain.Tag;
import backend.core.global.error.exception.CustomException;
import backend.core.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static backend.core.dto.request.TagRequestDto.TagCreateRequestDto;
import static backend.core.global.error.exception.ErrorCode.TAG_NOT_FOUND;

@Slf4j @Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public Long save(TagCreateRequestDto dto) {
        Tag tag = dto.toEntity();
        tagRepository.save(tag);

        return tag.getId();
    }

    public Tag findByIdOrThrow(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new CustomException(TAG_NOT_FOUND));

        return tag;
    }

    public List<Tag> findAllOrThrow(int offset, int limit) {
        List<Tag> tags = tagRepository.findAll(offset, limit)
                .orElseThrow(() -> new CustomException(TAG_NOT_FOUND));

        return tags;
    }
}
