package backend.core.tag;

import backend.core.global.response.ApiResponse;
import backend.core.post.service.PostService;
import backend.core.tag.domain.Tag;
import backend.core.tag.dto.TagCreateRequestDto;
import backend.core.tag.dto.TagDeleteResponseDto;
import backend.core.tag.dto.TagResponseDto;
import backend.core.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final PostService postService;

    @GetMapping("/tags")
    public ApiResponse tagsV1(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "100") int limit) {
        List<Tag> tagList = tagService.findAllOrThrow(offset, limit);

        List<TagResponseDto> result = tagList.stream()
                .map(tag -> new TagResponseDto(tag))
                .collect(Collectors.toList());

        return ApiResponse.builder().count(result.size()).data(result).build();
    }

    @DeleteMapping("/tag/{id}")
    public TagDeleteResponseDto deleteTagV1(
            @PathVariable Long id) {
        return new TagDeleteResponseDto(tagService.delete(id));
    }

    @GetMapping("/tag/{id}")
    public TagResponseDto tagV1(
            @PathVariable Long id) {
        Tag tag = tagService.findByIdOrThrow(id);
        return new TagResponseDto(tag);
    }

    @PostMapping("/tag")
    public TagResponseDto saveTagV1(
            @Valid @RequestBody TagCreateRequestDto dto) {
        return new TagResponseDto(saveTag(dto));
    }

    @PostMapping("/tags")
    public HttpStatus saveTagsV1(
            @Valid @RequestBody List<TagCreateRequestDto> dtoList) {
        dtoList.stream()
                .map(dto -> saveTag(dto))
                .collect(Collectors.toList());
        return HttpStatus.OK;
    }

    private Tag saveTag(TagCreateRequestDto dto) {
        Long tagId = tagService.save(dto);
        Tag tag = tagService.findByIdOrThrow(tagId);
        return tag;
    }

    // TODO 추가 태그 수정 api
}
