package backend.core.controller;

import backend.core.domain.Post;
import backend.core.domain.Tag;
import backend.core.dto.response.TagResponseDto;
import backend.core.global.response.ApiResponse;
import backend.core.service.PostService;
import backend.core.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static backend.core.dto.request.TagRequestDto.TagCreateRequestDto;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final PostService postService;

    @GetMapping("/tags")
    public ApiResponse findAll(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Tag> tagList = tagService.findAllOrThrow(offset, limit);

        List<TagResponseDto> result = tagList.stream()
                .map(tag -> new TagResponseDto(tag))
                .collect(Collectors.toList());

        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @GetMapping("/tag/{id}")
    public TagResponseDto findById(
            @PathVariable Long id) {
        Tag tag = tagService.findByIdOrThrow(id);
        return new TagResponseDto(tag);
    }

    @PostMapping("/tag")
    public TagResponseDto save(
            @Valid @RequestBody TagCreateRequestDto dto) {
        Post post = postService.findByIdOrThrow(dto.getPostId());
        dto.setPost(post);

        Long tagId = tagService.save(dto);
        Tag tag = tagService.findByIdOrThrow(tagId);
        return new TagResponseDto(tag);
    }

    // TODO 추가 태그 수정 api
}
